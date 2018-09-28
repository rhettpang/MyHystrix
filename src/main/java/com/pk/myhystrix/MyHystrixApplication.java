package com.pk.myhystrix;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.*;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.contrib.javanica.command.GenericCommand;
import com.netflix.hystrix.contrib.javanica.command.GenericSetterBuilder;
import com.netflix.hystrix.contrib.javanica.command.HystrixCommandBuilder;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
//import com.pk.myhystrix.config.MyGenericCommand;
//import com.pk.myhystrix.config.MyHystrixCommand;
//import com.pk.myhystrix.controller.MyCommond;
import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MyHystrixApplication {

//    @Autowired
//    private MyGenericCommand myGenericCommand;


//    @Autowired
//    private MyCommond myCommond;

    public static void main(String[] args) {
        SpringApplication.run(MyHystrixApplication.class,args);
    }


    //用来拦截处理HystrixCommand注解
    @Bean
    public HystrixCommandAspect hystrixAspect() {
        return new HystrixCommandAspect();
    }

    //用来像监控中心Dashboard发送stream信息
    @Bean
    public ServletRegistrationBean hystrixMetricsStreamServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        registration.addUrlMappings("/hystrix.stream");
        return registration;
    }


    @Bean
    public AbstractConfiguration ConfigurationManager(){
        AbstractConfiguration abstractConfiguration = ConfigurationManager.getConfigInstance();
        //Command
        //execution execution控制 HystrixCommand.run()方法
        //隔离策略，可选值THREAD, SEMAPHORE，默认使用THREAD
        abstractConfiguration.setProperty("hystrix.command.default.execution.isolation.strategy", "THREAD");
        //设置超时时间
        abstractConfiguration.setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", "1000");
        //设置超时时间是否生效，默认true
        abstractConfiguration.setProperty("hystrix.command.default.execution.timeout.enabled", "true");
        //发生超时时是否中断当前进程，默认true
        abstractConfiguration.setProperty("hystrix.command.default.execution.isolation.thread.interruptOnTimeout", "false");
        //当发生取消操作时，是否中断操作，默认false(在哪取消)
        abstractConfiguration.setProperty("hystrix.command.default.execution.isolation.thread.interruptOnCancel", "true");
        //当设置信号量隔离的时候，设置最大允许的并发量,默认10
//        abstractConfiguration.setProperty("hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests", "10");

        //fallback
        //当并发数达到（该值）最大限制时，会直接抛出异常，不会执行fallback，对THREAD和semaphore都生效，默认10
//        abstractConfiguration.setProperty("hystrix.command.default.fallback.isolation.semaphore.maxConcurrentRequests", "100");
        //设置fallback是否生效，默认true，生效的前提是要指定fallback方法
//        abstractConfiguration.setProperty("hystrix.command.default.fallback.enabled", "true");

        //Circuit Breaker
        //断路器是否生效，默认true
        abstractConfiguration.setProperty("hystrix.command.default.circuitBreaker.enabled","true");
        //设置断路器生效的阀值，默认20. 如果设置的是20，但是默认时间内（metrics.rollingStats.timeInMilliseconds的值：如默认10s）
        // 最多只有19个请求，即使全部失败也不会触发断路
        abstractConfiguration.setProperty("hystrix.command.default.circuitBreaker.requestVolumeThreshold","20");
        //断路开启后多少时间内拒绝外部请求，默认5000ms
        abstractConfiguration.setProperty("hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds","20000");
//        //失败率，默认50，即50%失败会触发断路
        abstractConfiguration.setProperty("hystrix.command.default.circuitBreaker.errorThresholdPercentage", "50");
        //强制开启断路器，默认false,如果设置为true，其优先级高于forceClosed
//        abstractConfiguration.setProperty("hystrix.command.default.circuitBreaker.forceOpen", "false");
        //强制关闭断路器，默认false,如果设置为true，其优先级低于forceOpen
//        abstractConfiguration.setProperty("hystrix.command.default.circuitBreaker.forceClosed", "false");

//        //Metrics
//        //设置度量值的时间长度，默认10s
        abstractConfiguration.setProperty("hystrix.command.default.metrics.rollingStats.timeInMilliseconds", "10000");
//        //设置bucket的数量，默认10，要能被metrics.rollingStats.timeInMilliseconds整除才行
        abstractConfiguration.setProperty("hystrix.command.default.metrics.rollingStats.numBuckets", "10");
        abstractConfiguration.setProperty("hystrix.command.default.metrics.healthSnapshot.intervalInMilliseconds", "500");

//        //ThreadPool Properties
//        //线程池的大小，默认10
        abstractConfiguration.setProperty("hystrix.threadpool.default.coreSize", "5");
//        //在熔断开启前支持的最大并发数，默认10，allowMaximumSizeToDivergeFromCoreSize设置时才生效
        abstractConfiguration.setProperty("hystrix.threadpool.default.maximumSize", "10");
        //用来控制maximumSize 是否生效的，允许maximumSize 大于或等于coreSize
        abstractConfiguration.setProperty("hystrix.threadpool.default.allowMaximumSizeToDivergeFromCoreSize", "true");
//        //设置BlockingQueue的实现的最大队列大小，如果设置-1,使用SynchronousQueue，设置其他值使用LinkedBlockingQueue
//        //在不重启项目的情况下，这里的值时不能改变的.SynchronousQueue是大小为1的阻塞队列
        abstractConfiguration.setProperty("hystrix.threadpool.default.maxQueueSize", "10");
//        //设置队列的最大值，即使没有达到maxQueueSize 的值也会拒绝后边的请求，只有在maxQueueSize不为-1时才生效，也是为了弥补maxQueueSize不能动态更改而设定的
//        abstractConfiguration.setProperty("hystrix.threadpool.default.queueSizeRejectionThreshold", "5");
//        //设置线程保持的时间，默认1分钟。允许只存在2个独立的线程存活。当coreSize < maximumSize时，这里用来控制一个线程的存活时间
//        abstractConfiguration.setProperty("hystrix.threadpool.default.keepAliveTimeMinutes", "1");

        return abstractConfiguration;
    }





