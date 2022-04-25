package code;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MyBenchmark {
    public static List<Integer> list1000 = new ArrayList<Integer>() {{
        for (int i = 0; i < 1000; i++) add(i);
        Collections.shuffle(this);
    }};
    public static List<Integer> list10000 = new ArrayList<Integer>() {{
        for (int i = 0; i < 10000; i++) add(i);
        Collections.shuffle(this);
    }};
    public static List<Integer> list100000 = new ArrayList<Integer>() {{
        for (int i = 0; i < 100000; i++) add(i);
        Collections.shuffle(this);
    }};
    public static List<Integer> list1000000 = new ArrayList<Integer>() {{
        for (int i = 0; i < 1000000; i++) add(i);
        Collections.shuffle(this);
    }};

    public static List<Integer> currentList = list1000000;

    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 1)
    public void testCreateIterator(CreateIteratorState state) {
        state.iterator = state.tree.iterator();
    }

    @State(Scope.Thread)
    public static class CreateIteratorState {
        public BinarySearchTree<Integer> tree = new BinarySearchTree<>(true);
        public Iterator<Integer> iterator;
        public List<Integer> list;

        @Setup(Level.Invocation)
        public void doSetup() {
            list = currentList;
            Collections.shuffle(list);
            tree.addAll(list);
        }
    }

    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 1)
    public void testGetFirst(GetFirstState state, Blackhole bh) {
        bh.consume(state.iterator.next());
    }

    @State(Scope.Thread)
    public static class GetFirstState {
        public BinarySearchTree<Integer> tree = new BinarySearchTree<>(true);
        public Iterator<Integer> iterator;
        public List<Integer> list;

        @Setup(Level.Invocation)
        public void doSetup() {
            list = currentList;
            Collections.shuffle(list);
            tree.addAll(list);
            iterator = tree.iterator();
        }
    }

    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 1)
    public void testGetMiddle(GetMiddleState state, Blackhole bh) {
        bh.consume(state.iterator.next());
    }

    @State(Scope.Thread)
    public static class GetMiddleState {
        public BinarySearchTree<Integer> tree = new BinarySearchTree<>(true);
        public Iterator<Integer> iterator;
        public List<Integer> list;

        @Setup(Level.Invocation)
        public void doSetup() {
            list = currentList;
            Collections.shuffle(list);
            tree.addAll(list);
            iterator = tree.iterator();
            for (int i = 0; i<list.size()/2;i++) iterator.next();
        }
    }

    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 1)
    public void testPassThroughFirst(GetPassThroughStateFirst state, Blackhole bh) {
        for (int i = 0; i < state.list.size(); i++) {
            bh.consume(state.tree.iterator().next());
        }
    }

    @State(Scope.Thread)
    public static class GetPassThroughStateFirst {
        public BinarySearchTree<Integer> tree = new BinarySearchTree<>(true);
        public Iterator<Integer> iterator;
        public List<Integer> list;

        @Setup(Level.Invocation)
        public void doSetup() {
            list = currentList;
            tree.addAll(list);
            iterator = tree.iterator();
        }
    }

    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 5, time = 1)
    @Measurement(iterations = 10, time = 1)
    public void testPassThroughSecond(GetPassThroughStateSecond state, Blackhole bh) {
        for (int i = 0; i < state.list.size(); i++) {
            bh.consume(state.tree.iterator().next());
        }
    }

    @State(Scope.Thread)
    public static class GetPassThroughStateSecond {
        public BinarySearchTree<Integer> tree = new BinarySearchTree<>(false);
        public Iterator<Integer> iterator;
        public List<Integer> list;

        @Setup(Level.Invocation)
        public void doSetup() {
            list = currentList;
            tree.addAll(list);
            iterator = tree.iterator();
        }
    }
}
