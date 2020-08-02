package it.justjoin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Award {

    private String name;
    private String details;
    private int year;

}
