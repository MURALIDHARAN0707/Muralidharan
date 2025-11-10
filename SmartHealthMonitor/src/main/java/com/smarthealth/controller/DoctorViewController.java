package com.smarthealth.controller;

import com.smarthealth.model.Doctor;
import com.smarthealth.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/doctors")
public class DoctorViewController {

    @Autowired
    private DoctorRepository doctorRepository;

    // View all doctors
    @GetMapping("/view")
    public String viewDoctors(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("doctor", new Doctor());
        return "doctors";
    }

    // Save doctor
    @PostMapping("/add")
    public String addDoctor(@ModelAttribute Doctor doctor) {
        doctorRepository.save(doctor);
        return "redirect:/doctors/view";
    }

    // Edit doctor
    @GetMapping("/edit/{id}")
    public String editDoctor(@PathVariable Long id, Model model) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doctor ID: " + id));
        model.addAttribute("doctor", doctor);
        model.addAttribute("doctors", doctorRepository.findAll());
        return "doctors";
    }

    // Delete doctor
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
        return "redirect:/doctors/view";
    }
}
