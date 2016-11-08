package csvtransformer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This abstract class is a base for finding and replacing defined patterns. 
 * 
 * @author Arkadiusz So³tysiak
 */

public abstract class Normalizer {
	
	/** 
	 * Regular expression for searched pattern. 
	 * Defined in the child classes.
	 */
	protected String regex;
	
	/**
	 * Replacing method to be implemented in the child classes. 
	 * 
	 * @param groups is an array of found pattern occurrences.
	 * @return modified pattern occurrence.
	 */
	protected abstract String replace(String[] groups);
	

	/**
	 * Method searches for the {@link #regex} match
	 * in the given input.
	 * 
	 * @param input to searched for REGEX pattern.
	 * @return array of Strings containing found REGEX groups
	 */
	private String[] find(String input) {

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
	
	/**
	 * Combines results of {@link #find(String)} and {@link #replace(String[])}.
	 * 
	 * @param input to searched for REGEX pattern.
	 * @return normalized String
	 */
	public String normalize(String input) {

		String[] foundGroups = find(input);

		return replace(foundGroups);
	}

}
