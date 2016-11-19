package csvtransformer;


/**
 * This is a child class of {@link csvtransformer.Normalizer} class
 * which implements replacing algorithm for different price notations.
 * <p>Example price notation inputs:
 * <p><ul>
 * <li>1,000.00
 * <li>1000,00
 * <li>100.000,0
 * </ul>
 * <p>After replacing results:
 * <p><ul>
 * <li>1000.00
 * <li>1000.00
 * <li>100000.0
 * </ul>
 * 
 * @author Arkadiusz So³tysiak
 */

public class PriceNormalizer extends Normalizer{

	/** regular expression for price pattern */
	private static final String PRICE_REGEX = "\\d+([.,]\\d{3})*([.,]\\d{1,2})?";
	
	/** regular expression for decimal separator */
	private static final String LAST_SEPARATOR_REGEX = "[.,](?=\\d{1,2}$)";
	
	/** regular expression for all separators (decimal and thousands) */
	private static final String SEPARATORS_REGEX = "[.,]";

	/**
	 * Constructor initializes {@link Normalizer#regex} field
	 * to be used in {@link Normalizer#find(String)} method. 
	 */
	public PriceNormalizer() {
		super.regex = PRICE_REGEX;
	}
	

	/**
	 * Implementation of {@link Normalizer#replace(String[])} method
	 * to erase unwanted punctuation and set decimal separator as a period.
	 */
	@Override
	protected String replace(String[] groups) {

		if (groups == null) {
			return "";
		} else {

			/* groups[0] - entire match */
			String matchedPrice = groups[0];
			
			/* temporary character to set at the decimal separator position */
			String tempPeriodReplacement = "#";
			
			/* setting decimal separator position with # */
			String lastPunctHashedPrice = matchedPrice.replaceAll(
					LAST_SEPARATOR_REGEX, tempPeriodReplacement);
			
			/* removing all of the separators */
			String punctRemovedPrice = lastPunctHashedPrice.replaceAll(
					SEPARATORS_REGEX, "");
			
			/* going back to '.' instead of # */
			String backToPeriodPrice = punctRemovedPrice.replaceAll(
					tempPeriodReplacement, ".");

			return backToPeriodPrice;
		}
	}

}
