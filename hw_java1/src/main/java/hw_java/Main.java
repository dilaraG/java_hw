package hw_java;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(new File("test_table.xlsx"));
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter dataFormatter = new DataFormatter();

            for (int rowIndex = 3; rowIndex <= 12; rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                if (row != null) {
                    long id;
                    String idCellValue = dataFormatter.formatCellValue(row.getCell(0));
                    try {
                        id = Long.parseLong(dataFormatter.formatCellValue(row.getCell(0)));
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: значение ID не является числом. Значение: '" + idCellValue + "'"+rowIndex);
                        continue;
                    }
                    String email = dataFormatter.formatCellValue(row.getCell(2));
                    String phone = dataFormatter.formatCellValue(row.getCell(3));
                    String address = dataFormatter.formatCellValue(row.getCell(4));
                    String iban = dataFormatter.formatCellValue(row.getCell(14));
                    String bic = dataFormatter.formatCellValue(row.getCell(15));
                    String accountHolder = dataFormatter.formatCellValue(row.getCell(16));
                    BankAccount bankAccount = new BankAccount(iban, bic, accountHolder);
                    // System.out.println(accountHolder);
                    Employee employee = null;
                 
                    boolean isIndividual = !dataFormatter.formatCellValue(row.getCell(8)).isEmpty() || !dataFormatter.formatCellValue(row.getCell(9)).isEmpty();
                    boolean isCompany = !dataFormatter.formatCellValue(row.getCell(11)).isEmpty() && dataFormatter.formatCellValue(row.getCell(12)).matches("SARL|SARS");
                    
                    if (isIndividual) {
                        String firstName = dataFormatter.formatCellValue(row.getCell(6));
                        String lastName = dataFormatter.formatCellValue(row.getCell(7));
                        boolean hasChildren = Boolean.parseBoolean(dataFormatter.formatCellValue(row.getCell(8)));
        
                        int age;
                        try {
                            age = (int) Double.parseDouble(dataFormatter.formatCellValue(row.getCell(9)).trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка: значение возраста не является числом. Значение: '" + dataFormatter.formatCellValue(row.getCell(8)) + "'");
                            continue;
                        }
        
                        employee = new Individual(id, email, phone, address, bankAccount, firstName, lastName, hasChildren, age);
                    } else if (isCompany) {
                        String name = dataFormatter.formatCellValue(row.getCell(11));
                        CompanyType companyType;
                        try {
                            companyType = CompanyType.valueOf(dataFormatter.formatCellValue(row.getCell(12)).toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: неверный тип компании. Значение: '" + dataFormatter.formatCellValue(row.getCell(12)) + "'");
                            continue;
                        }
        
                        employee = new Company(id, email, phone, address, bankAccount, name, companyType);
                    } else {
                        System.out.println("Ошибка: не удалось определить тип объекта."+rowIndex);
                        continue;
                    }
                    employees.add(employee);
                }
                
            }
            workbook.close();
            fis.close();
            // Подсчет и вывод данных
            int individualCount = 0;
            int companyCount = 0;
            int age_max = 20;

            System.out.println("Имя и фамилия сотрудников, которым меньше 20 лет:");
            for (Employee employee : employees) {
                if (employee instanceof Individual) {
                    individualCount++;
                    Individual individual = (Individual) employee;
                    if (individual.getAge() < age_max) {
                        System.out.println("Имя: " + individual.getFirstName() + ", Фамилия: " + individual.getLastName());
                    }
                } else if (employee instanceof Company) {
                    companyCount++;
                }
            }

            System.out.println("Количество физических лиц: " + individualCount);
            System.out.println("Количество компаний: " + companyCount);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
    
}