package edu.sample.microbenchmark;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import uc.j.OSClock;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class BenchmarkSingleThreadjOSClock {
	@Param({"1000", "10000", "100000", "1000000", "10000000", "100000000"})
	public int iterations;
	@Benchmark
	public void SystemTimeMillis(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(System.currentTimeMillis());
		}
	}
	@Benchmark
	public void SystemNanoTime(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(System.nanoTime());
		}
	}
	@Benchmark
	public void OSClockTimeMills(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(OSClock.currentTimeMillis());
		}
	}
	@Benchmark
	public void OSClockNanoTime(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(OSClock.nanoTime());
		}
	}
	@Benchmark
	public void OSClockEpochMillis(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(OSClock.getEpochMillis());
		}
	}
	@Benchmark
	public void SystemSecondDiv(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(System.currentTimeMillis() / 1000L);
		}
	}
	@Benchmark
	public void OSClockEpochSecond(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(OSClock.getEpochSecond());
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkSingleThreadjOSClock.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}