package csvtransformer;

/**
 * DateNormalizer class is a child of Normalizer class which defines:
 * - DATE_REGEX constant
 * - replace() method
 * 
 * @author Arkadiusz So³tysiak
 */

public class DateNormalizer extends Normalizer {

	/* constant to store regex for matching wanted date patterns */
	private static final String DATE_REGEX = "(\\d{1,2})(?:(?:\\.(\\d{1,2})\\.(\\d{4}))|(?:-(\\d{1,2})-(\\d{4})))";

	/* constructor assigns DATE_REGEX to use in find() method */
	public DateNormalizer() {
		super.regex = DATE_REGEX;
	}

	/*
	 * Overriding the replace() method to parse date and set day, month and year
	 * at the proper positions.
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
			 * USA date format: groups[2] == null means that the regex group for
			 * USA date format was matched.
			 */
			if (groups[2] == null) {
				if (groups[4].length() == 1) {
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
			
				// Not USA date format
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
