import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineItem {
    int quantity;
    String type;
    float price;
    double taxes;

    public LineItem() {
        quantity = 0;
        type = null;
        price = 0.0f;
        taxes = 0.0;
    }

    public LineItem(int quant, String itemType, float itemPrice) {
        quantity = quant;
        type = itemType;
        price = itemPrice;
    }

    int getQuantity() {
        return quantity;
    }

    String getType() {
        return type;
    }

    float getPrice() {
        return price;
    }

    double getTaxes() {
        return taxes;
    }

    boolean isImported() {
        if (this.getType().contains("imported")) {
            return true;
        } else {
            return false;
        }
    }

    static String buildRegex(String[] itemTypes) {
        return String.join("| ", itemTypes);
    }

    boolean isSalesTaxExempt() {
        String itemName = this.getType();
        String regex = buildRegex(salesTaxExemptTypes());
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(itemName);
        return matcher.find();
    }

    static String[] salesTaxExemptTypes() {
       String[] types = new String[3];
        types[0] = "book";
        types[1] = "pill";
        types[2] = "chocolate";
        return types;
    }
    //make this an ivar so it doesn't rebuild the array every time it's called

    void setTaxes() {
        TaxCalculator calculator = new TaxCalculator();
        taxes = this.getQuantity() * calculator.allTax(this);
    }

    double totalCost() {
        this.setTaxes();
        double total = this.getQuantity() * this.getPrice() + this.getTaxes();
        return Math.round(total * 100.0) / 100.0;
    }
}