package it.justjoin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Book extends AbstractEntity {

    private String title;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews;

    @ElementCollection
    @CollectionTable(
            name = "award",
            joinColumns = @JoinColumn(name = "book_id")
    )
    private List<Award> awards;

}
