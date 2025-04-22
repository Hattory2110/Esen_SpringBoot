package com.esen.bookstore.data;

import com.esen.bookstore.model.Book;
import com.esen.bookstore.model.Bookstore;
import com.esen.bookstore.repository.BookRepository;
import com.esen.bookstore.repository.BookStoreRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.pmw.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataLoader {

    private final BookRepository bookRepository;

    private final BookStoreRepository bookStoreRepository;

    @Value("classpath:Books.json")
    private Resource booksResource;

    @Value("classpath:BookStores.json")
    private Resource bookStoresResource;



    @PostConstruct
    public void loadData(){

        var objectMapper = new ObjectMapper();

        try {
            var booksJson = StreamUtils.copyToString(booksResource.getInputStream(), StandardCharsets.UTF_8);
            var books = objectMapper.readValue(booksJson, new TypeReference<List<Book>>() {});

            bookRepository.saveAll(books);

            var bookStoreJson = StreamUtils.copyToString(bookStoresResource.getInputStream(), StandardCharsets.UTF_8);
            var bookStores = objectMapper.readValue(bookStoreJson, new TypeReference<List<Bookstore>>() {});

            bookStoreRepository.saveAll(bookStores);

        } catch (IOException e){
            Logger.error("Cannot seed the database", e);
            System.out.println(e);
        }

    }
}
