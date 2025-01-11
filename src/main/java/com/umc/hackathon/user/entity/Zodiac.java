package com.umc.hackathon.user.entity;

import java.time.LocalDate;

public enum Zodiac {
    Aries(0),
    Taurus(1),
    Gemini(2),
    Cancer(3),
    Leo(4),
    Virgo(5),
    Libra(6),
    Scorpius(7),
    Sagittarius(8),
    Capricorn(9),
    Aquarius(10),
    Pisces(11);

    private final int value;

    Zodiac(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static Zodiac fromDate(LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        if ((month == 3 && day >= 21) || (month == 4 && day <= 19)) {
            return Zodiac.Aries;
        } else if (month == 4 || month == 5 && day <= 20) {
            return Zodiac.Taurus;
        } else if (month == 5 || month == 6 && day <= 20) {
            return Zodiac.Gemini;
        } else if (month == 6 || month == 7 && day <= 22) {
            return Zodiac.Cancer;
        } else if (month == 7 || month == 8 && day <= 22) {
            return Zodiac.Leo;
        } else if (month == 8 || month == 9 && day <= 22) {
            return Zodiac.Virgo;
        } else if (month == 9 || month == 10 && day <= 22) {
            return Zodiac.Libra;
        } else if (month == 10 || month == 11 && day <= 21) {
            return Zodiac.Scorpius;
        } else if (month == 11 || month == 12 && day <= 21) {
            return Zodiac.Sagittarius;
        } else if (month == 12 || month == 1 && day <= 19) {
            return Zodiac.Capricorn;
        } else if (month == 1 || month == 2 && day <= 18) {
            return Zodiac.Aquarius;
        } else {
            return Zodiac.Pisces;
        }
    }

}
