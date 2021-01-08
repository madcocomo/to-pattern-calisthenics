package com.theladders.avital.cc.jobapplication;

import com.theladders.avital.cc.jobseeker.JobSeeker;

import java.time.LocalDate;
import java.util.Objects;

public class ApplicantInfo {
    private final LocalDate applicationTime;
    private final JobSeeker applicant;

    public ApplicantInfo(LocalDate applicationTime, JobSeeker applicant) {
        this.applicationTime = applicationTime;
        this.applicant = applicant;
    }

    public boolean isMatched(LocalDate date)  {
        return applicationTime.equals(date);
    }

    public boolean isBeforeOrSame(LocalDate to) {
        return !to.isBefore(applicationTime);
    }

    public boolean isAfterOrSame(LocalDate from) {
        return !from.isAfter(applicationTime);
    }

    String toTableCells() {
        return "<td>" + applicant + "</td>" +
                "<td>" + applicationTime.format(JobApplication.DATE_TIME_FORMATTER) + "</td>";
    }

    String toCsvCells() {
        return applicant + "," +
                applicationTime.format(JobApplication.DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicantInfo that = (ApplicantInfo) o;
        return Objects.equals(applicationTime, that.applicationTime) && Objects.equals(applicant, that.applicant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationTime, applicant);
    }
}