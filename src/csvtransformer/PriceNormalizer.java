package csvtransformer;


/**
 * PriceNormalizer class is a child of Normalizer class which defines:
 * - PRICE_REGEX
 * - LAST_SEPARATOR_REGEX
 * - SEPARATORS_REGEX
 * - replace() method
 * 
 * @author Arkadiusz So³tysiak
 */

public class PriceNormalizer extends Normalizer{

	/* constants to store regex for matching 
	 * - price pattern,
	 * - last separator before the decimal part,
	 * - occurring separators in given numbers
	 */
	private static final String PRICE_REGEX = "\\d+([.,]\\d{3})*([.,]\\d{1,2})?";
	private static final String LAST_SEPARATOR_REGEX = "[.,](?=\\d{1,2}$)";
	private static final String SEPARATORS_REGEX = "[.,]";

	/* constructor assigns PRICE_REGEX to use in find() method */
	public PriceNormalizer() {
		super.regex = PRICE_REGEX;
	}
	
	/*
	 * Overriding the replace() method to erase unwanted
	 * punctuation and set decimal separator as a dot.
	 */
	@Override
	protected String replace(String[] groups) {

		if (groups == null) {
			return "";
		} else {

			/* groups[0] - entire match */
			String matchedPrice = groups[0];
			/* temporary character to set at the decimal separator position */
			String tempDotReplacement = "#";
			/* setting decimal separator position with # */
			String lastPunctHashedPrice = matchedPrice.replaceAll(
					LAST_SEPARATOR_REGEX, tempDotReplacement);
			/* removing all of the separators */
			String punctRemovedPrice = lastPunctHashedPrice.replaceAll(
					SEPARATORS_REGEX, "");
			/* going back to '.' instead of # */
			String backToDotPrice = punctRemovedPrice.replaceAll(
					tempDotReplacement, ".");

			return backToDotPrice;
		}
	}

}
