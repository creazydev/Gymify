package com.github.Gymify.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable

@NoArgsConstructor
@Getter
public class Period implements Comparable<Period> {

    @Column(name = "period_begin", nullable = false)
    private LocalDateTime periodBegin;

    @Column(name = "period_due", nullable = false)
    private LocalDateTime periodDue;

    @Column(name = "period_duration", nullable = false)
    private Duration periodDuration;

    public Period(LocalDateTime periodBegin, LocalDateTime periodDue) {
        this.periodBegin = periodBegin;
        this.periodDue = periodDue;
        this.periodDuration = Duration.between(periodBegin, periodDue);
    }

    @Override
    public int compareTo(Period period) {
        if (this.periodBegin.isBefore(period.getPeriodBegin())) {
            return 1;
        } else if (period.getPeriodBegin().isBefore(this.periodDue)) {
            return -1;
        } else {
            return 0;
        }
    }

    public static Period of(LocalDateTime periodBegin, LocalDateTime periodDue) {
        return new Period(periodBegin, periodDue);
    }

    public static Period between(Period period, Period period2) {
        return Period.of(period.getPeriodDue(), period2.getPeriodBegin());
    }

    public boolean inPeriod(LocalDateTime localDateTime) {
        return Objects.nonNull(localDateTime)
            && !this.getPeriodBegin().isAfter(localDateTime)
            && !localDateTime.isAfter(this.getPeriodDue());
    }

    public boolean isOverlapping(Period period) {
        return this.isOverlapping(period.getPeriodBegin(), period.getPeriodDue());
    }

    public boolean isOverlapping(LocalDateTime periodBegin, LocalDateTime periodDue) {
        return this.getPeriodBegin().isBefore(periodDue) && periodBegin.isBefore(this.getPeriodDue());
    }
}
