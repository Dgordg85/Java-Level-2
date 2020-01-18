package Lesson_5;

import java.util.ArrayList;
import java.util.List;

public class Homework {
    private static final int size = 1000000;
    private static final int threadCount = 8;
    private static final int h = size / threadCount;

    static volatile List<float[]> list = new ArrayList<>();
    private static List<Thread> listThread;

    public static void main(String[] args) throws InterruptedException {
        first();
        second();
    }

    //Первый метод
    private static void first(){
        float[] arr = fillArray(new float[size]);

        long beginFirst = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
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
        for (int i = 0; i < threadCount - 1; i++) {
            float[] listArr = new float[h];
            System.arraycopy(arr, h * i, listArr, 0, h);
            list.add(listArr);
        }

        int preLast = h * (threadCount - 1);
        float[] listArr = new float[size - preLast];
        System.arraycopy(arr, preLast, listArr, 0, size - preLast);
        list.add(listArr);
    }


    private static float[] assembleMassive(){
        System.out.println("Собираю массив...");
        float[] arr = new float[size];

        for (int i = 0; i < list.size() - 1; i++) {
            System.arraycopy(list.get(i), 0, arr, h * i, h);
        }

        System.arraycopy(list.get(list.size() - 1), 0, arr, (threadCount - 1) * h, size - (threadCount - 1) * h);
        return arr;
    }

    synchronized static void setList(int index, float[] arr) {
        list.set(index, arr);
    }

}
