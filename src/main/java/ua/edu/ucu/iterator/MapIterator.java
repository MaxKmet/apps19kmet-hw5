package ua.edu.ucu.iterator;

import ua.edu.ucu.function.IntUnaryOperator;

import java.util.Iterator;

public class MapIterator implements Iterator<Integer> {

    private Iterator<Integer> previous;
    private IntUnaryOperator operator;

    public MapIterator(Iterator<Integer> previous, IntUnaryOperator operator){
        this.previous = previous;
        this.operator = operator;
    }

    @Override
    public boolean hasNext() {
        return previous.hasNext();
    }

    @Override
    public Integer next(){
        return operator.apply(previous.next());
    }


}
