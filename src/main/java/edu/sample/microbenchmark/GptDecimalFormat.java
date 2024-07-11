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
		if(hasDecimal)
			return integerPart + "." + decimalPart;
		return integerPart;
	}
	private String formatIntegerPart(double value, String pattern) {
		int intValue = (int)value;
		String intString = Integer.toString(intValue);
		StringBuilder result = new StringBuilder();
		int patternIndex = pattern.length() - 1;
		int intStringIndex = intString.length() - 1;
		while(patternIndex >= 0) {
			char patternChar = pattern.charAt(patternIndex);
			if(patternChar == '0' || patternChar == '#') {
				if(intStringIndex >= 0) {
					result.insert(0, intString.charAt(intStringIndex));
					--intStringIndex;
				}
				else if(patternChar == '0')
					result.insert(0, '0');
			}
			else {
				result.insert(0, patternChar);
			}
			--patternIndex;
		}
		while(intStringIndex >= 0) {
			result.insert(0, intString.charAt(intStringIndex));
			--intStringIndex;
		}
		return result.toString();
	}
	private String formatDecimalPart(double value, String pattern) {
		String decimalString = Double.toString(value);
		int decimalIndex = decimalString.indexOf('.');
		StringBuilder result = new StringBuilder();
		if(decimalIndex == -1)
			return result.toString();
		decimalString = decimalString.substring(decimalIndex + 1);
		for(int i = 0, patternLength = pattern.length(), decimalStringLength = decimalString.length(); i < patternLength; ++i) {
			if(i < decimalStringLength)
				result.append(decimalString.charAt(i));
			else if(pattern.charAt(i) == '0')
				result.append('0');
			else
				break;
		}
		return result.toString();
	}
}
