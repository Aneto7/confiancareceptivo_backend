package com.confianca.services;

import com.confianca.domain.*;
import com.confianca.domain.enums.Perfil;
import com.confianca.repositories.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;

@Service
public class DBservice {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private BandeiraDeCartaoDeCreditoRepository bandeiraRepository;

    @Autowired
    private CentroDeCustoRepository centroRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private TipoClienteRepository tipoClienteRepository;

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private CupomRepository cupomRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CondicaoPagamentoRecebimentoRepository condicaoPagamentoRecebimentoRepository;

    @Autowired
    private TipoPagamentoRecebimentoRepository tipoPagamentoRecebimentoRepository;

    @Autowired
    private DataTarifaRepository dataTarifaRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private GuiaRepository guiaRepository;

    @Autowired
    private TipoDespesaRepository tipoDespesaRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private PontoDeSaidaRepository pontoDeSaidaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private DetalheProdutoRepository detalheProdutoRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private HistoricoRepository historicoRepository;

    @Autowired
    private ImagemRepository imagemRepository;

    @Autowired
    private MetaRepository metaRepository;

    @Autowired
    private PassageiroRepository passageiroRepository;

    @Autowired
    private RecebimentoRepository recebimentoRepository;

    @Autowired
    private TipoReceitaRepository tipoReceitaRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private DiaDoAnoRepository diaDoAnoRepository;

    @Autowired
    private ProdutoVendidoRepository produtoVendidoRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private TipoProdutoRepository tipoProdutoRepository;

    @Autowired
    private OpcionalRepository opcionalRepository;

    @Autowired
    private TarifarioRepository tarifarioRepository;

    @Autowired
    private TarifarioDadoRepository tarifarioDadoRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TipoFornecedorRepository tipoFornecedorRepository;

