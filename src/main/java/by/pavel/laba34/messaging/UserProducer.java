package by.pavel.laba34.messaging;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@ApplicationScoped
public class UserProducer {

    @Inject
    private JMSContext context;

    @Resource(lookup = "jms/UserQueue")
    private Queue userQueue;

    public void sendUserCreated(Long userId) {
        context.createProducer().send(userQueue, userId.toString());
        System.out.println("JMS: Отправлено сообщение о создании пользователя с ID: " + userId);
    }
}