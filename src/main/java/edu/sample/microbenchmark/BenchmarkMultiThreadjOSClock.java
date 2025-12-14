package edu.sample.microbenchmark;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
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
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Threads(Threads.MAX)
@Fork(2)
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
public class BenchmarkMultiThreadjOSClock {
	@OperationsPerInvocation(100000000)
	@Benchmark
	public void SystemTimeMillis(Blackhole bh) {
		bh.consume(System.currentTimeMillis());
	}
	@OperationsPerInvocation(100000000)
	@Benchmark
	public void OSClockTimeMills(Blackhole bh) {
		bh.consume(OSClock.currentTimeMillis());
	}
	@OperationsPerInvocation(100000000)
	@Benchmark
	public void SystemNanoTime(Blackhole bh) {
		bh.consume(System.nanoTime());
	}
	@OperationsPerInvocation(100000000)
	@Benchmark
	public void OSClockNanoTime(Blackhole bh) {
		bh.consume(OSClock.nanoTime());
	}
	@OperationsPerInvocation(100000000)
	@Benchmark
	public void OSClockMonotonicNanos(Blackhole bh) {
		bh.consume(OSClock.monotonicNanos());
	}
	@OperationsPerInvocation(100000000)
	@Benchmark
	public void OSClockEpochMillis(Blackhole bh) {
		bh.consume(OSClock.epochMillis());
	}
	@OperationsPerInvocation(100000000)
	@Benchmark
	public void SystemSecondDivide(Blackhole bh) {
		bh.consume(System.currentTimeMillis() / 1000L);
	}
	@OperationsPerInvocation(100000000)
	@Benchmark
	public void OSClockEpochSecond(Blackhole bh) {
		bh.consume(OSClock.epochSeconds());
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkMultiThreadjOSClock.class.getSimpleName()).threads(Runtime.getRuntime().availableProcessors()).build();
		new Runner(opt).run();
	}
}