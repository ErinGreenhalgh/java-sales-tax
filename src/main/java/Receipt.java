import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    double totalTax;
    double totalCost;
    List<LineItem> items;
    String number;

    public Receipt(List<LineItem> lineItems) {
        items = lineItems;
        this.setTotalCost();
        this.setTotalTax();
    }

    public Receipt(List<LineItem> lineItems, String receiptNumber) {
        items = lineItems;
        number = receiptNumber;
        this.setTotalCost();
        this.setTotalTax();
    }

    public Receipt() {
        items = null;
    }

    void setItems(List<LineItem> lineItems) {
        items = lineItems;
    }

    List<LineItem> getItems() {
        return items;
    }

    double getTotalTax() {
        return totalTax;
    }

    double getTotalCost() {
        return totalCost;
    }

    String getNumber() { return number; }

    List<Double> totalCostPerItem() {
        return this.getItems()
                   .stream()
                   .map(item -> item.totalCost())
                   .collect(Collectors.toList());
    }

    void setTotalCost() {
        double total = this.totalCostPerItem()
                        .stream()
                        .reduce(0.0, (a,b) -> a +b);
        totalCost = Math.round(total * 100.0) / 100.0;
    }

    void setTotalTax() {
        double total = this.getItems()
                       .stream()
                       .map(item -> item.getTaxes())
                       .reduce(0.0, (a,b) -> a +b);
        totalTax = Math.round(total * 100.0) / 100.0;
    }

}
