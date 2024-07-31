package edu.sample.microbenchmark;
import java.util.concurrent.TimeUnit;
import javax.xml.ws.handler.MessageContext.Scope;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
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
public class BenchmarkMersenneTwister {
	public MersenneTwister MT_RAND;
	public static MersenneTwister MT_SRAND;
	public final MersenneTwister MT_FRAND = new MersenneTwister();
	public static final MersenneTwister MT_SFRAND = new MersenneTwister();
	public MersenneTwisterFast MTF_RAND;
	public static MersenneTwisterFast MTF_SRAND;
	public final MersenneTwisterFast MTF_FRAND = new MersenneTwisterFast();
	public static final MersenneTwisterFast MTF_SFRAND = new MersenneTwisterFast();
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
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkMersenneTwister.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}