public class YearlyReport {
    int month;                              //месяц
    int amount;                             // сумма
    boolean isExpense;                      // трата - TRUE, доход -FALSE

    YearlyReport(int reportMonth, int reportAmount, boolean reportIsEpense) {
        month = reportMonth;
        amount = reportAmount;
        isExpense = reportIsEpense;
    }

    YearlyReport(int reportAmount, boolean reportIsEpense) {
        amount = reportAmount;
        isExpense = reportIsEpense;
    }

    YearlyReport() {

    }
}
