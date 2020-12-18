package com.ozan.exchange.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple Web side Response
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
@ApiModel( description = "Ozan Web  Response" )
public class Response
{
    public static final Response EMPTY_RESPONSE = Response.builder().build();
    @ApiModelProperty( notes = "Data" )
    private Object data;
    @ApiModelProperty( notes = "Error" )
    private ResponseError error;
}
