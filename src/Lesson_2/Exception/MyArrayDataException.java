package Lesson_2.Exception;

public class MyArrayDataException extends Exception  {

    public MyArrayDataException(int i, int j) {
        super("Ошибка c данными в массиве. Ячейка [" + i + ", " + j + "]");
    }
}
