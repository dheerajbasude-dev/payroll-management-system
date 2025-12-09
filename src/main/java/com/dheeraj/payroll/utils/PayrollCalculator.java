package com.dheeraj.payroll.utils;

import com.dheeraj.payroll.document.Employee;
import com.dheeraj.payroll.document.Payroll;
import com.dheeraj.payroll.dto.response.SummaryResponse;
import com.dheeraj.payroll.exception.InvalidEmployeeDesignationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class PayrollCalculator {

    private PayrollCalculator() {
        //Prevent instantiation
    }

    public static Double computeSalary(Employee employee) {
        Double maxSalary = getMaxSalary(employee.getAge(), employee.getDesignation());
        Double ratingFactor = getRatingFactor(employee.getRating());
        return roundToTwoDecimalPlaces(maxSalary * ratingFactor);
    }


    private static Double getMaxSalary(Integer age, String designation) {
        if(age >= 21 && age <= 26) {
            if(!capitalizeWords(designation).equals("Software Developer")) {
                throw new InvalidEmployeeDesignationException("Designation must have \"Software Developer\" for age between 21 and 26 years");
            }
            return 40000.0;

        } else if (age > 26 && age <= 35) {
            return switch (designation){
                case "Senior Developer" -> 80000.0;
                case "Tech Lead" -> 90000.0;
                case "Architect" -> 100000.0;
                default -> throw new InvalidEmployeeDesignationException("Designation must have \"Senior Developer\", \"Tech Lead\" and \"Architect\" for age between 26 and 35 years");
            };
        } else if (age > 35 && age <= 60) {
            return switch (designation){
                case "Manager" -> 150000.0;
                case "Senior Manager" -> 200000.0;
                case "Delivery Head" -> 300000.0;
                default -> throw new InvalidEmployeeDesignationException("Designation must have \"Manager\", \"Senior Manager\" and \"Delivery Head\" for age between 35 and 60 years");
            };
        } else {
            throw new InvalidEmployeeDesignationException("Invalid designation");
        }
    }


    private static String capitalizeWords(String str){
        return Arrays.stream(str.split(" "))
                .map(word -> word.substring(0,1).toUpperCase()+word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    private static Double getRatingFactor(Integer rating) {
        if(rating < 1 || rating > 5) {
            throw new InvalidEmployeeDesignationException("Rating must be between 1 and 5");
        }
        return Math.pow(0.95, rating - 1);
    }

    public static Double roundToTwoDecimalPlaces(Double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public static Double computeTax(Double monthSalary){
        double yearSalary = monthSalary * 12;
        double yearTax;

        if (yearSalary <= 500000){
            yearTax = 0.0;
        } else if (yearSalary <= 700000) {
            yearTax = (yearSalary - 500000) * 0.10;
        } else if (yearSalary <= 1000000) {
            yearTax = (200000 * 0.10) + ((yearSalary - 700000) * 0.20);
        } else {
            yearTax = (200000 * 0.10) + (300000 * 0.20) + ((yearSalary - 1000000) * 0.30);
        }

        return roundToTwoDecimalPlaces(yearTax/12);
    }

    public static SummaryResponse computeSummary(List<Payroll> payrolls){
        if(payrolls == null || payrolls.isEmpty()){
            return new SummaryResponse(List.of(),0.0,0.0,0.0);
        }

        double totalGrossSalary = roundToTwoDecimalPlaces(payrolls.stream()
                .mapToDouble(Payroll::getGrossSalary).sum());
        double totalTaxAmount = roundToTwoDecimalPlaces(payrolls.stream()
                .mapToDouble(Payroll::getTaxAmount).sum());
        double totalNetSalary = roundToTwoDecimalPlaces(payrolls.stream()
                .mapToDouble(Payroll::getNetSalary).sum());

        return new SummaryResponse(payrolls, totalGrossSalary, totalTaxAmount, totalNetSalary);
    }


}
