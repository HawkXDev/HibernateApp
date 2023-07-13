package org.example;

import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class);

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.getCurrentSession();) {

            session.beginTransaction();

            List<Person> list = session.createQuery("from Person where age > 20 and name like 'T%'")
                    .getResultList();
            for (Person person : list) {
                System.out.println(person);
            }

            session.createQuery("update Person set name='test' where age > 20").executeUpdate();
            session.createQuery("delete Person where age < 20").executeUpdate();

            session.getTransaction().commit();
        }
    }
}
