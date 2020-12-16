package com.ozan.exchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

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
