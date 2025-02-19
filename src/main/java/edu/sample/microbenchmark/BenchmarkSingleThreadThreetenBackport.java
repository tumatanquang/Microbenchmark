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
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class BenchmarkSingleThreadThreetenBackport {
	@Param({"1000", "10000", "100000", "1000000", "10000000"})
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
			bh.consume(new Date().getTime());
		}
	}
	@Benchmark
	public void ThreeTenBackportInstant(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(java.time.Clock.systemUTC().millis());
		}
	}
	@Benchmark
	public void JavaTimeInstance(Blackhole bh) {
		for(int i = -1; ++i < iterations;) {
			bh.consume(org.threeten.bp.Clock.systemUTC().millis());
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkSingleThreadThreetenBackport.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}