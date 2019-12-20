package Lesson_3;

import java.util.HashMap;
import java.util.HashSet;

public class PhoneBook {
    private HashMap<String, HashSet<String>> phoneBook;

    public PhoneBook() {
        this.phoneBook = new HashMap<>();
    }

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Макаров", "+79123562850");
        phoneBook.add("Макаров", "+79123562750");
        phoneBook.add("Карпов", "+73455353608");
        phoneBook.get("Марков");
        phoneBook.get("Макаров");
    }

    public void add(String soname, String telephone){
        HashSet<String> hs = phoneBook.getOrDefault(soname, new HashSet<>());
        hs.add(telephone);
        phoneBook.put(soname, hs);
    }

    public void get(String soname){
        if (phoneBook.containsKey(soname))
            System.out.println(phoneBook.get(soname));
        else
            System.out.println("Такой фамилии нет!");
    }
}
