package com.ozan.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/***
 *  Represent of Exchange Conversion DAO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conversion
{
    private String base;
    private String symbol;
    private String transaction;
    private Date dateCreated;
    private Double amount;
    private Double conversion;
}
