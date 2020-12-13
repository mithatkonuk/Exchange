package com.ozan.exchange.annotation;

import java.lang.annotation.*;

@Retention( RetentionPolicy.RUNTIME )
@Documented
@Target( ElementType.METHOD )
public @interface OzanLogged
{
}
