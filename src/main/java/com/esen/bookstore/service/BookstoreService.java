package com.esen.bookstore.service;


import com.esen.bookstore.model.Book;
import com.esen.bookstore.model.Bookstore;
import com.esen.bookstore.repository.BookRepository;
import com.esen.bookstore.repository.BookStoreRepository;
import lombok.RequiredArgsConstructor;
import org.pmw.tinylog.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookstoreService {

    private final BookStoreRepository repository;

    public void removeBookFromInventories(Book book){
        repository.findAll()
                .forEach(bookstore -> {
                    bookstore.getInventory().remove(book);
                    repository.save(bookstore);
                });
    }

    public List<Bookstore> findAll() {
        return repository.findAll();
    }

    public void save(Bookstore bookstore){
        repository.save(bookstore);
    }

    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new IllegalArgumentException("Cannot find bookstore");
        }
        repository.deleteById(id);

        Logger.info("Delete sucessful.");
    }

    public Bookstore update(Long id, String location, Double priceModifier, Double moneyInRegister) {
        if (Stream.of(location, priceModifier, moneyInRegister).allMatch(Objects::isNull)) {
            throw new IllegalArgumentException("At least one input is required");
        }

        if(!repository.existsById(id)) {
            throw new IllegalArgumentException("Cannot find bookstore");
        }

        var bookstore = repository.findById(id).get();

        if (location != null) { bookstore.setLocation(location);}
        if (priceModifier != null) { bookstore.setPriceModifier(priceModifier);}
        if (moneyInRegister != null) { bookstore.setMoneyInRegister(moneyInRegister);}

        return repository.save(bookstore);
    }

}
