package com.ozan.exchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class ExchangeRequest
{
    @NotNull
    private String base;
    @NotNull
    private String symbol;
    @PositiveOrZero
    private Double amount;

    @NotEmpty
    @NotNull
    private String token;
}
