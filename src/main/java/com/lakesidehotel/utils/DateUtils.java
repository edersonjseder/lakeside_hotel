package com.lakesidehotel.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtils {
    public String parseDate(LocalDateTime data) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(data);
    }

    public LocalDateTime parteToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        return dateTime;
    }

    public boolean isBefore(LocalDateTime startDate, LocalDateTime endDate) {
        return endDate.isBefore(startDate);
    }
}
