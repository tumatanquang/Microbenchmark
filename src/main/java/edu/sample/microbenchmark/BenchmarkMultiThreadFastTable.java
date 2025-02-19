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
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import javolution.util.FastTable;
import uc.util.ReentrantLockFastTable;
import uc.util.SynchronizedObjectMutexFastTable;
import uc.util.SynchronizedThisMutexFastTable;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Group)
@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class BenchmarkMultiThreadFastTable {
	public FastTable<Double> rwFastTable;
	public SynchronizedThisMutexFastTable<Double> syncThisTable;
	public SynchronizedObjectMutexFastTable<Double> syncObjectTable;
	public ReentrantLockFastTable<Double> concurrentTable;
	@Param({"1000", "10000", "100000", "1000000"})
	public int iterations;
	private double[] randomValues;
	@Setup(Level.Iteration)
	public void setUp() {
		randomValues = new double[iterations];
		for(int i = 0; i < iterations; ++i) {
			randomValues[i] = Math.random();
		}
		rwFastTable = new FastTable<Double>().shared();
		syncThisTable = new SynchronizedThisMutexFastTable<Double>(iterations).shared();
		syncObjectTable = new SynchronizedObjectMutexFastTable<Double>(iterations).shared();
		concurrentTable = new ReentrantLockFastTable<Double>(iterations).shared();
	}
	@Benchmark
	@Group("readWrite")
	@GroupThreads(Threads.MAX)
	public void ReadWriteAdd() {
		for(int i = 0; i < iterations; ++i) {
			rwFastTable.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("syncThis")
	@GroupThreads(Threads.MAX)
	public void SyncThisAdd() {
		for(int i = 0; i < iterations; ++i) {
			syncThisTable.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("syncObject")
	@GroupThreads(Threads.MAX)
	public void SyncObjectAdd() {
		for(int i = 0; i < iterations; ++i) {
			syncObjectTable.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("reentrantLock")
	@GroupThreads(Threads.MAX)
	public void ReentrantLockAdd() {
		for(int i = 0; i < iterations; ++i) {
			concurrentTable.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("readWrite")
	@GroupThreads(Threads.MAX)
	public void ReadWriteForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = rwFastTable.size(); ++i < s;) {
			bh.consume(rwFastTable.get(i));
		}
	}
	@Benchmark
	@Group("syncThis")
	@GroupThreads(Threads.MAX)
	public void SyncThisForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = syncThisTable.size(); ++i < s;) {
			bh.consume(syncThisTable.get(i));
		}
	}
	@Benchmark
	@Group("syncObject")
	@GroupThreads(Threads.MAX)
	public void SyncObjectForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = syncObjectTable.size(); ++i < s;) {
			bh.consume(syncObjectTable.get(i));
		}
	}
	@Benchmark
	@Group("reentrantLock")
	@GroupThreads(Threads.MAX)
	public void ReentrantLockForwardGetIndex(Blackhole bh) {
		for(int i = -1, s = concurrentTable.size(); ++i < s;) {
			bh.consume(concurrentTable.get(i));
		}
	}
	@Benchmark
	@Group("readWrite")
	@GroupThreads(Threads.MAX)
	public void ReadWriteBackwardGetIndex(Blackhole bh) {
		for(int i = rwFastTable.size(); --i >= 0;) {
			bh.consume(rwFastTable.get(i));
		}
	}
	@Benchmark
	@Group("syncThis")
	@GroupThreads(Threads.MAX)
	public void SyncThisBackwardGetIndex(Blackhole bh) {
		for(int i = syncThisTable.size(); --i >= 0;) {
			bh.consume(syncThisTable.get(i));
		}
	}
	@Benchmark
	@Group("syncObject")
	@GroupThreads(Threads.MAX)
	public void SyncObjectBackwardGetIndex(Blackhole bh) {
		for(int i = syncObjectTable.size(); --i >= 0;) {
			bh.consume(syncObjectTable.get(i));
		}
	}
	@Benchmark
	@Group("reentrantLock")
	@GroupThreads(Threads.MAX)
	public void ReentrantLockBackwardGetIndex(Blackhole bh) {
		for(int i = concurrentTable.size(); --i >= 0;) {
			bh.consume(concurrentTable.get(i));
		}
	}
	@Benchmark
	@Group("readWrite")
	@GroupThreads(Threads.MAX)
	public void ReadWriteBackwardRemoveIndex(Blackhole bh) {
		for(int i = rwFastTable.size(); --i >= 0;) {
			bh.consume(rwFastTable.remove(i));
		}
	}
	@Benchmark
	@Group("syncThis")
	@GroupThreads(Threads.MAX)
	public void SyncThisBackwardRemoveIndex(Blackhole bh) {
		for(int i = syncThisTable.size(); --i >= 0;) {
			bh.consume(syncThisTable.remove(i));
		}
	}
	@Benchmark
	@Group("syncObject")
	@GroupThreads(Threads.MAX)
	public void SyncObjectBackwardRemoveIndex(Blackhole bh) {
		for(int i = syncObjectTable.size(); --i >= 0;) {
			bh.consume(syncObjectTable.remove(i));
		}
	}
	@Benchmark
	@Group("reentrantLock")
	@GroupThreads(Threads.MAX)
	public void ReentrantLockBackwardRemoveIndex(Blackhole bh) {
		for(int i = concurrentTable.size(); --i >= 0;) {
			bh.consume(concurrentTable.remove(i));
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkMultiThreadFastTable.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}