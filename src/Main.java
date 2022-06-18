import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) {

        String filesPath = "resources/";            //передаем путь местонахождения файлов
        MonthlyReport monthlyReport;
        YearlyReport yearlyReport;

        ArrayList<MonthlyReport> filesMonthly;
        ArrayList<YearlyReport> filesYearly;
        ArrayList<String> listOfMonths = new ArrayList<String>() {{
            add("-");
            add("январь(е)");
            add("февраль(е)");
            add("март(е)");
            add("апрель(е)");
            add("май(е)");
            add("июнь(е)");
            add("июль(е)");
            add("август(е)");
            add("сентябрь(е)");
            add("октябрь(е)");
            add("ноябрь(е)");
            add("декабрь(е)");
        }};
        HashMap<Integer, ArrayList<MonthlyReport>> storageOfMonthlyReports = new HashMap<>();
        HashMap<Integer, ArrayList> storageOfYearlyReports = new HashMap<>();

        printMenu();
        Scanner scanner = new Scanner(System.in);
        int command = scanner.nextInt();
        while (command != 0) {
            switch (command) {
                case 1: {
                    for (int i = 1; i <= 3; i++) {
                        FileContents reader = new FileContents();
                        String data = reader.readFileContentsOrNull(filesPath + "m.20210" + i + ".csv");
                        if (data == null) {
                            break;
                        }

                        String[] lines = data.split("\\n");
                        filesMonthly = new ArrayList<>();

                        for (int j = 1; j < lines.length; j++) {
                            String[] lineContent = lines[j].split(",");
                            String reportItemName = lineContent[0];
                            boolean reportIsExpense = Boolean.parseBoolean(lineContent[1]);
                            int reportQuantity = Integer.parseInt(lineContent[2]);
                            int reportSumOfOne = Integer.parseInt(lineContent[3]);
                            monthlyReport = new MonthlyReport(reportItemName, reportIsExpense, reportQuantity, reportSumOfOne);
                            filesMonthly.add(monthlyReport);
                        }
                        storageOfMonthlyReports.put(i, filesMonthly);
                    }
                    System.out.println("Отчет за все месяцы считан");

                    break;
                }
                case 2: {
                    FileContents reader = new FileContents();
                    String data = reader.readFileContentsOrNull(filesPath + "y.2021.csv");
                    String[] lines = data.split("\\n");
                    filesYearly = new ArrayList<>();

                    for (int i = 1; i < lines.length; i++) {
                        String[] lineContent = lines[i].split(",");
                        int reportMonth = Integer.parseInt(lineContent[0]);
                        int reportAmount = Integer.parseInt(lineContent[1]);
                        boolean reportIsExpense = Boolean.parseBoolean(lineContent[2]);
                        yearlyReport = new YearlyReport(reportAmount, reportIsExpense);

                        if (i % 2 != 0) {
                            filesYearly.add(yearlyReport);
                        } else {
                            filesYearly.add(yearlyReport);
                            storageOfYearlyReports.put(reportMonth, filesYearly);
                            filesYearly = new ArrayList<>();
                        }
                    }
                    System.out.println("Отчет за год считан");

                    break;
                }
                case 3: {
                    if (storageOfMonthlyReports.isEmpty() || storageOfYearlyReports.isEmpty()) {
                        System.out.println("Месячные или годовой отчеты не считаны");
                    } else {
                        for (int i = 1; i <= storageOfMonthlyReports.size(); i++) {
                            filesMonthly = storageOfMonthlyReports.get(i);
                            filesYearly = storageOfYearlyReports.get(i);
                            MonthlyReport monthlyReport1 = new MonthlyReport();
                            YearlyReport yearlyReport1 = new YearlyReport();
                            int expense = 0;
                            int income = 0;
                            int expenseYearly = 0;
                            int incomeYearly = 0;

                            for (int j = 0; j < filesMonthly.size(); j++) {
                                MonthlyReport str = filesMonthly.get(j);
                                int cost = monthlyReport1.cost(str);
                                if (str.isExpense) {
                                    expense += cost;
                                } else {
                                    income += cost;
                                }
                            }

                            for (int k = 0; k < filesYearly.size(); k++) {
                                yearlyReport1 = filesYearly.get(k);
                                if (yearlyReport1.isExpense) {
                                    expenseYearly = yearlyReport1.amount;
                                } else {
                                    incomeYearly = yearlyReport1.amount;
                                }
                            }

                            if (income != incomeYearly) {
                                System.out.println("В " + listOfMonths.get(i) + " несоответствие доходов");
                            } else if (expense != expenseYearly) {
                                System.out.println("В " + listOfMonths.get(i) + " несоответствие расходов");
                            }
                        }
                        System.out.println("Операция успешно завершена");
                    }
                    break;
                }
                case 4: {
                    for (int k = 1; k <= storageOfMonthlyReports.size(); k++) {
                        System.out.println(listOfMonths.get(k) + ":");

                        filesMonthly = storageOfMonthlyReports.get(k);

                        MonthlyReport monthlyReport1 = new MonthlyReport();

                        int maxProfit = 0;
                        int maxExpenses = 0;
                        String maxProfitItemName = " ";                       //товар с наибольшей прибыльностью
                        String maxExpensesItemName = " ";                       // самая большая статья расходов
                        for (int i = 0; i < filesMonthly.size(); i++) {
                            MonthlyReport str = filesMonthly.get(i);
                            int cost = monthlyReport1.cost(str);
                            if (str.isExpense) {
                                if (cost > maxExpenses) {
                                    maxExpenses = cost;
                                    maxExpensesItemName = str.itemName;
                                }
                            } else {
                                if (cost > maxProfit) {
                                    maxProfit = cost;
                                    maxProfitItemName = str.itemName;
                                }
                            }
                        }

                        System.out.print("Самый прибыльный товар: " + maxProfitItemName);  // самый прибыльный товар месяца.
                        System.out.println(" ,продан на сумму: " + maxProfit);  // сумма самого прибыльного тавара месяца.
                        System.out.print("Самая большая трата: " + maxExpensesItemName);         //самая большая статья расходов
                        System.out.println(" , потрачено: " + maxExpenses);           //сумма самой большой статьи расходов
                    }
                    break;
                }
                case 5: {
                    System.out.println("2021 год");
                    int expenseYearly = 0;
                    int cycle = storageOfYearlyReports.size();
                    int incomeYearly = 0;
                    for (int i = 1; i <= storageOfYearlyReports.size(); i++) {
                        System.out.print("Прибыль за " + listOfMonths.get(i));
                        filesYearly = storageOfYearlyReports.get(i);
                        int expense = 0;
                        int income = 0;

                        for (int j = 0; j < filesYearly.size(); j++) {
                            YearlyReport str = filesYearly.get(j);
                            if (str.isExpense) {
                                expense = str.amount;
                                expenseYearly += expense;
                            } else {
                                income = str.amount;
                                incomeYearly += income;
                            }
                        }
                        int profit = income - expense;
                        System.out.println(" составила: " + profit);                      // расчет прибыли за месяц
                    }

                    int averageExpenseYearly = expenseYearly / cycle;
                    int averageIncomeYearly = incomeYearly / cycle;
                    System.out.println("Средний расход за все месяцы: " + averageExpenseYearly);
                    System.out.println("Средний доход за все месяцы: " + averageIncomeYearly);

                    break;
                }

                default:
                    System.out.println("Такого раздела не существует");
            }
            printMenu();
            command = scanner.nextInt();

        }
        System.out.println("Программа завершена");
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать?");
        System.out.println("1 - Считать все месячные отчеты");
        System.out.println("2 - Считать годовой отчет");
        System.out.println("3 - Сверить отчеты");
        System.out.println("4 - Вывести информацию о всех месячных отчетах");
        System.out.println("5 - Вывести информацию о годовом отчете");
        System.out.println("0 - Завершит работу программы");
    }

}

