package it.justjoin.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class Book extends AbstractEntity {

    private String title;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews;

}
