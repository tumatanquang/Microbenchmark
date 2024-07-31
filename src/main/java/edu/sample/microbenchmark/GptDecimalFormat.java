package edu.sample.microbenchmark;
public class GptDecimalFormat {
	private String mPattern;
	public GptDecimalFormat(String pattern) {
		mPattern = pattern;
	}
	public String format(float value) {
		return format((double)value);
	}
	public String format(double value) {
		boolean hasDecimal = mPattern.contains(".");
		String[] parts = mPattern.split("\\.");
		String integerPartPattern = parts[0];
		String decimalPartPattern = hasDecimal && parts.length > 1 ? parts[1] : "";
		String integerPart = formatIntegerPart(value, integerPartPattern);
		String decimalPart = hasDecimal ? formatDecimalPart(value, decimalPartPattern) : "";
		if(hasDecimal) return integerPart + "." + decimalPart;
		return integerPart;
	}
	private static String formatIntegerPart(double value, String pattern) {
		int intValue = (int)value;
		String intString = String.valueOf(intValue);
		StringBuilder result = new StringBuilder();
		int patternIndex = pattern.length() - 1, intStringIndex = intString.length() - 1;
		for(char patternChar; patternIndex >= 0; --patternIndex) {
			patternChar = pattern.charAt(patternIndex);
			if(patternChar == '0' || patternChar == '#') {
				if(intStringIndex >= 0) {
					result.insert(0, intString.charAt(intStringIndex));
					--intStringIndex;
				}
				else if(patternChar == '0') result.insert(0, '0');
			}
			else result.insert(0, patternChar);
		}
		for(; intStringIndex >= 0; --intStringIndex) {
			result.insert(0, intString.charAt(intStringIndex));
		}
		return result.toString();
	}
	private static String formatDecimalPart(double value, String pattern) {
		String decimalString = String.valueOf(value);
		int decimalIndex = decimalString.indexOf('.');
		StringBuilder result = new StringBuilder();
		if(decimalIndex < 0) return result.toString();
		decimalString = decimalString.substring(decimalIndex + 1);
		for(int i = -1, patternLength = pattern.length(), decimalStringLength = decimalString.length(); ++i < patternLength;) {
			if(i < decimalStringLength) result.append(decimalString.charAt(i));
			else if(pattern.charAt(i) == '0') result.append('0');
			else break;
		}
		return result.toString();
	}
}
