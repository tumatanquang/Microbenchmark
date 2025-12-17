package edu.sample.microbenchmark;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import uc.j.OSClock;
@Threads(Threads.MAX)
@Fork(3)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class BenchmarkMultiThreadjOSClock {
	private static final int BATCH_SIZE = 10_000_000;
	private static final java.time.Clock CLOCK = java.time.Clock.systemUTC();
	@Setup(Level.Trial)
	public void warmJNI() {
		for(int i = 0; i < 10_000; ++i) {
			OSClock.currentTimeMillis();
			OSClock.nanoTime();
			OSClock.epochMillis();
			OSClock.epochSeconds();
		}
	}
	@Benchmark
	@Warmup(iterations = 5, batchSize = BATCH_SIZE)
	@Measurement(iterations = 10, batchSize = BATCH_SIZE)
	public void SystemTimeMillis(Blackhole bh) {
		bh.consume(System.currentTimeMillis());
	}
	@Benchmark
	@Warmup(iterations = 5, batchSize = BATCH_SIZE)
	@Measurement(iterations = 10, batchSize = BATCH_SIZE)
	public void OSClockTimeMills(Blackhole bh) {
		bh.consume(OSClock.currentTimeMillis());
	}
	@Benchmark
	@Warmup(iterations = 5, batchSize = BATCH_SIZE)
	@Measurement(iterations = 10, batchSize = BATCH_SIZE)
	public void SystemNanoTime(Blackhole bh) {
		bh.consume(System.nanoTime());
	}
	@Benchmark
	@Warmup(iterations = 5, batchSize = BATCH_SIZE)
	@Measurement(iterations = 10, batchSize = BATCH_SIZE)
	public void OSClockNanoTime(Blackhole bh) {
		bh.consume(OSClock.nanoTime());
	}
	@Benchmark
	@Warmup(iterations = 5, batchSize = BATCH_SIZE)
	@Measurement(iterations = 10, batchSize = BATCH_SIZE)
	public void OSClockMonotonicNanos(Blackhole bh) {
		bh.consume(OSClock.monotonicNanos());
	}
	@Benchmark
	@Warmup(iterations = 5, batchSize = BATCH_SIZE)
	@Measurement(iterations = 10, batchSize = BATCH_SIZE)
	public void OSClockEpochMillis(Blackhole bh) {
		bh.consume(OSClock.epochMillis());
	}
	@Benchmark
	@Warmup(iterations = 5, batchSize = BATCH_SIZE)
	@Measurement(iterations = 10, batchSize = BATCH_SIZE)
	public void JavaTimeMillis(Blackhole bh) {
		bh.consume(CLOCK.millis());
	}
	@Benchmark
	@Warmup(iterations = 5, batchSize = BATCH_SIZE)
	@Measurement(iterations = 10, batchSize = BATCH_SIZE)
	public void SystemSecondDivide(Blackhole bh) {
		bh.consume(System.currentTimeMillis() / 1000L);
	}
	@Benchmark
	@Warmup(iterations = 5, batchSize = BATCH_SIZE)
	@Measurement(iterations = 10, batchSize = BATCH_SIZE)
	public void OSClockEpochSecond(Blackhole bh) {
		bh.consume(OSClock.epochSeconds());
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkMultiThreadjOSClock.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}