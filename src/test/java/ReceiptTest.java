import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReceiptTest {
    LineItem item1;
    LineItem item2;
    Receipt r = new Receipt();
    List<LineItem> items = new ArrayList<LineItem>();

    @Before
    public void initialize() {
        item1 = new LineItem(1, "imported book", 15.99f);
        item1.setTaxes();

        item2 = new LineItem(1, "music CD", 10.50f);
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
