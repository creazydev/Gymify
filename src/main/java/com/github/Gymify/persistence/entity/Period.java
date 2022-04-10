package com.github.Gymify.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Embeddable

@NoArgsConstructor
@Getter
public class Period implements Comparable<Period> {

    @Column(name = "period_begin_timestamp", nullable = false)
    private Long periodBeginTimestamp;

    @Column(name = "period_due_timestamp", nullable = false)
    private Long periodDueTimestamp;

    @Column(name = "period_duration", nullable = false)
    private Duration periodDuration;

    public Period(Long periodBeginTimestamp, Long periodDueTimestamp) {
        this.periodBeginTimestamp = periodBeginTimestamp;
        this.periodDueTimestamp = periodDueTimestamp;
        this.periodDuration = Duration.between(
            timestampToLocalDateTime(periodBeginTimestamp),
            timestampToLocalDateTime(periodDueTimestamp)
        );
    }

    @Override
    public int compareTo(Period period) {
        if (this.getPeriodBeginTimestamp() < period.getPeriodBeginTimestamp()) {
            return 1;
        } else if (period.getPeriodBeginTimestamp() < this.getPeriodDueTimestamp()) {
            return -1;
        } else {
            return 0;
        }
    }

    public static LocalDateTime timestampToLocalDateTime(Long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, TimeZone.getDefault().toZoneId());
    }

    public static Period of(Long periodBeginTimestamp, Long periodDueTimestamp) {
        return new Period(periodBeginTimestamp, periodDueTimestamp);
    }

    public static Period between(Period period, Period period2) {
        return Period.of(period.getPeriodDueTimestamp(), period2.getPeriodBeginTimestamp());
    }

    public boolean inPeriod(Long timestamp) {
        return !(this.getPeriodBeginTimestamp() > timestamp)
            && !(timestamp > this.getPeriodDueTimestamp());
    }

    public boolean isOverlapping(Period period) {
        return this.isOverlapping(period.getPeriodBeginTimestamp(), period.getPeriodDueTimestamp());
    }

    public boolean isOverlapping(Long periodBeginTimestamp, Long periodDueTimestamp) {
        return this.getPeriodBeginTimestamp() < periodDueTimestamp && periodBeginTimestamp < this.getPeriodDueTimestamp();
    }
}
