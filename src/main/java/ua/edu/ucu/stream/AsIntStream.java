package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;
import ua.edu.ucu.iterator.FilterIterator;
import ua.edu.ucu.iterator.FlatMapIterator;
import ua.edu.ucu.iterator.MapIterator;
import ua.edu.ucu.iterator.StreamIterator;

import java.util.ArrayList;
import java.util.Iterator;


public class AsIntStream implements IntStream {

    private Iterator<Integer> elementsIterator;

    private AsIntStream(int... values) {
        ArrayList<Integer> elements = new ArrayList<>();
        for (int v : values) {
            elements.add(v);
        }
        this.elementsIterator = new StreamIterator(elements);
    }

    private AsIntStream(Iterator<Integer> elemIterator) {
        this.elementsIterator = elemIterator;
    }

    public static IntStream of(int... values) {
        return new AsIntStream(values);
    }

    @Override
    public Double average() {
        int[] arr = this.toArray();
        if (arr.length == 0){
            throw new IllegalArgumentException();
        }
        int sum = 0;
        for (int el : arr) {
            sum += el;
        }

        return (double) sum / arr.length;
    }

    @Override
    public Integer max() {
        if (!elementsIterator.hasNext()){
            throw new IllegalArgumentException();
        }
        else {
            Integer maximum = elementsIterator.next();
            while (elementsIterator.hasNext()) {
                Integer el = elementsIterator.next();
                if (el > maximum) {
                    maximum = el;
                }
            }

            return maximum;
        }

    }

    @Override
    public Integer min() {
        if(!elementsIterator.hasNext())
        {
            throw new IllegalArgumentException();
        }
        else{
            Integer minimum = elementsIterator.next();
            while (elementsIterator.hasNext()) {
                Integer el = elementsIterator.next();
                if (el < minimum) {
                    minimum = el;
                }
            }
            return minimum;
        }

    }

    @Override
    public long count() {
        long counter = 0;
        while (elementsIterator.hasNext()) {
            counter += 1;
            elementsIterator.next();
        }
        return counter;
    }

    @Override
    public Integer sum() {
        if(!elementsIterator.hasNext()){
            throw new IllegalArgumentException();
        }
        Integer sumOfElems = 0;
        while (elementsIterator.hasNext()) {
            sumOfElems += elementsIterator.next();
        }
        return sumOfElems;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        return new AsIntStream(new FilterIterator(this.elementsIterator, predicate));
    }

    @Override
    public void forEach(IntConsumer action) {
        while (elementsIterator.hasNext()) {
            action.accept(elementsIterator.next());
        }

    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return new AsIntStream(new MapIterator(this.elementsIterator, mapper));
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        return new AsIntStream(new FlatMapIterator(this.elementsIterator, func));
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int result = identity;
        while (elementsIterator.hasNext()) {
            Integer inp = elementsIterator.next();
            result = op.apply(result, inp);
        }
        return result;
    }

    @Override
    public int[] toArray() {
        ArrayList<Integer> arrList = new ArrayList<>();

        while (elementsIterator.hasNext()) {
            arrList.add(elementsIterator.next());
        }
        int[] array = new int[arrList.size()];
        int i = 0;
        for (Integer el : arrList) {
            array[i] = el;
            i++;
        }
        return array;
    }

}
