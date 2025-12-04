package com.dheeraj.payroll.utils;

import com.dheeraj.payroll.document.Employee;
import com.dheeraj.payroll.exception.InvalidEmployeeDesignationException;

import java.util.Arrays;
import java.util.Collections;
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

    private Double getMaxSalary(Integer age, String designation) {
        if(age >= 21 && age <= 26) {
            return (capitalizeWords(designation).equals("Software Developer")) ? 40000.0 :
                    throw new InvalidEmployeeDesignationException("Designation must have \"Software Developer\" for age between 21-26 years");
            }
        } else if (age > 26 && age <= 35) {
            return

        }
    }


    private String capitalizeWords(String str){
        return Arrays.stream(str.split(" "))
                .map(word -> word.substring(0,1).toUpperCase()+word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

}
