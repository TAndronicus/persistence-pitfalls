package it.justjoin.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    void nPlusOneProblemExample() {
        reviewService.displayAllNPlusOne();
    }

    @Test
    void nPlusOneWorkarounds() {
        reviewService.displayAllEager();
    }
}
