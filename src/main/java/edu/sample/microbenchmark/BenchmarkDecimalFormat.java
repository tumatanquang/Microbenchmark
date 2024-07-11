package edu.sample.microbenchmark;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
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
class BenchmarkDecimalFormat {
	public static final DecimalFormatSymbols SYMBOLS = new DecimalFormatSymbols(Locale.US);
	public static final DecimalFormat DF = new DecimalFormat("0.##", SYMBOLS);
	@Param({"1000", "10000", "100000", "1000000"})
	public int iterations;
	public float[] floatValues;
	@Setup
	public void setUp() {
		floatValues = new float[iterations];
		for(int i = 0; i < iterations; ++i) {
			floatValues[i] = (float)Math.random();
		}
	}
	@Benchmark
	public void benchmarkDecimalFormat() {
		for(float value : floatValues) {
			DF.format(value);
		}
	}
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkDecimalFormat.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}