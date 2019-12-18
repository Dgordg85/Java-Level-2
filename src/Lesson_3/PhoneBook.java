package Lesson_3;

import java.util.HashMap;
import java.util.Map;

public class PhoneBook {
    private static HashMap<String, String> phoneBook = new HashMap<>();

    static {
        phoneBook.put("+73455353607", "Марков");
        phoneBook.put("+73455353608", "Марков");
        phoneBook.put("+79123540789", "Иванов");
    }

    public static void main(String[] args) {
        add("Макаров", "+79123562850");
        add("Карпов", "+73455353608");
        get("Марков");
        get("Макаров");
    }

    public static void add(String soname, String telephone){
        if (!phoneBook.containsKey(telephone)){
            phoneBook.put(telephone, soname);
        } else {
            System.out.println("Человек с таким номером уже есть в базе.");
        }

    }

    public static void get(String soname){
        if (phoneBook.containsValue(soname)){
            System.out.println(soname + ":");
            for(Map.Entry<String, String> entry : phoneBook.entrySet()){
                if (entry.getValue() == soname){
                    System.out.println(entry.getKey());
                }
            }
        }
    }
}
