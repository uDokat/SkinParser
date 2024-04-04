package ua.dokat.entity.http;

public record HttpRequestInfo(String url, String params) {

    public String getAbsoluteUrl() {
        return url + params;
    }
}