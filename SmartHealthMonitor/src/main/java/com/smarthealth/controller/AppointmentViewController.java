package com.smarthealth.controller;

import com.smarthealth.model.Appointment;
import com.smarthealth.model.Doctor;
import com.smarthealth.model.Patient;
import com.smarthealth.repository.AppointmentRepository;
import com.smarthealth.repository.DoctorRepository;
import com.smarthealth.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointments")
public class AppointmentViewController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/view")
    public String viewAppointments(Model model) {
        model.addAttribute("appointments", appointmentRepository.findAll());
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "appointments";
    }

    @PostMapping("/save")
    public String saveAppointment(
            @RequestParam("patientId") Long patientId,
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("date") String date,
            @RequestParam("time") String time,
            @RequestParam(value = "notes", required = false) String notes) {

        Patient patient = patientRepository.findById(patientId).orElse(null);
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);

        if (patient == null || doctor == null) {
            return "redirect:/appointments/view?error=invalid";
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(java.time.LocalDate.parse(date));
        appointment.setTime(java.time.LocalTime.parse(time));
        appointment.setNotes(notes);

        appointmentRepository.save(appointment);
        return "redirect:/appointments/view";
    }

    @GetMapping("/edit/{id}")
    public String editAppointment(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow();
        model.addAttribute("appointment", appointment);
        model.addAttribute("appointments", appointmentRepository.findAll());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "appointments";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
        return "redirect:/appointments/view";
    }
}
