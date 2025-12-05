package com.dheeraj.payroll.repository;

import com.dheeraj.payroll.document.Payroll;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PayrollRepository extends MongoRepository<Payroll,String> {

}
