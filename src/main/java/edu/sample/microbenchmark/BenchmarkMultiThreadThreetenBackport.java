package edu.sample.microbenchmark;
import java.util.Date;
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
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ec.util.TimeUtils;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Threads(Threads.MAX)
@Fork(1)
@Warmup(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
public class BenchmarkMultiThreadThreetenBackport {
	private static final java.time.Clock TIME_CLOCK = java.time.Clock.systemUTC();
	private static final org.threeten.bp.Clock THREETEN_CLOCK = org.threeten.bp.Clock.systemUTC();
	@Param({"10000", "100000", "1000000", "10000000", "100000000"})
	public int iterations;
	@Benchmark
	public void SystemCurrentTimeMillis(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(System.currentTimeMillis());
		}
	}
	@Benchmark
	public void NanoTimeCurrentTimeMillis(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(TimeUtils.currentTimeMillis());
		}
	}
	@Benchmark
	public void DateGetTime(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(new Date().getTime());
		}
	}
	@Benchmark
	public void ThreeTenBackportClock(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(THREETEN_CLOCK.millis());
		}
	}
	@Benchmark
	public void JavaTimeClock(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(TIME_CLOCK.millis());
		}
	}
	@Benchmark
	public void ThreeTenBackportInstant(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(org.threeten.bp.Instant.now().toEpochMilli());
		}
	}
	@Benchmark
	public void JavaTimeInstance(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(java.time.Instant.now().toEpochMilli());
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkMultiThreadThreetenBackport.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}