    @Autowired
    private PagadorRepository pagadorRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public void instantiateTestDatabase() throws ParseException {

        Boolean test = false;

        if (test) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


            BandeiraDeCartaoDeCredito bandeira1 = new BandeiraDeCartaoDeCredito(null, "Visa", "Visa", "Ativo");
            BandeiraDeCartaoDeCredito bandeira2 = new BandeiraDeCartaoDeCredito(null, "MasterCard", "Master", "Ativo");
            BandeiraDeCartaoDeCredito bandeira3 = new BandeiraDeCartaoDeCredito(null, "Elo", "Elo", "Ativo");
            BandeiraDeCartaoDeCredito bandeira4 = new BandeiraDeCartaoDeCredito(null, "American Express", "Amex", "Ativo");
            BandeiraDeCartaoDeCredito bandeira5 = new BandeiraDeCartaoDeCredito(null, "HiperCard", "Hiper", "Ativo");
            BandeiraDeCartaoDeCredito bandeira6 = new BandeiraDeCartaoDeCredito(null, "Discover Network", "Disco", "Ativo");
            BandeiraDeCartaoDeCredito bandeira7 = new BandeiraDeCartaoDeCredito(null, "Diners Club", "Diners", "Ativo");

            bandeiraRepository.saveAll(Arrays.asList(bandeira1, bandeira2, bandeira3, bandeira4, bandeira5, bandeira6, bandeira7));

            CentroDeCusto centro1 = new CentroDeCusto(null, "Comercial", "Ativo", "Alert Bar. The Biznus template is equipped with " +
                    "an alert bar that’s controlled via the CMS. Just add a new collection item and you’ll have an announcement bar at the top of every page." +
                    " Once you’re finished, just delete the item and the bar disappears. Contact Form and Page The template comes with a contact form and a " +
                    "place to add a link to whatever help desk system you use so that it’s easy for customers to reach out if they need help with an order." +
                    "Donations Page. This template uses products without shipping to provide preset amounts for people to be able to donate to your business.");
            CentroDeCusto centro2 = new CentroDeCusto(null, "Financeiro", "Ativo", "Alert Bar. The Biznus template is equipped with " +
                    "an alert bar that’s controlled via the CMS. Just add a new collection item and you’ll have an announcement bar at the top of every page." +
                    " Once you’re finished, just delete the item and the bar disappears. Contact Form and Page The template comes with a contact form and a " +
                    "place to add a link to whatever help desk system you use so that it’s easy for customers to reach out if they need help with an order." +
                    "Donations Page. This template uses products without shipping to provide preset amounts for people to be able to donate to your business.");
            CentroDeCusto centro3 = new CentroDeCusto(null, "Jurídico", "Ativo", "Alert Bar. The Biznus template is equipped with " +
                    "an alert bar that’s controlled via the CMS. Just add a new collection item and you’ll have an announcement bar at the top of every page." +
                    " Once you’re finished, just delete the item and the bar disappears. Contact Form and Page The template comes with a contact form and a " +
                    "place to add a link to whatever help desk system you use so that it’s easy for customers to reach out if they need help with an order." +
                    "Donations Page. This template uses products without shipping to provide preset amounts for people to be able to donate to your business.");
            CentroDeCusto centro4 = new CentroDeCusto(null, "Marketing", "Ativo", "Alert Bar. The Biznus template is equipped with " +
                    "an alert bar that’s controlled via the CMS. Just add a new collection item and you’ll have an announcement bar at the top of every page." +
                    " Once you’re finished, just delete the item and the bar disappears. Contact Form and Page The template comes with a contact form and a " +
                    "place to add a link to whatever help desk system you use so that it’s easy for customers to reach out if they need help with an order." +
                    "Donations Page. This template uses products without shipping to provide preset amounts for people to be able to donate to your business.");

            centroRepository.saveAll(Arrays.asList(centro1, centro2, centro3, centro4));

            Pais pais1 = new Pais(null, "Brasil", "BRA");
//        Pais pais2 = new Pais(null, "Argentina", "ARG");
//        Pais pais3 = new Pais(null, "Chile", "CHL");
//        Pais pais4 = new Pais(null, "Paraguai", "PRY");
//        Pais pais5 = new Pais(null, "Colômbia", "COL");
//        Pais pais6 = new Pais(null, "Uruguai", "URY");

            Estado estado1 = new Estado(null, pais1, "Acre", "AC");
            Estado estado2 = new Estado(null, pais1, "Alagoas", "AL");
            Estado estado3 = new Estado(null, pais1, "Amapá", "AP");
            Estado estado4 = new Estado(null, pais1, "Amazonas", "AM");
            Estado estado5 = new Estado(null, pais1, "Bahia", "BA");
            Estado estado6 = new Estado(null, pais1, "Ceara", "CE");
            Estado estado7 = new Estado(null, pais1, "Distrito Federal", "DF");
            Estado estado8 = new Estado(null, pais1, "Espírito Santo", "ES");
            Estado estado9 = new Estado(null, pais1, "Goiás", "GO");
            Estado estado10 = new Estado(null, pais1, "Maranhão", "MA");
            Estado estado11 = new Estado(null, pais1, "Mato Grosso", "MT");
            Estado estado12 = new Estado(null, pais1, "Mato Grosso so Sul", "MS");
            Estado estado13 = new Estado(null, pais1, "Minas Gerais", "MG");
            Estado estado14 = new Estado(null, pais1, "Pará", "PA");
            Estado estado15 = new Estado(null, pais1, "Paraíba", "PB");
            Estado estado16 = new Estado(null, pais1, "Paraná", "PR");
            Estado estado17 = new Estado(null, pais1, "Pernambuco", "PE");
            Estado estado18 = new Estado(null, pais1, "Piauí", "PI");
            Estado estado19 = new Estado(null, pais1, "Rio de Janeiro", "RJ");
            Estado estado20 = new Estado(null, pais1, "Rio Grande do Norte", "RN");
            Estado estado21 = new Estado(null, pais1, "Rio Grande do Sul", "RS");
            Estado estado22 = new Estado(null, pais1, "Rondônia", "RO");
            Estado estado23 = new Estado(null, pais1, "Roraima", "RR");
            Estado estado24 = new Estado(null, pais1, "Santa Catarina", "SC");
            Estado estado25 = new Estado(null, pais1, "São Paulo", "SP");
            Estado estado26 = new Estado(null, pais1, "Sergipe", "SE");
            Estado estado27 = new Estado(null, pais1, "Tocantis", "TO");

            Cidade cidade1 = new Cidade(null, estado1, "Rio Branco");
            Cidade cidade2 = new Cidade(null, estado2, "Maceió");
            Cidade cidade3 = new Cidade(null, estado3, "Macapá");
            Cidade cidade4 = new Cidade(null, estado4, "Manaus");
            Cidade cidade5 = new Cidade(null, estado5, "Salvador");
            Cidade cidade6 = new Cidade(null, estado6, "Fortaleza");
            Cidade cidade7 = new Cidade(null, estado7, "Brasília");
            Cidade cidade8 = new Cidade(null, estado8, "Vitória");
            Cidade cidade9 = new Cidade(null, estado9, "Goiânia");
            Cidade cidade10 = new Cidade(null, estado10, "São Luiz");
            Cidade cidade11 = new Cidade(null, estado11, "Cuiabá");
            Cidade cidade12 = new Cidade(null, estado12, "Campo Grande");
            Cidade cidade13 = new Cidade(null, estado13, "Belo Horizonte");
            Cidade cidade14 = new Cidade(null, estado14, "Belém");
            Cidade cidade15 = new Cidade(null, estado15, "João Pessoa");
            Cidade cidade16 = new Cidade(null, estado16, "Curitiba");
            Cidade cidade17 = new Cidade(null, estado17, "Recife");
            Cidade cidade18 = new Cidade(null, estado18, "Terezina");
            Cidade cidade19 = new Cidade(null, estado19, "Rio de Janeiro");
            Cidade cidade20 = new Cidade(null, estado20, "Natal");
            Cidade cidade21 = new Cidade(null, estado21, "Porto Alegre");
            Cidade cidade22 = new Cidade(null, estado22, "Porto Velho");
            Cidade cidade23 = new Cidade(null, estado23, "Boa Vista");
            Cidade cidade24 = new Cidade(null, estado24, "Florianópolis");
            Cidade cidade25 = new Cidade(null, estado25, "São Paulo");
            Cidade cidade26 = new Cidade(null, estado26, "Aracajú");
            Cidade cidade27 = new Cidade(null, estado27, "Palmas");

//        paisRepository.saveAll(Arrays.asList(pais1, pais2, pais3, pais4, pais5, pais6));
            paisRepository.saveAll(Arrays.asList(pais1));

            estadoRepository.saveAll(Arrays.asList(estado1, estado2, estado3, estado4, estado5, estado6, estado7, estado8, estado9,
                    estado10, estado11, estado12, estado13, estado14, estado15, estado16, estado17, estado18, estado19,
                    estado20, estado21, estado22, estado23, estado24, estado25, estado26, estado27));

            cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3, cidade4, cidade5, cidade6, cidade7, cidade8, cidade9,
                    cidade10, cidade11, cidade12, cidade13, cidade14, cidade15, cidade16, cidade17, cidade18, cidade19,
                    cidade20, cidade21, cidade22, cidade23, cidade24, cidade25, cidade26, cidade27));

            TipoCliente tipoCliente1 = new TipoCliente(null, "Matriz", 0.00, "Ativo");
            TipoCliente tipoCliente2 = new TipoCliente(null, "Agência", 0.00, "Ativo");
            TipoCliente tipoCliente3 = new TipoCliente(null, "Interno", 0.00, "Ativo");
            TipoCliente tipoCliente4 = new TipoCliente(null, "Operadora", 0.30, "Ativo");
            TipoCliente tipoCliente5 = new TipoCliente(null, "Pessoa Física", 0.00, "Ativo");

            tipoClienteRepository.saveAll(Arrays.asList(tipoCliente1, tipoCliente2, tipoCliente3, tipoCliente4, tipoCliente5));

            Unidade unidade1 = new Unidade(null, cidade11, "Matriz", "Confiança Agência de Passagens e Turismo", "6533142700",
                    "Av. São Sebastião, 2852 - Quilombo, Cuiabá - MT", "78045305", "03488137000125", "Responsável", "responsavel@confianca.com",
                    pe.encode("123"), "Ativo", "Observação", "65999999999");

            unidadeRepository.saveAll(Arrays.asList(unidade1));

            Cupom cupom1 = new Cupom(null, "Teste Cupom1", 0.10, "Ativo", "Obs teste1");
            Cupom cupom2 = new Cupom(null, "Teste Cupom2", 0.12, "Ativo", "Obs teste2");
            Cupom cupom3 = new Cupom(null, "Teste Cupom3", 0.05, "Ativo", "Obs teste3");
            Cupom cupom4 = new Cupom(null, "Teste Cupom4", 0.03, "Ativo", "Obs teste4");
            Cupom cupom5 = new Cupom(null, "Teste Cupom5", 0.15, "Ativo", "Obs teste5");

            cupomRepository.saveAll(Arrays.asList(cupom1, cupom2, cupom3, cupom4, cupom5));

            Cliente cliente1 = new Cliente(null, tipoCliente1, cidade11, unidade1, null, "Matriz",
                    "03488137000125", "joseeduardo@confiancaturismo.com.br", sdf.parse("30/09/2017"), "6533142700",
                    "65999999999", "78045305", "Quilombo", "Av. São Sebastião, 2852", "A", "Ativo", "Teste");

            clienteRepository.saveAll(Arrays.asList(cliente1));

            TipoPagamentoRecebimento tipoPagamentoRecebimento1 = new TipoPagamentoRecebimento(null, "Cartão de Crédito", "Ativo");
            TipoPagamentoRecebimento tipoPagamentoRecebimento2 = new TipoPagamentoRecebimento(null, "Cartão de Débito", "Ativo");
            TipoPagamentoRecebimento tipoPagamentoRecebimento3 = new TipoPagamentoRecebimento(null, "Pix", "Ativo");
            TipoPagamentoRecebimento tipoPagamentoRecebimento4 = new TipoPagamentoRecebimento(null, "Depósito", "Ativo");
            TipoPagamentoRecebimento tipoPagamentoRecebimento5 = new TipoPagamentoRecebimento(null, "Transferência", "Ativo");
            TipoPagamentoRecebimento tipoPagamentoRecebimento6 = new TipoPagamentoRecebimento(null, "Boleto", "Ativo");
            TipoPagamentoRecebimento tipoPagamentoRecebimento7 = new TipoPagamentoRecebimento(null, "Faturado", "Ativo");

            tipoPagamentoRecebimentoRepository.saveAll(Arrays.asList(tipoPagamentoRecebimento1,
                    tipoPagamentoRecebimento2, tipoPagamentoRecebimento3, tipoPagamentoRecebimento4,
                    tipoPagamentoRecebimento5, tipoPagamentoRecebimento6, tipoPagamentoRecebimento7));

            CondicaoPagamentoRecebimento condicaoPR1 = new CondicaoPagamentoRecebimento(null, bandeira1, tipoPagamentoRecebimento1, 10, 1000.0,
                    100.0, 0.1, "Parcelamento", "Ativo");
            CondicaoPagamentoRecebimento condicaoPR2 = new CondicaoPagamentoRecebimento(null, bandeira2, tipoPagamentoRecebimento2, 20, 2000.0,
                    200.0, 0.2, "Parcelamento2", "Ativo");
            CondicaoPagamentoRecebimento condicaoPR3 = new CondicaoPagamentoRecebimento(null, null, tipoPagamentoRecebimento2, 20, 2000.0,
                    200.0, 0.2, "Parcelamento3", "Ativo");

            condicaoPagamentoRecebimentoRepository.saveAll(Arrays.asList(condicaoPR1, condicaoPR2, condicaoPR3));

            Servico servicoProduto1 = new Servico(null, null, null, null, "Teste",
                    "Teste", sdf.parse("30/10/2022"), sdf.parse("30/10/2022"),null,
                    "identificadorServico", "Ativo", null, null, new Date());
            Servico servicoProduto2 = new Servico(null, null, null, null, "Teste",
                    "Teste", sdf.parse("30/10/2022"), sdf.parse("30/10/2022"),null,
                    "identificadorServico", "Ativo", null, null, new Date());
            Servico servicoProduto3 = new Servico(null, null, null, null, "Teste",
                    "Teste", sdf.parse("30/10/2022"), sdf.parse("30/10/2022"),null,
                    "identificadorServico", "Ativo", null, null, new Date());
            Servico servicoProduto4 = new Servico(null, null, null, null, "Teste",
                    "Teste", sdf.parse("30/10/2022"), sdf.parse("30/10/2022"),null,
                    "identificadorServico", "Ativo", null, null, new Date());

            servicoRepository.saveAll(Arrays.asList(servicoProduto1, servicoProduto2, servicoProduto3, servicoProduto4));

            Guia guia1 = new Guia(null, "José Guia", "6598749874", "6598759875", "joseguia@teste.com.br", "Ativo", "Ativo", "12312312312");
            Guia guia2 = new Guia(null, "Maria Guia", "6598749874", "6598759875", "mariaguia@teste.com.br", "Ativo", "Ativo", "45645645645");
            Guia guia3 = new Guia(null, "Marcio Guia", "6598749874", "6598759875", "marcioguia@teste.com.br", "Ativo", "Ativo", "78978978978");
            Guia guia4 = new Guia(null, "Ana Guia", "6598749874", "6598759875", "anaguia@teste.com.br", "Ativo", "Ativo", "98765432119");

            guiaRepository.saveAll(Arrays.asList(guia1, guia2, guia3, guia4));

            TipoDespesa tipoDespesa1 = new TipoDespesa(null, "Despesa Fixa", "Ativo");
            TipoDespesa tipoDespesa2 = new TipoDespesa(null, "Despesa Variável", "Ativo");
            TipoDespesa tipoDespesa3 = new TipoDespesa(null, "Juros", "Ativo");

            tipoDespesaRepository.saveAll(Arrays.asList(tipoDespesa1, tipoDespesa2, tipoDespesa3));

            TipoFornecedor tipoFornecedor1 = new TipoFornecedor(null, "Prestador de Serviço", "Ativo");

            tipoFornecedorRepository.saveAll(Arrays.asList(tipoFornecedor1));

            Fornecedor fornecedor1 = new Fornecedor(null, tipoFornecedor1, cidade1, tipoDespesa1, "Fornecedor Teste1",
                    "Razao Social Fornecedor Teste1", "32321321000145", "321321", "Responsável F1",
                    "65987987987", "65987987987", "f1teste@teste.com.br", "78000000",
                    "Centro", "Rua C, 123", "Ativo", "Obs Teste F1");
            Fornecedor fornecedor2 = new Fornecedor(null, tipoFornecedor1, cidade11, tipoDespesa2, "Fornecedor Teste2",
                    "Razao Social Fornecedor Teste2", "32321321000133", "321321", "Responsável F2",
                    "65987987987", "65987987987", "f2teste@teste.com.br", "78000000",
                    "Centro", "Rua C, 123", "Ativo", "Obs Teste F2");
            Fornecedor fornecedor3 = new Fornecedor(null, tipoFornecedor1, cidade10, tipoDespesa3, "Fornecedor Teste3",
                    "Razao Social Fornecedor Teste3", "32321321000111", "321321", "Responsável F3",
                    "65987987987", "65987987987", "f3teste@teste.com.br", "78000000",
                    "Centro", "Rua C, 123", "Ativo", "Obs Teste F3");

            fornecedorRepository.saveAll(Arrays.asList(fornecedor1, fornecedor2, fornecedor3));

            Despesa despesa1 = new Despesa(null, servicoProduto1, guia1, centro1, tipoDespesa1,
                    fornecedor1, "Despesa 1", 1000.0, "Ativo", sdf.parse("30/10/2022"),
                    sdf.parse("25/10/2022"), 1,sdf.parse("25/10/2022"), true, "Obs Despesa 1",
                    tipoPagamentoRecebimento1, new Date());
            Despesa despesa2 = new Despesa(null, servicoProduto2, guia2, centro2, tipoDespesa2,
                    fornecedor2, "Despesa 2", 1000.0, "Ativo", sdf.parse("30/10/2022"),
                    sdf.parse("25/10/2022"), 1, sdf.parse("25/10/2022"), true, "Obs Despesa 2",
                    tipoPagamentoRecebimento1, new Date());
            Despesa despesa3 = new Despesa(null, servicoProduto3, null, centro3, tipoDespesa3,
                    null, "Despesa 3", 1000.0, "Ativo", sdf.parse("30/10/2022"),
                    sdf.parse("25/10/2022"), 1, sdf.parse("25/10/2022"), true, "Obs Despesa 3",
                    tipoPagamentoRecebimento1, new Date());

            despesaRepository.saveAll(Arrays.asList(despesa1, despesa2, despesa3));

            Pagamento pagamento1 = new Pagamento(null, centro1, fornecedor1, tipoPagamentoRecebimento1,
                    tipoDespesa1, null, despesa1, null, "Teste pagamento", 200.00,
                    0.00, 0.00, 200.00,
                    sdf.parse("25/04/2023"), null, "Não",1,1,
                    "Ativo", new Date());
            Pagamento pagamento2 = new Pagamento(null, centro1, fornecedor1, tipoPagamentoRecebimento1,
                    tipoDespesa1, null, despesa2, null, "Teste pagamento", 200.00,
                    0.00, 0.00, 200.00,
                    sdf.parse("25/04/2023"), null, "Não",1,1,
                    "Ativo", new Date());

            pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

            PontoDeSaida pontoDeSaida1 = new PontoDeSaida(null, cidade11, "teste", "Centro", "Ativo");
            PontoDeSaida pontoDeSaida2 = new PontoDeSaida(null, cidade11, "teste1", "Agência", "Ativo");
            PontoDeSaida pontoDeSaida3 = new PontoDeSaida(null, cidade11, "teste2", "Chapada", "Ativo");

            pontoDeSaidaRepository.saveAll(Arrays.asList(pontoDeSaida1, pontoDeSaida2, pontoDeSaida3));

            TipoProduto tp1 = new TipoProduto(null, "Tour Regular", "Ativo", true);
            TipoProduto tp2 = new TipoProduto(null, "Transfer", "Ativo", false);
            TipoProduto tp3 = new TipoProduto(null, "Locação de Veículo", "Ativo", false);
            TipoProduto tp4 = new TipoProduto(null, "Evento", "Ativo", true);

            tipoProdutoRepository.saveAll(Arrays.asList(tp1, tp2, tp3, tp4));

            Opcional opcional1 = new Opcional(null, "Cadeirinha", "Ativo", 100.00, 2);
            Opcional opcional2 = new Opcional(null, "Seguro", "Ativo", 50.00, 1);

            opcionalRepository.saveAll(Arrays.asList(opcional1, opcional2));

            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");

            Produto produto1 = new Produto(null, null, cidade1, pontoDeSaida1, null, tp1, "Pantanal Norte",
                    "Anotações produto", "12:00","10:00", "Local Saída Teste", 300.0, 15, "Mensal",
                    "Ativo", "Observação produto", false, false, Arrays.asList("Ativo", "Teste"));
            Produto produto2 = new Produto(null, fornecedor1, cidade10, pontoDeSaida1, cupom2, tp2, "Chapada dos Guimarães",
                    "Anotações produto", "11:00","12:00", "Local Saída Teste", 270.0, 15, "Mensal",
                    "Ativo", "Observação produto",false,false, Arrays.asList("Ativo", "Teste"));
            Produto produto3 = new Produto(null, null, cidade20, pontoDeSaida3, null, tp3, "Nobres",
                    "Anotações produto", "09:00","12:00", "Local Saída Teste", 1750.0, 15, "Mensal",
                    "Ativo", "Observação produto",false,false, Arrays.asList("Ativo", "Teste"));
            Produto produto4 = new Produto(null, null, cidade20, pontoDeSaida3, null, tp3, "Malai Manso Resort",
                    "Anotações produto", "09:00", "12:00","Local Saída Teste", 125.0, 15, "Mensal",
                    "Ativo", "Observação produto",false,false, Arrays.asList("Ativo", "Teste"));

            produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3, produto4));

            produto1.setOpcionais(Arrays.asList(opcional1, opcional2));

            produtoRepository.saveAll(Arrays.asList(produto1));

            DetalheProduto detalheProduto1 = new DetalheProduto(null, produto1, "Detalhes do Produto",
                    "Produto informado como destalhes de informações que serão apresentadas",
                    "Ativo");
            DetalheProduto detalheProduto2 = new DetalheProduto(null, produto1, "Informações de Candelamento",
                    "Produto informado como destalhes de informações que serão apresentadas",
                    "Ativo");
            DetalheProduto detalheProduto3 = new DetalheProduto(null, produto1, "Informações sobre partida",
                    "Produto informado como destalhes de informações que serão apresentadas",
                    "Ativo");
            DetalheProduto detalheProduto4 = new DetalheProduto(null, produto2, "Detalhes do Produto",
                    "Produto informado como destalhes de informações que serão apresentadas",
                    "Ativo");
            DetalheProduto detalheProduto5 = new DetalheProduto(null, produto2, "Informações de Candelamento",
                    "Produto informado como destalhes de informações que serão apresentadas",
                    "Ativo");
            DetalheProduto detalheProduto6 = new DetalheProduto(null, produto2, "Informações sobre partida",
                    "Produto informado como destalhes de informações que serão apresentadas",
                    "Ativo");

            detalheProdutoRepository.saveAll(Arrays.asList(detalheProduto1, detalheProduto2, detalheProduto3, detalheProduto4, detalheProduto5, detalheProduto6));

            Date inicio = new Date();

            for (int i = 0; i < 2000; i++) {
                inicio.setDate(inicio.getDate() + 1);
                DataTarifa dataTarifa = new DataTarifa(null, inicio);

                dataTarifaRepository.saveAll(Arrays.asList(dataTarifa));
                System.out.println(dataTarifa);

            }
            inicio.setDate(inicio.getDate() + 1);
            DataTarifa dataTarifas = new DataTarifa(null, inicio);

            dataTarifaRepository.saveAll(Arrays.asList(dataTarifas));

