package com.example.spring_into.converter;

import com.example.spring_into.config.DurationConfig;
import com.example.spring_into.dto.TollRequest;
import com.example.spring_into.dto.TollResponse;
import com.example.spring_into.enums.Duration;
import com.example.spring_into.model.TollPass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Period;
import java.util.Date;

import static com.example.spring_into.util.Helper.formatDate;

@Component
@Slf4j
public class TollConverter {
    private final DurationConfig durationConfig;

    @Autowired
    public TollConverter(DurationConfig durationConfig) {
        this.durationConfig = durationConfig;
    }

    public TollPass toTollPass(TollRequest tollRequest) {
        TollPass tollPass = new TollPass();
        tollPass.setRegNumber(tollRequest.getRegNumber());
        tollPass.setCountry(tollRequest.getCountry());
        setDateTime(tollPass, tollRequest);

        return tollPass;
    }

    private void setDateTime(TollPass tollPass, TollRequest tollRequest) {
        Instant startTime = Instant.now();
        int duration = durationToDays(tollRequest.getDuration());
        Instant expDate = startTime.plus(Period.ofDays(duration));
        tollPass.setStartDate(startTime);
        tollPass.setExpDate(expDate);
    }

    private int durationToDays(Duration duration) {
        switch (duration) {
            case WEEKEND:
                return this.durationConfig.getWeekend();
            case WEEK:
                return this.durationConfig.getWeek();
            case MONTH:
                return this.durationConfig.getMonth();
            case YEAR:
                return this.durationConfig.getYear();
            default:
                log.error("No config for {} was found", duration);
                return 10;
        }

    }

    public TollResponse toTollResponse(TollPass tollPass) {
        TollResponse tollResponse = new TollResponse();
        tollResponse.setEmail(tollPass.getOwner().getEmail());
        tollResponse.setRegNumber(tollPass.getRegNumber());
        tollResponse.setStartDate(formatDate(tollPass.getStartDate()));
        tollResponse.setExpDate(formatDate(tollPass.getExpDate()));

        return tollResponse;
    }

}
