package com.ozan.exchange.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class ForgienConversion
{
    @NotNull
    private String base;
    @NotNull
    private String symbol;
    @PositiveOrZero
    private Double amount;
    private Double conversion;
    private Date date;
}
