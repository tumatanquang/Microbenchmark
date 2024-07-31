package edu.sample.microbenchmark;
public class SimpleDecimalFormat {
	private String mPattern;
	public SimpleDecimalFormat(String pattern) {
		mPattern = pattern;
	}
	public String format(float number) {
		return format((double)number);
	}
	public String format(double number) {
		boolean hasDecimal = false, leadingZero = false;
		int decimalPlaces = 0;
		String pattern = mPattern;
		char c;
		for(int i = -1, length = pattern.length(); ++i < length;) {
			c = pattern.charAt(i);
			if(c == '0' || c == '#') {
				if(hasDecimal) ++decimalPlaces;
				if(c == '0' && !hasDecimal) leadingZero = true;
			}
			else if(c == '.') hasDecimal = true;
		}
		String formattedNumber = roundToString(number, decimalPlaces);
		if(!leadingZero && formattedNumber.charAt(0) == '0') {
			formattedNumber = formattedNumber.substring(1);
		}
		return formattedNumber;
	}
	private static String roundToString(double value, int precision) {
		StringBuilder sb = new StringBuilder();
		if(precision > 0) {
			long factor = (long)Math.pow(10, precision);
			value = Math.round(value * factor) / (double)factor;
		}
		sb.append(value);
		return sb.toString();
	}
}