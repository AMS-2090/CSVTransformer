package csvtransformer;

/**
 * Normalizer abstract class defines methods: 
 * - find() 
 * - normalize() 
 * which are inherited by PriceNormalizer and DateNormalizer classes.
 * Normalizer also defines abstract method:
 * - replace()
 * 
 * @author Arkadiusz So³tysiak
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Normalizer {
	
	/* regex is defined in child classes */
	protected String regex;
	
	/* replace() method is defined by child classes */
	protected abstract String replace(String[] groups);
	
	/*
	 * find() method searches for regex match
	 * and returns array of found matched groups.
	 */
	public String[] find(String input) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		String[] groups = null;

		if (matcher.find()) {

			int groupCount = matcher.groupCount();
			groups = new String[groupCount + 1];
			for (int i = 0; i < groups.length; i++) {
				groups[i] = matcher.group(i);
			}
		}
		return groups;
	}
	
	/*
	 * normalize() method combines results of find() and
	 * replace() to return normalized String.
	 */
	public String normalize(String input) {

		String[] foundGroups = find(input);

		return replace(foundGroups);
	}

}
