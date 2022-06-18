public class MonthlyReport {
    String itemName;                   // названеие товара
    boolean isExpense;                 // трата - TRUE, доход -FALSE
    int quantity;                       // кол-во закеупленного или проданного товара
    int sumOfOne;                       // стоимость единици товара


    MonthlyReport(String ReportItemName, boolean ReportIsExpense, int ReportQuantity, int ReportSumOfOne) {
        itemName = ReportItemName;
        isExpense = ReportIsExpense;
        quantity = ReportQuantity;
        sumOfOne = ReportSumOfOne;
    }

    MonthlyReport() {

    }

    Integer cost(MonthlyReport monthlyData) {
        /* if (monthlyData.isExpense) {
            System.out.println("Список пустой");
            return null;
        }*/
        return monthlyData.quantity * monthlyData.sumOfOne;
    }


}
