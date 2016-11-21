import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TaxCalculatorTest {

    @Test
    public void calculatesSalesTaxOnItemTest() {
        LineItem item = new LineItem(1, "music CD", 14.99f);

        TaxCalculator calc = new TaxCalculator();
        double taxes = calc.calculateSalesTax(item);
        assertEquals(taxes, 1.50, 0.001);

        LineItem exempt = new LineItem(1, "book", 10.50f);
        TaxCalculator calcTwo = new TaxCalculator();
        double no_taxes = calcTwo.calculateSalesTax(exempt);
        assertEquals(no_taxes, 0.0, 0.0001);
    }

    @Test
    public void calculateImportTaxOnItemsTest() {
        LineItem item = new LineItem(1, "imported bottle of headache pills", 9.75f);

        TaxCalculator calc = new TaxCalculator();
        double importTax = calc.calculateImportTax(item);
        assertEquals(importTax, 0.50, 0.0001);
    }

    @Test
    public void calculateAllTaxOnAnItemTest() {
        LineItem item = new LineItem(1, "imported bottle of perfume", 27.99f);

        TaxCalculator calc = new TaxCalculator();
        double total_tax = calc.allTax(item);
        assertEquals(total_tax, 4.20, 0.0001);
    }

    @Test
    public void findLastNumberOfADoubleTest() {
        double number = 0.56;
        TaxCalculator calc = new TaxCalculator();
        int lastNumber = calc.getLastNumberOfDouble(number);
        assertEquals(lastNumber, 6);
    }

    @Test
    public void roundsUpToNearestZeroTest() {
        double tax = 0.56;
        TaxCalculator calc = new TaxCalculator();
        double roundedTax = calc.roundUpToZero(tax);
        assertEquals(roundedTax, 0.60, 0.00001);
    }

    @Test
    public void roundsUpToNearestFiveTest() {
        double tax = 1.32;
        TaxCalculator calc = new TaxCalculator();
        double roundedTax = calc.roundUpToFive(tax);
        assertEquals(roundedTax, 1.35, 0.00001);
    }

    @Test
    public void roundsImportTaxesCorrectlyTest() {
        LineItem itemOne = new LineItem(1, "imported box of chocolates", 11.25f);

        TaxCalculator calc = new TaxCalculator();

        double taxesOne = calc.calculateImportTax(itemOne);
        assertEquals(taxesOne, 0.60, 0.00001);
    }

    @Test
    public void roundsSalesTaxesCorrectlyTest() {
        LineItem itemTwo = new LineItem(1, "music CD", 13.20f);

        TaxCalculator calc = new TaxCalculator();
        double taxesTwo = calc.calculateSalesTax(itemTwo);
        assertEquals(taxesTwo, 1.35, 0.00001);

        LineItem itemThree = new LineItem(1, "perfume", 15.52f);

        double taxesThree = calc.calculateSalesTax(itemThree);
        assertEquals(taxesThree, 1.55, 0.00001);
    }

    @Test
    public void correctlyChecksTaxEndingInZeroTest() {
        double tax = 0.2;
        TaxCalculator calc = new TaxCalculator();
        assertTrue(calc.endsInZero(tax));
    }

    @Test
    public void correctlyRoundsTaxEndingInZeroTest() {
        LineItem itemSales = new LineItem(1, "music CD", 2.00f);

        TaxCalculator calc = new TaxCalculator();
        double taxesSales = calc.calculateSalesTax(itemSales);
        assertEquals(taxesSales, 0.2, 0.00001);
    }
 }
