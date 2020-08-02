package it.justjoin.service;

import it.justjoin.model.Review;
import it.justjoin.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final EntityManager entityManager;
    private final ReviewRepository reviewRepository;

    public void display(Review review) {
        System.out.println(
//                review.getBook().getTitle() + ", grade: " +
                        review.getGrade() + ", comment: " + review.getReviewComment());
    }

    public void displayAllNPlusOne() {
        ((List<Review>) entityManager.createNativeQuery("select * from review", Review.class)
                .getResultList()).forEach(this::display);
    }

    public void displayAllEager() {
        entityManager
                .createQuery("select r from Review r left join fetch r.book", Review.class)
                .getResultList().forEach(this::display);
    }

}
