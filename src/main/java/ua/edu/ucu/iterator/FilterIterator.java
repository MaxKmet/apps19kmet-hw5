package ua.edu.ucu.iterator;

import ua.edu.ucu.function.IntPredicate;

import java.util.Iterator;

public class FilterIterator implements Iterator<Integer> {

    private Iterator<Integer> previous;
    private IntPredicate predicate;
    private Integer nextElement;

    public FilterIterator(Iterator<Integer> previous, IntPredicate predicate) {
        this.previous = previous;
        this.predicate = predicate;
        this.nextElement = null;
    }

    @Override
    public boolean hasNext() {
        if (nextElement != null) {
            return true;
        } else {
            Integer t;
            while (previous.hasNext()) {
                t = previous.next();
                if (predicate.test(t)) {
                    nextElement = t;
                    return true;
                }

            }
            nextElement = null;
            return false;
        }
    }


    @Override
    public Integer next() {

        Integer t = nextElement;
        nextElement = null;
        return t;
    }


}