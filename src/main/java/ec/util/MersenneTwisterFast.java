package ec.util;
/**
 * https://cs.gmu.edu/~sean/research/mersenne/MersenneTwisterFast.java
 * @author Sean Luke
 */
public strictfp final class MersenneTwisterFast {
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
	private static final int MAGIC_FACTOR_F = 11;
	private static final int MAGIC_FACTOR_G = 18;
	private static final int MAGIC_FACTOR_H = 16;
	private static final int MAGIC_MASK_A = -1658038656;
	private static final int MAGIC_MASK_B = -272236544;
	private static final int MAGIC_MASK_C = 1;
	private static final int MAGIC_MASK_D = 32;
	private static final int MAGIC_MASK_E = 53;
	private static final int MAGIC_MASK_F = 7;
	private static final int MAGIC_MASK_G = 15;
	private static final int MAGIC_MASK_H = 24;
	private static final int MAGIC_SEED = 19650218;
	private int[] mtn;
	private int mti;
	private double __nextNextGaussian;
	private boolean __haveNextNextGaussian;
	public MersenneTwisterFast() {
		this(System.currentTimeMillis());
	}
	public MersenneTwisterFast(long seed) {
		setSeed(seed);
	}
	public MersenneTwisterFast(int[] seed) {
		setSeed(seed);
	}
	public void setSeed(long seed) {
		final int[] mt = new int[N];
		mt[0] = (int)(seed & SEED_MASK);
		for(mti = 0; ++mti < N;) {
			mt[mti] = (MAGIC_FACTOR_A * (mt[mti - 1] ^ (mt[mti - 1] >>> MAGIC_FACTOR_D)) + mti);
		}
		mtn = mt;
		__haveNextNextGaussian = false;
	}
	public void setSeed(int[] array) {
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
	public int nextInt() {
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
		y ^= y >>> MAGIC_FACTOR_F;
		y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
		y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
		y ^= (y >>> MAGIC_FACTOR_G);
		return y;
	}
	public short nextShort() {
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
		y ^= y >>> MAGIC_FACTOR_F;
		y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
		y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
		y ^= (y >>> MAGIC_FACTOR_G);
		return (short)(y >>> MAGIC_FACTOR_H);
	}
	public char nextChar() {
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
		y ^= y >>> MAGIC_FACTOR_F;
		y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
		y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
		y ^= (y >>> MAGIC_FACTOR_G);
		return (char)(y >>> MAGIC_FACTOR_H);
	}
	public boolean nextBoolean() {
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
		y ^= y >>> MAGIC_FACTOR_F;
		y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
		y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
		y ^= (y >>> MAGIC_FACTOR_G);
		return (y >>> MAGIC_FACTOR_E) != 0;
	}
	public boolean nextBoolean(float probability) {
		if(probability == 0) return false;
		else if(probability == 1) return true;
		else if(probability < 0 || probability > 1) throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
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
		y ^= y >>> MAGIC_FACTOR_F;
		y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
		y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
		y ^= (y >>> MAGIC_FACTOR_G);
		return (y >>> 8) / ((float)(1 << MAGIC_MASK_H)) < probability;
	}
	public boolean nextBoolean(double probability) {
		if(probability == 0) return false;
		else if(probability == 1) return true;
		else if(probability < 0 || probability > 1) throw new IllegalArgumentException("probability must be between 0.0 and 1.0 inclusive.");
		final int[] mt = mtn;
		int y, z;
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
		y ^= y >>> MAGIC_FACTOR_F;
		y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
		y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
		y ^= (y >>> MAGIC_FACTOR_G);
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
			}
			for(; i < N - 1; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
			}
			z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
			mti = 0;
		}
		z = mt[mti++];
		z ^= z >>> MAGIC_FACTOR_F;
		z ^= (z << MAGIC_MASK_F) & MAGIC_MASK_A;
		z ^= (z << MAGIC_MASK_G) & MAGIC_MASK_B;
		z ^= (z >>> MAGIC_FACTOR_G);
		return ((((long)(y >>> 6)) << 27) + (z >>> 5)) / (double)(1L << MAGIC_MASK_E) < probability;
	}
	public byte nextByte() {
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
		y ^= y >>> MAGIC_FACTOR_F;
		y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
		y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
		y ^= (y >>> MAGIC_FACTOR_G);
		return (byte)(y >>> MAGIC_MASK_H);
	}
	public void nextBytes(byte[] bytes) {
		final int[] mt = mtn;
		for(int i = -1, y, l = bytes.length; ++i < l;) {
			if(mti >= N) {
				int j;
				for(j = 0; j < N - M; ++j) {
					y = (mt[j] & UPPER_MASK) | (mt[j + 1] & LOWER_MASK);
					mt[j] = mt[j + M] ^ (y >>> MAGIC_MASK_C) ^ MAGIC[y & MAGIC_MASK_C];
				}
				for(; j < N - 1; ++j) {
					y = (mt[j] & UPPER_MASK) | (mt[j + 1] & LOWER_MASK);
					mt[j] = mt[j + (M - N)] ^ (y >>> MAGIC_MASK_C) ^ MAGIC[y & MAGIC_MASK_C];
				}
				y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (y >>> MAGIC_MASK_C) ^ MAGIC[y & MAGIC_MASK_C];
				mti = 0;
			}
			y = mt[mti++];
			y ^= y >>> MAGIC_FACTOR_F;
			y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
			y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
			y ^= (y >>> MAGIC_FACTOR_G);
			bytes[i] = (byte)(y >>> MAGIC_MASK_H);
		}
	}
	public long nextLong() {
		final int[] mt = mtn;
		int y, z;
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
		y ^= y >>> MAGIC_FACTOR_F;
		y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
		y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
		y ^= (y >>> MAGIC_FACTOR_G);
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
			}
			for(; i < N - 1; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
			}
			z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
			mti = 0;
		}
		z = mt[mti++];
		z ^= z >>> MAGIC_FACTOR_F;
		z ^= (z << MAGIC_MASK_F) & MAGIC_MASK_A;
		z ^= (z << MAGIC_MASK_G) & MAGIC_MASK_B;
		z ^= (z >>> MAGIC_FACTOR_G);
		return (((long)y) << MAGIC_MASK_D) + z;
	}
	public long nextLong(long bound) {
		if(bound <= 0) throw new IllegalArgumentException("bound must be positive, got: " + bound);
		final int[] mt = mtn;
		long bits, val;
		do {
			int y, z;
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
			y ^= y >>> MAGIC_FACTOR_F;
			y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
			y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
			y ^= (y >>> MAGIC_FACTOR_G);
			if(mti >= N) {
				int i;
				for(i = 0; i < N - M; ++i) {
					z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + M] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
				}
				for(; i < N - 1; ++i) {
					z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
				}
				z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
				mti = 0;
			}
			z = mt[mti++];
			z ^= z >>> MAGIC_FACTOR_F;
			z ^= (z << MAGIC_MASK_F) & MAGIC_MASK_A;
			z ^= (z << MAGIC_MASK_G) & MAGIC_MASK_B;
			z ^= (z >>> MAGIC_FACTOR_G);
			bits = (((((long)y) << MAGIC_MASK_D) + z) >>> MAGIC_MASK_C);
			val = bits % bound;
		}
		while(bits - val + (bound - 1) < 0);
		return val;
	}
	public double nextDouble() {
		final int[] mt = mtn;
		int y, z;
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
		y ^= y >>> MAGIC_FACTOR_F;
		y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
		y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
		y ^= (y >>> MAGIC_FACTOR_G);
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
			}
			for(; i < N - 1; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
			}
			z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
			mti = 0;
		}
		z = mt[mti++];
		z ^= z >>> MAGIC_FACTOR_F;
		z ^= (z << MAGIC_MASK_F) & MAGIC_MASK_A;
		z ^= (z << MAGIC_MASK_G) & MAGIC_MASK_B;
		z ^= (z >>> MAGIC_FACTOR_G);
		return ((((long)(y >>> 6)) << 27) + (z >>> 5)) / (double)(1L << MAGIC_MASK_E);
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
	public void clearGaussian() {
		__haveNextNextGaussian = false;
	}
	public double nextGaussian() {
		if(__haveNextNextGaussian) {
			__haveNextNextGaussian = false;
			return __nextNextGaussian;
		}
		double v1, v2, s;
		final int[] mt = mtn;
		do {
			int y, z, a, b;
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
			y ^= y >>> MAGIC_FACTOR_F;
			y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
			y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
			y ^= (y >>> MAGIC_FACTOR_G);
			if(mti >= N) {
				int i;
				for(i = 0; i < N - M; ++i) {
					z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + M] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
				}
				for(; i < N - 1; ++i) {
					z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
				}
				z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (z >>> MAGIC_MASK_C) ^ MAGIC[z & MAGIC_MASK_C];
				mti = 0;
			}
			z = mt[mti++];
			z ^= z >>> MAGIC_FACTOR_F;
			z ^= (z << MAGIC_MASK_F) & MAGIC_MASK_A;
			z ^= (z << MAGIC_MASK_G) & MAGIC_MASK_B;
			z ^= (z >>> MAGIC_FACTOR_G);
			if(mti >= N) {
				int i;
				for(i = 0; i < N - M; ++i) {
					a = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + M] ^ (a >>> MAGIC_MASK_C) ^ MAGIC[a & MAGIC_MASK_C];
				}
				for(; i < N - 1; ++i) {
					a = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (a >>> MAGIC_MASK_C) ^ MAGIC[a & MAGIC_MASK_C];
				}
				a = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (a >>> MAGIC_MASK_C) ^ MAGIC[a & MAGIC_MASK_C];
				mti = 0;
			}
			a = mt[mti++];
			a ^= a >>> MAGIC_FACTOR_F;
			a ^= (a << MAGIC_MASK_F) & MAGIC_MASK_A;
			a ^= (a << MAGIC_MASK_G) & MAGIC_MASK_B;
			a ^= (a >>> MAGIC_FACTOR_G);
			if(mti >= N) {
				int i;
				for(i = 0; i < N - M; ++i) {
					b = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + M] ^ (b >>> MAGIC_MASK_C) ^ MAGIC[b & MAGIC_MASK_C];
				}
				for(; i < N - 1; ++i) {
					b = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (b >>> MAGIC_MASK_C) ^ MAGIC[b & MAGIC_MASK_C];
				}
				b = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (b >>> MAGIC_MASK_C) ^ MAGIC[b & MAGIC_MASK_C];
				mti = 0;
			}
			b = mt[mti++];
			b ^= b >>> MAGIC_FACTOR_F;
			b ^= (b << MAGIC_MASK_F) & MAGIC_MASK_A;
			b ^= (b << MAGIC_MASK_G) & MAGIC_MASK_B;
			b ^= (b >>> MAGIC_FACTOR_G);
			v1 = ((((long)(y >>> 6)) << 27) + (z >>> 5)) / (double)(1L << MAGIC_MASK_E) * 2 - 1;
			v2 = ((((long)(a >>> 6)) << 27) + (b >>> 5)) / (double)(1L << MAGIC_MASK_E) * 2 - 1;
			s = v1 * v1 + v2 * v2;
		}
		while(s >= 1 || s == 0);
		double multiplier = StrictMath.sqrt(StrictMath.log(s) * -2 / s);
		__nextNextGaussian = v2 * multiplier;
		__haveNextNextGaussian = true;
		return v1 * multiplier;
	}
	public float nextFloat() {
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
		y ^= y >>> MAGIC_FACTOR_F;
		y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
		y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
		y ^= (y >>> MAGIC_FACTOR_G);
		return (y >>> 8) / ((float)(1 << MAGIC_MASK_H));
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
	public int nextInt(int bound) {
		if(bound <= 0) throw new IllegalArgumentException("bound must be positive, got: " + bound);
		final int[] mt = mtn;
		if((bound & -bound) == bound) {
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
			y ^= y >>> MAGIC_FACTOR_F;
			y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
			y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
			y ^= (y >>> MAGIC_FACTOR_G);
			return (int)((bound * (long)(y >>> MAGIC_MASK_C)) >> MAGIC_FACTOR_E);
		}
		int bits, val;
		do {
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
			y ^= y >>> MAGIC_FACTOR_F;
			y ^= (y << MAGIC_MASK_F) & MAGIC_MASK_A;
			y ^= (y << MAGIC_MASK_G) & MAGIC_MASK_B;
			y ^= (y >>> MAGIC_FACTOR_G);
			bits = (y >>> MAGIC_MASK_C);
			val = bits % bound;
		}
		while(bits - val + (bound - 1) < 0);
		return val;
	}
}