//            Tarifa tarifa1 = new Tarifa(null, produto2, dataTarifas, cupom1, "Tarifa 1", 10.0, 5.0, 0.0, "Ativo", 15, 5,
//                    "Observação teste");
//            Tarifa tarifa2 = new Tarifa(null, produto1, dataTarifas, cupom2, "Tarifa 2", 20.0, 10.0, 0.0, "Ativo", 20, 3,
//                    "Observação teste");
//            Tarifa tarifa3 = new Tarifa(null, produto2, dataTarifas, cupom1, "Tarifa 3", 30.0, 15.0, 0.0, "Ativo", 10, 0,
//                    "Observação teste");
//            Tarifa tarifa4 = new Tarifa(null, produto2, dataTarifas, cupom1, "Tarifa 4", 10.0, 5.0, 0.0, "Ativo", 15, 5,
//                    "Observação teste");
//            Tarifa tarifa5 = new Tarifa(null, produto1, dataTarifas, cupom2, "Tarifa 5", 20.0, 10.0, 0.0, "Ativo", 15, 5,
//                    "Observação teste");
//            Tarifa tarifa6 = new Tarifa(null, produto2, dataTarifas, cupom1, "Tarifa 6", 30.0, 15.0, 0.0, "Ativo", 15, 5,
//                    "Observação teste");
//            Tarifa tarifa7 = new Tarifa(null, produto2, dataTarifas, cupom1, "Tarifa 7", 10.0, 5.0, 0.0, "Ativo", 15, 5,
//                    "Observação teste");
//            Tarifa tarifa8 = new Tarifa(null, produto1, dataTarifas, cupom2, "Tarifa 8", 20.0, 10.0, 0.0, "Ativo", 15, 5,
//                    "Observação teste");
//            Tarifa tarifa9 = new Tarifa(null, produto2, dataTarifas, cupom1, "Tarifa 9", 30.0, 15.0, 0.0, "Ativo", 15, 5,
//                    "Observação teste");

