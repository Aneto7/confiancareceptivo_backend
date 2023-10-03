package com.confianca.services;

import com.confianca.cielo.Merchant;
import com.confianca.cielo.ecommerce.*;
import com.confianca.cielo.ecommerce.request.CieloError;
import com.confianca.cielo.ecommerce.request.CieloRequestException;
import com.confianca.domain.*;
import com.confianca.repositories.RecebimentoRepository;
import com.confianca.services.exeptions.DataIntegrityException;
import com.confianca.services.exeptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.Format;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class RecebimentoService {

    @Value("${cielo.id}")
    private String merchantId;

    @Value("${cielo.key}")
    private String merchantKey;

    @Autowired
    private RecebimentoRepository repo;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private TarifaService tarifaService;

    @Autowired
    private PassageiroService passageiroService;

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private CentroDeCustoService centroDeCustoService;

    @Autowired
    private TipoReceitaService tipoReceitaService;

    @Autowired
    private ReceitaService receitaService;

    @Autowired
    private ProdutoVendidoService produtoVendidoService;

    @Autowired
    private HistoricoService historicoService;

    @Autowired
    private TransferService transferService;

    @Autowired
    private TipoPagamentoRecebimentoService tipoPagamentoRecebimentoService;

    @Autowired
    private RecebimentoFinanceiroService recebimentoFinanceiroService;

    public Recebimento find(Integer id) {
        Optional<Recebimento> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Recebimento.class.getName()));
    }

    public List<Recebimento> findByVenda(Integer id) {
        List<Recebimento> objTeste = repo.findByVendaId(id);
        return objTeste;
    }

    public Recebimento insert(Recebimento obj) {
        obj.setId(null);
        obj.setEmissao(new Date());
        obj = repo.save(obj);
        historicoService.insertAny(obj, "Inserir");
        return obj;
    }

    public Recebimento update(Recebimento obj) throws CieloRequestException, IOException {
        Recebimento newObj = find(obj.getId());
        System.out.println("Imprimindo tipo de recedimento: " + obj.getStatus());
        if (obj.getStatus().equals("Cancelado")) {

            System.out.println("impirmindo quando cancelado");
            cancelarRecebimentoCartao(obj);
        }
        updateData(newObj, obj);
        newObj = repo.save(newObj);
        historicoService.insertAny(obj, "Atualizar");
        return newObj;
    }

    public void cancelarRecebimentoCartao(Recebimento obj) throws CieloRequestException, IOException {

        // E também podemos fazer seu cancelamento, se for o caso
        String valorString = String.format("%.2f", obj.getValor());
        valorString = valorString.replace(",", "");
        int valorInt = Integer.parseInt(valorString);
        String paymentId = obj.getIdCartao();
        Merchant merchant = new Merchant(merchantId, merchantKey);
        if(paymentId == null || paymentId.equalsIgnoreCase("")){

        } else {
            SaleResponse sale = new CieloEcommerce(merchant, Environment.SANDBOX).cancelSale(paymentId, valorInt);
        }
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cartão que possui lançamentos");
        }
    }

    public List<Recebimento> findAll() {
        return repo.findAll();
    }

    public Page<Recebimento> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    private void updateData(Recebimento newObj, Recebimento obj) {
        newObj.setNome(obj.getNome());
        newObj.setTipo(obj.getTipo());
        newObj.setVenda(obj.getVenda());
        newObj.setValor(obj.getValor());
        newObj.setnParcelas(obj.getnParcelas());
        newObj.setParcelado(obj.getParcelado());
        newObj.setQuantidadeParcelas(obj.getQuantidadeParcelas());
        newObj.setRecebimentoData(obj.getRecebimentoData());
        newObj.setVencimento(obj.getVencimento());
    }

    public Venda criandoRecebimentoFaturado(Venda obj) throws ParseException {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dataNasc = null;

        List<CentroDeCusto> centros = centroDeCustoService.findAll();
        List<TipoReceita> tipos = tipoReceitaService.findAll();
        TipoPagamentoRecebimento tipoPR = tipoPagamentoRecebimentoService.findByNome("Faturado");

        if(obj.getCliente().getNascimento() != null){
            dataNasc = formatter.format(obj.getCliente().getNascimento());
        }
        String tipoIdentidade = "CPF";

        if (obj.getCliente().getCnpjOuCpf().length() > 11) tipoIdentidade = "CNPJ";

        Recebimento recebimentoVenda = new Recebimento(null, obj.getRecebimentos().get(0).getTipo(), obj, null, obj.getRecebimentos().get(0).getNome(),
                obj.getValorFinal(), null, obj.getRecebimentos().get(0).getVencimento(), obj.getRecebimentos().get(0).getParcelado(),
                obj.getRecebimentos().get(0).getQuantidadeParcelas(), obj.getRecebimentos().get(0).getnParcelas(), "Faturar", new Date());

        insert(recebimentoVenda);

        //CRIAR UM RECEBIMENTO FINANCEIRO PARA CADA PARCELA QUANDO FOR CARTÃO DE CRÉDITO E FAZER O CÁLCULO COM DESCONTOS
        // E VALOR REAL A SER RECEBIDO, ISSO PODERÁ SER MANIPULADO NO ADMINISTADOR

        int i = 1;

        while (i <= obj.getRecebimentos().get(0).getQuantidadeParcelas()) {
            Double valorParcela = obj.getValorFinal() / obj.getRecebimentos().get(0).getQuantidadeParcelas();
            RecebimentoFinanceiro recebimentoFinanceiro = new RecebimentoFinanceiro(null, centros.get(0), obj.getCliente(),
                    obj.getRecebimentos().get(0).getTipo(), tipos.get(0), obj, recebimentoVenda, null,
                    obj.getRecebimentos().get(0).getNome(), valorParcela, 0.0, 0.0, valorParcela,
                    obj.getRecebimentos().get(0).getVencimento(), obj.getRecebimentos().get(0).getVencimento(), obj.getRecebimentos().get(0).getParcelado(),
                    obj.getRecebimentos().get(0).getQuantidadeParcelas(), i, obj.getRecebimentos().get(0).getStatus(), new Date());
            recebimentoFinanceiroService.insert(recebimentoFinanceiro);
            i++;
        }

        for (ProdutoVendido prodV : obj.getProdutosVendidos()) {
            Servico servicoRecebimento = new Servico();
            List<ProdutoVendido> consultProdV = produtoVendidoService.findServicoCadastrado(prodV);
            if (consultProdV.isEmpty()) {
                List<Servico> servicoRec = servicoService.findByDataInAndProdutoId(prodV.getTarifa().getData().getData(), prodV.getProduto().getId());
                if (!servicoRec.isEmpty()) {
                    servicoRecebimento = servicoRec.get(0);
                } else {
                    servicoRecebimento = null;
                }
            } else {
                servicoRecebimento = consultProdV.get(0).getServico();
            }

            if (servicoRecebimento != null && consultProdV.get(0).getProduto().getIndividual() == false) {
                servicoRecebimento.getProdutosVendidos().add(prodV);

                servicoService.update(servicoRecebimento);

                prodV.setServico(servicoRecebimento);
                produtoVendidoService.update(prodV);
            } else {
                //Criando o identificador do servico
                String identificadorServico = obj.getProdutosVendidos().get(0).getTarifa().getData().getData().toString();
                identificadorServico = "." + identificadorServico.substring(5, 7) + "/" + identificadorServico.substring(2, 4);
                identificadorServico = servicoService.findParteIdentificador(identificadorServico) + identificadorServico;

                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date data = prodV.getTarifa().getData().getData();

                if(prodV.getProduto().getTipoProduto().getNome().equalsIgnoreCase("Transfer")){
                    data = prodV.getDataIda();
                } else {
                    String hora = "";
                    if(prodV.getProduto().getHorarioSaida() == null ||
                            prodV.getProduto().getHorarioSaida().equalsIgnoreCase("")){
                        hora = "00:00";
                        System.out.println("imprimir hora: " + hora);
                    } else {
                        hora = prodV.getProduto().getHorarioSaida();
                        System.out.println("imprimir hora sem null: " + hora);
                    }
                    System.out.println("imprimir hora: " + hora);
                    data = fmt.parse(prodV.getTarifa().getData().getData().toString() + " " + hora);
                }

                List<ProdutoVendido> listProdV = Arrays.asList(prodV);
                servicoRecebimento = new Servico(null, null, prodV.getProduto().getTipoProduto(), obj.getCliente(), obj.getCliente().getNome(), prodV.getNome(),
                        prodV.getTarifa().getData().getData(), data,
                        null, identificadorServico, "Ativo", null, null, null, listProdV, prodV.getProduto(), new Date());

                System.out.println("Imprindo antes de criar o serviço para o produto");

                servicoRecebimento = servicoService.insert(servicoRecebimento);

                prodV.setServico(servicoRecebimento);
                produtoVendidoService.update(prodV);

                if(prodV.getProduto().getTipoProduto().getPlanilha()) {

                    Transfer transfer = new Transfer(null, null, prodV.getProduto().getPontoDeSaida(), prodV.getServico(),
                            prodV.getVenda().getCliente().getNome() + " - " + prodV.getProduto().getNome() + " - " + prodV.getTarifa().getData().getData(),
                            data, null, null, null, prodV.getProduto().getNome(),
                            prodV.getPassageiros().get(0).getNome(), prodV.getVenda().getCliente().getNome(),
                            false, "Ativo", null, new Date());
                    transferService.insert(transfer);
                }
            }

            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date vencimento = new Date();

            //CRIAR ROTINA DE VÁRIAS PARCELAS COMO VÁRIAS RECEITAS, VERIFICAR SE DESTA FORMA ESTÁ CORRETO

            Receita receita = new Receita(null, obj, servicoRecebimento, centros.get(0), tipos.get(0),
                    prodV.getVenda().getCliente(), prodV.getProduto().getTipoProduto(), recebimentoVenda,
                    prodV.getNome(), prodV.getValor(), prodV.getValor(), null, null,
                    obj.getRecebimentos().get(0).getQuantidadeParcelas(),"Faturar", vencimento,
                    null, vencimento, true, null, tipoPR, prodV, new Date());
            receitaService.insertVenda(receita);
        }
        obj.setStatus("Faturar");

        return obj;

    }

    public Double validacaoValor(Venda obj) {
        Double valorTotal = 0.0;
        Double valorPorProduto = 0.0;
        Double valorOpcionais = 0.0;
        Integer adultos = 0;
        Integer crianca = 0;
        Integer bebe = 0;
        Integer idoso = 0;
        for (ProdutoVendido prod : obj.getProdutosVendidos()) {
            Produto novoProdutoVenda = produtoService.find(prod.getId());
            List<Tarifa> buscaTarifa = tarifaService.findByStatusAndProdutoIdAndDataData(prod.getId(), prod.getTarifa().getData().getData());
            prod.setTarifa(buscaTarifa.get(0));
            prod.setProduto(novoProdutoVenda);
            prod.setVenda(obj);

            for (Passageiro passageiro : prod.getPassageiros()) {
                Calendar dateOfBirth = new GregorianCalendar();
                dateOfBirth.setTime(passageiro.getNascimento());
                Calendar today = Calendar.getInstance();
                int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
                if (today.before(dateOfBirth)) {
                    age--;
                }
                if (age < 2) {
                    bebe++;
                } else if (age < 11) {
                    crianca++;
                } else if (age < 60) {
                    adultos++;
                } else if (age >= 60) {
                    idoso++;
                }
                passageiro.setProdutoVendido(prod);
            }

            for (OpcionalVendido opcionalVendido : prod.getOpcionaisVendidos()) {
                valorOpcionais = valorOpcionais + (opcionalVendido.getValor() * opcionalVendido.getQuantidade());
            }

            if (!prod.getProduto().getProdutoAlterarValor()) {
                if (!prod.getProduto().getIndividual() &&
                        prod.getProduto().getTipoProduto().getNome().equalsIgnoreCase("Transfer")) {
                    valorPorProduto = prod.getTarifa().getValor() + valorOpcionais;
                    prod.setValor(valorPorProduto);
                    valorTotal = valorTotal + prod.getTarifa().getValor() + valorOpcionais;
                } else if (prod.getProduto().getIndividual() &&
                        prod.getProduto().getTipoProduto().getNome().equalsIgnoreCase("Transfer")) {
                    valorPorProduto = prod.getTrechoProduto().getValor() + valorOpcionais;
                    prod.setValor(valorPorProduto);
                    valorTotal = valorTotal + prod.getTrechoProduto().getValor() + valorOpcionais;
                } else {
                    valorPorProduto = (prod.getTarifa().getValor() * adultos)
                            + (prod.getTarifa().getValorCrianca() * crianca)
                            + (prod.getTarifa().getValorBebe() * bebe)
                            + (prod.getTarifa().getValorIdoso() * idoso)
                            + valorOpcionais;
                    prod.setValor(valorPorProduto);
                    valorTotal = valorTotal + (prod.getTarifa().getValor() * adultos)
                            + (prod.getTarifa().getValorCrianca() * crianca)
                            + (prod.getTarifa().getValorBebe() * bebe)
                            + (prod.getTarifa().getValorIdoso() * idoso)
                            + valorOpcionais;
                }
            }
        }
        return valorTotal;
    }

    public Venda criandoRecebimentoCartao(Venda obj) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dataNasc = formatter.format(obj.getCliente().getNascimento());
        String tipoIdentidade = "CPF";

        List<CentroDeCusto> centros = centroDeCustoService.findAll();
        List<TipoReceita> tipos = tipoReceitaService.findAll();
        TipoPagamentoRecebimento tipoPR = tipoPagamentoRecebimentoService.findByNome("Cartão de Crédito");

        if (obj.getCliente().getCnpjOuCpf().length() > 11) tipoIdentidade = "CNPJ";

        String valorString = String.format("%.2f", obj.getValorFinal());
        valorString = valorString.replace(",", "");
        int valorInt = Integer.parseInt(valorString);

        Cidade cidadeBase = cidadeService.findByNome(obj.getCliente().getCidade().getNome());
        String siglaCidade = "BRA";
        if (cidadeBase != null)
            siglaCidade = cidadeBase.getEstado().getPais().getSigla();
        String validade = obj.getCartao().getExpirationDate();
        validade = validade.substring(0, 2) + "/" + validade.substring(2, 6);
        obj.getCartao().setExpirationDate(validade);

        Merchant merchant = new Merchant(merchantId, merchantKey);
        Sale sale = new Sale(deAccent(obj.getNome()));
        Customer customer = sale.customer(deAccent(obj.getCliente().getNome()), obj.getCliente().getEmail(), dataNasc, obj.getCliente().getCnpjOuCpf(), tipoIdentidade);

        // Crie uma instância de Payment informando o valor do pagamento
        Payment payment = sale.payment(valorInt, 3, Payment.Currency.BRL, siglaCidade,
                deAccent("Nome do Cliente: " + obj.getCliente().getNome() + " - Nome da Venda: " + obj.getNome()));

        // Crie  uma instância de Credit Card utilizando os dados de teste
        // esses dados estão disponíveis no manual de integração
        payment.creditCard(obj.getCartao().getSecurityCode(), obj.getCartao().getBrand())
                .setExpirationDate(obj.getCartao().getExpirationDate())
                .setCardNumber(obj.getCartao().getCardNumber())
                .setHolder(obj.getCartao().getHolder());

        Recebimento recebimentoVenda = new Recebimento(null, obj.getRecebimentos().get(0).getTipo(), obj,
                null, obj.getRecebimentos().get(0).getNome(), obj.getValorFinal(), null,
                obj.getRecebimentos().get(0).getVencimento(), obj.getRecebimentos().get(0).getParcelado(),
                obj.getRecebimentos().get(0).getQuantidadeParcelas(), obj.getRecebimentos().get(0).getnParcelas(),
                obj.getRecebimentos().get(0).getStatus(), new Date());

        // Crie o pagamento na Cielo
        try {
            // Configure o SDK com seu merchant e o ambiente apropriado para criar a venda
            sale = new CieloEcommerce(merchant, Environment.SANDBOX).createSale(sale);

            System.out.println("Imprimindo dados venda:");

            // Com a venda criada na Cielo, já temos o ID do pagamento, TID e demais
            // dados retornados pela Cielo
            String paymentId = sale.getPayment().getPaymentId();
            String returnMessage = sale.getPayment().getReturnMessage();
            String returnCode = sale.getPayment().getReturnCode();

            // Com o ID do pagamento, podemos fazer sua captura, se ela não tiver sido capturada ainda
            //SaleResponse saleResposta = new CieloEcommerce(merchant, Environment.SANDBOX).captureSale(paymentId, valorInt, 0);

            // E também podemos fazer seu cancelamento, se for o caso
            //sale = new CieloEcommerce(merchant, Environment.SANDBOX).cancelSale(paymentId, 15700);

            recebimentoVenda.setStatus(cartaoService.statusRecebimento(returnCode));
            recebimentoVenda.setIdCartao(paymentId);
            insert(recebimentoVenda);
            obj.setStatus(cartaoService.statusRecebimento(returnCode));

            //CRIAR UM RECEBIMENTO FINANCEIRO PARA CADA PARCELA QUANDO FOR CARTÃO DE CRÉDITO E FAZER O CÁLCULO COM DESCONTOS
            // E VALOR REAL A SER RECEBIDO, ISSO PODERÁ SER MANIPULADO NO ADMINISTADOR

            int i = 1;

            while (i <= obj.getRecebimentos().get(0).getQuantidadeParcelas()) {
                Double valorParcela = obj.getValorFinal() / obj.getRecebimentos().get(0).getQuantidadeParcelas();

                RecebimentoFinanceiro recebimentoFinanceiro = new RecebimentoFinanceiro(null, centros.get(0),
                        obj.getCliente(), obj.getRecebimentos().get(0).getTipo(), tipos.get(0), obj,
                        recebimentoVenda, null, obj.getRecebimentos().get(0).getNome(), valorParcela, 0.0, 0.0,
                        valorParcela, obj.getRecebimentos().get(0).getVencimento(),
                        obj.getRecebimentos().get(0).getVencimento(), obj.getRecebimentos().get(0).getParcelado(),
                        obj.getRecebimentos().get(0).getQuantidadeParcelas(), i,
                        obj.getRecebimentos().get(0).getStatus(), new Date());
                recebimentoFinanceiroService.insert(recebimentoFinanceiro);
                i++;
            }

            for (ProdutoVendido prodV : obj.getProdutosVendidos()) {
                Servico servicoRecebimento = new Servico();

                //ALTERAR buscar informações sobre produto vinculado ao serviçoe não produtos vendidos no serviço
                List<ProdutoVendido> consultProdV = produtoVendidoService.findServicoCadastrado(prodV);

                if (consultProdV.isEmpty()) {
                    List<Servico> servicoRec = servicoService.findByDataInAndProdutoId(prodV.getTarifa().getData().getData(), prodV.getProduto().getId());
                    if (!servicoRec.isEmpty()) {
                        servicoRecebimento = servicoRec.get(0);
                    } else {
                        servicoRecebimento = null;
                    }
                } else {
                    servicoRecebimento = consultProdV.get(0).getServico();
                }

                if (servicoRecebimento != null && consultProdV.get(0).getProduto().getIndividual() == false) {
                    servicoRecebimento.getProdutosVendidos().add(prodV);

                    servicoService.update(servicoRecebimento);

                    prodV.setServico(servicoRecebimento);
                    produtoVendidoService.update(prodV);
                } else {

                    //Criando o identificador do servico
                    String identificadorServico = obj.getProdutosVendidos().get(0).getTarifa().getData().getData().toString();
                    identificadorServico = "." + identificadorServico.substring(5, 7) + "/" + identificadorServico.substring(2, 4);
                    identificadorServico = servicoService.findParteIdentificador(identificadorServico) + identificadorServico;

                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Date data = prodV.getTarifa().getData().getData();

                    if(prodV.getProduto().getTipoProduto().getNome().equalsIgnoreCase("Transfer")){
                        data = prodV.getDataIda();
                    } else {
                        data = fmt.parse(prodV.getTarifa().getData().getData().toString() + " " + prodV.getProduto().getHorarioSaida());
                    }


                    List<ProdutoVendido> listProdV = Arrays.asList(prodV);
                    servicoRecebimento = new Servico(null, null, prodV.getProduto().getTipoProduto(), obj.getCliente(), obj.getCliente().getNome(), prodV.getNome(),
                            prodV.getTarifa().getData().getData(), data,
                            null, identificadorServico, "Ativo", null, null, null,
                            listProdV, prodV.getProduto(), new Date());

                    servicoRecebimento = servicoService.insert(servicoRecebimento);

                    prodV.setServico(servicoRecebimento);
                    produtoVendidoService.update(prodV);

                    if(prodV.getProduto().getTipoProduto().getPlanilha()) {

                        Transfer transfer = new Transfer(null, null, prodV.getProduto().getPontoDeSaida(), prodV.getServico(),
                                prodV.getVenda().getCliente().getNome() + " - " + prodV.getProduto().getNome() + " - " + prodV.getTarifa().getData().getData(),
                                data, null, null, null, prodV.getProduto().getNome(),
                                prodV.getPassageiros().get(0).getNome(), prodV.getVenda().getCliente().getNome(),
                                false, "Ativo", null, new Date());
                        transferService.insert(transfer);
                    }
                }

                SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date vencimento = form.parse(sale.getPayment().getReceivedDate());
                Date dataPagamento = form.parse(sale.getPayment().getReceivedDate());

                Receita receita = new Receita(null, obj, servicoRecebimento, centros.get(0), tipos.get(0),
                        prodV.getVenda().getCliente(), prodV.getProduto().getTipoProduto(), recebimentoVenda,
                        prodV.getNome(), prodV.getValor(), prodV.getValor(), null, null,
                        obj.getRecebimentos().get(0).getQuantidadeParcelas(),"Realizado", vencimento,
                        null, null, false, null, tipoPR, prodV, new Date());

                receitaService.insertVenda(receita);
            }
            return obj;
        } catch (CieloRequestException e) {
            // Em caso de erros de integração, podemos tratar o erro aqui.
            // os códigos de erro estão todos disponíveis no manual de integração.
            CieloError error = e.getError();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
