package it.justjoin.service;

import it.justjoin.model.Message;
import it.justjoin.repo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final EntityManager entityManager;

    @Transactional
    public void displayMessagesAndMarkAsReadForReceiver(Long receiverId) {
        for (Message message : messageRepository.findAllByReceiverId(receiverId)) {
            display(message.getTitle(), message.getContent());
            message.setRead(true);
//            entityManager.flush(); // bardzo, bardzo źle
//            messageRepository.saveAndFlush(message); // bardzo źle
//            messageRepository.save(message); // źle
        }
    }

    private void display(String title, String content) {
        System.out.println(
                Stream.of(title, content)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining("\n"))
        );
    }
}
