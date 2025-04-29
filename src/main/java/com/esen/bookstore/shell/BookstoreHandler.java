package com.esen.bookstore.shell;


import com.esen.bookstore.model.Book;
import com.esen.bookstore.model.Bookstore;
import com.esen.bookstore.service.BookstoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

@ShellComponent
@ShellCommandGroup("Bookstore related commands")
@RequiredArgsConstructor
public class BookstoreHandler {

    private final BookstoreService bookstoreService;

    @ShellMethod(value = "List all bookstores", key = "list bookstores")
    public String listBookstores() {
        return bookstoreService.findAll().stream()
                .map(bookstore ->
                {
                    bookstore.setInventory(null);
                    return bookstore;
                })
                .map(Bookstore::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(value = "Create a bookstore", key = "create bookstore")
    public void createBookstore(String location, Double priceModifier, Double moneyInRegister) {
        bookstoreService.save(Bookstore.builder()
                .location(location)
                .moneyInRegister(moneyInRegister)
                .priceModifier(priceModifier)
                .build());
    }

    @ShellMethod(value = "Delete bookstore by ID", key = "delete bookstore")
    public void deleteBookstore(long id) {
        bookstoreService.delete(id);
    }

}
