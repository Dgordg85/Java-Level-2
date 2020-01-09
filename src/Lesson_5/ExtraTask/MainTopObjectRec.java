package Lesson_5.ExtraTask;

import java.util.ArrayList;
import java.util.List;

public class MainTopObjectRec {

    private static List<TopObject> topObjects = new ArrayList<>();
    private static List<TopObject> topObjectsResultList = new ArrayList<>();

    public static void main(String[] args) {
        topObjects.add(new TopObject(1, null));
        topObjects.add(new TopObject(2, "1"));
        topObjects.add(new TopObject(3, "2"));
        topObjects.add(new TopObject(4, "1"));
        topObjects.add(new TopObject(5, null));
        topObjects.add(new TopObject(6, "5"));
        topObjects.add(new TopObject(7, null));
        topObjects.add(new TopObject(8, "3"));

        for (TopObject obj : topObjects) {
            add(obj);
        }
    }


    private static void add(TopObject obj) {
        if (!obj.hasParent()) {
            topObjectsResultList.add(obj);
        } else {
            for (int i = 0; i < topObjectsResultList.size(); i++) {

                addChild(obj, i);

                if (topObjectsResultList.get(i).getId() == Integer.parseInt(obj.getParentId())) {
                    topObjectsResultList.get(i).setChild(obj);
                    if (!obj.hasParent()) {
                        add(topObjectsResultList.get(i));
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private static void addChild(TopObject obj, int i) {
        if (topObjectsResultList.get(i).getChildren().size() != 0) {
            for (int j = 0; j < topObjectsResultList.get(i).getChildren().size(); j++) {
                if (topObjectsResultList.get(i).getChildren().get(j).getId() == Integer.parseInt(obj.getParentId())) {
                    topObjectsResultList.get(i).getChildren().get(j).setChild(obj);
                }
            }
        }
    }
}
