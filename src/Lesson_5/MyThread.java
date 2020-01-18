package Lesson_5;

public class MyThread implements Runnable {
    private float[] arr;
    private int listInt;

    @Override
    public void run() {
        for (int i = 0; i < this.arr.length; i++) {
            this.arr[i] = (float)(this.arr[i] * Math.sin(0.2f + i/ 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            //System.out.println("Работает нить " + listInt);
        }

        Homework.setList(this.listInt, this.arr);
        System.out.println("Нить " + listInt + " завершила работу");
    }

    public MyThread(float[] arr, int listInt) {
        this.arr = arr;
        this.listInt = listInt;
    }
}
