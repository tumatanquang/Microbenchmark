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
import uc.util.ConcurrentFastTable;
import uc.util.SynchronizedFastTable;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class BenchmarkSingleThreadFastTable {
	public SynchronizedFastTable<Double> syncTable;
	public ConcurrentFastTable<Double> concurrentTable;
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
		syncTable = new SynchronizedFastTable<Double>(iterations).shared();
		concurrentTable = new ConcurrentFastTable<Double>(iterations).shared();
	}
	@Setup(Level.Invocation)
	public void setUpForRemove() {
		syncTable = new SynchronizedFastTable<Double>(iterations).shared();
		concurrentTable = new ConcurrentFastTable<Double>(iterations).shared();
		for(int i = 0; i < iterations; ++i) {
			double value = randomValues[i];
			syncTable.add(value);
			concurrentTable.add(value);
		}
	}
	@Benchmark
	public void SyncListAdd() {
		for(int i = 0; i < iterations; ++i) {
			syncTable.add(randomValues[i]);
		}
	}
	@Benchmark
	public void ConcurrentListAdd() {
		for(int i = 0; i < iterations; ++i) {
			concurrentTable.add(randomValues[i]);
		}
	}
	@Benchmark
	public void SyncListForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = syncTable.size(); ++i < s;) {
			bh.consume(syncTable.get(i));
		}
	}
	@Benchmark
	public void ConcurrentForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = concurrentTable.size(); ++i < s;) {
			bh.consume(concurrentTable.get(i));
		}
	}
	@Benchmark
	public void SyncListBackwardGetIndex(Blackhole bh) {
		for(int i = syncTable.size(); --i >= 0;) {
			bh.consume(syncTable.get(i));
		}
	}
	@Benchmark
	public void ConcurrentBackwardGetIndex(Blackhole bh) {
		for(int i = concurrentTable.size(); --i >= 0;) {
			bh.consume(concurrentTable.get(i));
		}
	}
	@Benchmark
	public void SyncListBackwardRemoveIndex(Blackhole bh) {
		for(int i = syncTable.size(); --i >= 0;) {
			bh.consume(syncTable.remove(i));
		}
	}
	@Benchmark
	public void ConcurrentBackwardRemoveIndex(Blackhole bh) {
		for(int i = concurrentTable.size(); --i >= 0;) {
			bh.consume(concurrentTable.remove(i));
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkSingleThreadFastTable.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}