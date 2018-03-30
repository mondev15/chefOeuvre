package model;

/**
 * Model of the route
 * 
 * @author Evergiste
 */

public class Route {

    private String dep;
    private String arr;
    private String list;

    public Route(String dep, String arr, String list) {
        this.dep = dep;
        this.arr = arr;
        this.list = list;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getArr() {
        return arr;
    }

    public void setArr(String arr) {
        this.arr = arr;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "{" + "dep=" + dep + ", arr=" + arr + ", list=" + list + '}';
    }
    
}
