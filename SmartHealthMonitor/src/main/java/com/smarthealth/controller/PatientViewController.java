package com.smarthealth.controller;

import com.smarthealth.model.Patient;
import com.smarthealth.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientViewController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/view")
    public String viewPatients(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("patient", new Patient());
        return "patients";
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute Patient patient) {
        patientRepository.save(patient);
        return "redirect:/patients/view";
    }

    @GetMapping("/edit/{id}")
    public String editPatient(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        model.addAttribute("patient", patient);
        model.addAttribute("patients", patientRepository.findAll());
        return "patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
        return "redirect:/patients/view";
    }
}
