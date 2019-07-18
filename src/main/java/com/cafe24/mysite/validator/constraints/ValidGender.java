package com.cafe24.mysite.validator.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cafe24.mysite.validator.GenderValidator;

@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy=GenderValidator.class)
public @interface ValidGender {
	//메시지는 여기에
	String message() default "Invalid Gender";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {}; 
}