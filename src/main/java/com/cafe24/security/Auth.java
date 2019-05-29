package com.cafe24.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD}) //이 annotation을 어디에 붙여쓰겠냐?
@Retention(RetentionPolicy.RUNTIME) //runtime 동안 보유
public @interface Auth {
	
//	String value() default "user";
//	int test() default 1;
	
	//이렇게 하면 분명 문제가 있다 @Auth(role = "USER") 일때 user라고 안쓰고 막 users 쓰고 그럴수있기때문에
	//String role() default "USER"; 
	//그래서 enum 씀
	
	public enum Role{ USER,ADMIN };
	
	public Role role() default Role.USER;
	
}
