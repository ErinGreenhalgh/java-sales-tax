import org.junit.Test;
import static org.junit.Assert.*;

public class LineItemTest {

    @Test
    public void lineItemWithQuantityTypeandPriceTest() {
        LineItem item = new LineItem(2, "book", 13.49f);
        assertEquals(item.getQuantity(), 2);
        assertEquals(item.getType(), "book");
        assertEquals(item.getPrice(), 13.49, 0.0001);
    }

    @Test
    public void determineImportedItemsTest() {
        String typeOne = "imported bottle of perfume";
        String typeTwo = "book";
        LineItem itemOne = new LineItem(1, typeOne, 2.00f);
        LineItem itemTwo = new LineItem(1, typeTwo, 2.00f);
        assertTrue(itemOne.isImported());
        assertFalse(itemTwo.isImported());
    }

    @Test
    public void storeSalesTaxExemptTypesTest() {
        String[] types = LineItem.salesTaxExemptTypes();
        String[] correctTypes = {"book", "pill", "chocolate"};
        assertArrayEquals(types,correctTypes);
    }
    //change this to be an ivar, could be a static ivar

    @Test
    public void buildRegexTest() {
        String[] elements = {"this", "that", "another"};
        String result = LineItem.buildRegex(elements);
        assertEquals(result, "this| that| another");
    }

    @Test
    public void determineItemSalesTaxableTest() {
        String typeOne = "packet of headache pills";
        String typeTwo = "box of imported chocolates";
        String typeThree = "book";
        String typeFour = "bottle of perfume";

        LineItem itemOne = new LineItem(1, typeOne, 1.0f);
        LineItem itemTwo = new LineItem(1, typeTwo, 1.0f);
        LineItem itemThree = new LineItem(1, typeThree, 1.0f);
        LineItem itemFour = new LineItem(1, typeFour, 1.0f);

        assertTrue(itemOne.isSalesTaxExempt());
        assertTrue(itemTwo.isSalesTaxExempt());
        assertTrue(itemThree.isSalesTaxExempt());
        assertFalse(itemFour.isSalesTaxExempt());
    }

    @Test
    public void itemTaxesTest() {
        LineItem item = new LineItem(1, "imported bottle of perfume", 27.99f);
        item.setTaxes();
        assertEquals(item.getTaxes(), 4.20, 0.0001);
    }

    @Test
    public void totalCostTest() {
        LineItem item = new LineItem(1, "bottle of perfume", 12.99f);
        double total = item.totalCost();
        assertEquals(total, 14.29, 0.0001);
    }

    @Test
    public void givesCorrectTaxesForItemsWithQuantityGreaterThanOne() {
        LineItem item1 = new LineItem(3, "bottles of perfume", 2.00f);
        item1.setTaxes();
        assertEquals(item1.getTaxes(), 0.60, 0.0001);

        LineItem item2 = new LineItem(2, "imported boxes of chocolate", 1.50f);
        item2.setTaxes();
        assertEquals(item2.getTaxes(), 0.20, 0.0001);
    }
}
