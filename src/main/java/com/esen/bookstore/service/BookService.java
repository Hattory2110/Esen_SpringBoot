package com.esen.bookstore.service;


import com.esen.bookstore.model.Book;
import com.esen.bookstore.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pmw.tinylog.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookstoreService bookstoreService;

    public void save(Book book) {
        bookRepository.save(book);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void delete(Long id) {
        if(!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot find book");
        }

        var book = bookRepository.findById(id).get();
        bookstoreService.removeBookFromInventories(book);
        bookRepository.deleteById(id);

        Logger.info("Delete sucessful.");
    }


    public Book update(Long id, String publisher, Double price, String title, String author) {
        if (Stream.of(title, author, publisher, price).allMatch(Objects::isNull)) {
            throw new IllegalArgumentException("At least one input is required");
        }

        if(!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot find book");
        }

        var book = bookRepository.findById(id).get();

        if (title != null) { book.setTitle(title);}
        if (publisher != null) { book.setPublisher(publisher);}
        if (price != null) { book.setPrice(price);}
        if (author != null) { book.setAuthor(author);}

        return bookRepository.save(book);
    }
}
