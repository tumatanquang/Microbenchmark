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
import uc.util.BackportReentrantLockArrayList;
import uc.util.BackportReentrantReadWriteLockArrayList;
import uc.util.ReentrantLockArrayList;
import uc.util.ReentrantReadWriteLockArrayList;
import uc.util.SynchronizedObjectArrayList;
import uc.util.SynchronizedThisArrayList;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Threads(Threads.MAX)
@Fork(1)
@Warmup(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
public class BenchmarkMultiThreadArrayList {
	public BackportReentrantLockArrayList<Double> brlList;
	public BackportReentrantReadWriteLockArrayList<Double> brwlList;
	public ReentrantLockArrayList<Double> lockList;
	public ReentrantReadWriteLockArrayList<Double> rwlList;
	public SynchronizedObjectArrayList<Double> syncObjectList;
	public SynchronizedThisArrayList<Double> syncThisList;
	@Param({"1000", "10000", "100000", "1000000"})
	public int iterations;
	public double[] randomValues;
	@Setup(Level.Iteration)
	public void setup() {
		randomValues = new double[iterations];
		for(int i = 0; i < iterations; ++i) {
			randomValues[i] = Math.random();
		}
		brlList = new BackportReentrantLockArrayList<Double>().shared();
		brwlList = new BackportReentrantReadWriteLockArrayList<Double>().shared();
		lockList = new ReentrantLockArrayList<Double>().shared();
		rwlList = new ReentrantReadWriteLockArrayList<Double>().shared();
		syncObjectList = new SynchronizedObjectArrayList<Double>().shared();
		syncThisList = new SynchronizedThisArrayList<Double>().shared();
	}
	@Benchmark
	public void BackportLock(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			brlList.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(brlList.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(brlList.remove(i));
		}
	}
	@Benchmark
	public void ReadWrite(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			brwlList.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(brwlList.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(brwlList.remove(i));
		}
	}
	@Benchmark
	public void InternalLock(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			lockList.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(lockList.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(lockList.remove(i));
		}
	}
	@Benchmark
	public void InternalReadWrite(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			rwlList.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(rwlList.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(rwlList.remove(i));
		}
	}
	@Benchmark
	public void SynchronizedObject(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			syncObjectList.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(syncObjectList.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(syncObjectList.remove(i));
		}
	}
	@Benchmark
	public void SynchronizedThis(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			syncThisList.add(randomValues[i]);
		}
		for(int i = 0; i < iterations; ++i) {
			bh.consume(syncThisList.get(i));
		}
		for(int i = iterations; --i >= 0;) {
			bh.consume(syncThisList.remove(i));
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkMultiThreadArrayList.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}