package com.ozan.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExchangeResponse
{
    private String base;
    private String symbol;
    private Double amount;
    private Double conversion;
    private Date date;
    private Double rate;
    private String token;
}
