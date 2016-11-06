package csvtransformer;

/**
 * CSVModifier class defines fields and methods to manage
 * CSV file modification.
 * It defines:
 * - constants describing columns positions in original and
 * 	 new CSV files.
 * - Lists of String[] to store original and modified data.
 * - public methods for setting and getting CSV rows.
 * - public transformAll() method to execute all modifications.
 * - and additional supporting private methods.
 * 
 * @author Arkadiusz So³tysiak
 */

import java.util.ArrayList;
import java.util.List;

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
	
	/* List of String[] to store original data from CSV file */
	private List<String[]> originalCsvRows;
	/* List of String[] to store modified data from CSV file */
	private List<String[]> modCsvRows;

	public CSVModifier() {
		originalCsvRows = null;
		modCsvRows = null;
	}

	public CSVModifier(List<String[]> originalCsvRows) {
		 setCsvRows(originalCsvRows);
	}

	public List<String[]> getCsvRows() {
		return modCsvRows;
	}

	public void setCsvRows(List<String[]> originalCsvRows) {
		this.originalCsvRows = originalCsvRows;
		/* creating new empty List for modified data */
		modCsvRows = new ArrayList<String[]>();
	}
	
	/*
	 * transformAll() method adds column names to new empty List
	 * and then inserts modified lines of products.
	 */
	public void transformAll() {
		addColumnNames();
		for (int i = 1; i < originalCsvRows.size(); i++){
			addProductRecord(i);	
		}
	}
	
	private void addColumnNames() {
		
		String[] colNames = { "name", "offerurl", "price", "published",
				"description" };
		modCsvRows.add(colNames);
	}
	
	/*
	 * addProductRecord() method assembles record of a one product
	 * into String[], from entities delivered from the specific get methods.   
	 */
	private void addProductRecord(int productPos) {
		
		String[] productRecord = new String[5];
		productRecord[NEW_PRODUCT_NAME_POS] = getProductName(productPos);
		productRecord[OFFERURL_POS] = getOfferUrl(productPos);
		productRecord[PRICE_POS] = getPrice(productPos);
		productRecord[PUBLISHED_POS] = getDate(productPos);
		productRecord[NEW_DESCRIPTION_POS] = getDescription(productPos);
		/* adding record to ArrayList */
		modCsvRows.add(productRecord);
	}
	
	private String getProductName(int productPos) {
		return originalCsvRows.get(productPos)[PRODUCT_NAME_POS];
	}
	
	/*
	 * getOfferUrl() method returns value for the 'offerurl' column
	 * by assembling it from the 'link' and the 'sku' field.
	 */
	private String getOfferUrl(int productPos) {
		
		String url = getUrl(productPos);
		String sku = getSku(productPos);
		StringBuilder offerUrl = new StringBuilder(url);
		offerUrl.append("?id=").append(sku);
		
		return offerUrl.toString();
	}
	
	private String getUrl(int productPos) {
		return originalCsvRows.get(productPos)[LINK_POS];
	}
	
	private String getSku(int productPos) {
		return originalCsvRows.get(productPos)[SKU_POS];
	}
	
	/*
	 * getPrice() method returns value for the 'price' column
	 * by normalizing the 'Selling-Price' column.
	 */
	private String getPrice(int productPos) {
		
		String sellingPrice = getSellingPrice(productPos);
		PriceNormalizer priceNormalizer = new PriceNormalizer();
		
		return priceNormalizer.normalize(sellingPrice);
	}
	
	private String getSellingPrice(int productPos) {
		return originalCsvRows.get(productPos)[SELLING_PRICE_POS];
	}
	
	/*
	 * getData() method returns value for 'published' column
	 * by searching date in the 'description' column
	 * and normalizing it.
	 */
	private String getDate(int productPos) {
		String description = getDescription(productPos);
		DateNormalizer dateNormalizer = new DateNormalizer();
		
		return dateNormalizer.normalize(description);
	}
	
	private String getDescription(int productPos) {
		return originalCsvRows.get(productPos)[DESCRIPTION_POS];
	}

}
