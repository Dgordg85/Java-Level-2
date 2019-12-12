package Lesson_1.Marathon.Competitors;

public class Human extends Animal implements Competitor {

    public Human(String name) {
        super("Человек", name, 5000, 30, 200);
    }
}
