package com.ozan.exchange.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class OzanExchangePaging implements Serializable
{

    private Object data;
    private long totalSize;
    private int pageSize;
    private int pageNumber;
    private int totalPageSize;

    public static OzanExchangePaging of( Object data, long totalSize, int pageNumber, int pageSize,
                    int totalPageSize )
    {
        return OzanExchangePaging.builder().
                        data(data).pageNumber(pageNumber).pageSize(pageSize).totalSize(totalSize)
                        .totalPageSize(totalPageSize).build();
    }
}
