package cn.ccb.framework.beans.factory.support;

import cn.ccb.framework.beans.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * BeanDefinitionRegistry注册表子实现类
 */
public class SimpleBeanDefinitionRegistry implements BeanDefinitionRegistry{

    private Map<String,BeanDefinition> beanDefinitionMap=new HashMap<String, BeanDefinition>();

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName,beanDefinition);
    }

    public void removeBeanDefinition(String beanName) {
        beanDefinitionMap.remove(beanName);
    }

    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    public int getBeanDefinitionCount() {
        return beanDefinitionMap.size();
    }

    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }
}
