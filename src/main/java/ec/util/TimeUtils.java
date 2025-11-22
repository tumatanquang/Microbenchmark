package ec.util;
public final class TimeUtils {
	private TimeUtils() {}
	private static final long BASE_MILLIS = System.currentTimeMillis();
	private static final long BASE_NANOS = System.nanoTime();
	public static long currentTimeMillis() {
		return BASE_MILLIS + (System.nanoTime() - BASE_NANOS) / 1_000_000L;
	}
}