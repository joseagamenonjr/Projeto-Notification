package com.desafio.resources.dto;

public class OptOutDTO {
    private Long usuarioId;
    private boolean optOut;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public boolean isOptOut() {
        return optOut;
    }

    public void setOptOut(boolean optOut) {
        this.optOut = optOut;
    }
}
