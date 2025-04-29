package com.esen.bookstore.shell;


import com.esen.bookstore.model.Book;
import com.esen.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("Book related commands")
@RequiredArgsConstructor
public class BookHandler {

    private final BookService bookService;

    @ShellMethod(value = "Create a book", key = "create book")
    public void createBook(String publisher, Double price, String title, String author) {
        bookService.save(Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .price(price)
                .build());
    }

    @ShellMethod(value = "List all books", key = "list books")
    public String listBooks() {
        return bookService.findAll().stream()
                .map(Book::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(value = "Delete book by ID", key = "delete book")
    public void deleteBook(long id) {
        bookService.delete(id);
    }

    @ShellMethod(value = "Update book", key = "update book")
    public String updateBook(@ShellOption(defaultValue = ShellOption.NULL) Long id,
                             @ShellOption(defaultValue = ShellOption.NULL) String publisher,
                             @ShellOption(defaultValue = ShellOption.NULL) Double price,
                             @ShellOption(defaultValue = ShellOption.NULL) String title,
                             @ShellOption(defaultValue = ShellOption.NULL) String author) {
        return bookService.update(id, publisher, price, title, author).toString();
    }
}
