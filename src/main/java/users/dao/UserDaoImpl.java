package users.dao;

import users.model.User;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {
//
//    private SessionFactory sessionFactory;
//
//    @Autowired
//    public void setSessionFactory(SessionFactory sessionFactory){
//        this.sessionFactory = sessionFactory;
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public List<User> allUsers() {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("FROM User").list();
//    }
//
//    @Override
//    public void add(User user) {
//        Session session = sessionFactory.getCurrentSession();
//        session.persist(user);
//    }
//
//    @Override
//    public void delete(User user) {
//        Session session = sessionFactory.getCurrentSession();
//        session.delete(user);
//    }
//
//    @Override
//    public void edit(User user) {
//        Session session = sessionFactory.getCurrentSession();
//        session.update(user);
//    }
//
//    @Override
//    public User getById(int id) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.get(User.class, id);
//    }
@PersistenceContext
private EntityManager entityManager;


    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void update(User updatedUser) {
        entityManager.merge(updatedUser);
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User getByLogin(String email) {
        return entityManager.createQuery("SELECT user FROM User user WHERE user.email=:email", User.class)
                .setParameter("email", email).getSingleResult();
    }
}



