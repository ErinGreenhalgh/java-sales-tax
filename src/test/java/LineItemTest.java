import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class LineItemTest {

    @Test
    public void lineItemWithQuantityTest() {
        LineItem item = new LineItem();
        String quant = "2";
        assertEquals(item.getQuantity(), 0);
        item.setQuantity(quant);
        assertEquals(item.getQuantity(), 2);
    }

    @Test
    public void lineItemWithPriceTest() {
        LineItem item = new LineItem();
        String price = "12.99";
        assertEquals(item.getPrice(), 0, 0.1);
        item.setPrice(price);
        assertEquals(item.getPrice(), 12.99, 0.1);
    }

    @Test
    public void lineItemWithTypeTest() {
        LineItem itemOne = new LineItem();
        LineItem itemTwo = new LineItem();
        String typeOne = "box of chocolates";
        String typeTwo = "imported bottle of perfume";
        assertEquals(itemOne.getType(), null);
        assertEquals(itemTwo.getType(), null);
        itemOne.setType(typeOne);
        itemTwo.setType(typeTwo);
        assertEquals(itemOne.getType(), typeOne);
        assertEquals(itemTwo.getType(), typeTwo);
    }

    @Test
    public void determineImportedItemsTest() {
        LineItem itemOne = new LineItem();
        LineItem itemTwo = new LineItem();
        String typeOne = "imported bottle of perfume";
        String typeTwo = "book";
        itemOne.setType(typeOne);
        itemTwo.setType(typeTwo);
        assertTrue(itemOne.isImported());
        assertFalse(itemTwo.isImported());
    }

    @Test
    public void storeSalesTaxExemptTypesTest() {
        String[] types = LineItem.salesTaxExemptTypes();
        String[] correctTypes = {"book", "pill", "chocolate"};
        assertArrayEquals(types,correctTypes);
    }

    @Test
    public void buildRegexTest() {
        String[] elements = {"this", "that", "another"};
        String result = LineItem.buildRegex(elements);
        assertEquals(result, "this| that| another");
    }

    @Test
    public void determineItemSalesTaxableTest() {
        LineItem itemOne = new LineItem();
        LineItem itemTwo = new LineItem();
        LineItem itemThree = new LineItem();
        LineItem itemFour = new LineItem();

        String typeOne = "packet of headache pills";
        String typeTwo = "box of imported chocolates";
        String typeThree = "book";
        String typeFour = "bottle of perfume";

        itemOne.setType(typeOne);
        itemTwo.setType(typeTwo);
        itemThree.setType(typeThree);
        itemFour.setType(typeFour);

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
    public void lineItemConstructorTest() {
        HashMap<String, String> itemData = new HashMap<String, String>();
        itemData.put("quantity", "1");
        itemData.put("type", "bottle of perfume");
        itemData.put("price", "12.99");

        LineItem item = new LineItem(itemData);
        assertEquals(item.getQuantity(), 1);
        assertEquals(item.getType(), "bottle of perfume");
        assertEquals(item.getPrice(), 12.99, 0.0001);
    }

    @Test
    public void totalCostTest() {
        HashMap<String, String> itemData = new HashMap<String, String>();
        itemData.put("quantity", "1");
        itemData.put("type", "bottle of perfume");
        itemData.put("price", "12.99");

        LineItem item = new LineItem(itemData);
        double total = item.totalCost();

        assertEquals(total, 14.29, 0.0001);
    }

    @Test
    public void givesCorrectTaxesForItemsWithQuantityGreaterThanOne() {
        HashMap<String, String> itemData1 = new HashMap<String, String>();
        itemData1.put("quantity", "3");
        itemData1.put("type", "bottle of perfume");
        itemData1.put("price", "2.00");

        LineItem item1 = new LineItem(itemData1);
        item1.setTaxes();
        assertEquals(item1.getTaxes(), 0.60, 0.0001);

        HashMap<String, String> itemData2 = new HashMap<String, String>();
        itemData2.put("quantity", "2");
        itemData2.put("type", "imported box of chocolates");
        itemData2.put("price", "1.50");

        LineItem item2 = new LineItem(itemData2);
        item2.setTaxes();
        assertEquals(item2.getTaxes(), 0.20, 0.0001);
    }

}
