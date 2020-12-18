package com.ozan.exchange.annotation;

import java.lang.annotation.*;

/**
 * Custom Annotation to calculate time execution on methods
 * @author mithat.konuk
 */
@Retention( RetentionPolicy.RUNTIME )
@Documented
@Target( ElementType.METHOD )
public @interface OzanExecutionTimeLogged
{
}
