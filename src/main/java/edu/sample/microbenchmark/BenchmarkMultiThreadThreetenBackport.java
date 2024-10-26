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
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Threads(Threads.MAX)
@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class BenchmarkMultiThreadThreetenBackport {
	@Param({"1000", "10000", "100000", "1000000"})
	public int iterations;
	@Benchmark
	public void SystemCurrentTimeMillis(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(System.currentTimeMillis());
		}
	}
	@Benchmark
	public void DateGetTime(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(new java.util.Date().getTime());
		}
	}
	@Benchmark
	public void GregorianCalendarGetTime(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(java.util.GregorianCalendar.getInstance().getTimeInMillis());
		}
	}
	@Benchmark
	public void ThreeTenBackportClock(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(org.threeten.bp.Clock.systemUTC().millis());
		}
	}
	@Benchmark
	public void JavaTimeClock(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(java.time.Clock.systemUTC().millis());
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkMultiThreadThreetenBackport.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}
