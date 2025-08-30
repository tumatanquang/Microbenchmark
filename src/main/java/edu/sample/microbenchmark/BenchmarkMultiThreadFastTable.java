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
import uc.util.BackportReentrantLockFastTable;
import uc.util.BackportReentrantReadWriteLockFastTable;
import uc.util.ReentrantLockFastTable;
import uc.util.ReentrantReadWriteLockFastTable;
import uc.util.SynchronizedObjectFastTable;
import uc.util.SynchronizedThisFastTable;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Group)
@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class BenchmarkMultiThreadFastTable {
	public BackportReentrantLockFastTable<Double> brlTable;
	public BackportReentrantReadWriteLockFastTable<Double> brwlTable;
	public ReentrantLockFastTable<Double> lockTable;
	public ReentrantReadWriteLockFastTable<Double> rwlTable;
	public SynchronizedObjectFastTable<Double> syncObjectTable;
	public SynchronizedThisFastTable<Double> syncThisTable;
	@Param({"1000", "10000", "100000", "1000000"})
	public int iterations;
	public double[] randomValues;
	@Setup(Level.Iteration)
	public void setup() {
		randomValues = new double[iterations];
		for(int i = 0; i < iterations; ++i) {
			randomValues[i] = Math.random();
		}
		brlTable = new BackportReentrantLockFastTable<Double>().shared();
		brwlTable = new BackportReentrantReadWriteLockFastTable<Double>().shared();
		lockTable = new ReentrantLockFastTable<Double>().shared();
		rwlTable = new ReentrantReadWriteLockFastTable<Double>().shared();
		syncObjectTable = new SynchronizedObjectFastTable<Double>().shared();
		syncThisTable = new SynchronizedThisFastTable<Double>().shared();
	}
	@Benchmark
	@Group("BackportLock")
	@GroupThreads(2)
	public void BackportLockAdd() {
		for(int i = 0; i < iterations; ++i) {
			brlTable.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("BackportReadWrite")
	@GroupThreads(2)
	public void ReadWriteAdd() {
		for(int i = 0; i < iterations; ++i) {
			brwlTable.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("InternalLock")
	@GroupThreads(2)
	public void InternalLockAdd() {
		for(int i = 0; i < iterations; ++i) {
			lockTable.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("InternalReadWrite")
	@GroupThreads(2)
	public void InternalReadWriteAdd() {
		for(int i = 0; i < iterations; ++i) {
			lockTable.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("SynchronizedObject")
	@GroupThreads(2)
	public void SynchronizedObjectAdd() {
		for(int i = 0; i < iterations; ++i) {
			syncObjectTable.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("SynchronizedThis")
	@GroupThreads(2)
	public void SynchronizedThisAdd() {
		for(int i = 0; i < iterations; ++i) {
			syncThisTable.add(randomValues[i]);
		}
	}
	@Benchmark
	@Group("BackportLock")
	@GroupThreads(2)
	public void BackportLockGet(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(brlTable.get(i));
		}
	}
	@Benchmark
	@Group("BackportReadWrite")
	@GroupThreads(2)
	public void ReadWriteGet(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(brwlTable.get(i));
		}
	}
	@Benchmark
	@Group("InternalLock")
	@GroupThreads(2)
	public void InternalLockGet(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(lockTable.get(i));
		}
	}
	@Benchmark
	@Group("InternalReadWrite")
	@GroupThreads(2)
	public void InternalReadWriteGet(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(lockTable.get(i));
		}
	}
	@Benchmark
	@Group("SynchronizedObject")
	@GroupThreads(2)
	public void SynchronizedObjectGet(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(syncObjectTable.get(i));
		}
	}
	@Benchmark
	@Group("SynchronizedThis")
	@GroupThreads(2)
	public void SynchronizedThisGet(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(syncThisTable.get(i));
		}
	}
	@Benchmark
	@Group("BackportLock")
	@GroupThreads(2)
	public void BackportLockRemove(Blackhole bh) {
		for(int i = iterations; --i >= 0;) {
			bh.consume(brlTable.remove(i));
		}
	}
	@Benchmark
	@Group("BackportReadWrite")
	@GroupThreads(2)
	public void ReadWriteRemove(Blackhole bh) {
		for(int i = iterations; --i >= 0;) {
			bh.consume(brwlTable.remove(i));
		}
	}
	@Benchmark
	@Group("InternalLock")
	@GroupThreads(2)
	public void InternalLockRemove(Blackhole bh) {
		for(int i = iterations; --i >= 0;) {
			bh.consume(lockTable.remove(i));
		}
	}
	@Benchmark
	@Group("InternalReadWrite")
	@GroupThreads(2)
	public void InternalReadWriteRemove(Blackhole bh) {
		for(int i = iterations; --i >= 0;) {
			bh.consume(lockTable.remove(i));
		}
	}
	@Benchmark
	@Group("SynchronizedObject")
	@GroupThreads(2)
	public void SynchronizedObjectRemove(Blackhole bh) {
		for(int i = iterations; --i >= 0;) {
			bh.consume(syncObjectTable.remove(i));
		}
	}
	@Benchmark
	@Group("SynchronizedThis")
	@GroupThreads(2)
	public void SynchronizedThisRemove(Blackhole bh) {
		for(int i = iterations; --i >= 0;) {
			bh.consume(syncThisTable.remove(i));
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkMultiThreadFastTable.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}