package com.ozan.exchange.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class PageableEntity implements Serializable
{

    private Object data;
    private long totalSize;
    private int pageSize;
    private int pageNumber;
    private int totalPageSize;

    public static PageableEntity of( Object data, long totalSize, int pageNumber, int pageSize,
                    int totalPageSize )
    {
        return PageableEntity.builder().
                        data(data).pageNumber(pageNumber).pageSize(pageSize).totalSize(totalSize)
                        .totalPageSize(totalPageSize).build();
    }
}
