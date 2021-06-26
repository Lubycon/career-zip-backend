package com.careerzip.global.admin.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class DateParameters {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            // Date가 null인 경우 한달전을 시작 날짜로 조회 합니다.
            this.startDate = LocalDate.now().minusMonths(1);
            return;
        }
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate == null) {
            this.endDate = LocalDate.now();
            return;
        }
        this.endDate = endDate;
    }
}
