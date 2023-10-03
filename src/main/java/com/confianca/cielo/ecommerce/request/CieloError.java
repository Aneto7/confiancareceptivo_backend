package com.confianca.cielo.ecommerce.request;

public class CieloError {
    private final Integer Code;
    private final String Message;

    public CieloError(Integer code, String message) {
        this.Code = code;
        this.Message = message;
    }

    /**
     * @return The error code as specified on manual
     */
    public Integer getCode() {
        return Code;
    }

    /**
     * @return The error message as specified on manual
     */
    public String getMessage() {
        return Message;
    }
}
