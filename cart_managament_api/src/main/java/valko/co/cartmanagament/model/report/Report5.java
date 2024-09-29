package valko.co.cartmanagament.model.report;

import java.time.LocalDateTime;
import java.util.List;

public record Report5(String reportName,
                      LocalDateTime startDate,
                      LocalDateTime endDate,
                      List<ReportTop5> items) {
    public Report5(String reportName, LocalDateTime startDate, LocalDateTime endDate, List<ReportTop5> items) {
        this.reportName = reportName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.items = items;
    }
}