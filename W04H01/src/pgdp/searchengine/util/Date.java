package pgdp.searchengine.util;

public class Date {
    private final int day;
    private final int month;
    private final int year;

    public Date(int day, int month, int year) {
        if (!isValidDate(day, month, year)) {
            System.out.println("Der " + day + "." + month + "." + year + " ist kein valides Datum.");
            this.day = -1;
            this.month = -1;
            this.year = -1;
        } else {
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    public boolean equals(Date other) {
        return day == other.day && month == other.month && year == other.year;
    }

    public static boolean isLeapYear(int year) {
        return Math.abs(year) % 4 == 0 && Math.abs(year) % 100 != 0 || Math.abs(year) % 400 == 0;
    }

    public static int daysInYear(int year) {
        return isLeapYear(year) ? 366 : 365;
    }

    public static int daysInMonth(int month, int year) {
        if (month <= 7 && month != 2) {
            return month % 2 == 0 ? 30 : 31;
        } else if (month == 2) {
            return isLeapYear(year) ? 29 : 28;
        }
        return month % 2 == 0 ? 31 : 30;
    }

    public static boolean isValidDate(int day, int month, int year) {
        return day > 0 && month > 0 && day <= daysInMonth(month, year) && month <= 12;
    }

    public int daysPassedThisYear() {
        if (!isValidDate(this.day, this.month, this.year)) {
            System.out.println("Methode auf ungültigem Datum aufgerufen!");
        }

//        int startDate = 1;
//        int startMonth = 1;
//        int daysPassedThisMonth = this.day - startDate;
//        int monthsPassedThisYear = this.month - startMonth;
        int totalDaysPassed = 0;

        for (int i = 1; i < this.month; i++) {
            totalDaysPassed += daysInMonth(i, this.year);
        }
        totalDaysPassed += this.day;

        return totalDaysPassed;
    }

    public int daysLeftThisYear() {
        if (!isValidDate(this.day, this.month, this.year)) {
            System.out.println("Methode auf ungültigem Datum aufgerufen!");
        }
        return daysInYear(this.year) - daysPassedThisYear();
    }

    public int daysUntil(Date other) {
        if (!isValidDate(this.day, this.month, this.year)) {
            System.out.println("Methode auf ungültigem Datum aufgerufen!");
        }

        if (this.year == other.getYear() && this.month == other.getMonth() && this.day == other.getDay()) {
            return 0;
        }

        int daysApart = 0;
        if (this.year == other.getYear()) {
            if (this.month == other.getMonth()) {
                daysApart = other.getDay() - this.day;
            } else {
                daysApart += daysInMonth(this.month, this.year) - this.day;
                for (int i = this.month + 1; i < other.getMonth(); i++) {
                    daysApart += daysInMonth(i, this.year);
                }
                daysApart += other.day;
            }
            return daysApart;
        }

        Date earlier = this.year < other.year ? this : other;
        Date later = this.year < other.year ? other : this;
        int vorzeichen = this.year < other.year ? 1 : -1;
        daysApart += earlier.daysLeftThisYear();

        for (int i = earlier.year + 1; i < later.year; i++) {
            daysApart += daysInYear(i);
        }

        daysApart += later.daysPassedThisYear();

        return daysApart * vorzeichen;
    }

    public int yearsUntil(Date other) {
        if (!isValidDate(this.day, this.month, this.year)) {
            System.out.println("Methode auf ungültigem Datum aufgerufen!");
        }

        int yearDifference = other.getYear() - this.year;
        if (other.getMonth() < this.month || (other.getMonth() == this.month && other.getDay() < this.day)) {
            yearDifference--;
        }

        return yearDifference;
    }

    public static Date dateMillisecondsAfterNewYear1970(long millis) {
        long millisToDays = (long) Math.floor((double) millis / 86400000);
        int year = 1970;

        if (millisToDays < 0) {
            while (millisToDays < 0) {
                millisToDays += daysInYear(--year);
            }
        } else {
            while (millisToDays >= daysInYear(year)) {
                millisToDays -= daysInYear(year++);
            }
        }

        int month = 1;
        while (millisToDays >= daysInMonth(month, year)) {
            millisToDays -= daysInMonth(month++, year);
        }

        int day = (int) millisToDays + 1;

        return new Date(day, month, year);
    }

    public static Date today() {
        return dateMillisecondsAfterNewYear1970(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return day + "." + month + "." + year;
    }

    //Getter
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public static void main(String[] args) {
        Date d1 = new Date(3, 9, 2021);
        Date d2 = new Date(2, 9, 2025);
//        System.out.println(d1.daysUntil(d2));
        System.out.println(d1.yearsUntil(d2));
    }
}
