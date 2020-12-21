package cn.ccb.framework.beans.context.support;

import cn.ccb.framework.beans.context.ApplicationContext;
import cn.ccb.framework.beans.factory.support.BeanDefinitionReader;
import cn.ccb.framework.beans.factory.support.BeanDefinitionRegistry;
import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * ApplicationContext接口的子实现类，用于非延时加载
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    // 声明解析器对象
    protected BeanDefinitionReader beanDefinitionReader;

    //定义用于存储bean对象的map容器
    protected Map<String,Object> singleObjects=new HashMap<String, Object>();

    // 声明配置文件路径的变量
    protected String configLocation;

    public void refresh() throws DocumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        // 加载BeanDefinition对象
        beanDefinitionReader.loadBeanDefinition(configLocation);
        finishBeanInitialization();
    };

    private void finishBeanInitialization() throws ClassNotFoundException, InvocationTargetException, InstantiationException, NoSuchMethodException, IllegalAccessException {
        // 获取注册表对象
        BeanDefinitionRegistry registry = beanDefinitionReader.getRegistry();

        // 获取BeanDefinition对象
        String[] beanNames = registry.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            // 进行bean初始化
            getBean(beanName);
        }
    }
}
