package by.pavel.laba34.service;

import by.pavel.laba34.entity.User;
import by.pavel.laba34.messaging.UserProducer;
import by.pavel.laba34.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class UserService {

    @Inject
    private UserRepository repository;

    @Inject
    private UserProducer producer;

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUser(Long id) {
        User user = repository.findById(id);
        if (user == null) {
            throw new RuntimeException("User not found with id=" + id);
        }
        return user;
    }

    public User createUser(User user) {
        User created = repository.save(user);
        producer.sendUserCreated(created.getId());
        return created;
    }

    public User updateUser(Long id, User user) {
        User existing = getUser(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        return repository.update(existing);
    }

    public void deleteUser(Long id) {
        getUser(id);
        repository.delete(id);
    }

    public int generateUsers(int count) {
        int generatedCount = 0;
        for (int i = 1; i <= count; i++) {

            String uniqueSuffix = System.currentTimeMillis() + "_" + i;
            User user = new User("TestUser_" + i, "user_" + uniqueSuffix + "@example.com");

            User created = repository.save(user);
            producer.sendUserCreated(created.getId());
            generatedCount++;
        }
        return generatedCount;
    }
}