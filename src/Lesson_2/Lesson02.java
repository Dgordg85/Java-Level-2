package Lesson_2;

import Lesson_2.Exception.MyArrayDataException;
import Lesson_2.Exception.MyArraySizeException;


public class Lesson02 {

    public static void main(String[] args) {

        String[][] arr1 = new String[4][4];
        fillArray(arr1);
        arr1[0][2] = "Число";
        tryBlock(arr1);

        String[][] arr2 = new String[4][3];
        fillArray(arr2);
        tryBlock(arr2);

        String[][] arr3 = new String[4][4];
        fillArray(arr3);
        tryBlock(arr3);

    }

    private static int arraySum(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        int sum = 0;

        if (arr.length != 4 || arr[0].length != 4 || arr[1].length != 4 || arr[2].length != 4 || arr[3].length != 4) throw new MyArraySizeException();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (Exception e) {
                    throw new MyArrayDataException(i, j);
                }

            }
        }

        return sum;
    }

    private static void fillArray(String[][] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = "" + (int) (Math.random() * 100);
            }
        }
    }

    private static void tryBlock(String[][] arr){
        try {
            System.out.println(arraySum(arr));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }
}
