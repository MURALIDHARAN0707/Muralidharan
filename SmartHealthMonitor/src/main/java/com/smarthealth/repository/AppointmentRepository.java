package com.smarthealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.smarthealth.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
