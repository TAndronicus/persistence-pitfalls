package it.justjoin.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void nPlusOneProblemExample() {
        bookService.displayReviewsByBookIds(List.of(1L, 2L));
    }

    @Test
    void recreatingEmbeddableElementCollection() {
        bookService.removeAwardById(2L, "Dijkstra");
    }

    @Test
    void embeddableElementCollectionNative() {
        bookService.removeAwardByIdNative(2L, "Dijkstra");
    }

}