//            tarifaRepository.saveAll(Arrays.asList(tarifa1, tarifa2, tarifa3, tarifa4, tarifa5, tarifa6, tarifa7, tarifa8, tarifa9));

            Usuario usuario1 = new Usuario(null, cliente1, "José Eduardo", "65999999999", "Administrador", "Total",
                    "Ativo", "joseeduardo@confiancaturismo.com.br", pe.encode("123"));
            Usuario usuario2 = new Usuario(null, cliente1, "Administrador", "65999999999", "Administrador", "Total",
                    "Ativo", "admin@admin", pe.encode("admin123"));

            usuario1.addPerfil(Perfil.toEnum(1));
            usuario2.addPerfil(Perfil.toEnum(1));

            usuarioRepository.saveAll(Arrays.asList(usuario1, usuario2));

            Cartao cartao1 = new Cartao(null, "1111111111111111", "Nome Cartao Teste",
                    "10/2028", "123", "false", "visa", "false");

            cartaoRepository.saveAll(Arrays.asList(cartao1));

            Pagador pagador = new Pagador(null, null, "João pagador", "joao@pagador.com", "12345678933", "65999202020");

            pagadorRepository.saveAll(Arrays.asList(pagador));


            Venda venda1 = new Venda(null, cliente1, usuario1, cartao1, pagador, "Venda 1", 2000.0, 0.00, 2000.0, "Ativo", "Fechado",
                    sdf.parse("20/09/2022"), sdf.parse("20/09/2022"), "123123", "Obs", new Date());

            Venda venda2 = new Venda(null, cliente1, usuario1, cartao1, null, "Venda 2", 2000.0, 0.00, 2000.0, "Ativo", "Fechado",
                    sdf.parse("20/09/2022"), sdf.parse("20/09/2022"), "123123", "Obs", new Date());

            Venda venda3 = new Venda(null, cliente1, usuario1, cartao1, null, "Venda 3", 2000.0, 0.00, 2000.0, "Ativo", "Fechado",
                    sdf.parse("20/09/2022"), sdf.parse("20/09/2022"), "123123", "Obs", new Date());

            vendaRepository.saveAll(Arrays.asList(venda1, venda2, venda3));

