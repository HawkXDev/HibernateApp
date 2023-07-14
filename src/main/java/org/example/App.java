package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.getCurrentSession();) {

            session.beginTransaction();

            Person person = new Person("Test cascading", 30);

            person.addItem(new Item("Item1"));
            person.addItem(new Item("Item2"));
            person.addItem(new Item("Item3"));

            session.persist(person);

            session.getTransaction().commit();
        }
    }
}
