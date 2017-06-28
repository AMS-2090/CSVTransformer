package csvtransformer;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PriceNormalizerTest {

	private Normalizer priceNorm;
	
	@Before
	public final void before() {
		priceNorm = new PriceNormalizer();
	}
	
	@Test
	public void whenNormalizeAndCommaAsDecimalSeparatorThenPutPeriod() {
		String input = "price with decimal separator: 1000,0 PLN ";
		String normalizedInput = priceNorm.normalize(input);
		assertEquals("1000.0", normalizedInput);
	}
	
	@Test
	public void whenNormalizeAndCommaAs000SeparatorThenNoSep() {
		String input = "price with thousands separators: 1,000,000 USD";
		String normalizedInput = priceNorm.normalize(input);
		assertEquals("1000000", normalizedInput);
	}
	
	@Test
	public void whenNormalizeAndPeriodAs000SeparatorThenNoSep() {
		String input = "price with thousands separators: 1.000.000 GBP";
		String normalizedInput = priceNorm.normalize(input);
		assertEquals("1000000", normalizedInput);
	}
	
	@Test
	public void whenNormalizeAndCommaAs000AndDecimalSeparatorThenNo000SepAndPutPeriod() {
		String input = "price with comma as thousands and decimal separator: 1,000,00 USD";
		String normalizedInput = priceNorm.normalize(input);
		assertEquals("1000.00", normalizedInput);
	}
	
	@Test
	public void whenNormalizeAndPeriodAs000AndDecSeparatorThenNo000Sep() {
		String input = "price with period as thousands and decimal separator: 1.000.0 EUR";
		String normalizedInput = priceNorm.normalize(input);
		assertEquals("1000.0", normalizedInput);
	}

	@Test
	public void whenNormalizeAndCommaAs000SeparatorAndPeriodAsDecSepThenNo000Sep() {
		String input = "price with comma as thousands separator"
				+ " and period as decimal separator: 1,000.0 USD";
		String normalizedInput = priceNorm.normalize(input);
		assertEquals("1000.0", normalizedInput);
	}
	
	@Test
	public void whenNormalizeAndNoPriceFoundThenEmptyString() {
		String input = "Here are no price patterns at all.";
		String normalizedInput = priceNorm.normalize(input);
		assertEquals("", normalizedInput);
	}
	
}
