import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReceiptTest {
    LineItem item1 = new LineItem();
    LineItem item2 = new LineItem();
    Receipt r = new Receipt();
    List<LineItem> items = new ArrayList<LineItem>();

    @Before
    public void initialize() {
        HashMap<String, String> data1 = new HashMap<String, String>();
        data1.put("quantity", "1");
        data1.put("type", "imported book");
        data1.put("price", "15.99");
        item1.setQuantity(data1.get("quantity"));
        item1.setType(data1.get("type"));
        item1.setPrice(data1.get("price"));
        item1.setTaxes();

        HashMap<String, String> data2 = new HashMap<String, String>();
        data2.put("quantity", "1");
        data2.put("type", "music CD");
        data2.put("price", "10.50");
        item2.setQuantity(data2.get("quantity"));
        item2.setType(data2.get("type"));
        item2.setPrice(data2.get("price"));
        item2.setTaxes();


        items.add(item1);
        items.add(item2);


        r.setItems(items);
    }

    @Test
    public void initializeReceiptWithLineItemsTest() {
        Receipt receipt = new Receipt(items);
        assertEquals(receipt.getItems(), items);
    }

    @Test
    public void initializeReceiptWithNumberTest() {
        Receipt receipt = new Receipt(items, "15");
        assertEquals(receipt.getNumber(), "15");
    }

    @Test
    public void findTotalCostsForEachItem() {
        List<Double> expectedTotals = new ArrayList<Double>();
        expectedTotals.add(item1.totalCost());
        expectedTotals.add(item2.totalCost());

        List<Double> totals = r.totalCostPerItem();
        assertEquals(totals, expectedTotals);
        //check for rounding errors
    }

    @Test
    public void totalCostTest() {
        r.setTotalCost();
        double total = r.getTotalCost();
        assertEquals(total, 28.34, 0.001);
    }

    @Test
    public void totalTaxTest() {
        double itemTaxes = 1.85;
        r.setTotalTax();
        double totalTax = r.getTotalTax();
        assertEquals(totalTax, itemTaxes, 0.0001);
    }
}
