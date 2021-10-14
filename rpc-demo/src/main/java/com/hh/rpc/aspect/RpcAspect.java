package com.hh.rpc.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/20
 * @Modified By :
 */
@Aspect
@Slf4j
@Component
public class RpcAspect {
    /**
     * 服务暴露接口的切点
     */
    @Pointcut("execution(* com.hh.rpc.service.consumer..*(..))")
    public void providerService() {
    }

    @Around("providerService()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        log.info("进入环绕通知");
        //执行该方法
        Object object = pjp.proceed();
        log.info("退出方法");
        return object;
    }
}
