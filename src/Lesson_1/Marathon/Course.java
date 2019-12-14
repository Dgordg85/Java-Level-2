package Lesson_1.Marathon;

import Lesson_1.Marathon.Competitors.Competitor;
import Lesson_1.Marathon.Obstacles.Obstacle;

public class Course {
    private Obstacle[] obstacles;

    public Course(Obstacle... obstacles) {
        this.obstacles = obstacles;
    }

    public void doIt(Team team){
        for (Competitor c : team.getCompetitors()) {
            for (Obstacle o : obstacles) {
                o.doIt(c);
                if (!c.isOnDistance()) break;
            }
        }
    }
}
