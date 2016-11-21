public class Runner {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No filename was entered. Please try again.");
        } else {
            for (String fileName : args) {
                parseAndWriteReceipt(fileName);
            }
            System.out.println("Receipt writing complete.");
        }
    }

    static void parseAndWriteReceipt(String fileName) {
        FileParser parser = new FileParser();
        Receipt receipt = parser.parseFileToReceipt(fileName);

        FileWriter writer = new FileWriter(receipt);
        writer.writeToFile();
    }
}

