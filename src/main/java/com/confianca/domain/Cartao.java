package com.confianca.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "Cartao", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idCartao"})})
public class Cartao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCartao", nullable = false)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "CardNumber", nullable = false, length = 255)
    private String cardNumber;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Holder", nullable = false, length = 255)
    private String holder;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ExpirationDate", nullable = false, length = 255)
    private String expirationDate;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SecurityCode", nullable = false, length = 255)
    private String securityCode;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SaveCard", nullable = false, length = 255)
    private String saveCard;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Brand", nullable = false, length = 255)
    private String brand;

    @Size(max = 255)
    @Column(name = "CardToken", length = 255)
    private String cardToken;

    @JsonIgnore
    @OneToMany(mappedBy="cartao")
    private List<Venda> vendas;

    public Cartao() {
    }

    public Cartao(Integer id, String cardNumber, String holder, String expirationDate, String securityCode, String saveCard, String brand, String cardToken) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.holder = holder;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        this.saveCard = saveCard;
        this.brand = brand;
        this.cardToken = cardToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getSaveCard() {
        return saveCard;
    }

    public void setSaveCard(String saveCard) {
        this.saveCard = saveCard;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cartao)) return false;

        Cartao cartao = (Cartao) o;

        return getId() != null ? getId().equals(cartao.getId()) : cartao.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cartao{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
