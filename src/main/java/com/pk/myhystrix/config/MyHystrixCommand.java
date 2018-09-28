//package com.pk.myhystrix.config;
//
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixException;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
//import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Inherited;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Target({ElementType.METHOD})
//@Retention(RetentionPolicy.RUNTIME)
//@Inherited
//@Documented
//public @interface MyHystrixCommand  {
//
//    String groupKey() default "";
//
//    String commandKey() default "";
//
//    String threadPoolKey() default "";
//
//    String fallbackMethod() default "myfallBack";
//
//    HystrixProperty[] commandProperties() default {};
//
//    HystrixProperty[] threadPoolProperties() default {};
//
//    Class<? extends Throwable>[] ignoreExceptions() default {};
//
//    ObservableExecutionMode observableExecutionMode() default ObservableExecutionMode.EAGER;
//
//    HystrixException[] raiseHystrixExceptions() default {};
//
//    String defaultFallback() default "myfallBack";
//
//
//
//}
