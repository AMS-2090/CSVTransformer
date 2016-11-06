package csvtransformer;


/**
 * The CSVTransform program extracts data from the file 'product.csv',
 * applies some transformations and saves the result to another file
 * called 'my_result.csv' 
 * 
 * @author Arkadiusz So³tysiak
 */

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

public class CSVTransformer {

	public static void main(String[] args) {
		
		/* Name of a file to read */
		File file = new File("products.csv");

		/* Returning List of String[] to store rows from CSV file */
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
		File outputFile = new File("my_result.csv");
		
		System.out.println("Writing data into " + outputFile + " file.");

		/* Writing data to the CSV file */
		writeCSV(outputFile, modCsvRows);

	}
	
	/*
	 * readCSV() method uses CSVReader from OpenCSV library
	 * to read CSV data into ArrayList.
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
