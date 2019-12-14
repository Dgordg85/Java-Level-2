package Lesson_2.Exception;

public class MyArraySizeException extends Exception {

    public MyArraySizeException() {
        super("Необходимо подавать строковый массив размером 4х4");
    }
}
