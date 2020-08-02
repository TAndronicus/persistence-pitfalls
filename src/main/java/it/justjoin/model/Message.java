package it.justjoin.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Message extends AbstractEntity {

    private String title;
    private String content;
    private Long senderId;
    private Long receiverId;
    private boolean read;

}
