package com.example.planyerpulse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class drasiCLASS {
    LocalDate date;
    String meros;
    LocalTime wra;
    String action;

    public drasiCLASS(LocalDate date, String meros, LocalTime wra, String action) {
        this.date = date;
        this.meros = meros;
        this.wra = wra;
        this.action = action;
    }

    @Override
    public String toString() {
        return date + " | " + meros + " | " + wra + " | " + action;
    }
}
