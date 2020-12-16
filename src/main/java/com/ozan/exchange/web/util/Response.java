package com.ozan.exchange.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude( JsonInclude.Include.NON_NULL )
@ApiModel( description = "Definition of General Response Ozan Exchange" )
public class Response
{
    public static final Response EMPTY_RESPONSE = Response.builder().build();
    @ApiModelProperty( notes = "represent data" )
    private Object data;
    @ApiModelProperty( notes = "represent of error" )
    private ResponseError error;
}
