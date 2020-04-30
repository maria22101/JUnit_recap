package com.virtualpairprogrammers;

import java.math.BigDecimal;

public class GradesExplanation {

    public static String getAverageYearGrade(int firstSemesterGrade, int secondSemesterGrade) {
        BigDecimal result = new BigDecimal(firstSemesterGrade + secondSemesterGrade)
                .divide(BigDecimal.valueOf(2))
                .setScale(0, BigDecimal.ROUND_UP);
        return String.valueOf(result);
    }

}
