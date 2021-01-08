package com.theladders.avital.cc.jobapplication;

import com.theladders.avital.cc.jobseeker.JobSeeker;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JobApplicationHtmlExporter implements JobApplicationExporter {
    static final String HTML_HEADER = "<!DOCTYPE html>"
            + "<body>"
            + "<table>"
            + "<thead>"
            + "<tr><th>Employer</th><th>Job</th><th>Job Type</th><th>Applicants</th><th>Date</th>"
            + "</tr>"
            + "</thead>"
            + "<tbody>";
    static final String HTML_END = "</tbody>" + "</table>" + "</body>" + "</html>";

    private String concatTableRow(String content, List<JobApplication> appliedOnDate) {
        for (JobApplication application : appliedOnDate) {
            content = content.concat(toTableRow(application));
        }
        return content;
    }

    String toTableRow(JobApplication application) {
        return "<tr>" +
                application.publishedJob.toTableCells() +
                application.applicantInfo.toTableCells() +
                "</tr>";
    }

    @Override
    public String export(LocalDate date, Set<Map.Entry<JobSeeker, JobApplications>> entries) {
        String content = "";
        for (Map.Entry<JobSeeker, JobApplications> set : entries) {
            List<JobApplication> appliedOnDate = set.getValue().getMatchedItems(date);
            content = concatTableRow(content, appliedOnDate);
        }
        return HTML_HEADER + content + HTML_END;
    }
}