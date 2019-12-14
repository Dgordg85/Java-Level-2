package Lesson_2.Enum;

public enum Days {
    Monday("Понедельник", false),
    Tuesday("Вторник", false),
    Wednesday("Среда", false),
    Thursday("Четверг", false),
    Friday("Пятница", false),
    Saturday("Суббота", true),
    Sunday("Воскресенье", true);

    private String nameRus;
    private boolean isWeekend;

    Days(String nameRus, boolean isWeekend) {
        this.nameRus = nameRus;
        this.isWeekend = isWeekend;
    }

    public String getNameRus() {
        return nameRus;
    }

    public boolean isWeekend() {
        return isWeekend;
    }
}


class MainEnum{
    public static void main(String[]args){
        System.out.println(getDayStatus(Days.Friday));
        System.out.println(getDayStatus(Days.Wednesday));
        System.out.println(getDayStatus(Days.Monday));
        System.out.println(getDayStatus(Days.Saturday));
        System.out.println(getDayStatus(Days.Thursday));
        System.out.println(getDayStatus(Days.Tuesday));
        System.out.println(getDayStatus(Days.Sunday));
    }

    private static String getDayStatus(Days day){
        String result = day.getNameRus() + " ";

        if (day.isWeekend()) result += "выходной";
        else {
            result += (5 - day.ordinal()) * 8;

            if (day.ordinal() == 2 || day.ordinal() == 1)
                result += " часа";
            else
                result += " часов";
        }

        return result;
    }
}
