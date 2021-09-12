package io.rachidassouani.urlshortenerservice.dto;

public class UrlErrorResponseDto {
    private String status;
    private String error;

    // Getters & Setters
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}
