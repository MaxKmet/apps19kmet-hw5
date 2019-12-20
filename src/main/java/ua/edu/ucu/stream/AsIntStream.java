package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;
import ua.edu.ucu.iterator.FilterIterator;
import ua.edu.ucu.iterator.FlatMapIterator;
import ua.edu.ucu.iterator.MapIterator;
import ua.edu.ucu.iterator.StreamIterator;

import java.util.ArrayList;
import java.util.Iterator;


public class AsIntStream implements IntStream {

    public static void main(String[] args) {
        IntStream intStream = AsIntStream.of(-1, 0, 1, 2, 3); // input values
        Double res = intStream
                .filter(x -> x > 0) // 1, 2, 3
                .map(x -> x * x) // 1, 4, 9
                .flatMap(x -> AsIntStream.of(x - 1, x, x + 1)) // 0, 1, 2, 3, 4, 5, 8, 9, 10
                .average(); // 42
        System.out.println(res);
    }

    private Iterator<Integer> elementsIterator;

    private AsIntStream(int... values) {
        ArrayList<Integer> elements = new ArrayList<>();
        for(int v : values){
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
        int sum = 0;
        for(int el : arr){
            sum += el;
        }

        return (double) sum/arr.length;
    }

    @Override
    public Integer max() {
        if(elementsIterator.hasNext()) {
            Integer maximum = elementsIterator.next();
            while (elementsIterator.hasNext()) {
                Integer el = elementsIterator.next();
                if(el > maximum)
                {
                    maximum = el;
                }
            }

            return maximum;
        }
        return null;
    }

    @Override
    public Integer min() {
        if(elementsIterator.hasNext()){
            Integer minimum = elementsIterator.next();
            while (elementsIterator.hasNext()) {
                Integer el = elementsIterator.next();
                if(el < minimum)
                {
                    minimum = el;
                }
            }
            return minimum;
        }
        return null;
    }

    @Override
    public long count() {
        long counter = 0;
        while (elementsIterator.hasNext()){
            counter += 1;
            elementsIterator.next();
        }
        return counter;
    }

    @Override
    public Integer sum() {
        Integer sumOfElems = 0;
        while (elementsIterator.hasNext()){
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
        while(elementsIterator.hasNext()){
            action.accept(elementsIterator.next());
        }

    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        return new AsIntStream(new MapIterator(this.elementsIterator, mapper));
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        return new AsIntStream(new FlatMapIterator(this.elementsIterator,func));
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int result = identity;
        while (elementsIterator.hasNext()){
            Integer inp = elementsIterator.next();
            result = op.apply(result, inp);
        }
        return result;
    }

    @Override
    public int[] toArray() {
        ArrayList<Integer> arrList = new ArrayList<>();

        while (elementsIterator.hasNext()){
            arrList.add(elementsIterator.next());
        }
        int[] array = new int[arrList.size()];
        int i = 0;
        for (Integer el : arrList){
            array[i] = el;
            i++;
        }
        return array;
    }

}
