package com.theladders.avital.cc.jobapplication;

import com.theladders.avital.cc.employer.Employer;
import com.theladders.avital.cc.job.Job;
import com.theladders.avital.cc.job.PublishedJob;
import com.theladders.avital.cc.jobseeker.JobSeeker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Predicate;

public class JobApplication {
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final ApplicantInfo applicantInfo;
    final PublishedJob publishedJob;

    public JobApplication(Job job, LocalDate applicationTime, Employer employer, JobSeeker applicant) {
        applicantInfo = new ApplicantInfo(applicationTime, applicant);
        this.publishedJob = new PublishedJob(job, employer);
    }

    static Predicate<JobApplication> getPredicate(String jobName, Employer employer) {
        return application -> application.publishedJob.isMatched(jobName, employer);
    }

    public static Predicate<JobApplication> getPredicate(LocalDate date) {
        return application -> application.applicantInfo.isMatched(date);
    }

    static Predicate<JobApplication> getPredicate(String jobName, LocalDate from, LocalDate to) {
        Predicate<JobApplication> predicate = application -> true;
        if (jobName != null) {
            predicate = predicate.and(application -> application.publishedJob.isMatched(jobName));
        }
        if (from != null) {
            predicate = predicate.and(application -> application.applicantInfo.isAfterOrSame(from));
        }
        if (to != null) {
            predicate = predicate.and(application -> application.applicantInfo.isBeforeOrSame(to));
        }
        return predicate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobApplication that = (JobApplication) o;
        return Objects.equals(applicantInfo, that.applicantInfo) && Objects.equals(publishedJob, that.publishedJob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicantInfo, publishedJob);
    }

}