package ua.edu.ucu.iterator;

import ua.edu.ucu.function.IntToIntStreamFunction;
import ua.edu.ucu.stream.AsIntStream;

import java.util.ArrayList;
import java.util.Iterator;

public class FlatMapIterator implements Iterator<Integer> {

    private Iterator<Integer> previous;
    private IntToIntStreamFunction operator;
    private StreamIterator tempIterator;

    public FlatMapIterator(Iterator<Integer> previous, IntToIntStreamFunction operator){
        this.previous = previous;
        this.operator = operator;
        this.tempIterator = new StreamIterator();
    }

    @Override
    public boolean hasNext() {
        return previous.hasNext() || tempIterator.hasNext();
    }

    @Override
    public Integer next(){
        if (tempIterator.hasNext()) {
            return tempIterator.next();
        }
        else {
            if (previous.hasNext()) {
                AsIntStream tempStream = (AsIntStream) operator.applyAsIntStream(previous.next());
                int[] arr = tempStream.toArray();
                ArrayList<Integer> lst = new ArrayList<Integer>();
                for(int el : arr){
                    lst.add(el);
                }
                tempIterator = new StreamIterator(lst);
                //return tempIterator.next();

            }

        }
        return null;
    }


}
