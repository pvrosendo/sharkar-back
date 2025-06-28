package com.rosendo.sharkar.application.service;

import com.rosendo.sharkar.presentation.dto.AccountCredentialsDto;
import com.rosendo.sharkar.presentation.dto.TokenDto;
import com.rosendo.sharkar.domain.model.User;
import com.rosendo.sharkar.domain.repository.UserRepository;
import com.rosendo.sharkar.exception.RequiredObjectIsNullException;
import com.rosendo.sharkar.infrastructure.security.JwtTokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    public TokenDto signIn(AccountCredentialsDto credentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getEmail() != null ? credentials.getEmail() : credentials.getUsername(),
                        credentials.getPassword()
                )
        );
        var user = getByEmailOrUsername(credentials.getEmail(), credentials.getUsername());

        return tokenProvider.createAccessToken(
                user.getUsername(),
                user.getEmail(),
                user.getRoles()
        );
    }

    public TokenDto refreshToken(String username, String refreshToken) {
        var user = userRepository.findUserByUserName(username);
        TokenDto token;
        if (user != null) {
            token = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
        return token;
    }

    public AccountCredentialsDto create(AccountCredentialsDto user) {

        if (user == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one new User!");
        var entity = new User();
        entity.setFullName(user.getFullName());
        entity.setUserName(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(generateHashedPassword(user.getPassword()));
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setEnabled(true);

        var dto = userRepository.save(entity);
        return new AccountCredentialsDto(dto.getUsername(),  dto.getEmail(), dto.getPassword(), dto.getFullName());
    }

    private String generateHashedPassword(String password) {

        PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
                "", 8, 185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pbkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return passwordEncoder.encode(password);
    }

    public User getByEmailOrUsername(String email, String username){
        var userEmail = userRepository.findUserByEmail(email);

        if (userEmail == null) {
            var userUsername = userRepository.findUserByUserName(username);
            if (userUsername == null) {

                throw new UsernameNotFoundException("Username or email not found!");
            }
            return userUsername;
        }
        return userEmail;
    }

    public User updateUser(AccountCredentialsDto credentials) {
        User user = getByEmailOrUsername(credentials.getEmail(), credentials.getUsername());

        user.setFullName(credentials.getFullName());
        user.setUserName(credentials.getUsername());
        user.setEmail(credentials.getEmail());
        user.setPassword(generateHashedPassword(credentials.getPassword()));

        return userRepository.save(user);
    }

    public User updateUserPassword(AccountCredentialsDto credentials) {
        User user = getByEmailOrUsername(credentials.getEmail(), credentials.getUsername());
        user.setPassword(generateHashedPassword(credentials.getPassword()));
        return userRepository.save(user);
    }

    public boolean parametersAreInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }

    public boolean credentialsIsInvalid(AccountCredentialsDto credentials) {
        return credentials == null ||
                StringUtils.isBlank(credentials.getPassword()) ||
                (StringUtils.isBlank(credentials.getUsername()) && StringUtils.isBlank(credentials.getEmail()));
    }
}
