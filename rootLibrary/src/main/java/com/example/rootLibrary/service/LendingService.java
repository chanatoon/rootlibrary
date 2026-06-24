package com.example.rootLibrary.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.rootLibrary.entity.Book;
import com.example.rootLibrary.entity.Book.BookStatus;

@Service
public class LendingService {

    private final BookService bookService;

    public LendingService(BookService bookService) {
        this.bookService = bookService;
    }

    public void lend(Long bookId, String borrower, LocalDate dueDate) {
        Book book = bookService.findById(bookId);
        if (!book.isAvailable()) {
            throw new IllegalStateException("この書籍はすでに貸出中です");
        }
        book.setStatus(BookStatus.LENDING);
        book.setBorrower(borrower);
        book.setDueDate(dueDate);
        bookService.save(book);
    }

    public void returnBook(Long bookId) {
        Book book = bookService.findById(bookId);
        if (book.isAvailable()) {
            throw new IllegalStateException("この書籍は貸出中ではありません");
        }
        book.setStatus(BookStatus.AVAILABLE);
        book.setBorrower(null);
        book.setDueDate(null);
        bookService.save(book);
    }
}
