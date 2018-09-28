//package com.pk.myhystrix.controller;
//
//import com.netflix.hystrix.HystrixCommand;
//import com.netflix.hystrix.HystrixCommandGroupKey;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MyCommond extends HystrixCommand<Object> {
//
//    private static final Logger logeer = LoggerFactory.getLogger(MyCommond.class);
//
//
//    public MyCommond(){
//        super(HystrixCommandGroupKey.Factory.asKey("UserGroupKey"));
//    }
//
//    @Override
//    protected Object run() throws Exception {
//        return null;
//    }
//    @Override
//    protected Object getFallback() {
////        throw new UnsupportedOperationException("No fallback available.");
//        return null;
//    }
//
//}
