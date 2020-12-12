package com.ozan.exchange.foreign.Exchange.dto;

import lombok.*;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Exchange
{
    private String base;
    private Map<String, Double> rates;
    private Date date;

}
