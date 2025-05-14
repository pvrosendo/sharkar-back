package com.rosendo.dream_car.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rosendo.dream_car.presentation.dto.TokenDto;
import com.rosendo.dream_car.exception.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-lenght:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    @Value("${security.jwt.cookie.name:jwt}")
    private String jwtCookieName = "jwt";

    @Value("${security.jwt.cookie.refresh-name:refresh}")
    private String refreshCookieName = "refresh";

    @Value("${security.jwt.cookie.domain:localhost}")
    private String cookieDomain = "localhost";

    @Value("${security.jwt.cookie.secure:true}")
    private boolean cookieSecure = true;

    @Value("${security.jwt.cookie.http-only:true}")
    private boolean cookieHttpOnly = true;

    @Value("${security.jwt.cookie.same-site:Strict}")
    private String cookieSameSite = "Strict";

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDto createAccessToken(String username, String email, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        String accessToken = getAccessToken(username, email, roles, now, validity);
        String refreshToken = getRefreshToken(username, email, roles, now);
        return new TokenDto(username, email, true, now, validity, accessToken, refreshToken);
    }

    public TokenDto refreshToken(String refreshToken) {
        var token = "";
        if(refreshTokenContainsBearer(refreshToken)) {
            token = refreshToken.substring("Bearer ".length());
        }

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getClaim("username").asString();
        String email = decodedJWT.getClaim("email").asString();

        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        return createAccessToken(username, email, roles);
    }

    private String getRefreshToken(String username, String email,  List<String> roles, Date now) {
        Date refreshTokenValidity = new Date(now.getTime() + (validityInMilliseconds * 3));;
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(refreshTokenValidity)
                .withSubject(email != null ? email : username)
                .sign(algorithm);
    }

    private String getAccessToken(String username, String email, List<String> roles, Date now, Date validity) {

        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(email != null ? email : username)
                .withIssuer(issuerUrl)
                .sign(algorithm);
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }

    public String resolveToken(HttpServletRequest request) {
        // First try to get token from cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (jwtCookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        
        // Fallback to Authorization header for backward compatibility
        String bearerToken = request.getHeader("Authorization");
        if (refreshTokenContainsBearer(bearerToken)) {
            return bearerToken.substring("Bearer ".length());
        }
        
        return null;
    }

    private static boolean refreshTokenContainsBearer(String refreshToken) {
        return StringUtils.isNotBlank(refreshToken) && refreshToken.startsWith("Bearer ");
    }

    public boolean validateToken(String token){
        DecodedJWT decodedJWT = decodedToken(token);
        try {
            if(decodedJWT.getExpiresAt().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or Invalid JWT Token!");
        }
    }

    public void setTokenCookies(HttpServletResponse response, TokenDto token) {
        Cookie accessTokenCookie = createCookie(
            jwtCookieName,
            token.getAccessToken(),
            (int) (validityInMilliseconds / 1000),
            true
        );
        
        Cookie refreshTokenCookie = createCookie(
            refreshCookieName,
            token.getRefreshToken(),
            (int) ((validityInMilliseconds * 3) / 1000),
            true
        );

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    public void clearTokenCookies(HttpServletResponse response) {
        Cookie accessTokenCookie = createCookie(jwtCookieName, "", 0, false);
        Cookie refreshTokenCookie = createCookie(refreshCookieName, "", 0, false);
        
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private Cookie createCookie(String name, String value, int maxAge, boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(cookieDomain);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setSecure(cookieSecure);
        cookie.setHttpOnly(httpOnly);
        
        // Set SameSite attribute
        String sameSite = cookieSameSite;
        if (sameSite.equalsIgnoreCase("None")) {
            cookie.setSecure(true); // SameSite=None requires Secure
        }
        
        return cookie;
    }
}
