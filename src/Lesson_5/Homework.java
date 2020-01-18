package Lesson_5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Homework {
    private static final int SIZE = 1000000;
    private static final int THREAD_COUNT = 8;
    private static final int PART_SIZE = SIZE / THREAD_COUNT;

    static volatile List<float[]> list = new ArrayList<>();
    private static List<Thread> listThread;

    public static void main(String[] args) throws InterruptedException {
        first();
        second();
    }

    //Первый метод
    private static void first(){
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1f);

        long beginFirst = System.currentTimeMillis();

        for (int i = 0; i < SIZE; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Метод first завершил работу");
        System.out.println("Время работы первого метода: " + (System.currentTimeMillis() - beginFirst) + "мс");

    }

    //Второй метод
    private static void second() throws InterruptedException {

        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1f);
      
        long beginSecond = System.currentTimeMillis();
        divideMassive(arr);

        listThread = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            listThread.add(new Thread(new MyThread(list.get(i), i)));
        }

        for (Thread thread : listThread){
            thread.start();
        }

        for (Thread thread : listThread){
            thread.join();
        }

        arr = assembleMassive();
        System.out.println("Метод second завершил работу");
        System.out.println("Время работы второго метода: " + (System.currentTimeMillis() - beginSecond) + "мс");
        return;
    }

    private static void divideMassive(float[] arr){
        for (int i = 0; i < THREAD_COUNT - 1; i++) {
            float[] listArr = new float[PART_SIZE];
            System.arraycopy(arr, PART_SIZE * i, listArr, 0, PART_SIZE);
            list.add(listArr);
        }

        int preLast = PART_SIZE * (THREAD_COUNT - 1);
        float[] listArr = new float[SIZE - preLast];
        System.arraycopy(arr, preLast, listArr, 0, SIZE - preLast);
        list.add(listArr);
    }


    private static float[] assembleMassive(){
        System.out.println("Собираю массив...");
        float[] arr = new float[SIZE];

        for (int i = 0; i < list.size() - 1; i++) {
            System.arraycopy(list.get(i), 0, arr, PART_SIZE * i, PART_SIZE);
        }

        System.arraycopy(list.get(list.size() - 1), 0, arr, (THREAD_COUNT - 1) * PART_SIZE, SIZE - (THREAD_COUNT - 1) * PART_SIZE);
        return arr;
    }

    synchronized static void setList(int index, float[] arr) {
        list.set(index, arr);
    }

}
