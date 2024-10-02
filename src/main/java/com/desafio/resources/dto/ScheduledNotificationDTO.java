package com.desafio.resources.dto;

import java.time.LocalDateTime;

public class ScheduledNotificationDTO {
    private Long usuarioId;
    private String mensagem;
    private LocalDateTime agendamento;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(LocalDateTime agendamento) {
        this.agendamento = agendamento;
    }
}
