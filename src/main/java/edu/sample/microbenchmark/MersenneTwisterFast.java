package edu.sample.microbenchmark;
import java.io.Serializable;
/**
 * https://cs.gmu.edu/~sean/research/mersenne/MersenneTwisterFast.java
 * @author Sean Luke
 */
public strictfp class MersenneTwisterFast implements Serializable {
	private static final long serialVersionUID = -8219700664442619525L;
	private static final int UPPER_MASK = Integer.MIN_VALUE;
	private static final int LOWER_MASK = Integer.MAX_VALUE;
	private static final int SEED_MASK = -1;
	private static final int N = 624;
	private static final int M = 397;
	private static final int[] MAGIC = {0, -1727483681};
	private static final int MAGIC_FACTOR_A = 1812433253;
	private static final int MAGIC_FACTOR_B = 1664525;
	private static final int MAGIC_FACTOR_C = 1566083941;
	private static final int MAGIC_MASK_A = -1658038656;
	private static final int MAGIC_MASK_B = -272236544;
	private static final int MAGIC_SEED = 19650218;
	private int[] mtn = new int[N];
	private int mti;
	private double nextNextGaussian;
	private boolean haveNextNextGaussian;
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
		final int[] mt = mtn;
		mt[0] = (int)(seed & SEED_MASK);
		for(mti = 0; ++mti < N;) {
			mt[mti] = (MAGIC_FACTOR_A * (mt[mti - 1] ^ (mt[mti - 1] >>> 30)) + mti);
		}
		haveNextNextGaussian = false;
	}
	public void setSeed(int[] array) {
		final int length = array.length;
		if(length == 0) throw new IllegalArgumentException("Array length must be greater than zero");
		setSeed(MAGIC_SEED);
		final int[] mt = mtn;
		int i = 1, j = 0, k = N > length ? N : length;
		for(; k != 0; --k) {
			mt[i] = (mt[i] ^ ((mt[i - 1] ^ (mt[i - 1] >>> 30)) * MAGIC_FACTOR_B)) + array[j] + j;
			++i;
			++j;
			if(i >= N) {
				mt[0] = mt[N - 1];
				i = 1;
			}
			if(j >= length) j = 0;
		}
		for(k = N - 1; k != 0; --k) {
			mt[i] = (mt[i] ^ ((mt[i - 1] ^ (mt[i - 1] >>> 30)) * MAGIC_FACTOR_C)) - i;
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
				mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			for(; i < N - 1; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & MAGIC_MASK_A;
		y ^= (y << 15) & MAGIC_MASK_B;
		y ^= (y >>> 18);
		return y;
	}
	public short nextShort() {
		final int[] mt = mtn;
		int y;
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			for(; i < N - 1; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & MAGIC_MASK_A;
		y ^= (y << 15) & MAGIC_MASK_B;
		y ^= (y >>> 18);
		return (short)(y >>> 16);
	}
	public char nextChar() {
		final int[] mt = mtn;
		int y;
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			for(; i < N - 1; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & MAGIC_MASK_A;
		y ^= (y << 15) & MAGIC_MASK_B;
		y ^= (y >>> 18);
		return (char)(y >>> 16);
	}
	public boolean nextBoolean() {
		final int[] mt = mtn;
		int y;
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			for(; i < N - 1; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & MAGIC_MASK_A;
		y ^= (y << 15) & MAGIC_MASK_B;
		y ^= (y >>> 18);
		return (y >>> 31) != 0;
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
				mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			for(; i < N - 1; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & MAGIC_MASK_A;
		y ^= (y << 15) & MAGIC_MASK_B;
		y ^= (y >>> 18);
		return (y >>> 8) / ((float)(1 << 24)) < probability;
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
				mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			for(; i < N - 1; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & MAGIC_MASK_A;
		y ^= (y << 15) & MAGIC_MASK_B;
		y ^= (y >>> 18);
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (z >>> 1) ^ MAGIC[z & 0x1];
			}
			for(; i < N - 1; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (z >>> 1) ^ MAGIC[z & 0x1];
			}
			z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ MAGIC[z & 0x1];
			mti = 0;
		}
		z = mt[mti++];
		z ^= z >>> 11;
		z ^= (z << 7) & MAGIC_MASK_A;
		z ^= (z << 15) & MAGIC_MASK_B;
		z ^= (z >>> 18);
		return ((((long)(y >>> 6)) << 27) + (z >>> 5)) / (double)(1L << 53) < probability;
	}
	public byte nextByte() {
		final int[] mt = mtn;
		int y;
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			for(; i < N - 1; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & MAGIC_MASK_A;
		y ^= (y << 15) & MAGIC_MASK_B;
		y ^= (y >>> 18);
		return (byte)(y >>> 24);
	}
	public void nextBytes(byte[] bytes) {
		final int[] mt = mtn;
		for(int i = -1, y, l = bytes.length; ++i < l;) {
			if(mti >= N) {
				int j;
				for(j = 0; j < N - M; ++j) {
					y = (mt[j] & UPPER_MASK) | (mt[j + 1] & LOWER_MASK);
					mt[j] = mt[j + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				}
				for(; j < N - 1; ++j) {
					y = (mt[j] & UPPER_MASK) | (mt[j + 1] & LOWER_MASK);
					mt[j] = mt[j + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				}
				y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				mti = 0;
			}
			y = mt[mti++];
			y ^= y >>> 11;
			y ^= (y << 7) & MAGIC_MASK_A;
			y ^= (y << 15) & MAGIC_MASK_B;
			y ^= (y >>> 18);
			bytes[i] = (byte)(y >>> 24);
		}
	}
	public long nextLong() {
		final int[] mt = mtn;
		int y, z;
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			for(; i < N - 1; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & MAGIC_MASK_A;
		y ^= (y << 15) & MAGIC_MASK_B;
		y ^= (y >>> 18);
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (z >>> 1) ^ MAGIC[z & 0x1];
			}
			for(; i < N - 1; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (z >>> 1) ^ MAGIC[z & 0x1];
			}
			z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ MAGIC[z & 0x1];
			mti = 0;
		}
		z = mt[mti++];
		z ^= z >>> 11;
		z ^= (z << 7) & MAGIC_MASK_A;
		z ^= (z << 15) & MAGIC_MASK_B;
		z ^= (z >>> 18);
		return (((long)y) << 32) + z;
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
					mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				}
				for(; i < N - 1; ++i) {
					y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				}
				y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				mti = 0;
			}
			y = mt[mti++];
			y ^= y >>> 11;
			y ^= (y << 7) & MAGIC_MASK_A;
			y ^= (y << 15) & MAGIC_MASK_B;
			y ^= (y >>> 18);
			if(mti >= N) {
				int i;
				for(i = 0; i < N - M; ++i) {
					z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + M] ^ (z >>> 1) ^ MAGIC[z & 0x1];
				}
				for(; i < N - 1; ++i) {
					z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (z >>> 1) ^ MAGIC[z & 0x1];
				}
				z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ MAGIC[z & 0x1];
				mti = 0;
			}
			z = mt[mti++];
			z ^= z >>> 11;
			z ^= (z << 7) & MAGIC_MASK_A;
			z ^= (z << 15) & MAGIC_MASK_B;
			z ^= (z >>> 18);
			bits = (((((long)y) << 32) + z) >>> 1);
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
				mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			for(; i < N - 1; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & MAGIC_MASK_A;
		y ^= (y << 15) & MAGIC_MASK_B;
		y ^= (y >>> 18);
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (z >>> 1) ^ MAGIC[z & 0x1];
			}
			for(; i < N - 1; ++i) {
				z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (z >>> 1) ^ MAGIC[z & 0x1];
			}
			z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ MAGIC[z & 0x1];
			mti = 0;
		}
		z = mt[mti++];
		z ^= z >>> 11;
		z ^= (z << 7) & MAGIC_MASK_A;
		z ^= (z << 15) & MAGIC_MASK_B;
		z ^= (z >>> 18);
		return ((((long)(y >>> 6)) << 27) + (z >>> 5)) / (double)(1L << 53);
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
		haveNextNextGaussian = false;
	}
	public double nextGaussian() {
		if(haveNextNextGaussian) {
			haveNextNextGaussian = false;
			return nextNextGaussian;
		}
		double v1, v2, s;
		final int[] mt = mtn;
		do {
			int y, z, a, b;
			if(mti >= N) {
				int i;
				for(i = 0; i < N - M; ++i) {
					y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				}
				for(; i < N - 1; ++i) {
					y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				}
				y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				mti = 0;
			}
			y = mt[mti++];
			y ^= y >>> 11;
			y ^= (y << 7) & MAGIC_MASK_A;
			y ^= (y << 15) & MAGIC_MASK_B;
			y ^= (y >>> 18);
			if(mti >= N) {
				int i;
				for(i = 0; i < N - M; ++i) {
					z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + M] ^ (z >>> 1) ^ MAGIC[z & 0x1];
				}
				for(; i < N - 1; ++i) {
					z = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (z >>> 1) ^ MAGIC[z & 0x1];
				}
				z = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (z >>> 1) ^ MAGIC[z & 0x1];
				mti = 0;
			}
			z = mt[mti++];
			z ^= z >>> 11;
			z ^= (z << 7) & MAGIC_MASK_A;
			z ^= (z << 15) & MAGIC_MASK_B;
			z ^= (z >>> 18);
			if(mti >= N) {
				int i;
				for(i = 0; i < N - M; ++i) {
					a = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + M] ^ (a >>> 1) ^ MAGIC[a & 0x1];
				}
				for(; i < N - 1; ++i) {
					a = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (a >>> 1) ^ MAGIC[a & 0x1];
				}
				a = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (a >>> 1) ^ MAGIC[a & 0x1];
				mti = 0;
			}
			a = mt[mti++];
			a ^= a >>> 11;
			a ^= (a << 7) & MAGIC_MASK_A;
			a ^= (a << 15) & MAGIC_MASK_B;
			a ^= (a >>> 18);
			if(mti >= N) {
				int i;
				for(i = 0; i < N - M; ++i) {
					b = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + M] ^ (b >>> 1) ^ MAGIC[b & 0x1];
				}
				for(; i < N - 1; ++i) {
					b = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (b >>> 1) ^ MAGIC[b & 0x1];
				}
				b = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (b >>> 1) ^ MAGIC[b & 0x1];
				mti = 0;
			}
			b = mt[mti++];
			b ^= b >>> 11;
			b ^= (b << 7) & MAGIC_MASK_A;
			b ^= (b << 15) & MAGIC_MASK_B;
			b ^= (b >>> 18);
			v1 = 2 * (((((long)(y >>> 6)) << 27) + (z >>> 5)) / (double)(1L << 53)) - 1;
			v2 = 2 * (((((long)(a >>> 6)) << 27) + (b >>> 5)) / (double)(1L << 53)) - 1;
			s = v1 * v1 + v2 * v2;
		}
		while(s >= 1 || s == 0);
		double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s) / s);
		nextNextGaussian = v2 * multiplier;
		haveNextNextGaussian = true;
		return v1 * multiplier;
	}
	public float nextFloat() {
		final int[] mt = mtn;
		int y;
		if(mti >= N) {
			int i;
			for(i = 0; i < N - M; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			for(; i < N - 1; ++i) {
				y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
				mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			}
			y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
			mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
			mti = 0;
		}
		y = mt[mti++];
		y ^= y >>> 11;
		y ^= (y << 7) & MAGIC_MASK_A;
		y ^= (y << 15) & MAGIC_MASK_B;
		y ^= (y >>> 18);
		return (y >>> 8) / ((float)(1 << 24));
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
					mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				}
				for(; i < N - 1; ++i) {
					y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				}
				y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				mti = 0;
			}
			y = mt[mti++];
			y ^= y >>> 11;
			y ^= (y << 7) & MAGIC_MASK_A;
			y ^= (y << 15) & MAGIC_MASK_B;
			y ^= (y >>> 18);
			return (int)((bound * (long)(y >>> 1)) >> 31);
		}
		int bits, val;
		do {
			int y;
			if(mti >= N) {
				int i;
				for(i = 0; i < N - M; ++i) {
					y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + M] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				}
				for(; i < N - 1; ++i) {
					y = (mt[i] & UPPER_MASK) | (mt[i + 1] & LOWER_MASK);
					mt[i] = mt[i + (M - N)] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				}
				y = (mt[N - 1] & UPPER_MASK) | (mt[0] & LOWER_MASK);
				mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAGIC[y & 0x1];
				mti = 0;
			}
			y = mt[mti++];
			y ^= y >>> 11;
			y ^= (y << 7) & MAGIC_MASK_A;
			y ^= (y << 15) & MAGIC_MASK_B;
			y ^= (y >>> 18);
			bits = (y >>> 1);
			val = bits % bound;
		}
		while(bits - val + (bound - 1) < 0);
		return val;
	}
}