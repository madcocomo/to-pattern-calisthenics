package com.theladders.avital.cc.jobapplication;

import com.theladders.avital.cc.employer.Employer;
import com.theladders.avital.cc.job.Job;
import com.theladders.avital.cc.jobseeker.JobSeeker;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AppliedJobApplications {
    final private LinkedHashMap<JobSeeker, JobApplications> jobApplications = new LinkedHashMap<>();

    public void add(Employer employer, Job job, LocalDate applicationTime, JobSeeker jobSeeker) {
        JobApplications saved = jobApplications.getOrDefault(jobSeeker, new JobApplications());
        saved.add(employer, job, jobSeeker, applicationTime);
        jobApplications.put(jobSeeker, saved);
    }

    public List<JobApplication> getJobApplications(JobSeeker jobSeeker) {
        JobApplications jobApplications = this.jobApplications.get(jobSeeker);
        return jobApplications.toList();
    }

    public String exportHtml(LocalDate date) {
        return new JobApplicationHtmlExporter().export(date, this.jobApplications.entrySet());
    }

    public String exportCsv(LocalDate date) {
        return new JobApplicationCsvExporter().export(date, this.jobApplications.entrySet());
    }

    public int getCount(String jobName, Employer employer) {
        Predicate<JobApplication> predicate = JobApplication.getPredicate(jobName, employer);
        return (int) jobApplications.entrySet().stream()
                .filter(set -> set.getValue().isMatched(predicate))
                .count();
    }

    List<JobSeeker> find(String jobName, LocalDate from, LocalDate to) {
        Predicate<JobApplication> listPredicate = JobApplication.getPredicate(jobName, from, to);
        return jobApplications.entrySet().stream()
                .filter(set -> set.getValue().isMatched(listPredicate))
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
    }
}