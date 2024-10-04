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
import ec.util.MersenneTwister;
import ec.util.MersenneTwisterFast;
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Threads(Threads.MAX)
@Fork(1)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
public class BenchmarkMultiThreadMersenneTwister {
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
	@Setup(Level.Invocation)
	public void setUp() {
		MT_RAND = new MersenneTwister();
		MT_SRAND = new MersenneTwister();
		MTF_RAND = new MersenneTwisterFast();
		MTF_SRAND = new MersenneTwisterFast();
	}
	@Benchmark
	public void MT_RandNextFloat(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(MT_RAND.nextFloat());
		}
	}
	@Benchmark
	public void MT_RandStaticNextFloat(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(MT_SRAND.nextFloat());
		}
	}
	@Benchmark
	public void MT_RandFinalNextFloat(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(MT_FRAND.nextFloat());
		}
	}
	@Benchmark
	public void MT_RandStaticFinalNextFloat(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(MT_SFRAND.nextFloat());
		}
	}
	@Benchmark
	public void MTF_RandNextFloat(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(MTF_RAND.nextFloat());
		}
	}
	@Benchmark
	public void MTF_RandStaticNextFloat(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(MTF_SRAND.nextFloat());
		}
	}
	@Benchmark
	public void MTF_RandFinalNextFloat(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(MTF_FRAND.nextFloat());
		}
	}
	@Benchmark
	public void MTF_RandStaticFinalNextFloat(Blackhole bh) {
		for(int i = 0; i < iterations; ++i) {
			bh.consume(MTF_SFRAND.nextFloat());
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkMultiThreadMersenneTwister.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}