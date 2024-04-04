package ua.dokat.entity.http;

public record TraderInformation(String token, String cookie) {

    @Override
    public String token() {
        return token;
    }

    @Override
    public String cookie() {
        return cookie;
    }
}
