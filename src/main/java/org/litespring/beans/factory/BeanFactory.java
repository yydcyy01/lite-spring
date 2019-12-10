package org.litespring.beans.factory;

import org.litespring.beans.BeanDefinition;

public interface BeanFactory {

    // 通用, user, 啥啥啥的
    Object getBean(String string);

    BeanDefinition getBeanDefinition(String beanID);
}