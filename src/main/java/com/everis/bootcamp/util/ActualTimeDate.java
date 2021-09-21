package com.everis.bootcamp.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

@Getter
@NoArgsConstructor
public class ActualTimeDate {

    private final String date = new LocalDate().toString();
    private final String time = new LocalTime().toString();

}
