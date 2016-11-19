# CSVTransformer

The CSVTransformer program extracts data from the CSV (comma-separated values) file _products.csv_, applies a few changes and saves the result to the output file called _result.csv_.

To read and write CSV file program uses free [opencsv](http://opencsv.sourceforge.net/) library. It is available under a commercial-friendly Apache 2.0 license.

## Input file format:

* Column names:

Product Name|Link|SKU|Selling-Price|description
------------|----|---|-------------|-----------

* File encoding: **UTF-8**

* characters functions:

character function | character used in file
-------------------|-----------------------
separator          | **;** (_semicolon_)
quote              | **"** (_double quote_)
escape             | **\** (_backslash_)

## Output file format:

* Column names:

name|offerurl|price|published|description
----|--------|-----|---------|-----------

* File encoding: **ISO 8859-1**

* characters functions:

character function | character used in file
-------------------|-----------------------
separator          | **\|** (_pipe_)
quote              | **'** (_single quote_)
escape             | **\** (_backslash_)

## Values of the output file columns

output file column name | value
------------------------|-----------------------------------------------------------------------
name                    | same as ```Product Name``` column
offerurl                | content from ```Link``` column + ```?id=``` + ```SKU``` column content
price                   | numeric value from ```Selling-Price``` column normalized to have a period character ```.``` as a decimal separator and no thousands separator ```,``` eg. _1234.56_
published               | date from ```description``` column if it exists; normalized to _DD.MM.YYYY_ format
description             | same as the original ```description``` column
