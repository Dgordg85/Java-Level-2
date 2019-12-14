package Lesson_1.Marathon;

import Lesson_1.Marathon.Competitors.Cat;
import Lesson_1.Marathon.Competitors.Course;
import Lesson_1.Marathon.Competitors.Dog;
import Lesson_1.Marathon.Competitors.Human;
import Lesson_1.Marathon.Obstacles.Cross;
import Lesson_1.Marathon.Obstacles.Team;
import Lesson_1.Marathon.Obstacles.Wall;
import Lesson_1.Marathon.Obstacles.Water;

public class Main {
    public static void main(String[] args) {
       Team team = new Team(
               "Отряд мстителей",
               new Human("Боб"),
               new Cat("Барсик"),
               new Dog("Бобик"),
               new Human("Антон")
       );
       Course course = new Course(
               new Cross(80),
               new Wall(2),
               new Water(50)
       );

       course.doIt(team);
       team.showResults();

       team.show();
    }
}

