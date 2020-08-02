package it.justjoin.service;

import it.justjoin.model.Book;
import it.justjoin.model.Review;
import it.justjoin.repo.BookRepository;
import it.justjoin.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final EntityManager entityManager;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public void display(Review review) {
        System.out.println(review.getGrade() + ", comment: " + review.getReviewComment());
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

    @Transactional
    public void updateGrade(Long id, short newGrade) {
        reviewRepository.findById(id)
                .ifPresent(review -> {
                    review.setGrade(newGrade);
                    reviewRepository.save(review);
                });
    }

    public void createReviewJpaRepo(Long bookId, String content, Short grade) {
        bookRepository.findById(bookId)
                .ifPresent(book ->
                        reviewRepository.save(
                                Review.builder()
                                        .book(book)
                                        .reviewComment(content)
                                        .grade(grade)
                                        .build()
                        )
                );
    }

    @Transactional
    public void createReviewEM(Long bookId, String content, Short grade) {
        entityManager.persist(
                Review.builder()
                        .book(entityManager.find(Book.class, bookId))
                        .reviewComment(content)
                        .grade(grade)
                        .build()
        );
    }

    @Transactional
    public void createReviewReference(Long bookId, String content, Short grade) {
        entityManager.persist(
                Review.builder()
                        .book(entityManager.getReference(Book.class, bookId))
                        .reviewComment(content)
                        .grade(grade)
                        .build()
        );
    }

}
