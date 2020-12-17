package com.ozan.exchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * This class is represent of client exchange response
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
public class OzanExchange
{
    private String base;

    private String symbol;

    private Double amount;

    private Double conversion;

    private Date date;

    private Double rate;

    private String transaction;
}
