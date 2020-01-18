package Lesson_5.ExtraTask;

import java.util.ArrayList;

public class TopObject {
    private int id;
    private String parentId;
    private ArrayList<TopObject> children = new ArrayList<>();

    TopObject(int id, String parentId){
        this.id = id;
        this.parentId = parentId;
    }

    public int getId(){
        return id;
    }

    public String getParentId(){
        return parentId;
    }

    public ArrayList<TopObject> getChildren() {
        return children;
    }

    public boolean hasParent(){
        if(this.parentId != null){
            return true;
        }
        return false;
    }

    public void showChildren() {
        if(children.isEmpty()){
            System.out.println("No child objects");
            return;
        }
        for (TopObject child : children) {
            System.out.println(child.getId());
        }
    }

    public void setChild(TopObject child) {
        this.children.add(child);
    }

}
