package com.darshan.Resume_Analyzer.controllers;

import com.darshan.Resume_Analyzer.services.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/extract-skills")
    public ResponseEntity<?> uploadAndExtractSkills(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Please upload a file."));
        }
        try {
            List<String> extractedSkills = resumeService.extractSkillsFromFile(file);
            return ResponseEntity.ok(extractedSkills); // Directly return the list of skills
        } catch (Exception e) {
            // Log the exception e
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to process the resume."));
        }
    }
}