//        Imagem imagem1 = new Imagem(null, produto1, "Nome da imagem 1", "png",
//                "assets/images/product/fashion/39.jpg", 10.0);
//
//        Imagem imagem2 = new Imagem(null, produto2, "Nome da imagem 2", "png",
//                "assets/images/product/fashion/1.jpg", 10.0);
//
//        imagemRepository.saveAll(Arrays.asList(imagem1, imagem2));
//
//        Meta meta1 = new Meta(null, unidade1, "Meta 1", 0.1, 10000.0,
//                sdf.parse("01/09/2022"), sdf.parse("30/09/2022"), "Ativo");
//
//        Meta meta2 = new Meta(null, unidade2, "Meta 2", 0.1, 10000.0,
//                sdf.parse("01/09/2022"), sdf.parse("30/09/2022"), "Ativo");
//
//        metaRepository.saveAll(Arrays.asList(meta1, meta2));

//            ProdutoVendido produtoVendido1 = new ProdutoVendido(null, produto1, tarifa1, servicoProduto1, venda1, null, "1 Produto Vendido", 200.00, "Aprovado", new Date(), null);
//            ProdutoVendido produtoVendido2 = new ProdutoVendido(null, produto1, tarifa1, servicoProduto1, venda1, null, "2 Produto Vendido", 200.00, "Aprovado", new Date(), null);
//            ProdutoVendido produtoVendido3 = new ProdutoVendido(null, produto1, tarifa1, servicoProduto1, venda1, null, "3 Produto Vendido", 200.00, "Aprovado", new Date(), null);
//
//            produtoVendidoRepository.saveAll(Arrays.asList(produtoVendido1, produtoVendido2, produtoVendido3));

