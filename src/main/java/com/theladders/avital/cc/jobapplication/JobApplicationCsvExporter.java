package com.theladders.avital.cc.jobapplication;

import com.theladders.avital.cc.jobseeker.JobSeeker;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JobApplicationCsvExporter implements JobApplicationExporter {
    private static final String CSV_HEADER = "Employer,Job,Job Type,Applicants,Date" + "\n";

    private String concatCsvRow(String result, List<JobApplication> appliedOnDate) {
        for (JobApplication jobApplication : appliedOnDate) {
            result = result.concat(toCsvRow(jobApplication));
        }
        return result;
    }

    String toCsvRow(JobApplication application) {
        return application.publishedJob.toCsvCells() + "," + application.applicantInfo.toCsvCells() + "\n";
    }

    @Override
    public String export(LocalDate date, Set<Map.Entry<JobSeeker, JobApplications>> entries) {
        String result = CSV_HEADER;
        for (Map.Entry<JobSeeker, JobApplications> set : entries) {
            List<JobApplication> appliedOnDate = set.getValue().getMatchedItems(date);
            result = concatCsvRow(result, appliedOnDate);
        }
        return result;
    }
}
