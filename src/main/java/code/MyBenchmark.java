package code;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyBenchmark {

    public static List<Integer> getList(int num) {
        return new ArrayList<Integer>() {{
            for (int i = 0; i < num; i++) add(i);
            Collections.shuffle(this);
        }};
    }

    public static List<Integer> getLeftBranch(int num) {
        return new ArrayList<Integer>() {{
            for (int i = num; i > 0; i--) add(i);
        }};
    }

    public static boolean currentIteratorIsFirst = false;

    @State(Scope.Thread)
    public static class GetPassThroughState {
        @Param({"1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000", "200000", "300000", "400000", "500000", "600000", "700000", "800000", "900000", "1000000"})
        public static int currentNum;
        public List<Integer> list = getList(currentNum);
        public BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>(currentIteratorIsFirst) {{
            this.addAll(list);
        }};
        public Iterator<Integer> iterator;

        @Setup(Level.Invocation)
        public void doSetup() {
            iterator = tree.iterator();
        }

        @Benchmark
        @Fork(1)
        @BenchmarkMode(Mode.AverageTime)
        @OutputTimeUnit(TimeUnit.MICROSECONDS)
        @Warmup(iterations = 10, time = 1)
        @Measurement(iterations = 10, time = 1)
        public void testPassThrough(GetPassThroughState state, Blackhole bh) {
            for (int i = 0; i < state.list.size(); i++) {
                bh.consume(state.iterator.next());
            }
        }

    }

    @State(Scope.Thread)
    public static class GetFirstState {
        @Param({"1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000", "200000", "300000", "400000", "500000", "600000", "700000", "800000", "900000", "1000000"})
        public static int currentNum;
        public List<Integer> list = getLeftBranch(currentNum);
        public BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>(currentIteratorIsFirst) {{
            this.addAll(list);
        }};
        public Iterator<Integer> iterator;

        @Setup(Level.Invocation)
        public void doSetup() {
            iterator = tree.iterator();
        }

        @Benchmark
        @Fork(1)
        @BenchmarkMode(Mode.AverageTime)
        @OutputTimeUnit(TimeUnit.MICROSECONDS)
        @Warmup(iterations = 10, time = 1)
        @Measurement(iterations = 10, time = 1)
        public void testGetFirst(GetFirstState state, Blackhole bh) {
            bh.consume(state.iterator.next());
        }
    }

    @State(Scope.Thread)
    public static class GetMiddleState {
        @Param({"1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000", "200000", "300000", "400000", "500000", "600000", "700000", "800000", "900000", "1000000"})
        public static int currentNum;
        public List<Integer> list = getLeftBranch(currentNum);
        public BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>(currentIteratorIsFirst) {{
            this.addAll(list);
        }};
        public Iterator<Integer> iterator;

        @Setup(Level.Invocation)
        public void doSetup() {
            iterator = tree.iterator();
            for (int i = 0; i < list.size() / 2; i++) iterator.next();
        }

        @Benchmark
        @Fork(1)
        @BenchmarkMode(Mode.AverageTime)
        @OutputTimeUnit(TimeUnit.MICROSECONDS)
        @Warmup(iterations = 10, time = 1)
        @Measurement(iterations = 10, time = 1)
        public void testGetMiddle(GetMiddleState state, Blackhole bh) {
            bh.consume(state.iterator.next());
        }
    }

    @State(Scope.Thread)
    public static class CreateIteratorState {
        @Param({"1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000", "200000", "300000", "400000", "500000", "600000", "700000", "800000", "900000", "1000000"})
        public static int currentNum;
        public List<Integer> list = getList(currentNum);
        public BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>(currentIteratorIsFirst) {{
            this.addAll(list);
        }};
        public Iterator<Integer> iterator;

        @Benchmark
        @Fork(1)
        @BenchmarkMode(Mode.AverageTime)
        @OutputTimeUnit(TimeUnit.MICROSECONDS)
        @Warmup(iterations = 10, time = 1)
        @Measurement(iterations = 10, time = 1)
        public void testCreateIterator(CreateIteratorState state) {
            state.iterator = state.tree.iterator();
        }
    }
}
