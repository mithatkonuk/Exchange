package com.ozan.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exchange
{
    private String base;
    private Map<String, Double> rates;
    private Date date;

}
