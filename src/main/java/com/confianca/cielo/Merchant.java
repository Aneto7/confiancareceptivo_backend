package com.confianca.cielo;

public class Merchant {
    private final String id;
    private final String key;

    public Merchant(String id, String key) {
        this.id = id;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }
}
