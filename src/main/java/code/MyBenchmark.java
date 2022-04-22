package code;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyBenchmark {
    @Benchmark
    @Fork(1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Warmup(iterations = 10, time = 1)
    @Measurement(iterations = 10, time = 1)
    public void testMethod(MyState state, Blackhole bh) {
//        state.iterator = state.tree.iterator();
//        while (state.iterator.hasNext()) {
             bh.consume(state.iterator.next());
//        }
    }

    @State(Scope.Thread) //Each thread running the benchmark will create its own instance of the state object
    public static class MyState {
        public BinarySearchTree<Integer> tree;
        public Iterator<Integer> iterator;

        @Setup(Level.Invocation) //The method is called once for each call to the benchmark method
        public void doSetup() {
            tree = new BinarySearchTree<>();
            Random random = new Random();
            for (int i = 0; i < 20000; i++) tree.add(random.nextInt(30000));
            iterator = tree.iterator();
//            for (int i = 0; i < 10000; i++) {
//                 iterator.next();
//            }
//            System.out.println("Do setup invocation");
        }
    }
}
