package com.darshan.Resume_Analyzer.services;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    // Your predefined list of skills to search for
    private static final List<String> KNOWN_SKILLS = Arrays.asList(
            "Java", "Python", "Spring Boot", "React", "SQL", "HTML", "CSS", "JavaScript",
            "Node.js", "MongoDB", "C++", "Docker", "AWS", "Git", "REST API"
    );

    /**
     * Parses the resume file, extracts known skills, and returns them.
     * @param file The uploaded resume file.
     * @return A list of skills found in the resume.
     * @throws IOException If the file cannot be read.
     * @throws TikaException If the file cannot be parsed.
     */
    public List<String> extractSkillsFromFile(MultipartFile file) throws IOException, TikaException {
        // 1. Extract text from the file
        Tika tika = new Tika();
        String resumeText = tika.parseToString(file.getInputStream()).toLowerCase();

        // 2. Find and return the skills present in the text
        return KNOWN_SKILLS.stream()
                .filter(skill -> resumeText.contains(skill.toLowerCase()))
                .collect(Collectors.toList());
    }
}