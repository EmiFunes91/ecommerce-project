package com.ecommerce.controller;

import com.ecommerce.model.Usuario;
import com.ecommerce.config.JWTUtil;
import com.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/registro")
    public String registrarUsuario(@RequestBody Usuario usuario) {
        usuarioService.registrarUsuario(usuario);
        return "Usuario registrado con éxito";
    }

    @PostMapping("/login")
    public String autenticarUsuario(@RequestBody Usuario usuario) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getContraseña())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Credenciales inválidas");
        }
        return jwtUtil.generateToken(usuario.getEmail());
    }
}
