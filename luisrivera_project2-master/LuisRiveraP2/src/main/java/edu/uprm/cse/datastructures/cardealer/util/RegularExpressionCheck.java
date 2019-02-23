package edu.uprm.cse.datastructures.cardealer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionCheck {

	public static boolean regexChecker(String theRegex, String stringToCheck) {
		Pattern regexCompiler = Pattern.compile(theRegex);
		Matcher regexMatcher = regexCompiler.matcher(stringToCheck);

		if(regexMatcher.matches()) 
			return true;
		return false;
	}
}