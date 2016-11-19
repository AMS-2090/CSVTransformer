package csvtransformer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

/**
 * The Main class. The CSVTransformer program extracts data from the file
 * <em>products.csv</em>, applies a few changes and saves the result to another
 * file called <em>result.csv</em>.
 * 
 * @author Arkadiusz So³tysiak
 */

public class CSVTransformer {

	public static void main(String[] args) {
		
		String inputFileName = "test-files/products.csv";
		String outputFileName = "test-files/result.csv";
		
		/* Name of a file to read */
		File file = new File(inputFileName);

		/*
		 * Returning List of String[] to store rows from CSV file
		 */
		System.out.println("Reading data from " + file);
		List<String[]> csvRows = readCSV(file);
		if (csvRows == null) {
			System.out.println("Closing...");
			System.exit(1);
		}
				
		CSVModifier modifier = new CSVModifier();
		
		/* reading CSV Rows into modifier */
		modifier.setCsvRows(csvRows);
		
		/* execute modifications */
		modifier.transformAll();
		
		/* receiving modified data from the modifier */
		List<String[]> modCsvRows = modifier.getCsvRows();

		System.out.println("Data modified.");
		
		/* Name of a file to write */
		File outputFile = new File(outputFileName);
		
		System.out.println("Writing data into " + outputFile + " file.");

		/* Writing data to the CSV file */
		writeCSV(outputFile, modCsvRows);

	}
	
	/*
	 * readCSV() method uses CSVReader from OpenCSV library
	 * to read CSV data into ArrayList.
	 */
	/**
	 * This method uses {@link com.opencsv#CSVReader} class
	 * to read CSV data into {@link java.util#ArrayList}.
	 * <p>
	 * Input CSV file format should be:
	 * <p><ul>
	 * <li>encoding: <b>UTF-8</b>
	 * <li>separator character: <b>;</b> - <i>semicolon</i>
	 * <li>quote character: <b>"</b> - <i>double quote</i>
	 * <li>escape character: <b>\</b> - <i>backslash</i> 
	 * @param file to read from
	 * @return List<String[]> filled with CSV file content
	 */
	public static List<String[]> readCSV(File file) {

		List<String[]> csvRows;
		
		/*
		 * Using InputStreamReader and FileInputStream in order to
		 * set specific encoding, here: UTF-8.
		 * ; - as a separator character,
		 * " - as a quote character,
		 * \ - as a escape character.
		 */
		try (CSVReader reader = new CSVReader(new InputStreamReader(
				new FileInputStream(file), "UTF-8"), ';', '\"', '\\')) {
			
			csvRows = new ArrayList<>();
			csvRows = reader.readAll();

		} catch (FileNotFoundException e) {
			System.out.println("Can't find file " + file.toString());
			csvRows = null;
			// e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding in file " + file.toString());
			csvRows = null;
			// e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Unable to read file " + file.toString());
			csvRows = null;
			// e.printStackTrace();
		}

		return csvRows;
	}
	
	/*
	 * writeCSV() method uses CSVWriter from OpenCSV library to write
	 * modified data into new CSV file.
	 */
	/**
	 * This method uses {@link com.opencsv#CSVWriter} class
	 * to write modified data into a new CSV file.
	 * <p>
	 * Output CSV file format will be:
	 * <p><ul>
	 * <li>encoding: <b>ISO-8859-1</b>
	 * <li>separator character: <b>|</b> - <i>pipe</i>
	 * <li>quote character: <b>'</b> - <i>single quote</i>
	 * <li>escape character: <b>\</b> - <i>backslash</i>
	 * 
	 * @param outputFile to write
	 * @param csvRows is a List<String[]> to be written into file
	 */
	public static void writeCSV(File outputFile, List<String[]> csvRows) {

		/*
		 * Using OutputStreamWriter and FileOutputStream in order to
		 * set specific encoding, here: ISO-8859-1.
		 * | - as a separator character,
		 * ' - as a quote character,
		 * \ - as a escape character.
		 */
		try (CSVWriter writer = new CSVWriter(
				new OutputStreamWriter(
				new FileOutputStream(outputFile), "ISO-8859-1"), '|', '\'', '\\')) {

			for (String[] line : csvRows) {
				writer.writeNext(line);
			}
			System.out.println("Done!");

		} catch (FileNotFoundException e) {
			System.out.println("File " + outputFile.toString() + "cannot be created or opened.");
			//e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported encoding in file " + outputFile.toString());
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Unable to write file " + outputFile.toString());
			//e.printStackTrace();
		}

	}
}
