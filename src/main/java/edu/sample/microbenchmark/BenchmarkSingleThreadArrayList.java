package edu.sample.microbenchmark;
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
import uc.util.ConcurrentArrayList;
import uc.util.SynchronizedArrayList;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class BenchmarkSingleThreadArrayList {
	public SynchronizedArrayList<Double> syncList;
	public ConcurrentArrayList<Double> concurrentList;
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
	public void setUpForAdd() {
		syncList = new SynchronizedArrayList<Double>(iterations).shared();
		concurrentList = new ConcurrentArrayList<Double>(iterations).shared();
	}
	@Setup(Level.Invocation)
	public void setUpForRemove() {
		syncList = new SynchronizedArrayList<Double>(iterations).shared();
		concurrentList = new ConcurrentArrayList<Double>(iterations).shared();
		for(int i = 0; i < iterations; ++i) {
			double value = randomValues[i];
			syncList.add(value);
			concurrentList.add(value);
		}
	}
	@Benchmark()
	public void SyncListAdd() {
		for(int i = 0; i < iterations; ++i) {
			syncList.add(randomValues[i]);
		}
	}
	@Benchmark
	public void ConcurrentListAdd() {
		for(int i = 0; i < iterations; ++i) {
			concurrentList.add(randomValues[i]);
		}
	}
	@Benchmark
	public void SyncListForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = syncList.size(); ++i < s;) {
			bh.consume(syncList.get(i));
		}
	}
	@Benchmark
	public void ConcurrentForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = concurrentList.size(); ++i < s;) {
			bh.consume(concurrentList.get(i));
		}
	}
	@Benchmark
	public void SyncListBackwardGetIndex(Blackhole bh) {
		for(int i = syncList.size(); --i >= 0;) {
			bh.consume(syncList.get(i));
		}
	}
	@Benchmark
	public void ConcurrentBackwardGetIndex(Blackhole bh) {
		for(int i = concurrentList.size(); --i >= 0;) {
			bh.consume(concurrentList.get(i));
		}
	}
	@Benchmark
	public void SyncListBackwardRemoveIndex(Blackhole bh) {
		for(int i = syncList.size(); --i >= 0;) {
			bh.consume(syncList.remove(i));
		}
	}
	@Benchmark
	public void ConcurrentBackwardRemoveIndex(Blackhole bh) {
		for(int i = concurrentList.size(); --i >= 0;) {
			bh.consume(concurrentList.remove(i));
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkSingleThreadArrayList.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}