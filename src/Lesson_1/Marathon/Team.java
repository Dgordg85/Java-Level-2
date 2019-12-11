package Lesson_1.Marathon;

import Lesson_1.Marathon.Competitors.Competitor;

public class Team {
    private String name;
    private Competitor[] competitors;

    public Team(String name, Competitor... competitors) {
        this.name = name;
        this.competitors = new Competitor[competitors.length];
        for (int i = 0; i < competitors.length; i++) {
            this.competitors[i] = competitors[i];
        }
    }

    public Competitor[] getCompetitors() {
        Competitor[] competitorsCopy = new Competitor[competitors.length];
        for (int i = 0; i < competitors.length; i++) {
            competitorsCopy[i] = competitors[i];
        }
        return competitorsCopy;
    }

     public void showResults(){
         for (Competitor c : this.competitors) {
             c.info();
         }
     }


     public void show(){
         System.out.print("Название команды: " + this.name + ". Участники: ");
         for (Competitor c: getCompetitors()){
             System.out.print(c.getName() + " ");
         }
         System.out.println();
     }
}
