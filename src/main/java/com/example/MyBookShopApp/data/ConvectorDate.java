package com.example.MyBookShopApp.data;

import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Getter
@Setter
public class ConvectorDate {

    public ConvectorDate() {
    }

    public Date convertStrDateFormat(String date) {
        try {
            if (date.length() == 10) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate localDate = LocalDate.parse(date, formatter);
                return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date(2023, 8, 23);
    }
}
