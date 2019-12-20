package ua.edu.ucu.iterator;

import ua.edu.ucu.function.IntToIntStreamFunction;
import ua.edu.ucu.stream.AsIntStream;

import java.util.ArrayList;
import java.util.Iterator;

public class FlatMapIterator implements Iterator<Integer> {

    private Iterator<Integer> previous;
    private IntToIntStreamFunction operator;
    private StreamIterator tempIterator;
    private boolean hardStop;

    public FlatMapIterator(Iterator<Integer> previous, IntToIntStreamFunction operator){
        this.previous = previous;
        this.operator = operator;
        this.hardStop = false;
        this.tempIterator = new StreamIterator();
    }

    @Override
    public boolean hasNext() {
        return tempIterator.hasNext() || previous.hasNext() || !hardStop ;
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
                ArrayList<Integer> lst = new ArrayList<>();
                for(int el : arr){
                    lst.add(el);
                }
                tempIterator = new StreamIterator(lst);
                Integer next = tempIterator.next();

                if (!previous.hasNext()){
                    hardStop = true;
                }
                return next;

            }

        }
        return null;
    }


}
