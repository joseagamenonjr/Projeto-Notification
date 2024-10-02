package com.desafio.entities.enums;

//Tipo enumerado para classificar status do pedido sobre pagamento
//Utilizado para service de notificação
public enum Status {

    PAID(1),
    CANCELED(2),
    WAITING_PAYMENT(3),
    PENDING(0);

    private int code;

    private Status(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static Status valueOf(int code){
        for (Status value : Status.values()){
            if(value.getCode() == code){
                return value;
            }
        }
        throw new IllegalArgumentException("Código inválido");
    }
}
