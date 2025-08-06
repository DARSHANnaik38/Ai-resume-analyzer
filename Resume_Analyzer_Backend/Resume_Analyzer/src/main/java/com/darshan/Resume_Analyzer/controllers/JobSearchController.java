package com.darshan.Resume_Analyzer.controllers;

import com.darshan.Resume_Analyzer.services.JobSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
public class JobSearchController {

    private final JobSearchService jobSearchService;

    public JobSearchController(JobSearchService jobSearchService) {
        this.jobSearchService = jobSearchService;
    }

    @PostMapping("/search")
    public Mono<ResponseEntity<String>> searchJobs(@RequestBody Map<String, List<String>> payload) {
        List<String> skills = payload.get("skills");
        String query = String.join(" ", skills);
        return jobSearchService.searchJobs(query)
                .map(response -> ResponseEntity.ok().body(response));
    }
}
