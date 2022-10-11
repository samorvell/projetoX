package com.samorvell.pontointeligente.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ToString
@Entity
@Table(name = "point_mirror")
public class PointMirror implements Serializable {

    private static final long serialVersionUID = 6524560251526772839L;

    @Column(name = "funcionario_id", nullable = false)
    private Long funcionarioId;
    @Column(name = "acumulado", nullable = false)
    private LocalTime accumulated;
    @Column(name = "data", nullable = false)
    private LocalDate data;
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime creationDate;
    @Column
    private LocalTime total;
    @Column(name = "nome", nullable = false)
    private String nome;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mirror_id", nullable = false)
    @Id
    private Long mirrorId;

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public LocalTime getAccumulated() {
        return accumulated;
    }

    public void setAccumulated(LocalTime accumulated) {
        this.accumulated = accumulated;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalTime getTotal() {
        return total;
    }

    public void setTotal(LocalTime total) {
        this.total = total;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getMirrorId() {
        return mirrorId;
    }

    public void setMirrorId(Long mirrorId) {
        this.mirrorId = mirrorId;
    }

    @PrePersist
    public void prePersist() {
        final LocalDateTime current = LocalDateTime.now();
        creationDate = current;
        //updateDate = current;
    }


}