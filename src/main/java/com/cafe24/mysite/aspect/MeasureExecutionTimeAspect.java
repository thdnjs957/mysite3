package com.cafe24.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExecutionTimeAspect {
	
	@Around("execution(* *..repository.*.*(..)) || execution(* *..service.*.*(..)) || execution(* *..controller.*.*(..))") //dao 에다가 한거임
	public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable{
		//before
		StopWatch sw = new StopWatch();
		sw.start();
		
		//method 실행
		Object result = pjp.proceed(); 
		
		//after
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();
		
		String className = pjp.getTarget().getClass().getName(); //메소드가 실행하는 객체의 클레스의 이름이 나옴
		String methodName = pjp.getSignature().getName();//signature가 나오면 메소드라고 봐야함
		
		String taskName = className + "." + methodName;
		
		System.out.println("[Execution Time] : "+taskName+", [TotalTime] : "+totalTime );
		
		return result;
	}
}
