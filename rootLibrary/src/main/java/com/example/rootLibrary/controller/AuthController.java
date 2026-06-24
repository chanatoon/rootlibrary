package com.example.rootLibrary.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rootLibrary.service.AuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String employeeId,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        if (authService.login(employeeId, password)) {
            session.setAttribute("loggedIn", true);
            return "redirect:/books";
        }
        model.addAttribute("error", "社員番号またはパスワードが正しくありません");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
