package com.example.rootLibrary.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rootLibrary.entity.Book;
import com.example.rootLibrary.service.BookService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String keyword,
                       HttpSession session,
                       Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("books", bookService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "book/list";
    }

    @GetMapping("/new")
    public String newForm(HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("book", new Book());
        return "book/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("book", bookService.findById(id));
        return "book/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Book book, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        bookService.save(book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        bookService.delete(id);
        return "redirect:/books";
    }

    private boolean isLoggedIn(HttpSession session) {
        return Boolean.TRUE.equals(session.getAttribute("loggedIn"));
    }
}
