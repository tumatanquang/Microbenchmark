package ec.util;
import java.io.Serializable;
import java.util.Random;
/**
 * https://cs.gmu.edu/~sean/research/mersenne/MersenneTwister.java
 * @author Sean Luke
 */
public strictfp final class MersenneTwister extends Random implements Serializable {
	private static final long serialVersionUID = -4035832775130174188L;
	private static final int UPPER_MASK = Integer.MIN_VALUE;
	private static final int LOWER_MASK = Integer.MAX_VALUE;
	private static final int SEED_MASK = -1;
	private static final int N = 624;
	private static final int M = 397;
	private static final int[] MAGIC = {0, -1727483681};
	private static final int MAGIC_FACTOR_A = 1812433253;
	private static final int MAGIC_FACTOR_B = 1664525;
	private static final int MAGIC_FACTOR_C = 1566083941;
	private static final int MAGIC_FACTOR_D = 30;
	private static final int MAGIC_FACTOR_E = 31;
	private static final int MAGIC_MASK_A = -1658038656;
	private static final int MAGIC_MASK_B = -272236544;
	private static final int MAGIC_MASK_C = 1;
	private static final int MAGIC_SEED = 19650218;
	private int[] mtn;
	private int mti;
	private double __nextNextGaussian;
	private boolean __haveNextNextGaussian;
	public MersenneTwister() {
		this(System.currentTimeMillis());
	}
	public MersenneTwister(long seed) {
		super(seed);
		setSeed(seed);
	}
	public MersenneTwister(int[] array) {
		super(System.currentTimeMillis());
		setSeed(array);
	}
	@Override
	public synchronized void setSeed(long seed) {
		super.setSeed(seed);
		final int[] mt = new int[N];
		mt[0] = (int)(seed & SEED_MASK);
		mt[0] = (int)seed;
		for(mti = 0; ++mti < N;) {
			mt[mti] = (MAGIC_FACTOR_A * (mt[mti - 1] ^ (mt[mti - 1] >>> MAGIC_FACTOR_D)) + mti);
		}
		mtn = mt;
		__haveNextNextGaussian = false;
	}
	public synchronized void setSeed(int[] array) {
		final int length = array.length;
		if(length == 0) throw new IllegalArgumentException("Array length must be greater than zero");
		setSeed(MAGIC_SEED);
		final int[] mt = mtn;
		int i = 1, j = 0, k = N > length ? N : length;
		for(; k != 0; --k) {
			mt[i] = (mt[i] ^ ((mt[i - 1] ^ (mt[i - 1] >>> MAGIC_FACTOR_D)) * MAGIC_FACTOR_B)) + array[j] + j;
			++i;
			++j;
			if(i >= N) {
				mt[0] = mt[N - 1];
				i = 1;
			}
			if(j >= length) j = 0;
		}
		for(k = N - 1; k != 0; --k) {
			mt[i] = (mt[i] ^ ((mt[i - 1] ^ (mt[i - 1] >>> MAGIC_FACTOR_D)) * MAGIC_FACTOR_C)) - i;
			++i;
			if(i >= N) {
				mt[0] = mt[N - 1];
				i = 1;
			}
		}
		mt[0] = UPPER_MASK;
	}
	@Override
	protected synchronized int next(int bits) {
		final int[] mt = mtn;
		int y;
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (y >>> MAGIC_MASK_C) ^ MAGIC[y & MAGIC_MASK_C];
			}
			for(; i < N - 1; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (y >>> MAGIC_MASK_C) ^ MAGIC[y & MAGIC_MASK_C];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> MAGIC_MASK_C) ^ MAGIC[y & MAGIC_MASK_C];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & MAGIC_MASK_A;
		y ^= (y << 15) & MAGIC_MASK_B;
		y ^= (y >>> 18);
		return y >>> (32 - bits);
	}
	@Override
	public boolean nextBoolean() {
		return next(1) != 0;
	}
	public boolean nextBoolean(float probability) {
		if(probability == 0) return false;
		else if(probability == 1) return true;
		else if(probability < 0 || probability > 1) throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
		return nextFloat() < probability;
	}
	public boolean nextBoolean(double probability) {
		if(probability == 0) return false;
		else if(probability == 1) return true;
		else if(probability < 0 || probability > 1) throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
		return nextDouble() < probability;
	}
	@Override
	public int nextInt(int n) {
		if(n <= 0) throw new IllegalArgumentException("n must be positive, got: " + n);
		else if((n & -n) == n) return (int)((n * (long)next(MAGIC_FACTOR_E)) >> MAGIC_FACTOR_E);
		int bits, val;
		do {
			bits = next(MAGIC_FACTOR_E);
			val = bits % n;
		}
		while(bits - val + (n - 1) < 0);
		return val;
	}
	public long nextLong(long n) {
		if(n <= 0) throw new IllegalArgumentException("n must be positive, got: " + n);
		long bits, val;
		do {
			bits = (nextLong() >>> MAGIC_MASK_C);
			val = bits % n;
		}
		while(bits - val + (n - 1) < 0);
		return val;
	}
	@Override
	public double nextDouble() {
		return (((long)next(26) << 27) + next(27)) / (double)(1L << 53);
	}
	public double nextDouble(boolean includeZero, boolean includeOne) {
		double d = 0;
		do {
			d = nextDouble();
			if(includeOne && nextBoolean()) ++d;
		}
		while(d > 1 || !includeZero && d == 0);
		return d;
	}
	@Override
	public float nextFloat() {
		return next(24) / ((float)(1 << 24));
	}
	public float nextFloat(boolean includeZero, boolean includeOne) {
		float d = 0;
		do {
			d = nextFloat();
			if(includeOne && nextBoolean()) ++d;
		}
		while(d > 1 || !includeZero && d == 0);
		return d;
	}
	@Override
	public void nextBytes(byte[] bytes) {
		for(int x = -1, l = bytes.length; ++x < l;) {
			bytes[x] = (byte)next(8);
		}
	}
	public char nextChar() {
		return (char)(next(16));
	}
	public short nextShort() {
		return (short)(next(16));
	}
	public byte nextByte() {
		return (byte)(next(8));
	}
	public synchronized void clearGaussian() {
		__haveNextNextGaussian = false;
	}
	@Override
	public synchronized double nextGaussian() {
		if(__haveNextNextGaussian) {
			__haveNextNextGaussian = false;
			return __nextNextGaussian;
		}
		double v1, v2, s;
		do {
			v1 = nextDouble() * 2 - 1;
			v2 = nextDouble() * 2 - 1;
			s = v1 * v1 + v2 * v2;
		}
		while(s >= 1 || s == 0);
		double multiplier = StrictMath.sqrt(StrictMath.log(s) * -2 / s);
		__nextNextGaussian = v2 * multiplier;
		__haveNextNextGaussian = true;
		return v1 * multiplier;
	}
}