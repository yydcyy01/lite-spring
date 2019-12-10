package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * @author YYDCYY
 * @create 2019-12-10
 */
public interface BeanDefinitionRegistry {
	BeanDefinition getBeanDefinition(String beanID);

	/**
	 * 概念 : 获取 bean , 并注册
	 * @param id
	 * @param bd
	 */
	void registerBeanDefinition(String id, BeanDefinition bd);
}
