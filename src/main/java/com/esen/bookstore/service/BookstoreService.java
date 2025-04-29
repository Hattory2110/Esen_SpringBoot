package com.esen.bookstore.service;


import com.esen.bookstore.model.Book;
import com.esen.bookstore.model.Bookstore;
import com.esen.bookstore.repository.BookRepository;
import com.esen.bookstore.repository.BookStoreRepository;
import lombok.RequiredArgsConstructor;
import org.pmw.tinylog.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
