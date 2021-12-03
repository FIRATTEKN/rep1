package edu.estu;

public class MyGenericList<V1,V2> {
    private V1 token; //first member of pair
    private V2 fNumber; //second member of pair

    public MyGenericList(V1 first, V2 second) {
        this.token = first;
        this.fNumber = second;
    }

    public void setFirst(V1 first) {
        this.token = first;
    }

    public void setSecond(V2 second) {
        this.fNumber = second;
    }

    public V1 getFirst() {
        return token;
    }

    public V2 getSecond() {
        return fNumber;
    }

    public void print(){
        System.out.println(this.token+" "+this.fNumber);
    }
}
