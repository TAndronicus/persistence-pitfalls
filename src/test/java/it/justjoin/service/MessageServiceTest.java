package it.justjoin.service;

import it.justjoin.model.Message;
import it.justjoin.repo.MessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        entityManager
                .createQuery("update Message set read = false where id <> 1")
                .executeUpdate();
    }

    @Test
    @Transactional
    void firstLevelCacheFlushExample() {
        messageService.displayMessagesAndMarkAsReadForReceiver(1L);

        messageRepository.findAllByReceiverId(1L).stream()
                .map(Message::isRead)
                .forEach(Assertions::assertTrue);
    }

}
