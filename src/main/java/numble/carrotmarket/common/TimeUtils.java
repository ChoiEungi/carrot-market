package numble.carrotmarket.common;

import numble.carrotmarket.exception.CustomException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public enum TimeUtils {
    SECOND("초"),
    MINUTE("분"),
    HOUR("시간"),
    DAY("일"),
    MONTH("월"),
    YEAR("년");

    private static final String PREVIOUS_UNIT = "전";
    private static final String NOW = "now";
    private static final String UPDATED = "  수정됨";
    private static final int ONE_MINUTE_SECONDS = 60;
    private static final int ONE_HOUR_MINUTES = 60;
    private static final int ONE_DAY_HOURS = 24;
    private static final int ONE_MONTH_DAYS = 31;
    private static final int ONE_YEAR_MONTHS = 12;

    private final String name;

    TimeUtils(String name) {
        this.name = name;
    }

    public static String ofUpdatedTimeFormat(LocalDateTime updatedAt, LocalDateTime now){
        return ofTimeFormat(updatedAt, now) + UPDATED;
    }

    public static String ofTimeFormat(LocalDateTime createdAt, LocalDateTime now){
        return getLocalDateTimeFormatter(createdAt, now);
    }

    private static String getLocalDateTimeFormatter(LocalDateTime createdAt, LocalDateTime now) {
        Duration timeAmount = Duration.between(createdAt, now);
        if (timeAmount.isNegative() ) {
            throw new CustomException("잘못된 시간이 인자로 들어왔습니다.");
        }

        if (timeAmount.getSeconds() == 0 || timeAmount.isZero()) {
            return NOW;
        }

        if (timeAmount.getSeconds() < ONE_MINUTE_SECONDS) {
            return timeAmount.getSeconds() + previousFormat(SECOND.name);
        }

        if (timeAmount.toMinutes() < ONE_HOUR_MINUTES) {
            return timeAmount.toMinutes() + previousFormat(MINUTE.name);
        }

        if (timeAmount.toHours() < ONE_DAY_HOURS) {
            return timeAmount.toHours() + previousFormat(HOUR.name);
        }

        if (timeAmount.toDays() < ONE_MONTH_DAYS) {
            return timeAmount.toDays() + previousFormat(DAY.name);
        }

        return localDateFormatter(createdAt.toLocalDate(), now.toLocalDate());

    }

    private static String localDateFormatter(LocalDate createdAt, LocalDate now) {
        Period period = Period.between(createdAt, now);

        if (period.getMonths() < ONE_YEAR_MONTHS) {
            return period.getMonths() + previousFormat(MONTH.name);
        }

        return period.getYears() + previousFormat(YEAR.name);
    }

    private static String previousFormat(String name){
        return name + PREVIOUS_UNIT;
    }
}
