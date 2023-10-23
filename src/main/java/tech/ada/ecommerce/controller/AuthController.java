package tech.ada.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.ecommerce.dto.LoginDTO;
import tech.ada.ecommerce.response.AuthResponse;
import tech.ada.ecommerce.service.AuthService;

@RestController
@RequestMapping("/api/v1/login")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

}
