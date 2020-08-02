package it.justjoin.service;

import it.justjoin.model.Book;
import it.justjoin.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewService reviewService;
    private final EntityManager entityManager;

    @Transactional
    public void displayReviewsByBookIds(List<Long> ids) {
        bookRepository.findAllById(ids).stream()
                .flatMap(b -> b.getReviews().stream())
                .forEach(reviewService::display);
    }

    public void removeAwardById(Long bookId, String awardName) {
        Book book = entityManager.createQuery("from Book b left join fetch b.awards where b.id = :id", Book.class)
                .setParameter("id", bookId)
                .getSingleResult();
        book.setAwards(
                book.getAwards().stream()
                        .filter(a -> !a.getName().matches(".*" + awardName + ".*"))
                        .collect(Collectors.toList())
        );
        bookRepository.save(book);
    }

    @Transactional
    public void removeAwardByIdNative(Long bookId, String awardName) {
        entityManager.createNativeQuery("delete from award where book_id = :id and name ~ :name")
                .setParameter("id", bookId)
                .setParameter("name", awardName)
                .executeUpdate();
    }

}
