package com.smarthealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smarthealth.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
