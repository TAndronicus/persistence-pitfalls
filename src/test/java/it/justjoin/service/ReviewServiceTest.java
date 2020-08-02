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

    @Test
    void redundantSave() {
        reviewService.updateGrade(2L, (short) 5);
    }

    @Test
    void emFindExample() {
        // select nextval('seq') jes cache'owane - poniższe rozwiązania są równoważne
        reviewService.createReviewJpaRepo(3L, "Nawet lepiej niż ok", (short) 4);
        reviewService.createReviewEM(3L, "Ok", (short) 3);
    }

    @Test
    void emGetReferenceExample() {
        reviewService.createReviewReference(3L, "Najlepsza", (short) 5);
    }
}
