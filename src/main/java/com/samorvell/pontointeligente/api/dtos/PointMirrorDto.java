package com.samorvell.pontointeligente.api.dtos;

import com.samorvell.pontointeligente.api.model.Funcionario;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class PointMirrorDto {

    private Long mirrorId;
    private LocalTime accumulated;
    private LocalDateTime data;
    private String funcionario;


    public String getEmployee() {
        return funcionario;
    }

    public void setEmployee(String employee) {
        this.funcionario = employee;
    }

    public Long getMirrorId() {
        return mirrorId;
    }

    public void setMirrorId(Long mirrorId) {
        this.mirrorId = mirrorId;
    }
}