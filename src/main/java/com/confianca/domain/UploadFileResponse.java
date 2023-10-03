package com.confianca.domain;

public class UploadFileResponse {
    // You may add other file properties like size, file type, etc.
    private String fileName;

    public UploadFileResponse(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
