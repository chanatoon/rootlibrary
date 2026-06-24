package com.example.rootLibrary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.rootLibrary.entity.Book;
import com.example.rootLibrary.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return bookRepository.findAll();
        }
        return bookRepository.findByTitleContainingOrAuthorContaining(keyword, keyword);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("書籍が見つかりません: " + id));
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
