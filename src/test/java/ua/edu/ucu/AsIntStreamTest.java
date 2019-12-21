package ua.edu.ucu;

import org.junit.Before;
import org.junit.Test;
import ua.edu.ucu.stream.AsIntStream;
import ua.edu.ucu.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class AsIntStreamTest {

    private IntStream intStream;
    private IntStream emptyIntStream;

    @Before
    public void init() {
        int[] intArr = {-1, 0, 1, 2, 3};
        int[] emptyArr = {};
        intStream = AsIntStream.of(intArr);
        emptyIntStream = AsIntStream.of(emptyArr);
    }

    @Test
    public void testAverage() {
        double expResult = 1;
        double result = intStream.average();
        assertEquals(expResult, result, 0.01);
    }

    @Test
    public void testMin() {
        int expResult = -1;
        int result = intStream.min();
        assertEquals(expResult, result);
    }

    @Test
    public void testMax() {
        int expResult = 3;
        int result = intStream.max();
        assertEquals(expResult, result);
    }

    @Test
    public void testCount() {
        long expResult = 5;
        long result = intStream.count();
        assertEquals(expResult, result);
    }

    @Test
    public void testSum() {
        int expResult = 5;
        int result = intStream.sum();
        assertEquals(expResult, result);
    }

    @Test
    public void testFilter() {
        int[] expResult = new int[]{1, 2, 3};
        int[] res = intStream
                .filter(x -> x > 0).toArray();
        assertArrayEquals(expResult, res);
    }

    @Test
    public void testMap() {
        int[] expResult = new int[]{1, 0, 1, 4, 9};
        int[] res = intStream
                .map(x -> x * x).toArray();
        assertArrayEquals(expResult, res);
    }

    @Test
    public void testFlatMap() {
        int[] expResult = new int[]{-2, -1, 0, -1, 0, 1, 0, 1, 2, 1, 2, 3, 2, 3, 4};
        int[] res = intStream
                .flatMap(x -> AsIntStream.of(x - 1, x, x + 1)).toArray();
        assertArrayEquals(expResult, res);
    }

    @Test
    public void testReduce() {
        int expResult = 5;
        int res = intStream
                .reduce(0, (sum, x) -> sum += x);
        assertEquals(expResult, res);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageEmpty() {
        double result = emptyIntStream.average();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxEmpty() {
        double result = emptyIntStream.max();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinEmpty() {
        int result = emptyIntStream.min();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSumEmpty() {
        int result = emptyIntStream.sum();
    }

    @Test
    public void testForEach() {
        String expResult = "-10123";
        String result = StreamApp.streamForEach(intStream);
        assertEquals(expResult, result);
    }

}
