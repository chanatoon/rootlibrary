package com.example.rootLibrary.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rootLibrary.service.BookService;
import com.example.rootLibrary.service.LendingService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/books")
public class LendingController {

    private final LendingService lendingService;
    private final BookService bookService;

    public LendingController(LendingService lendingService, BookService bookService) {
        this.lendingService = lendingService;
        this.bookService = bookService;
    }

    @GetMapping("/{id}/lend")
    public String lendForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("book", bookService.findById(id));
        return "lending/lend";
    }

    @PostMapping("/{id}/lend")
    public String lend(@PathVariable Long id,
                       @RequestParam String borrower,
                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate,
                       HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        lendingService.lend(id, borrower, dueDate);
        return "redirect:/books";
    }

    @GetMapping("/{id}/return")
    public String returnForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("book", bookService.findById(id));
        return "lending/return";
    }

    @PostMapping("/{id}/return")
    public String returnBook(@PathVariable Long id, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        lendingService.returnBook(id);
        return "redirect:/books";
    }

    private boolean isLoggedIn(HttpSession session) {
        return Boolean.TRUE.equals(session.getAttribute("loggedIn"));
    }
}
