package com.ozan.exchange.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude( JsonInclude.Include.NON_NULL )
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel( description = "Definition of General Response Error" )
public class ResponseError
{
    public static final ResponseError EMPTY_RESPONSE_ERROR =
                    ResponseError.builder().errorCode(0).message("Successs").build();

    @ApiModelProperty( notes = "Represent of Error Code" )
    private int errorCode;

    @ApiModelProperty( notes = "Represent of custom message about errorcode" )
    private String message;
}
