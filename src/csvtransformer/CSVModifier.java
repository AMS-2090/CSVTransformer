package csvtransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manages CSV file modification.
 * <p>
 * Input file should have following column names and format:
 * <p>
 * {@code "Product Name";"Link";"SKU";"Selling-Price";"description"}
 * <p>
 * Output file will have following column names and format:
 * <p>
 * {@code 'name'|'offerurl'|'price'|'published'|'description'}
 * 
 * @author Arkadiusz So³tysiak
 */

public class CSVModifier {
	/*
	 * Constants describing specific column position
	 * in the original CSV file
	 */
	private static final int PRODUCT_NAME_POS = 0;
	private static final int LINK_POS = 1;
	private static final int SKU_POS = 2;
	private static final int SELLING_PRICE_POS = 3;
	private static final int DESCRIPTION_POS = 4;
	
	/*
	 * Constants describing specific column position
	 * in the target CSV file
	 */
	private static final int NEW_PRODUCT_NAME_POS = 0;
	private static final int OFFERURL_POS = 1;
	private static final int PRICE_POS = 2;
	private static final int PUBLISHED_POS = 3;
	private static final int NEW_DESCRIPTION_POS = 4;
	
	/** Stores original data from CSV file. */
	private List<String[]> originalCsvRows;
	/** Stores modified data from CSV file. */
	private List<String[]> modCsvRows;

	/**
	 * Constructor initializes two {@link #originalCsvRows}
	 * and {@link #modCsvRows} fields with {@code NULL}.
	 */
	public CSVModifier() {
		originalCsvRows = null;
		modCsvRows = null;
	}

	/**
	 * Constructor initializes {@link #originalCsvRows}
	 * with given {@code List<String[]>}.
	 * 
	 * @param originalCsvRows is a List of original CSV rows
	 */
	public CSVModifier(List<String[]> originalCsvRows) {
		 setCsvRows(originalCsvRows);
	}

	/** 
	 * @return modified rows of CSV file
	 */
	public List<String[]> getCsvRows() {
		return modCsvRows;
	}

	/**
	 * Initializes {@link #originalCsvRows} field
	 * with given CSV rows.
	 * 
	 * @param originalCsvRows is a List of original CSV rows
	 */
	public void setCsvRows(List<String[]> originalCsvRows) {
		this.originalCsvRows = originalCsvRows;
		/* creating new empty ArrayList for modified data */
		modCsvRows = new ArrayList<String[]>();
	}
	
	/**
	 * Adds column names to the new empty List {@link #modCsvRows}
	 * and then inserts modified CSV rows into it.
	 */
	public void transformAll() {
		addColumnNames();
		for (int i = 1; i < originalCsvRows.size(); i++){
			addProductRecord(i);	
		}
	}
	
	/**
	 * Adds new column names as a first row in the new CSV file. 
	 */
	private void addColumnNames() {
		
		String[] colNames = { "name", "offerurl", "price", "published",
				"description" };
		modCsvRows.add(colNames);
	}
	
	/**
	 * Adds modified product record (one CSV row) to the {@link #modCsvRows}.
	 * <p>
	 * It assembles {@code String[]} record from entities delivered from the specific get methods.   
	 */
	private void addProductRecord(int productPos) {
		
		String[] productRecord = new String[5];
		productRecord[NEW_PRODUCT_NAME_POS] = getProductName(productPos);
		productRecord[OFFERURL_POS] = getOfferUrl(productPos);
		productRecord[PRICE_POS] = getPrice(productPos);
		productRecord[PUBLISHED_POS] = getDate(productPos);
		productRecord[NEW_DESCRIPTION_POS] = getDescription(productPos);
		/* adding record to the ArrayList */
		modCsvRows.add(productRecord);
	}
	
	/**
	 * Gets the value for a new {@code name} column.
	 * 
	 * @param productPos is a product position in the original file
	 * (starting from <b>1</b>)
	 * @return original {@code Product Name}
	 */
	private String getProductName(int productPos) {
		return originalCsvRows.get(productPos)[PRODUCT_NAME_POS];
	}
	
	/**
	 * Gets the values from {@code Link} and {@code SKU} columns
	 * and combines them.
	 * 
	 * @param productPos is a product position in the original file
	 * (starting from <b>1</b>)
	 * @return value of the new {@code offerURL} column
	 */
	private String getOfferUrl(int productPos) {
		
		String url = getUrl(productPos);
		String sku = getSku(productPos);
		StringBuilder offerUrl = new StringBuilder(url);
		offerUrl.append("?id=").append(sku);
		
		return offerUrl.toString();
	}
	
	/**
	 * @param productPos is a product position in the original file
	 * (starting from <b>1</b>)
	 * @return value of the original {@code Link} column
	 */
	private String getUrl(int productPos) {
		return originalCsvRows.get(productPos)[LINK_POS];
	}
	
	/**
	 * @param productPos is a product position in the original file
	 * (starting from <b>1</b>)
	 * @return value of the original {@code SKU} column.
	 */
	private String getSku(int productPos) {
		return originalCsvRows.get(productPos)[SKU_POS];
	}
	
	/**
	 * Gets the value for a new {@code price} column
	 * by normalizing the old {@code Selling-Price} column.
	 * 
	 * @param productPos is a product position in the original file
	 * (starting from <b>1</b>)
	 * @return value of the new {@code price} column.
	 */
	private String getPrice(int productPos) {
		
		String sellingPrice = getSellingPrice(productPos);
		PriceNormalizer priceNormalizer = new PriceNormalizer();
		
		return priceNormalizer.normalize(sellingPrice);
	}
	
	/**
	 * @param productPos is a product position in the original file
	 * (starting from <b>1</b>)
	 * @return value of the old {@code Selling-Price} column
	 */
	private String getSellingPrice(int productPos) {
		return originalCsvRows.get(productPos)[SELLING_PRICE_POS];
	}
	
	/**
	 * Gets the value for a new {@code published} column
	 * by searching for date in the old {@code description} column
	 * and normalizing it.
	 * 
	 * @param productPos is a product position in the original file
	 * (starting from <b>1</b>)
	 * @return value of the new {@code published} column.
	 */
	private String getDate(int productPos) {
		String description = getDescription(productPos);
		DateNormalizer dateNormalizer = new DateNormalizer();
		
		return dateNormalizer.normalize(description);
	}
	
	/**
	 * 
	 * @param productPos is a product position in the original file
	 * (starting from <b>1</b>)
	 * @return value of the original {@code description} column.
	 */
	private String getDescription(int productPos) {
		return originalCsvRows.get(productPos)[DESCRIPTION_POS];
	}

}
