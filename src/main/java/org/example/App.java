package org.example;

import org.example.model.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Passport.class);

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.getCurrentSession()) {

            session.beginTransaction();

            Person person = session.get(Person.class, 1);
            System.out.println("Get person");

            // Get related entities (Lazy)
            System.out.println(person.getItems());

            Item item = session.get(Item.class, 1);
            System.out.println("Get item");

            // Get related owner (Eager)
            System.out.println(item.getOwner());

            Person person1 = session.get(Person.class, 10);

            Person person2 = session.get(Person.class, 4);
            Hibernate.initialize(person2.getItems());

            session.getTransaction().commit();

//            # LazyInitializationException
//            System.out.println(person1.getItems());

            System.out.println(person2.getItems());

            // Open the session and transaction again
            Session session1 = sessionFactory.getCurrentSession();
            session1.beginTransaction();

            System.out.println("Inside the second transaction");

            List<Item> items = session1.createQuery("select i from Item i where i.owner.id=:personId",
                            Item.class).setParameter("personId", person1.getId()).list();

            session1.getTransaction().commit();

            System.out.println(items);
        }
    }
}
