package ru.kosenko.greenhouse.users.configs;

import java.util.List;

public class JwtResponse {
    private String token;
    private List<?> list;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public JwtResponse() {
    }

    public JwtResponse(String token, List<?> list) {
        this.token = token;
        this.list = list;
    }
}
