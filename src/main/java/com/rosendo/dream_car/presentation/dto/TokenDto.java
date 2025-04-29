package com.rosendo.dream_car.presentation.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String accessToken;
    private String refreshToken;

    public TokenDto() {
    }

    public TokenDto(String username, Boolean authenticated,
                    Date created, Date expiration, String accessToken,
                    String refreshToken) {
        this.username = username;
        this.authenticated = authenticated;
        this.created = created;
        this.expiration = expiration;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        TokenDto tokenDto = (TokenDto) o;
        return Objects.equals(username, tokenDto.username) && Objects.equals(authenticated, tokenDto.authenticated) && Objects.equals(created, tokenDto.created) && Objects.equals(expiration, tokenDto.expiration) && Objects.equals(accessToken, tokenDto.accessToken) && Objects.equals(refreshToken, tokenDto.refreshToken);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(username);
        result = 31 * result + Objects.hashCode(authenticated);
        result = 31 * result + Objects.hashCode(created);
        result = 31 * result + Objects.hashCode(expiration);
        result = 31 * result + Objects.hashCode(accessToken);
        result = 31 * result + Objects.hashCode(refreshToken);
        return result;
    }
}
