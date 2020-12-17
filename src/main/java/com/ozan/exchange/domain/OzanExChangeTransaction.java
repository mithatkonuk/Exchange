package com.ozan.exchange.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table( name = "exchange_transaction_table" )
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Data
@ApiModel( description = "Definition of Exchange Transaction" )
public class OzanExChangeTransaction extends AbstractBaseEntity
{

    @ApiModelProperty( notes = "base represent source currency" )
    @NotNull
    private String base;

    @ApiModelProperty( notes = "symbol represent target currency" )
    @NotNull
    private String symbol;

    @ApiModelProperty( notes = "Amount of Source currency" )
    private Double amount;

    @ApiModelProperty( notes = "Rate between source and target currency" )
    private Double rate;

    @ApiModelProperty( notes = "Conversion from source to target currency via rate and amount" )
    private Double conversion;

    @ApiModelProperty( notes = "Represent date of transaction event" )
    @Temporal( TemporalType.DATE )
    @Column( name = "date_created" )
    private Date dateCreated;

    @ApiModelProperty( notes = "Represent timestamp of transaction event" )
    @Temporal( TemporalType.TIMESTAMP )
    @Column( name = "timestamp_created" )
    private Date timestampCreated;

}
