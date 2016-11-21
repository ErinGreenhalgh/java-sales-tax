import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FileWriter {
    Receipt receipt;
    String fileContents;

    public FileWriter(Receipt r) {
        receipt = r;
        fileContents = this.createContents();
    }

    Receipt getReceipt() {
        return receipt;
    }

    String getFileContents() {
        return fileContents;
    }

    String createContents() {
        return this.itemizedList() + priceTotals();
    }


    String itemizedList() {
       List<String> entries = this.getReceipt().getItems()
                .stream()
                .map(item -> item.getQuantity() + " " + item.getType() + ": " + this.formatDecimal(item.totalCost()) + "\n")
                .collect(Collectors.toList());
        String result = "";
        for (String entry : entries) {
            result = result + entry;
        }
        return result;
    }

    String priceTotals() {
        Receipt r = this.getReceipt();
        String taxTotal = "Sales Taxes: " + this.formatDecimal(r.getTotalTax());
        String costTotal = "Total: " + this.formatDecimal(r.getTotalCost());
        return taxTotal + "\n" +  costTotal;
    }

    String formatDecimal(double decimal) {
       return String.format("%.2f", decimal);
    }

    void writeToFile() {
        Charset charset = Charset.forName("US-ASCII");
        String text = this.getFileContents();
        String fileName = "./src/main/resources/output_" + this.getReceipt().getNumber() + ".txt";
        Path file = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(text, 0, text.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
