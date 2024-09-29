package valko.co.cartmanagament.model.report;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record Report(
        String reportName,
        LocalDateTime startDate,
        LocalDateTime endDate,
        List<ReportItem> items) {
}