//            Passageiro passageiro1 = new Passageiro(null, produtoVendido1, "Passaeiro 1",
//                    sdf.parse("01/01/1990"), "12345678912", "65985698569",
//                    "6598765987", "passageiro1@teste.com", "Ativo", "Obs teste");
//
//            Passageiro passageiro2 = new Passageiro(null, produtoVendido2, "Passaeiro 2",
//                    sdf.parse("01/01/1990"), "12345678912", "65985698569",
//                    "6598765987", "passageiro2@teste.com", "Ativo", "Obs teste");
//
//            passageiroRepository.saveAll(Arrays.asList(passageiro1, passageiro2));

            Recebimento recebimento1 = new Recebimento(null, tipoPagamentoRecebimento1, venda1, null,
                    "Nome Recebimento1", 100.0, sdf.parse("10/10/2022"), sdf.parse("10/10/2022"),
                    "Sim", 10, 9, "Faturado", new Date());
            Recebimento recebimento2 = new Recebimento(null, tipoPagamentoRecebimento2, venda2, null,
                    "Nome Recebimento2", 100.0, sdf.parse("10/10/2022"), sdf.parse("10/10/2022"),
                    "Sim", 10, 9, "Faturado", new Date());

            recebimentoRepository.saveAll(Arrays.asList(recebimento1, recebimento2));

            TipoReceita tipoReceita1 = new TipoReceita(null, "Receita de Venda", "Ativo");
            TipoReceita tipoReceita2 = new TipoReceita(null, "Receita de Recorrente", "Ativo");
            TipoReceita tipoReceita3 = new TipoReceita(null, "Receita de Investimentos", "Ativo");

            tipoReceitaRepository.saveAll(Arrays.asList(tipoReceita1, tipoReceita2, tipoReceita3));

            Receita receita1 = new Receita(null, null, servicoProduto1, centro1, tipoReceita1, cliente1, tp1, null,
                    "Receita 1", 100.00, 200.00, 0.0, 100.00, 2,
                    "Ativo", sdf.parse("10/10/2022"), sdf.parse("10/10/2022"), sdf.parse("10/10/2022"),
                    true, "Obs Receita", tipoPagamentoRecebimento1, null, new Date());
            Receita receita2 = new Receita(null, null, servicoProduto2, centro2, tipoReceita2, cliente1, tp1,null,
                     "Receita 2", 100.00, 200.00, 0.0, 100.00, 2,
                    "Ativo", sdf.parse("10/10/2022"), sdf.parse("10/10/2022"), sdf.parse("10/10/2022"),
                    true, "Obs Receita", tipoPagamentoRecebimento1, null, new Date());
            Receita receita3 = new Receita(null, null, servicoProduto3, centro3, tipoReceita3, cliente1, tp1,null,
                    "Receita 3", 100.00, 100.00, 0.0, 100.00, 1,
                    "Ativo", sdf.parse("10/10/2022"), sdf.parse("10/10/2022"), sdf.parse("10/10/2022"),
                    true, "Obs Receita", tipoPagamentoRecebimento1, null, new Date());

            receitaRepository.saveAll(Arrays.asList(receita1, receita2, receita3));

            DiaDoAno dia = new DiaDoAno(null, sdf.parse("01/01/2022"), "Ativo");

            diaDoAnoRepository.saveAll(Arrays.asList(dia));

            Tarifario tar1 = new Tarifario(null, produto1, "Tour Pantanal Full Day - Privativo (sem data fixa)", "Ativo", null, null, null, null);
            Tarifario tar2 = new Tarifario(null, produto1, "Tour 02D/01N - Pantanal - Privativo (sem data fixa)", "Ativo", null, null, null, null);
            Tarifario tar3 = new Tarifario(null, produto1, "Tour 03D/02N - Pantanal - Privativo (sem data fixa)", "Ativo", null, null, null, null);
            Tarifario tar4 = new Tarifario(null, produto1, "Tour 04D/03N - Pantanal - Privativo (sem data fixa)", "Ativo", null, null, null, null);

            tarifarioRepository.saveAll(Arrays.asList(tar1, tar2, tar3, tar4));

            TarifarioDado tarifarioDado1 = new TarifarioDado(null, tar1, "01 PAX", 1, 1700.0, "Ativo", null);
            TarifarioDado tarifarioDado2 = new TarifarioDado(null, tar1, "2 a 3 PAX", 2, 980.0, "Ativo", null);
            TarifarioDado tarifarioDado3 = new TarifarioDado(null, tar1, "4 a 6 PAX", 4, 850.0, "Ativo", null);
            TarifarioDado tarifarioDado4 = new TarifarioDado(null, tar1, "7 a 9 PAX", 7, 600.0, "Ativo", null);
            TarifarioDado tarifarioDado5 = new TarifarioDado(null, tar1, "10 a 12 PAX", 10, 500.0, "Ativo", null);

            TarifarioDado tarifarioDado6 = new TarifarioDado(null, tar2, "01 PAX", 1, 4200.0, "Ativo", null);
            TarifarioDado tarifarioDado7 = new TarifarioDado(null, tar2, "2 a 3 PAX", 2, 2600.0, "Ativo", null);
            TarifarioDado tarifarioDado8 = new TarifarioDado(null, tar2, "4 a 6 PAX", 4, 2400.0, "Ativo", null);
            TarifarioDado tarifarioDado9 = new TarifarioDado(null, tar2, "7 a 9 PAX", 7, 1600.0, "Ativo", null);
            TarifarioDado tarifarioDado10 = new TarifarioDado(null, tar2, "10 a 12 PAX", 10, 1350.0, "Ativo", null);

            tarifarioDadoRepository.saveAll(Arrays.asList(tarifarioDado1, tarifarioDado2, tarifarioDado3, tarifarioDado4, tarifarioDado5,
                    tarifarioDado6, tarifarioDado7, tarifarioDado8, tarifarioDado9, tarifarioDado10));

            Transfer transfer1 = new Transfer(null, fornecedor1, pontoDeSaida1, null, "Nome do Transfer", new Date(), "Corolla", "Condutor Teste",
                    "Rota Teste", "Destino Teste", "Passageiro Teste", "Cliente Teste", true, "Ativo", null, new Date());
            Transfer transfer2 = new Transfer(null, fornecedor2, pontoDeSaida2, null, "Nome do Transfer", new Date(), "Corolla 2 Teste", "Condutor Teste 2",
                    "Rota Teste 2", "Destino Teste 2", "Passageiro Teste 2", "Cliente Teste 2", false, "Ativo", null, new Date());
            Transfer transfer3 = new Transfer(null, fornecedor3, pontoDeSaida3, null, "Nome do Transfer", new Date(), "Corolla 3", "Condutor Teste 3",
                    "Rota Teste 3", "Destino Teste 3", "Passageiro Teste 3", "Cliente Teste 3", true, "Ativo", null, new Date());

            transferRepository.saveAll(Arrays.asList(transfer1, transfer2, transfer3));

            Historico historico1 = new Historico(null, null, 1, "Atualização de Centro de Custo", new Date(),
                    "Atualização realizada pelo usuário " + null + " no campo de Centro de Custo no dia " + new Date().toString(), "Ativo",
                    "CentroDeCusto", "Atualizar", null);

            Historico historico2 = new Historico(null, null, 1, "Atualização de Centro de Custo", new Date(),
                    "Atualização realizada pelo usuário " + null + " no campo de Centro de Custo no dia " + new Date().toString(), "Ativo",
                    "CentroDeCusto", "Atualizar", null);

            Historico historico3 = new Historico(null, null, 1, "Atualização de Centro de Custo", new Date(),
                    "Atualização realizada pelo usuário " + null + " no campo de Centro de Custo no dia " + new Date().toString(), "Ativo",
                    "CentroDeCusto", "Atualizar", null);


            historicoRepository.saveAll(Arrays.asList(historico1, historico2, historico3));

        } else {

        }
    }
}
