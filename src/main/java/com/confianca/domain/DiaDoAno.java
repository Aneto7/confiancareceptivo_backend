package com.confianca.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "DiaDoAno", catalog = "receptivo", schema = "", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"idDiaDoAno"})})
public class DiaDoAno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDiaDoAno", nullable = false)
    private Integer id;

    @NotNull
    @JsonIgnore
    @Column(name = "diDoAno", nullable = false)
//    @OneToMany(mappedBy="diaDoAno")
    private Date diaDoAno;

    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "status", nullable = false, length = 45)
    private String status;

    public DiaDoAno() {
    }

    public DiaDoAno(Integer id, Date diaDoAno, String status) {
        this.id = id;
        this.diaDoAno = diaDoAno;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDiaDoAno() {
        return diaDoAno;
    }

    public void setDiaDoAno(Date diaDoAno) {
        this.diaDoAno = diaDoAno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiaDoAno)) return false;

        DiaDoAno diaDoAno = (DiaDoAno) o;

        return getId() != null ? getId().equals(diaDoAno.getId()) : diaDoAno.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
