package Lesson_2.Enum;

public enum AlternativeDays {
    Monday("Понедельник", "40 часов"),
    Tuesday("Вторник", "32 часа"),
    Wednesday("Среда", "24 часа"),
    Thursday("Четверг", "16 часов"),
    Friday("Пятница", "8 часов"),
    Saturday("Суббота", "выходной"),
    Sunday("Воскресенье", "выходной");

    private String nameRus;
    private String value;

    public String getValue() {
        return value;
    }

    public String getNameRus() {
        return nameRus;
    }

    AlternativeDays(String nameRus, String value) {
        this.nameRus = nameRus;
        this.value = value;
    }
}


class AlternativeMainEnum{
    public static void main(String[]args){
        System.out.println(getDayStatus(AlternativeDays.Friday));
        System.out.println(getDayStatus(AlternativeDays.Wednesday));
        System.out.println(getDayStatus(AlternativeDays.Monday));
        System.out.println(getDayStatus(AlternativeDays.Saturday));
        System.out.println(getDayStatus(AlternativeDays.Thursday));
        System.out.println(getDayStatus(AlternativeDays.Tuesday));
        System.out.println(getDayStatus(AlternativeDays.Sunday));
    }

    private static String getDayStatus(AlternativeDays day){
        return day.getNameRus() + " " + day.getValue();
    }
}
