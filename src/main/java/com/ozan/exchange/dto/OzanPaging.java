package com.ozan.exchange.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
@Data
public class OzanPaging implements Serializable
{
    private Object data;

    private long totalSize;

    private int pageSize;

    private int pageNumber;

    private int totalPageSize;

    public static OzanPaging of( Object data, long totalSize, int pageNumber, int pageSize,
                    int totalPageSize )
    {
        return OzanPaging.builder().
                        data(data).pageNumber(pageNumber).pageSize(pageSize).totalSize(totalSize)
                        .totalPageSize(totalPageSize).build();
    }
}
