package edu.sample.microbenchmark;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Thread)
public class MyBenchmark {
	private static Random SRAND;
	private final Random FRAND = new Random();
	private static final Random SFRAND = new Random();
	private static MersenneTwisterFast MT_SRAND;
	private final MersenneTwisterFast MT_FRAND = new MersenneTwisterFast();
	private static final MersenneTwisterFast MT_SFRAND = new MersenneTwisterFast();
	@Setup
	public void setUp() {
		SRAND = new Random();
		MT_SRAND = new MersenneTwisterFast();
	}
	@Param({"1000", "10000", "100000", "1000000"})
	@Benchmark
	public void RandStaticNextInt(int iterations) {
		for(int i = 0; i < iterations; ++i) {
			SRAND.nextInt();
		}
	}
	@Param({"1000", "10000", "100000", "1000000"})
	@Benchmark
	public void RandFinalNextInt(int iterations) {
		for(int i = 0; i < iterations; ++i) {
			FRAND.nextInt();
		}
	}
	@Param({"1000", "10000", "100000", "1000000"})
	@Benchmark
	public void RandStaticFinalNextInt(int iterations) {
		for(int i = 0; i < iterations; ++i) {
			SFRAND.nextInt();
		}
	}
	@Param({"1000", "10000", "100000", "1000000"})
	@Benchmark
	public void MTRandStaticNextInt(int iterations) {
		for(int i = 0; i < iterations; ++i) {
			MT_SRAND.nextInt();
		}
	}
	@Param({"1000", "10000", "100000", "1000000"})
	@Benchmark
	public void MTRandFinalNextInt(int iterations) {
		for(int i = 0; i < iterations; ++i) {
			MT_FRAND.nextInt();
		}
	}
	@Param({"1000", "10000", "100000", "1000000"})
	@Benchmark
	public void MTRandStaticFinalNextInt(int iterations) {
		for(int i = 0; i < iterations; ++i) {
			MT_SFRAND.nextInt();
		}
	}
}