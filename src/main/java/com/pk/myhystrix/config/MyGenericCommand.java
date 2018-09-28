package com.pk.myhystrix.config;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.command.*;
import com.netflix.hystrix.contrib.javanica.exception.CommandActionExecutionException;
import com.netflix.hystrix.contrib.javanica.exception.FallbackInvocationException;
import com.netflix.hystrix.contrib.javanica.utils.CommonUtils;
import com.netflix.hystrix.contrib.javanica.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyGenericCommand extends GenericCommand {

    private static final Logger logger = LoggerFactory.getLogger(MyGenericCommand.class);

    public MyGenericCommand(){
//        ConfigurationManager.getConfigInstance()
//                .setProperty();
//        Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserGroupKey"))
//                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500));
        super(hystrixCommandBuilder());
    }
    private static HystrixCommandBuilder hystrixCommandBuilder(){
        GenericSetterBuilder.Builder builder =  new GenericSetterBuilder.Builder()
                .groupKey("UserGroup")
                .commandKey("MyCommandKey")
                .threadPoolKey("myThreadPoolKey")
                .scope(HystrixCollapser.Scope.GLOBAL);
        GenericSetterBuilder pSetterBuilder = new GenericSetterBuilder(builder);
        HystrixCommandBuilder.Builder hBuilder = new HystrixCommandBuilder.Builder<>().setterBuilder(pSetterBuilder);
        HystrixCommandBuilder hystrixCommandBuilder = new HystrixCommandBuilder(hBuilder);
        return hystrixCommandBuilder;
    }


//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    protected Object run() throws Exception {
//        logger.debug("execute command: {}", getCommandKey().name());
//        return process(new Action() {
//            @Override
//            Object execute() {
//                return getCommandAction().execute(getExecutionType());
//            }
//        });
//    }

    @Override
    protected Object getFallback() {
        final CommandAction commandAction = getFallbackAction();
        if (commandAction != null) {
            try {
//                return process(new Action() {
//                    @Override
//                    Object execute() {
//                        MetaHolder metaHolder = commandAction.getMetaHolder();
//                        Object[] args = CommonUtils.createArgsForFallback(metaHolder, getExecutionException());
//                        return commandAction.executeWithArgs(metaHolder.getFallbackExecutionType(), args);
//                    }
//                });
                System.out.println("This is my getFallback");
                return null;
            } catch (Throwable e) {
                throw new FallbackInvocationException(ExceptionUtils.unwrapCause(e));
            }
        } else {
            return super.getFallback();
        }
    }

    /**
     * Common action.
     */
    abstract class Action {
        /**
         * Each implementation of this method should wrap any exceptions in CommandActionExecutionException.
         *
         * @return execution result
         * @throws CommandActionExecutionException
         */
        abstract Object execute() throws CommandActionExecutionException;
    }
}
