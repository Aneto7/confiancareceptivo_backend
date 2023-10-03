package com.confianca.services;

import com.confianca.domain.Cliente;
import com.confianca.domain.ProdutoVendido;
import com.confianca.domain.Usuario;
import com.confianca.domain.Venda;
import com.confianca.dto.ClienteCadastroDTO;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

public interface EmailService {

    void sendOrderConfirmationEmail(Cliente obj);

    void sendEmail(SimpleMailMessage msg);

    void sendOrderConfirmationHtmlEmail(Cliente obj);

    void sendHtmlEmail(MimeMessage msg);

    void sendNewPasswordEmail(Usuario usuario, String newPass);

    void sendEmailComprovanteVenda(Venda venda, String email, byte[] pdf, List<byte[]> vouchers);

    void sendEmailVoucher(ProdutoVendido produtoVendido, String email, byte[] pdf);

    void sendEmailRelatorioPassageiro(Date dataT, byte[] excel);

    void sendSolicitacaoCadastro(ClienteCadastroDTO cliente);
}