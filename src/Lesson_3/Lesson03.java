package Lesson_3;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lesson03 {
    private static String task = "1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся). " +
            "Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем). " +
            "Посчитать сколько раз встречается каждое слово.";

    public static void main(String[] args) {
        List<String> list = getListFromString(task);
        printList(list, true, false);
        printList(list, false, true);
    }

    private static List<String> getListFromString(String str){
        List<String> list = new ArrayList<>();

        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я]+");
        Matcher matcher = pattern.matcher(str);

        while(matcher.find()){
            list.add(matcher.group().toLowerCase());
        }

        return list;
    }

    private static void printList(List<String> list, Boolean getUnique, Boolean getCount){
        Map<String, Integer> map = new HashMap<>();

        for (String str : list){
            Integer num = map.get(str);
            map.put(str, num == null ? 1 : num + 1);
        }

        for(Map.Entry<String, Integer> entry : map.entrySet()){
            if (getUnique == true && entry.getValue() == 1){
                printKeyValue(entry.getKey(), entry.getValue(), getCount);
            } else if (getUnique == false){
                printKeyValue(entry.getKey(), entry.getValue(), getCount);
            }
        }
    }

    private static void printKeyValue(String key, Integer value, Boolean getCount){
        System.out.print(key);
        if (getCount == true) System.out.println(" - " + value);
        else System.out.println();
    }

}
