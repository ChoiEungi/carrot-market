package numble.carrotmarket.common;

import numble.carrotmarket.exception.CustomException;
import org.joda.time.DateTime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public enum TimeUtils {
    MINUTE("분"),
    HOUR("시간"),
    DAY("일"),
    MONTH("월"),
    YEAR("년");

    private static final String PREVIOUS_UNIT = "전";
    private static final String NOW = "now";

    private final String name;

    TimeUtils(String name) {
        this.name = name;
    }

    public static String ofTimeFormat(LocalDateTime createdAt, LocalDateTime now){
        return getLocalDateTimeFormatter(createdAt, now);
    }

    private static String getLocalDateTimeFormatter(LocalDateTime createdAt, LocalDateTime now) {
        Duration timeAmount = Duration.between(createdAt, now);
        if (timeAmount.isNegative() ) {
            throw new CustomException("잘못된 시간이 인자로 들어왔습니다.");
        }

        if (timeAmount.isZero()) {
            return NOW;
        }

        if (timeAmount.toMinutes() < 60) {
            return timeAmount.toMinutes() + previousFormat(MINUTE.name);
        }

        if (timeAmount.toHours() < 24) {
            return timeAmount.toHours() + previousFormat(HOUR.name);
        }

        if (timeAmount.toDays() < 31) {
            return timeAmount.toDays() + previousFormat(DAY.name);
        }

        return localDateFormatter(createdAt.toLocalDate(), now.toLocalDate());

    }

    private static String localDateFormatter(LocalDate createdAt, LocalDate now) {
        Period period = Period.between(now, createdAt);

        if (period.getMonths() < 12) {
            return period.getMonths() + previousFormat(MONTH.name);
        }

        return period.getYears() + previousFormat(YEAR.name);
    }

    private static String previousFormat(String name){
        return name + PREVIOUS_UNIT;
    }
}
