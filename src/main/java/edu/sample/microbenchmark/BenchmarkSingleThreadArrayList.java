package edu.sample.microbenchmark;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
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
import uc.util.ReentrantLockArrayList;
import uc.util.SynchronizedObjectMutexArrayList;
import uc.util.SynchronizedThisMutexArrayList;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Group)
@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class BenchmarkSingleThreadArrayList {
	public SynchronizedThisMutexArrayList<Double> syncThisList;
	public SynchronizedObjectMutexArrayList<Double> syncObjectList;
	public ReentrantLockArrayList<Double> concurrentList;
	@Param({"1000", "10000", "100000", "1000000"})
	public int iterations;
	private double[] randomValues;
	@Setup(Level.Iteration)
	public void setUp() {
		randomValues = new double[iterations];
		for(int i = 0; i < iterations; ++i) {
			randomValues[i] = Math.random();
		}
		syncThisList = new SynchronizedThisMutexArrayList<Double>(iterations).shared();
		syncObjectList = new SynchronizedObjectMutexArrayList<Double>(iterations).shared();
		concurrentList = new ReentrantLockArrayList<Double>(iterations).shared();
	}
	@Benchmark
	@Group("syncThis")
    @GroupThreads(1)
	public void SyncThisAdd() {
		for(int i = 0; i < iterations; ++i) {
			syncThisList.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("syncObject")
    @GroupThreads(1)
	public void SyncObjectAdd() {
		for(int i = 0; i < iterations; ++i) {
			syncObjectList.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("reentrantLock")
    @GroupThreads(1)
	public void ReentrantLockListAdd() {
		for(int i = 0; i < iterations; ++i) {
			concurrentList.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("syncThis")
    @GroupThreads(1)
	public void SyncThisForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = syncThisList.size(); ++i < s;) {
			bh.consume(syncThisList.get(i));
		}
	}
	@Benchmark
	@Group("syncObject")
    @GroupThreads(1)
	public void SyncObjectForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = syncObjectList.size(); ++i < s;) {
			bh.consume(syncObjectList.get(i));
		}
	}
	@Benchmark
	@Group("reentrantLock")
    @GroupThreads(1)
	public void ReentrantLockForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = concurrentList.size(); ++i < s;) {
			bh.consume(concurrentList.get(i));
		}
	}
	@Benchmark
	@Group("syncThis")
    @GroupThreads(1)
	public void SyncThisBackwardGetIndex(Blackhole bh) {
		for(int i = syncThisList.size(); --i >= 0;) {
			bh.consume(syncThisList.get(i));
		}
	}
	@Benchmark
	@Group("syncObject")
    @GroupThreads(1)
	public void SyncObjectBackwardGetIndex(Blackhole bh) {
		for(int i = syncObjectList.size(); --i >= 0;) {
			bh.consume(syncObjectList.get(i));
		}
	}
	@Benchmark
	@Group("reentrantLock")
    @GroupThreads(1)
	public void ReentrantLockBackwardGetIndex(Blackhole bh) {
		for(int i = concurrentList.size(); --i >= 0;) {
			bh.consume(concurrentList.get(i));
		}
	}
	@Benchmark
	@Group("syncThis")
    @GroupThreads(1)
	public void SyncThisBackwardRemoveIndex(Blackhole bh) {
		for(int i = syncThisList.size(); --i >= 0;) {
			bh.consume(syncThisList.remove(i));
		}
	}
	@Benchmark
	@Group("syncObject")
    @GroupThreads(1)
	public void SyncObjectBackwardRemoveIndex(Blackhole bh) {
		for(int i = syncObjectList.size(); --i >= 0;) {
			bh.consume(syncObjectList.remove(i));
		}
	}
	@Benchmark
	@Group("reentrantLock")
    @GroupThreads(1)
	public void ReentrantLockBackwardRemoveIndex(Blackhole bh) {
		for(int i = concurrentList.size(); --i >= 0;) {
			bh.consume(concurrentList.remove(i));
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkSingleThreadArrayList.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}