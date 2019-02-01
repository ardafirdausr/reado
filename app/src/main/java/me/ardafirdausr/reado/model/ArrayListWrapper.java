package me.ardafirdausr.reado.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ArrayListWrapper<Type> implements Serializable {

    private ArrayList<Type> data;

    public ArrayListWrapper(ArrayList<Type> data){
        this.data = data;
    }

    public ArrayList<Type> getData() {
        return this.data;
    }
}
