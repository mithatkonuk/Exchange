package com.ozan.exchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude( JsonInclude.Include.NON_NULL )
public class ExchangeResponse
{
    private String base;
    private String symbol;
    private Double amount;
    private Double conversion;
    private Date date;
    private Double rate;
    private String transaction;
}
