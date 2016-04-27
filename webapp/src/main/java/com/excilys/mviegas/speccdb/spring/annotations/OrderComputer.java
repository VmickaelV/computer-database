package com.excilys.mviegas.speccdb.spring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by excilys on 18/04/16.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = OrderComputerValidator.class)
public @interface OrderComputer {
}
