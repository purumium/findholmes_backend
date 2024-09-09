package org.detective.controller.Specialty;

import org.detective.entity.Specialty;
import org.detective.services.Specialty.SpecialtyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/specialty")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }
    @GetMapping
    public List<Specialty> getAllLocation() {
        System.out.println(specialtyService.getAllSpecialties());
        return specialtyService.getAllSpecialties();
    }
}