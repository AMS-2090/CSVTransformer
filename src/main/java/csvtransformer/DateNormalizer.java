package csvtransformer;

/**
 * This is a child class of {@link csvtransformer.Normalizer} class
 * which implements replacing algorithm for different date notations.
 * <p>Example date notation inputs:
 * <p><ul>
 * <li>01.01.2016
 * <li>31.1.2016
 * <li>12-31-2016
 * </ul>
 * <p> After replacing result should have <b>DD.MM.YYYY</b> format. 
 * 
 * @author Arkadiusz So³tysiak
 */

public class DateNormalizer extends Normalizer {

	/**
	 * regular expression for date pattern
	 * <p><ul>
	 * <li>group 1: DAY for non-USA date format or MONTH for USA
	 * <li>group 2: MONTH for non-USA date format
	 * <li>group 3: YEAR for non-USA date format
	 * <li>group 4: DAY for USA date format
	 * <li>group 5: YEAR for USA date format
	 */
	private static final String DATE_REGEX 
	= "(\\d{1,2})(?:(?:\\.(\\d{1,2})\\.(\\d{4}))|(?:-(\\d{1,2})-(\\d{4})))";

	/**
	 * Constructor initializes {@link Normalizer#regex} field
	 * to be used in {@link Normalizer#find(String)} method. 
	 */
	public DateNormalizer() {
		super.regex = DATE_REGEX;
	}

	/**
	 * Implementation of {@link Normalizer#replace(String[])} method
	 * to set the proper date format (DD.MM.YYYY).
	 */
	@Override
	protected String replace(String[] groups) {

		if (groups == null) {
			return "";
		} else {

			StringBuilder sbDate = new StringBuilder();
			StringBuilder sbDay = new StringBuilder();
			StringBuilder sbMonth = new StringBuilder();
			StringBuilder sbYear = new StringBuilder();

			/*
			 * USA date format (MM-DD-YYYY): groups[2] == null
			 * Means that the regex group
			 * for USA date format was matched.
			 */
			if (groups[2] == null) {
				if (groups[4].length() == 1) {
					/* if one digit only, append 0 */
					sbDay.append("0").append(groups[4]);
				} else {
					sbDay.append(groups[4]);
				}
				if (groups[1].length() == 1) {
					sbMonth.append("0").append(groups[1]);
				} else {
					sbMonth.append(groups[1]);
				}
				sbYear.append(groups[5]);
				sbDate.append(sbDay).append(".").append(sbMonth).append(".")
						.append(sbYear);
			
				/* Non-USA date format */
			} else {
				if (groups[1].length() == 1) {
					sbDay.append("0").append(groups[1]);
				} else {
					sbDay.append(groups[1]);
				}
				if (groups[2].length() == 1) {
					sbMonth.append("0").append(groups[2]);
				} else {
					sbMonth.append(groups[2]);
				}
				sbYear.append(groups[3]);
				sbDate.append(sbDay).append(".").append(sbMonth).append(".")
						.append(sbYear);
			}
			return sbDate.toString();
		}
	}

}
