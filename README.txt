RUNNING THE APPLICATION:
- To begin, Ensure that you have maven installed. For instructions, see here: http://maven.apache.org/install.html

In the terminal:
1. Compile the application: mvn clean compile
2. To run the application giving file names as command line arguments:
mvn exec:java -Dexec.mainClass=Runner -Dexec.args="./src/main/resources/input_1.txt"
 - Ensure you are in the root directory of the project
 - To run pass in more than one filename, simply add the path separated by a space:
-Dexec.args="./src/main/resources/input_1.txt ./src/main/resources/input_2.txt ./src/main/resources/input_3.txt"
 - These input files listed above match those in the spec.

 - If adding an input file not already in the project directory by sure to specify the full path of the file.
 - When the program is finished parsing and writing the files, you will see a message that the receipt writing process is completed. Output files will be written to ./src/main/resources/
4. Run the tests: `mvn test`


DESIGN NOTES:
This application takes in files from the command line, parses them, and writes itemized receipt information to output files. The program is built to take in files that contain data from one receipt (i.e. one group of items, not several groups of items that belong on different output files.)

The program is divided into several classes in order to accomplish this:

- FileParser: Takes in a filename as a string, converts it to a path object, and then reads file contents through a BufferedReader. The FileParser then extracts and formats the necessary information to create LineItem objects that belong to a Receipt object.
- LineItem: LineItem objects receive a hash map of information about the quantity, item type, and price from the FileParser. They also contain information their tax status in the form of methods that determine whether or not an item is sales tax exempt or qualifies for the import tax based on its type. LineItems contain methods for storing their calculated taxes and total cost based on price and quantity.
- TaxCalculator: The TaxCalculator contains the rules for calculating sales and import tax on items and for correctly rounding taxes. Taxes are rounded to two decimal places, and are then rounded up to the nearest 0.05 cents, so that a tax cost of 0.32 rounds up to 0.35 and a tax cost of 0.38 rounds up to 0.40, for example. A tax cost of 0.30 remains unchanged.
- Receipt: Receipt objects take in LineItem objects and contain logic for finding the total taxes for all the items in the receipt and the total cost of the receipt. Total taxes is calculated by adding the sales and import taxes of all items. Total cost is calculated by adding the prices and taxes for all items in the receipt. Receipts also have a number attribute, which is parsed from the input file and which functions like an id.
- FileWriter: The FileWriter object takes in a receipt object, calls methods on receipt in order to find each item and its price with tax, the total tax, and total cost of the receipt. It then parses this information as a string to be written to an output file via a BufferedReader. The FileWriter uses the receipt’s number attribute to generate the output filename. It also contains formatting information for writing trailing zeros on decimals.
- Runner: The Runner contains the main() method. It takes in filenames as strings as command line arguments and then instantiates FileParser and FileWriter objects to to the work of parsing, creating LineItem and Receipt objects, and writing their contents to output files.

