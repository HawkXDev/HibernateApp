package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Item.class);

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.getCurrentSession();) {

            session.beginTransaction();

            Person person = session.get(Person.class, 3);
            System.out.println(person);

            List<Item> items = person.getItems();
            for (Item item : items) {
                System.out.println(item);
            }

//            Item item = session.get(Item.class, 5);
//            System.out.println(item);
//            Person owner = item.getOwner();
//            System.out.println(owner);

//            Person person1 = session.get(Person.class, 2);
//            Item item1 = new Item("item1", person1);
//            person1.getItems().add(item1);
//            session.persist(item1);

            Person person2 = new Person("Test", 10);
            Item item2 = new Item("item2", person2);
            person2.setItems(new ArrayList<>(Collections.singletonList(item2)));
            session.persist(person2);
            session.persist(item2);

            Person person3 = session.get(Person.class, 3);
            List<Item> items1 = person3.getItems();
            for (Item itm : items1) {
                session.remove(itm);
            }
            person.getItems().clear();

//            Person person4 = session.get(Person.class, 2);
//            session.remove(person4);
//            person.getItems().forEach(i -> i.setOwner(null));

            Person person5 = session.get(Person.class, 4);
            Item item = session.get(Item.class, 1);
            item.getOwner().getItems().remove(item);
            item.setOwner(person5);
            person5.getItems().add(item);

            session.getTransaction().commit();
        }
    }
}
