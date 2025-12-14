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
import org.openjdk.jmh.annotations.Threads;
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
@State(Scope.Benchmark)
@Threads(Threads.MAX)
@Fork(1)
@Warmup(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
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
	public void BackportLock(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			brlTable.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(brlTable.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(brlTable.remove(i));
		}
	}
	@Benchmark
	public void ReadWrite(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			brwlTable.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(brwlTable.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(brwlTable.remove(i));
		}
	}
	@Benchmark
	public void InternalLock(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			lockTable.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(lockTable.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(lockTable.remove(i));
		}
	}
	@Benchmark
	public void InternalReadWrite(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			rwlTable.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(rwlTable.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(rwlTable.remove(i));
		}
	}
	@Benchmark
	public void SynchronizedObject(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			syncObjectTable.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(syncObjectTable.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(syncObjectTable.remove(i));
		}
	}
	@Benchmark
	public void SynchronizedThis(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			syncThisTable.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(syncThisTable.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(syncThisTable.remove(i));
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkMultiThreadFastTable.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}