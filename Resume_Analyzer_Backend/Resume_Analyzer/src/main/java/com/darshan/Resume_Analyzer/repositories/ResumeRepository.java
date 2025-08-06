package com.darshan.Resume_Analyzer.repositories;

import com.darshan.Resume_Analyzer.models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
