package com.ozan.exchange.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class AbstractBaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @Column( name = "transaction_id", nullable = false )
    protected UUID transaction;

    public AbstractBaseEntity()
    {
        this.transaction = UUID.randomUUID();
    }

    @Override
    public int hashCode()
    {
        return transaction.hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(!(obj instanceof AbstractBaseEntity))
        {
            return false;
        }
        AbstractBaseEntity other = (AbstractBaseEntity)obj;
        return getTransaction().equals(other.getTransaction());
    }
}