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
	private MersenneTwister MT_RAND;
	private static MersenneTwister MT_SRAND;
	private final MersenneTwister MT_FRAND = new MersenneTwister();
	private static final MersenneTwister MT_SFRAND = new MersenneTwister();
	private MersenneTwisterFast MTF_RAND;
	private static MersenneTwisterFast MTF_SRAND;
	private final MersenneTwisterFast MTF_FRAND = new MersenneTwisterFast();
	private static final MersenneTwisterFast MTF_SFRAND = new MersenneTwisterFast();
	@Param({"1000", "10000", "100000", "1000000"})
	public int iterations;
	@Setup
	public void setUp() {
		MT_RAND = new MersenneTwister();
		MT_SRAND = new MersenneTwister();
		MTF_RAND = new MersenneTwisterFast();
		MTF_SRAND = new MersenneTwisterFast();
	}
	@Benchmark
	public void MT_RandNextInt() {
		for(int i = 0; i < iterations; ++i) {
			MT_RAND.nextInt();
		}
	}
	@Benchmark
	public void MT_RandStaticNextInt() {
		for(int i = 0; i < iterations; ++i) {
			MT_SRAND.nextInt();
		}
	}
	@Benchmark
	public void MT_RandFinalNextInt() {
		for(int i = 0; i < iterations; ++i) {
			MT_FRAND.nextInt();
		}
	}
	@Benchmark
	public void MT_RandStaticFinalNextInt() {
		for(int i = 0; i < iterations; ++i) {
			MT_SFRAND.nextInt();
		}
	}
	@Benchmark
	public void MTF_RandNextInt() {
		for(int i = 0; i < iterations; ++i) {
			MTF_RAND.nextInt();
		}
	}
	@Benchmark
	public void MTF_RandStaticNextInt() {
		for(int i = 0; i < iterations; ++i) {
			MTF_SRAND.nextInt();
		}
	}
	@Benchmark
	public void MTF_RandFinalNextInt() {
		for(int i = 0; i < iterations; ++i) {
			MTF_FRAND.nextInt();
		}
	}
	@Benchmark
	public void MTF_RandStaticFinalNextInt() {
		for(int i = 0; i < iterations; ++i) {
			MTF_SFRAND.nextInt();
		}
	}
}