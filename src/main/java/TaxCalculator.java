public class TaxCalculator {
    float salesTaxRate = 0.1f;
    float importTaxRate = 0.05f;

    float getSalesTaxRate() {
        return salesTaxRate;
    }

    float getImportTaxRate() {
        return importTaxRate;
    }

    double calculateSalesTax(LineItem item) {
        if (item.isSalesTaxExempt()) {
            return 0.0;
        } else {
            double taxes = item.getPrice() * this.getSalesTaxRate();
            double roundedToTwoDecimalPlaces = Math.round(taxes * 100.0) / 100.0;
            return roundTaxes(roundedToTwoDecimalPlaces);
        }
    }

    double roundTaxes(double rawTax) {
        if (this.endsInZero(rawTax)) {
            return rawTax;
        } else {
            int lastNumber = this.getLastNumberOfDouble(rawTax);
            if (lastNumber > 5) {
                return this.roundUpToZero(rawTax);
            } else if (lastNumber < 5) {
                return this.roundUpToFive(rawTax);
            } else {
                return (Math.round(rawTax * 100.0) / 100.0);
            }
        }
    }

    boolean endsInZero(double rawTax) {
        if (Double.toString(rawTax).length() == 3) {
            return true;
        } else {
            return false;
        }
    }

    double roundUpToZero(double rawTax) {
        return Math.ceil(rawTax * 10.0 ) / 10.0;
    }

    double roundUpToFive(double rawTax) {
        return (Math.floor(rawTax * 10.0) / 10.0) + 0.05;
    }

    int getLastNumberOfDouble(double number) {
        String taxString = Double.toString(number);
        Character lastCharacter = taxString.charAt(taxString.length() - 1);
        return Character.getNumericValue(lastCharacter);
    }


    double calculateImportTax(LineItem item) {
        if (item.isImported()) {
            double taxes = item.getPrice() * this.getImportTaxRate();
            double roundedToTwoDecimalPlaces = Math.round(taxes * 100.0) / 100.0;
            return roundTaxes(roundedToTwoDecimalPlaces);
        } else {
            return 0.0;
        }
    }

    double allTax(LineItem item) {
        return this.calculateSalesTax(item) + this.calculateImportTax(item);
    }
}
