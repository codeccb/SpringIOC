package cn.ccb.framework.beans.factory;

import java.lang.reflect.InvocationTargetException;

/**
 * IOC容器父接口
 */
public interface BeanFactory {

    // 根据bean对象的名称获取bean对象
    Object getBean(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException;

    // 根据Bean对象的名称获取bean对象，并进行类型转换
    <T> T getBean(String name,Class<? extends T> clzz) throws ClassNotFoundException, InvocationTargetException, InstantiationException, NoSuchMethodException, IllegalAccessException;
}
