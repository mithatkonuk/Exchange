package com.ozan.exchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * Exchange Service conversion request for post methods
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
@ApiModel( description = "Definition of exchange rate client request" )
public class Conversion
{
    @ApiModelProperty( notes = "source currency" )
    @NotNull( message = "base must be represent" )
    private String base;

    @ApiModelProperty( notes = "target currency" )
    @NotNull( message = "symbol must be represent" )
    private String symbol;

    @ApiModelProperty( notes = "amount" )
    @PositiveOrZero( message = "The amount of base currency should be positive" )
    private Double amount;

    @ApiModelProperty( notes = "detail" )
    private boolean detail;
}