//    @Bean
//    public GenericCommand genericCommand(){
//
////        List<HystrixProperty> properties = Collections.emptyList();
////        HystrixProperty commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100");
////        properties.add(commandProperties);
////        GenericSetterBuilder.Builder builder =  new GenericSetterBuilder.Builder()
////                .groupKey("UserGroup")
////                .commandKey("MyCommandKey")
////                .threadPoolKey("myThreadPoolKey")
////                .scope(HystrixCollapser.Scope.GLOBAL);
////        GenericSetterBuilder pSetterBuilder = new GenericSetterBuilder(builder);
////        HystrixCommandBuilder.Builder hBuilder = new HystrixCommandBuilder.Builder<>().setterBuilder(pSetterBuilder);
////        HystrixCommandBuilder hystrixCommandBuilder = new HystrixCommandBuilder(hBuilder);
////        GenericCommand genericCommand = new GenericCommand(hystrixCommandBuilder);
////        MyGenericCommand myGenericCommand = new MyGenericCommand();
//        GenericCommand genericCommand = myGenericCommand;
//        return genericCommand;
//    }
//
//    @Bean
//    public HystrixCommand hystrixCommand(){
//        return myCommond;
//    }

//    @Bean
//    public HystrixMetricsStreamServlet hystrixMetricsStreamServlet(){
//        return new HystrixMetricsStreamServlet();
//    }
//
//    @Bean
//    public ServletRegistrationBean registration(HystrixMetricsStreamServlet servlet){
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
//        registrationBean.setServlet(servlet);
//        registrationBean.setEnabled(true);//是否启用该registrationBean
//        registrationBean.addUrlMappings("/hystrix.stream");
//        return registrationBean;
//    }

//    class MyCommand extends HystrixCommand<String>{
//
//        public MyCommand(){
//            super(HystrixCommandGroupKey.Factory.asKey("UserGroup"));
//        }
//
//        @Override
//        protected String run() throws Exception {
//            System.out.println("circuitBreaker = " + circuitBreaker);
//            return null;
//        }
//        @Override
//        protected String getFallback() {
//            System.out.println("This is getFallback");
//            return null;
//        }
//    }
//
//    public String myfallBack(){
//        System.out.println("My fall back");
//        return "my fall back";
//    }
}
