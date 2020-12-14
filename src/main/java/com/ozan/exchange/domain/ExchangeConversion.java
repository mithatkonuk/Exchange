package com.ozan.exchange.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "exchange_conversion" )
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExchangeConversion extends AbstractBaseEntity
{

    private String base;

    private String symbol;

    private Double amount;

    private Double range;

    private Double conversion;

    @Temporal( TemporalType.DATE )
    @Column( name = "date_created" )
    private Date dateCreated;

    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "timestamp_created" )
    private Date timestampCreated;

}
