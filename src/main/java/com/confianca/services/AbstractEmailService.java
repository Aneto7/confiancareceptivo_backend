package com.confianca.services;

import com.confianca.domain.*;
import com.confianca.dto.ClienteCadastroDTO;
import com.confianca.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${default.controllermail}")
    private String emailpadrao;
    @Value("${default.suporte}")
    private String emailsuporte;
    @Value("${default.seguromail1}")
    private String emailseguro1;
    @Value("${default.seguromail2}")
    private String emailseguro2;

    @Override
    public void sendOrderConfirmationEmail(Cliente obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Cliente obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de cadastro de cliente! Código: " + obj);
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }

    protected String htmlFromTemplatePedido(Cliente obj) {
        Context context = new Context();
        context.setVariable("pedido", obj);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

    public void sendOrderConfirmationHtmlEmail(Cliente obj) {
        try {
            MimeMessage mm = prepareMimeMessageFromPedido(obj);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeMessageFromPedido(Cliente obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
        mmh.setTo(obj.getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(obj), true);
        return mimeMessage;
    }

    @Override
    public void sendNewPasswordEmail(Usuario usuario, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(usuario, newPass);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Usuario usuario, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(usuario.getLogin());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPass);
        return sm;
    }

    @Override
    public void sendEmailComprovanteVenda(Venda venda, String email, byte[] pdf, List<byte[]> vouchers) {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String emissao = formatador.format(venda.getDataVencimento());
        String quantidadeProdutosVendidoa = "Produto Vendido";
        String produtosVendidos = "";
        String dataProduto = "";

        if (venda.getProdutosVendidos().size() > 1) {
            quantidadeProdutosVendidoa = "Produtos Vendidos";
        }

        for (ProdutoVendido prodV : venda.getProdutosVendidos()) {
            if (prodV.getDataIda() == null) {
                dataProduto = formatador.format(prodV.getTarifa().getData().getData());
            } else {
                dataProduto = formatador.format(prodV.getDataIda());
            }

            produtosVendidos = produtosVendidos +
                    "<p style=\"margin: 0; margin-bottom: 16px;\"><strong>" + prodV.getNome() + "</strong>" +
                    "<br>Data: " + dataProduto;
            if (!prodV.getOpcionaisVendidos().isEmpty()) {
                produtosVendidos = produtosVendidos + "<br>Opcionais: ";
                for (OpcionalVendido opV : prodV.getOpcionaisVendidos()) {
                    produtosVendidos = produtosVendidos +
                            "<br>  - " + opV.getNome();
                }
            }
            produtosVendidos = produtosVendidos + "<br>";
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.addCc(nomeUsuarioLogado());
            helper.addCc(emailpadrao);
            helper.setFrom(sender);
            helper.setSubject("Compra confirmada! Código: " + venda.getId());
            helper.setSentDate(new Date(System.currentTimeMillis()));

            // Defina o conteúdo do email como HTML

            String str = "<!DOCTYPE html>" +
                    "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">" +
                    "" +
                    "<head>" +
                    "	<title></title>" +
                    "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
                    "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]--><!--[if !mso]><!-->" +
                    "	<link href=\"https://fonts.googleapis.com/css?family=Open+Sans\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->" +
                    "	<style>" +
                    "		* {" +
                    "			box-sizing: border-box;" +
                    "		}" +
                    "" +
                    "		body {" +
                    "			margin: 0;" +
                    "			padding: 0;" +
                    "		}" +
                    "" +
                    "		a[x-apple-data-detectors] {" +
                    "			color: inherit !important;" +
                    "			text-decoration: inherit !important;" +
                    "		}" +
                    "" +
                    "		#MessageViewBody a {" +
                    "			color: inherit;" +
                    "			text-decoration: none;" +
                    "		}" +
                    "" +
                    "		p {" +
                    "			line-height: inherit" +
                    "		}" +
                    "" +
                    "		.desktop_hide," +
                    "		.desktop_hide table {" +
                    "			mso-hide: all;" +
                    "			display: none;" +
                    "			max-height: 0px;" +
                    "			overflow: hidden;" +
                    "		}" +
                    "" +
                    "		.image_block img+div {" +
                    "			display: none;" +
                    "		}" +
                    "" +
                    "		@media (max-width:820px) {" +
                    "" +
                    "			.desktop_hide table.icons-inner," +
                    "			.social_block.desktop_hide .social-table {" +
                    "				display: inline-block !important;" +
                    "			}" +
                    "" +
                    "			.icons-inner {" +
                    "				text-align: center;" +
                    "			}" +
                    "" +
                    "			.icons-inner td {" +
                    "				margin: 0 auto;" +
                    "			}" +
                    "" +
                    "			.row-content {" +
                    "				width: 100% !important;" +
                    "			}" +
                    "" +
                    "			.mobile_hide {" +
                    "				display: none;" +
                    "			}" +
                    "" +
                    "			.stack .column {" +
                    "				width: 100%;" +
                    "				display: block;" +
                    "			}" +
                    "" +
                    "			.mobile_hide {" +
                    "				min-height: 0;" +
                    "				max-height: 0;" +
                    "				max-width: 0;" +
                    "				overflow: hidden;" +
                    "				font-size: 0px;" +
                    "			}" +
                    "" +
                    "			.desktop_hide," +
                    "			.desktop_hide table {" +
                    "				display: table !important;" +
                    "				max-height: none !important;" +
                    "			}" +
                    "		}" +
                    "	</style>" +
                    "</head>" +
                    "" +
                    "<body style=\"background-color: #ffffff; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">" +
                    "	<table class=\"nl-container\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row row-1\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<div class=\"spacer_block block-1\" style=\"height:20px;line-height:20px;font-size:1px;\"> </div>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-2\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #172f5a; background-position: top center; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<div class=\"spacer_block block-1\" style=\"height:65px;line-height:65px;font-size:1px;\"> </div>" +
                    "<table class=\"image_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"width:100%;padding-right:0px;padding-left:0px;\">" +
                    "<div class=\"alignment\" align=\"center\" style=\"line-height:10px\"><img src=\"https://327239ae9e.imgdist.com/public/users/Integrators/BeeProAgency/994144_978882/logo_tour_brancagrande.png\" style=\"display: block; height: auto; border: 0; width: 160px; max-width: 100%;\" width=\"160\" alt=\"Alternate text\" title=\"Alternate text\"></div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<div class=\"spacer_block block-3\" style=\"height:25px;line-height:25px;font-size:1px;\"> </div>" +
                    "<table class=\"text_block block-4\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div style=\"font-family: Arial, sans-serif\">" +
                    "<div class style=\"font-size: 12px; font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #ffffff; line-height: 1.2;\">" +
                    "<p style=\"margin: 0; font-size: 38px; text-align: center; mso-line-height-alt: 45.6px;\">COMPROVANTE DE COMPRA</p>" +
                    "</div>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<table class=\"text_block block-5\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div style=\"font-family: Arial, sans-serif\">" +
                    "<div class style=\"font-size: 12px; font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif; mso-line-height-alt: 18px; color: #ffffff; line-height: 1.5;\">" +
                    "<p style=\"margin: 0; font-size: 16px; text-align: center; mso-line-height-alt: 24px;\">Olá<br>" +
                    "<br> " + nomeUsuarioLogado() +
                    "<br>Este é um e-mail de confirmação da compra de produtos e serviços.<br><br>Ficamos felizes pela sua aquisição, abaixo seguem os produtos adquiridos. Em anexo estão os documentos que comprovam a compra e ainda os vouchers referentes a cada produto e serviços adquiridos.<br><br></p>" +
                    "</div>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-3\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"divider_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div class=\"alignment\" align=\"center\">" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 1px solid #dddddd;\"><span> </span></td>" +
                    "</tr>" +
                    "</table>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<table class=\"paragraph_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div style=\"color:#101112;direction:ltr;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;font-size:16px;font-weight:400;letter-spacing:0px;line-height:150%;text-align:left;mso-line-height-alt:24px;\">" +
                    "<p style=\"margin: 0; margin-bottom: 16px;\">Nº " + venda.getId() +
                    "<br>Data de emissão: " + emissao +
                    "<br>Valor: " + NumberFormat.getCurrencyInstance().format(venda.getValorFinal()) +
                    "<br><br><br><strong>" + quantidadeProdutosVendidoa + "</strong></p>" +
                    produtosVendidos +
                    "<p style=\"margin: 0; margin-bottom: 16px;\"> </p>" +
                    "<p style=\"margin: 0;\">Obrigado por ser nosso cliente!</p>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<div class=\"spacer_block block-3\" style=\"height:10px;line-height:10px;font-size:1px;\"> </div>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-4\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"image_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"width:100%;padding-right:0px;padding-left:0px;\">" +
                    "<div class=\"alignment\" align=\"center\" style=\"line-height:10px\"><img src=\"https://327239ae9e.imgdist.com/public/users/Integrators/BeeProAgency/994144_978882/logo-icon-antiga.png\" style=\"display: block; height: auto; border: 0; width: 51px; max-width: 100%;\" width=\"51\" alt=\"Alternate text\" title=\"Alternate text\"></div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<table class=\"social_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"text-align:center;padding-right:0px;padding-left:0px;\">" +
                    "<div class=\"alignment\" align=\"center\">" +
                    "<table class=\"social-table\" width=\"188px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block;\">" +
                    "<tr>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://www.facebook.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/facebook@2x.png\" width=\"32\" height=\"32\" alt=\"Facebook\" title=\"Facebook\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://twitter.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/twitter@2x.png\" width=\"32\" height=\"32\" alt=\"Twitter\" title=\"Twitter\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://plus.google.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/googleplus@2x.png\" width=\"32\" height=\"32\" alt=\"Google+\" title=\"Google+\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://instagram.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/instagram@2x.png\" width=\"32\" height=\"32\" alt=\"Instagram\" title=\"Instagram\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "</tr>" +
                    "</table>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<table class=\"divider_block block-3\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div class=\"alignment\" align=\"center\">" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 2px dotted #D4D3D3;\"><span> </span></td>" +
                    "</tr>" +
                    "</table>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-5\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"text_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div style=\"font-family: sans-serif\">" +
                    "<div class style=\"color: #C0C0C0; font-size: 12px; font-family: Open Sans, Helvetica Neue, Helvetica, Arial, sans-serif; mso-line-height-alt: 14.399999999999999px; line-height: 1.2;\">" +
                    "<p style=\"margin: 0; font-size: 12px; text-align: center; mso-line-height-alt: 14.399999999999999px;\">Receptivo Confiança<br>CNPJ: 23.858.227/0001-78<br>Av. São Sebastião, 2852 - Quilombo<br>Cuiabá - MT<br>CEP: 78.045-305</p>" +
                    "</div>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-6\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"icons_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"vertical-align: middle; color: #9d9d9d; font-family: inherit; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;\">" +
                    "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"alignment\" style=\"vertical-align: middle; text-align: center;\"><!--[if vml]><table align=\"left\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><![endif]-->" +
                    "<!--[if !vml]><!-->" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table><!-- End -->" +
                    "</body>" +
                    "" +
                    "</html>";

            String htmlContent = str;

            helper.setText(htmlContent, true);

            // Adicionar o anexo
            ByteArrayDataSource dataSource = new ByteArrayDataSource(pdf, "application/pdf");
            helper.addAttachment("comprovante.pdf", dataSource);

            for (int i = 0; i < vouchers.size(); i++) {
                byte[] voucher = vouchers.get(i);
                ByteArrayDataSource dataSource1 = new ByteArrayDataSource(voucher, "application/pdf");
                helper.addAttachment("voucher" + (i + 1) + ".pdf", dataSource1);
            }

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Trate as exceções adequadamente
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmailVoucher(ProdutoVendido produtoVendido, String email, byte[] pdf) {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        String produtosVendidos = "";
        String passageiros = "";
        String dataProduto = "";

        if (produtoVendido.getDataIda() == null) {
            dataProduto = formatador.format(produtoVendido.getTarifa().getData().getData());
        } else {
            dataProduto = formatador.format(produtoVendido.getDataIda());
        }

        produtosVendidos = produtosVendidos +
                "<p style=\"margin: 0; margin-bottom: 16px;\"><strong>" + produtoVendido.getNome() + "</strong>" +
                "<br>Data: " + dataProduto;
        if (!produtoVendido.getOpcionaisVendidos().isEmpty()) {
            produtosVendidos = produtosVendidos + "<br>Opcionais: ";
            for (OpcionalVendido opV : produtoVendido.getOpcionaisVendidos()) {
                produtosVendidos = produtosVendidos +
                        "<br>  - " + opV.getNome();
            }
        }

        produtosVendidos = produtosVendidos + "<br>";

        for (Passageiro passageiro : produtoVendido.getPassageiros()) {
            passageiros = passageiros +
                    "<br>  - Nome: " + passageiro.getNome() + ", CPF: " + passageiro.getCpf() + ", Nascimento: " + formatador.format(passageiro.getNascimento());
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.addCc(nomeUsuarioLogado());
            helper.addCc(emailpadrao);
            helper.setFrom(sender);
            helper.setSubject("Voucher referente ao produto: " + produtoVendido.getId() + " - " + produtoVendido.getNome() + " - " + dataProduto);
            helper.setSentDate(new Date(System.currentTimeMillis()));

            // Defina o conteúdo do email como HTML

            String str = "<!DOCTYPE html>" +
                    "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">" +
                    "" +
                    "<head>" +
                    "	<title></title>" +
                    "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
                    "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]--><!--[if !mso]><!-->" +
                    "	<link href=\"https://fonts.googleapis.com/css?family=Open+Sans\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->" +
                    "	<style>" +
                    "		* {" +
                    "			box-sizing: border-box;" +
                    "		}" +
                    "" +
                    "		body {" +
                    "			margin: 0;" +
                    "			padding: 0;" +
                    "		}" +
                    "" +
                    "		a[x-apple-data-detectors] {" +
                    "			color: inherit !important;" +
                    "			text-decoration: inherit !important;" +
                    "		}" +
                    "" +
                    "		#MessageViewBody a {" +
                    "			color: inherit;" +
                    "			text-decoration: none;" +
                    "		}" +
                    "" +
                    "		p {" +
                    "			line-height: inherit" +
                    "		}" +
                    "" +
                    "		.desktop_hide," +
                    "		.desktop_hide table {" +
                    "			mso-hide: all;" +
                    "			display: none;" +
                    "			max-height: 0px;" +
                    "			overflow: hidden;" +
                    "		}" +
                    "" +
                    "		.image_block img+div {" +
                    "			display: none;" +
                    "		}" +
                    "" +
                    "		@media (max-width:820px) {" +
                    "" +
                    "			.desktop_hide table.icons-inner," +
                    "			.social_block.desktop_hide .social-table {" +
                    "				display: inline-block !important;" +
                    "			}" +
                    "" +
                    "			.icons-inner {" +
                    "				text-align: center;" +
                    "			}" +
                    "" +
                    "			.icons-inner td {" +
                    "				margin: 0 auto;" +
                    "			}" +
                    "" +
                    "			.row-content {" +
                    "				width: 100% !important;" +
                    "			}" +
                    "" +
                    "			.mobile_hide {" +
                    "				display: none;" +
                    "			}" +
                    "" +
                    "			.stack .column {" +
                    "				width: 100%;" +
                    "				display: block;" +
                    "			}" +
                    "" +
                    "			.mobile_hide {" +
                    "				min-height: 0;" +
                    "				max-height: 0;" +
                    "				max-width: 0;" +
                    "				overflow: hidden;" +
                    "				font-size: 0px;" +
                    "			}" +
                    "" +
                    "			.desktop_hide," +
                    "			.desktop_hide table {" +
                    "				display: table !important;" +
                    "				max-height: none !important;" +
                    "			}" +
                    "		}" +
                    "	</style>" +
                    "</head>" +
                    "" +
                    "<body style=\"background-color: #ffffff; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">" +
                    "	<table class=\"nl-container\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row row-1\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<div class=\"spacer_block block-1\" style=\"height:20px;line-height:20px;font-size:1px;\"> </div>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-2\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #172f5a; background-position: top center; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<div class=\"spacer_block block-1\" style=\"height:65px;line-height:65px;font-size:1px;\"> </div>" +
                    "<table class=\"image_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"width:100%;padding-right:0px;padding-left:0px;\">" +
                    "<div class=\"alignment\" align=\"center\" style=\"line-height:10px\"><img src=\"https://327239ae9e.imgdist.com/public/users/Integrators/BeeProAgency/994144_978882/logo_tour_brancagrande.png\" style=\"display: block; height: auto; border: 0; width: 160px; max-width: 100%;\" width=\"160\" alt=\"Alternate text\" title=\"Alternate text\"></div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<div class=\"spacer_block block-3\" style=\"height:25px;line-height:25px;font-size:1px;\"> </div>" +
                    "<table class=\"text_block block-4\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div style=\"font-family: Arial, sans-serif\">" +
                    "<div class style=\"font-size: 12px; font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #ffffff; line-height: 1.2;\">" +
                    "<p style=\"margin: 0; font-size: 38px; text-align: center; mso-line-height-alt: 45.6px;\">VOUCHER</p>" +
                    "</div>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<table class=\"text_block block-5\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div style=\"font-family: Arial, sans-serif\">" +
                    "<div class style=\"font-size: 12px; font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif; mso-line-height-alt: 18px; color: #ffffff; line-height: 1.5;\">" +
                    "<p style=\"margin: 0; font-size: 16px; text-align: center; mso-line-height-alt: 24px;\">Olá<br>" +
                    "<br> " + nomeUsuarioLogado() +
                    "<br>Este é um e-mail de envio de voucher referente aos produtos adquiridos.<br><br>Ficamos felizes pela sua aquisição, abaixo seguem informações básicas do produto. Em anexo esté o voucher referente ao produto e serviços adquiridos.<br><br></p>" +
                    "</div>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-3\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"divider_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div class=\"alignment\" align=\"center\">" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 1px solid #dddddd;\"><span> </span></td>" +
                    "</tr>" +
                    "</table>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<table class=\"paragraph_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div style=\"color:#101112;direction:ltr;font-family:'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;font-size:16px;font-weight:400;letter-spacing:0px;line-height:150%;text-align:left;mso-line-height-alt:24px;\">" +
                    "<p style=\"margin: 0; margin-bottom: 16px;\">Voucher Nº " + produtoVendido.getId() +
                    "<br>Produto Vendido: " + produtosVendidos +
                    "<br><br><br><strong>Passageiros</strong></p>" +
                    passageiros +
                    "<p style=\"margin: 0; margin-bottom: 16px;\"> </p>" +
                    "<p style=\"margin: 0;\">Obrigado por ser nosso cliente!</p>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<div class=\"spacer_block block-3\" style=\"height:10px;line-height:10px;font-size:1px;\"> </div>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-4\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"image_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"width:100%;padding-right:0px;padding-left:0px;\">" +
                    "<div class=\"alignment\" align=\"center\" style=\"line-height:10px\"><img src=\"https://327239ae9e.imgdist.com/public/users/Integrators/BeeProAgency/994144_978882/logo-icon-antiga.png\" style=\"display: block; height: auto; border: 0; width: 51px; max-width: 100%;\" width=\"51\" alt=\"Alternate text\" title=\"Alternate text\"></div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<table class=\"social_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"text-align:center;padding-right:0px;padding-left:0px;\">" +
                    "<div class=\"alignment\" align=\"center\">" +
                    "<table class=\"social-table\" width=\"188px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block;\">" +
                    "<tr>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://www.facebook.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/facebook@2x.png\" width=\"32\" height=\"32\" alt=\"Facebook\" title=\"Facebook\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://twitter.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/twitter@2x.png\" width=\"32\" height=\"32\" alt=\"Twitter\" title=\"Twitter\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://plus.google.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/googleplus@2x.png\" width=\"32\" height=\"32\" alt=\"Google+\" title=\"Google+\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://instagram.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/instagram@2x.png\" width=\"32\" height=\"32\" alt=\"Instagram\" title=\"Instagram\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "</tr>" +
                    "</table>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<table class=\"divider_block block-3\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div class=\"alignment\" align=\"center\">" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 2px dotted #D4D3D3;\"><span> </span></td>" +
                    "</tr>" +
                    "</table>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-5\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"text_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div style=\"font-family: sans-serif\">" +
                    "<div class style=\"color: #C0C0C0; font-size: 12px; font-family: Open Sans, Helvetica Neue, Helvetica, Arial, sans-serif; mso-line-height-alt: 14.399999999999999px; line-height: 1.2;\">" +
                    "<p style=\"margin: 0; font-size: 12px; text-align: center; mso-line-height-alt: 14.399999999999999px;\">Receptivo Confiança<br>CNPJ: 23.858.227/0001-78<br>Av. São Sebastião, 2852 - Quilombo<br>Cuiabá - MT<br>CEP: 78.045-305</p>" +
                    "</div>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-6\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"icons_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"vertical-align: middle; color: #9d9d9d; font-family: inherit; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;\">" +
                    "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"alignment\" style=\"vertical-align: middle; text-align: center;\"><!--[if vml]><table align=\"left\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><![endif]-->" +
                    "<!--[if !vml]><!-->" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table><!-- End -->" +
                    "</body>" +
                    "" +
                    "</html>";

            String htmlContent = str;

            helper.setText(htmlContent, true);

            // Adicionar o anexo
            ByteArrayDataSource dataSource = new ByteArrayDataSource(pdf, "application/pdf");
            helper.addAttachment("Voucher.pdf", dataSource);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Trate as exceções adequadamente
            e.printStackTrace();
        }
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromVenda(String email) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(email);
        sm.setFrom(sender);
        sm.setSubject("Compra confirmada! Código: " + 1);
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Texto do corpo do email");
        return sm;
    }

    public String nomeUsuarioLogado() {
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserSS) {
                username = ((UserSS) principal).getUsername();
                System.out.println("Imprimindo email do usuário logado e autenticado: " + username);
            } else {
                username = principal.toString();
                System.out.println("Imprimindo email do usuário logado: " + username);
            }
        }
        return username;
    }

    @Override
    public void sendEmailRelatorioPassageiro(Date dataT, byte[] excel) {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailseguro1);
//            helper.addCc(nomeUsuarioLogado());
            helper.addCc(emailseguro2);
            helper.addCc(emailsuporte);
            helper.setFrom(sender);
            helper.setSubject("Relatório de Passageiros: " + formatador.format(dataT));
            helper.setSentDate(new Date(System.currentTimeMillis()));

            // Defina o conteúdo do email como HTML
            String htmlContent = "<!DOCTYPE html>" +
                    "<html xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" lang=\"en\">" +
                    "" +
                    "<head>" +
                    "	<title></title>" +
                    "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">" +
                    "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]--><!--[if !mso]><!-->" +
                    "	<link href=\"https://fonts.googleapis.com/css?family=Open+Sans\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->" +
                    "	<style>" +
                    "		* {" +
                    "			box-sizing: border-box;" +
                    "		}" +
                    "" +
                    "		body {" +
                    "			margin: 0;" +
                    "			padding: 0;" +
                    "		}" +
                    "" +
                    "		a[x-apple-data-detectors] {" +
                    "			color: inherit !important;" +
                    "			text-decoration: inherit !important;" +
                    "		}" +
                    "" +
                    "		#MessageViewBody a {" +
                    "			color: inherit;" +
                    "			text-decoration: none;" +
                    "		}" +
                    "" +
                    "		p {" +
                    "			line-height: inherit" +
                    "		}" +
                    "" +
                    "		.desktop_hide," +
                    "		.desktop_hide table {" +
                    "			mso-hide: all;" +
                    "			display: none;" +
                    "			max-height: 0px;" +
                    "			overflow: hidden;" +
                    "		}" +
                    "" +
                    "		.image_block img+div {" +
                    "			display: none;" +
                    "		}" +
                    "" +
                    "		@media (max-width:820px) {" +
                    "" +
                    "			.desktop_hide table.icons-inner," +
                    "			.social_block.desktop_hide .social-table {" +
                    "				display: inline-block !important;" +
                    "			}" +
                    "" +
                    "			.icons-inner {" +
                    "				text-align: center;" +
                    "			}" +
                    "" +
                    "			.icons-inner td {" +
                    "				margin: 0 auto;" +
                    "			}" +
                    "" +
                    "			.row-content {" +
                    "				width: 100% !important;" +
                    "			}" +
                    "" +
                    "			.mobile_hide {" +
                    "				display: none;" +
                    "			}" +
                    "" +
                    "			.stack .column {" +
                    "				width: 100%;" +
                    "				display: block;" +
                    "			}" +
                    "" +
                    "			.mobile_hide {" +
                    "				min-height: 0;" +
                    "				max-height: 0;" +
                    "				max-width: 0;" +
                    "				overflow: hidden;" +
                    "				font-size: 0px;" +
                    "			}" +
                    "" +
                    "			.desktop_hide," +
                    "			.desktop_hide table {" +
                    "				display: table !important;" +
                    "				max-height: none !important;" +
                    "			}" +
                    "		}" +
                    "	</style>" +
                    "</head>" +
                    "" +
                    "<body style=\"background-color: #ffffff; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">" +
                    "<table class=\"nl-container\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row row-1\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<div class=\"spacer_block block-1\" style=\"height:20px;line-height:20px;font-size:1px;\"> </div>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-2\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #011754; background-position: top center; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<div class=\"spacer_block block-1\" style=\"height:65px;line-height:65px;font-size:1px;\"> </div>" +
                    "<table class=\"image_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"width:100%;padding-right:0px;padding-left:0px;\">" +
                    "<div class=\"alignment\" align=\"center\" style=\"line-height:10px\"><img src=\"https://327239ae9e.imgdist.com/public/users/Integrators/BeeProAgency/994144_978882/logo-light-icon-base.png\" style=\"display: block; height: auto; border: 0; width: 50px; max-width: 100%;\" width=\"50\" alt=\"Alternate text\" title=\"Alternate text\"></div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<div class=\"spacer_block block-3\" style=\"height:25px;line-height:25px;font-size:1px;\"> </div>" +
                    "<table class=\"text_block block-4\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div style=\"font-family: Arial, sans-serif\">" +
                    "<div class style=\"font-size: 12px; font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #ffffff; line-height: 1.2;\">" +
                    "<p style=\"margin: 0; font-size: 38px; text-align: center; mso-line-height-alt: 45.6px;\">RELATÓRIO DE PASSAGEIROS</p>" +
                    "</div>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<table class=\"text_block block-5\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div style=\"font-family: Arial, sans-serif\">" +
                    "<div class style=\"font-size: 12px; font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif; mso-line-height-alt: 18px; color: #ffffff; line-height: 1.5;\">" +
                    "<p style=\"margin: 0; font-size: 16px; text-align: center; mso-line-height-alt: 24px;\">Olá<br><br>Este e-mail contém em anexo a lista de passageiros referente a data: "
                    + formatador.format(dataT) +
                    "<br><br>Ficamos a disposição para quaisquer necessidades.<br><br></p>" +
                    "</div>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-3\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"divider_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div class=\"alignment\" align=\"center\">" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 1px solid #dddddd;\"><span> </span></td>" +
                    "</tr>" +
                    "</table>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<div class=\"spacer_block block-3\" style=\"height:10px;line-height:10px;font-size:1px;\"> </div>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-4\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"image_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"padding-bottom:20px;width:100%;padding-right:0px;padding-left:0px;\">" +
                    "<div class=\"alignment\" align=\"center\" style=\"line-height:10px\"><img src=\"https://327239ae9e.imgdist.com/public/users/Integrators/BeeProAgency/994144_978882/logo-icon-antiga.png\" style=\"display: block; height: auto; border: 0; width: 51px; max-width: 100%;\" width=\"51\" alt=\"Alternate text\" title=\"Alternate text\"></div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<table class=\"social_block block-2\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"text-align:center;padding-right:0px;padding-left:0px;\">" +
                    "<div class=\"alignment\" align=\"center\">" +
                    "<table class=\"social-table\" width=\"188px\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block;\">" +
                    "<tr>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://www.facebook.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/facebook@2x.png\" width=\"32\" height=\"32\" alt=\"Facebook\" title=\"Facebook\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://twitter.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/twitter@2x.png\" width=\"32\" height=\"32\" alt=\"Twitter\" title=\"Twitter\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://plus.google.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/googleplus@2x.png\" width=\"32\" height=\"32\" alt=\"Google+\" title=\"Google+\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "<td style=\"padding:0 15px 0 0px;\"><a href=\"https://instagram.com/\" target=\"_blank\"><img src=\"https://app-rsrc.getbee.io/public/resources/social-networks-icon-sets/t-only-logo-dark-gray/instagram@2x.png\" width=\"32\" height=\"32\" alt=\"Instagram\" title=\"Instagram\" style=\"display: block; height: auto; border: 0;\"></a></td>" +
                    "</tr>" +
                    "</table>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "<table class=\"divider_block block-3\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div class=\"alignment\" align=\"center\">" +
                    "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"divider_inner\" style=\"font-size: 1px; line-height: 1px; border-top: 2px dotted #D4D3D3;\"><span> </span></td>" +
                    "</tr>" +
                    "</table>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-5\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"text_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\">" +
                    "<tr>" +
                    "<td class=\"pad\">" +
                    "<div style=\"font-family: sans-serif\">" +
                    "<div class style=\"color: #C0C0C0; font-size: 12px; font-family: Open Sans, Helvetica Neue, Helvetica, Arial, sans-serif; mso-line-height-alt: 14.399999999999999px; line-height: 1.2;\">" +
                    "<p style=\"margin: 0; font-size: 12px; text-align: center; mso-line-height-alt: 14.399999999999999px;\">Receptivo Confiança<br>CNPJ: 23.858.227/0001-78<br>Av. São Sebastião, 2852 - Quilombo<br>Cuiabá - MT<br>CEP: 78.045-305</p>" +
                    "</div>" +
                    "</div>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "<table class=\"row row-6\" align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td>" +
                    "<table class=\"row-content stack\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 800.00px;\" width=\"800.00\">" +
                    "<tbody>" +
                    "<tr>" +
                    "<td class=\"column column-1\" width=\"100%\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\">" +
                    "<table class=\"icons_block block-1\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"pad\" style=\"vertical-align: middle; color: #9d9d9d; font-family: inherit; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;\">" +
                    "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\">" +
                    "<tr>" +
                    "<td class=\"alignment\" style=\"vertical-align: middle; text-align: center;\"><!--[if vml]><table align=\"left\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\"><![endif]-->" +
                    "<!--[if !vml]><!-->" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table>" +
                    "</td>" +
                    "</tr>" +
                    "</tbody>" +
                    "</table><!-- End -->" +
                    "</body>" +
                    "" +
                    "</html>";
            helper.setText(htmlContent, true);

            // Adicionar o anexo
            ByteArrayDataSource dataSource = new ByteArrayDataSource(excel, "application/vnd.ms-excel");
            helper.addAttachment("PassageirosConfianca.xls", dataSource);

            javaMailSender.send(message);

        } catch (MessagingException e) {
            // Trate as exceções adequadamente
            e.printStackTrace();
        }
    }

    @Override
    public void sendSolicitacaoCadastro(ClienteCadastroDTO obj) {
        System.out.println("teste");
        SimpleMailMessage sm = prepareSolicitacaoCadastro(obj);
        sendEmail(sm);

    }

    protected SimpleMailMessage prepareSolicitacaoCadastro(ClienteCadastroDTO obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(emailpadrao);
        sm.setCc(obj.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de cadastro de cliente: " + obj.getNome());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Seguem dados referente a solicitação de cadastro do cliente: " + obj.toString());
        return sm;
    }
}
