import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    List<String> getReceiptContents(String fileName) {
        Charset charset = Charset.forName("US-ASCII");
        Path filePath = Paths.get(fileName);
        ArrayList<String> receiptText = new ArrayList<String>();
        try (BufferedReader reader = Files.newBufferedReader(filePath, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                receiptText.add(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return receiptText;
    }

    String getReceiptNumber(String fileName) {
        return String.valueOf(findQuantity(fileName));
    }

    List<LineItem> createItems(List<String> receiptContents) {
        List<LineItem> results = new ArrayList<>();
        for (String line : receiptContents) {
            LineItem entry = new LineItem(findQuantity(line), findItem(line), findPrice(line));
            results.add(entry);
        }
        return results;
    }

    int findQuantity (String text) {
        Matcher matcher = Pattern.compile("\\d+").matcher(text);
        matcher.find();
        return  Integer.parseInt(matcher.group());
    }

    String findItem(String text) {
        String itemString = text.split(" at")[0];
        Matcher matcher = Pattern.compile("\\D+\\S+").matcher(itemString);
        matcher.find();
        return matcher.group();
    }

    float findPrice(String text) {
        String[] result = text.split(" at");
        return Float.parseFloat(result[result.length - 1]);
    }

    Receipt createReceipt(List<LineItem> items, String receiptNumber) {
        return new Receipt(items, receiptNumber);
    }

    Receipt parseFileToReceipt(String fileName) {
        List<String> receiptText = getReceiptContents(fileName);
        List<LineItem> items = this.createItems(receiptText);
        String receiptNumber = this.getReceiptNumber(fileName);
        return this.createReceipt(items, receiptNumber);
    }
}

