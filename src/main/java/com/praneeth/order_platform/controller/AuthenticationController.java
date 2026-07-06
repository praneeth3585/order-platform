package com.praneeth.order_platform.controller;

import com.praneeth.order_platform.dto.AuthResponse;
import com.praneeth.order_platform.dto.LoginRequest;
import com.praneeth.order_platform.dto.RegisterRequest;
import com.praneeth.order_platform.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthResponse register(
            @RequestBody RegisterRequest request
    ) {

        return authenticationService.register(request);

    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request
    ) {

        return authenticationService.login(request);

    }

}