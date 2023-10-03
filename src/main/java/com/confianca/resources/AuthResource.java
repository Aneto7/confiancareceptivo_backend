package com.confianca.resources;

import com.confianca.dto.CpfDTO;
import com.confianca.dto.EmailDTO;
import com.confianca.security.JWTUtil;
import com.confianca.security.UserSS;
import com.confianca.services.AuthService;
import com.confianca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/auth")
public class
AuthResource {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername(), user.getId());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-header", "Authorization");
        System.out.println("teste refresh token");
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO obj) {
        service.sendNewPassword(obj.getEmail());
        return ResponseEntity.noContent().build();
    }
}