package com.hh.rpc.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取上下文
 *
 * @author hang.yuan 2021/10/14 14:06
 */

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext contex) throws BeansException {
        SpringContextUtil.context = contex;
    }


    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

}
