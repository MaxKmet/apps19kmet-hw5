package ua.edu.ucu.iterator;

import java.util.ArrayList;
import java.util.Iterator;

public class StreamIterator implements Iterator<Integer> {

    private int curIndex = 0;
    private ArrayList<Integer> collection;

    public StreamIterator(ArrayList<Integer> lst) {
        this.collection = lst;
    }

    public StreamIterator() {
        this.collection = new ArrayList<>();
    }

    @Override
    public boolean hasNext() {
        return (curIndex < collection.size());
    }

    @Override
    public Integer next() {
        Integer nextElement = collection.get(curIndex);
        curIndex += 1;
        return nextElement;
    }


}
