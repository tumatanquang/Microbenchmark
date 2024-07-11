package edu.sample.microbenchmark;
public class CompilotDecimalFormat {
	private final String mPattern;
	public CompilotDecimalFormat(String pattern) {
		mPattern = pattern;
	}
	public String format(float value) {
		return formatInternal(value);
	}
	public String format(double value) {
		return formatInternal(value);
	}
	private String formatInternal(double value) {
		String stringValue = Double.toString(value);
		int decimalSeparatorIndex = stringValue.indexOf('.');
		if(decimalSeparatorIndex < 0)
			return stringValue;
		int numDecimalPlaces = stringValue.length() - decimalSeparatorIndex - 1,
		maxDecimalPlaces = mPattern.length() - mPattern.indexOf('.') - 1;
		if(numDecimalPlaces > maxDecimalPlaces) {
			double roundedValue = Math.round(value * Math.pow(10, maxDecimalPlaces)) / Math.pow(10, maxDecimalPlaces);
			stringValue = Double.toString(roundedValue);
		}
		return stringValue;
	}
}
