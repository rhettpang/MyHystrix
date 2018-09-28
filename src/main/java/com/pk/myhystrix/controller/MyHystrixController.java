package com.pk.myhystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class MyHystrixController {

    private static final Logger logger = LoggerFactory.getLogger(MyHystrixController.class);

//    @Value("key.group:myHystrix")
    private static final String groupKey = "";

    @GetMapping("")
    @HystrixCommand(
            commandKey = "myControllerKey"
//            ,
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),//指定多久超时，单位毫秒。超时进fallback
//                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
//                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),//判断熔断的阈值，默认值50，表示在一个统计窗口内有50%的请求处理失败，会触发熔断
//            }
            ,
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "5"),
                    @HystrixProperty(name = "maximumSize", value = "10"),
                    @HystrixProperty(name = "maxQueueSize", value = "10000"),
                    @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
//                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000")
            }
            )
    public String test() throws Exception{
//        System.out.println("Into test");
        try {

            Thread.sleep(10000);
            logger.info("Into test");
        }catch (Exception e){}
//        int i = new Random().nextInt(99);
//        if (i != 49){
//            throw new Exception("Test error");
//        }
//        throw new Exception("Test error");
        return "success";
    }


    public String myfallBack(){
        logger.info("My fall back");
        return "my fall back";
    }
}
