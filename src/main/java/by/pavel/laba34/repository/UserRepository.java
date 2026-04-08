package by.pavel.laba34.repository;

import by.pavel.laba34.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class UserRepository {

    @PersistenceContext(unitName = "demoPU")
    private EntityManager em;

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u ORDER BY u.id", User.class)
                .getResultList();
    }

    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public User save(User user) {
        em.persist(user);
        em.flush();
        return user;
    }

    public User update(User user) {
        return em.merge(user);
    }

    public void delete(Long id) {
        User user = findById(id);
        if (user != null) {
            em.remove(user);
        }
    }
}