package cn.ccb.framework.beans.factory.support;

import org.dom4j.DocumentException;

/**
 * 读取并解析配置文件
 */
public interface BeanDefinitionReader {

    // 获取注册表对象
    BeanDefinitionRegistry getRegistry();

    // 加载配置文件并将bean对象在注册表中注册
    void loadBeanDefinition(String configLocation) throws DocumentException;
}
