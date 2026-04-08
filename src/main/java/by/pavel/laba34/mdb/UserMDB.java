package by.pavel.laba34.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

@JMSDestinationDefinition(
        name = "java:app/jms/UserQueue",
        interfaceName = "jakarta.jms.Queue",
        destinationName = "UserQueuePhysical"
)
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/UserQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
})
public class UserMDB implements MessageListener {

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage textMessage) {
            try {
                System.out.println("JMS MDB: Обработка пользователя с ID: " + textMessage.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}