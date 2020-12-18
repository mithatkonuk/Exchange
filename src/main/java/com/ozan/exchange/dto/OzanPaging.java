package com.ozan.exchange.dto;

import lombok.*;

import java.io.Serializable;

/**
 * General Ozan  Paging
 * @author mithat.konuk
 */
@Builder
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
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
