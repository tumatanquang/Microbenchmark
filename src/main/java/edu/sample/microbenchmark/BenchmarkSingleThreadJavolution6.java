package edu.sample.microbenchmark;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import javolution.util.FastTable;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class BenchmarkSingleThreadJavolution6 {
	public ArrayList<Double> arrayList;
	public FastTable<Double> fastTable;
	@Param({"1000", "10000", "100000", "1000000"})
	public int iterations;
	private double[] randomValues;
	@Setup(Level.Trial)
	public void preloadRandomValues() {
		randomValues = new double[iterations];
		for(int i = 0; i < iterations; ++i) {
			randomValues[i] = Math.random();
		}
	}
	@Setup(Level.Invocation)
	public void setUp() {
		arrayList = new ArrayList<Double>(iterations);
		fastTable = new FastTable<Double>();
		for(int i = 0; i < iterations; ++i) {
			double value = randomValues[i];
			arrayList.add(value);
			fastTable.add(value);
		}
	}
	@Benchmark
	public void ArrayListForeach(Blackhole bh) {
		for(double value : arrayList) {
			bh.consume(value);
		}
	}
	@Benchmark
	public void FastTableForeach(Blackhole bh) {
		for(double value : fastTable) {
			bh.consume(value);
		}
	}
	@Benchmark
	public void ArrayListIterator(Blackhole bh) {
		for(Iterator<Double> iter = arrayList.iterator(); iter.hasNext();) {
			bh.consume(iter.next());
		}
	}
	@Benchmark
	public void FastTableIterator(Blackhole bh) {
		for(Iterator<Double> iter = fastTable.iterator(); iter.hasNext();) {
			bh.consume(iter.next());
		}
	}
	@Benchmark
	public void ArrayListForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = arrayList.size(); ++i < s;) {
			bh.consume(arrayList.get(i));
		}
	}
	@Benchmark
	public void FastTableForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = fastTable.size(); ++i < s;) {
			bh.consume(fastTable.get(i));
		}
	}
	@Benchmark
	public void ArrayListBackwardGetIndex(Blackhole bh) {
		for(int i = arrayList.size(); --i >= 0;) {
			bh.consume(arrayList.get(i));
		}
	}
	@Benchmark
	public void FastTableBackwardGetIndex(Blackhole bh) {
		for(int i = fastTable.size(); --i >= 0;) {
			bh.consume(fastTable.get(i));
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkSingleThreadedJavolution6.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}