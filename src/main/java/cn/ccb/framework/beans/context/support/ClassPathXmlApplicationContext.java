package cn.ccb.framework.beans.context.support;

import cn.ccb.framework.beans.BeanDefinition;
import cn.ccb.framework.beans.MutablePropertyValues;
import cn.ccb.framework.beans.PropertyValue;
import cn.ccb.framework.beans.factory.xml.XmlBeanDefinitionReader;
import cn.ccb.framework.utils.StringUtils;
import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * IOC容器的子实现类
 * 用于加载类路径下的xml格式配置文件
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext{

    public ClassPathXmlApplicationContext(String configuration) {
        this.configLocation=configuration;
        // 构建解析器对象
        beanDefinitionReader=new XmlBeanDefinitionReader();

        try {
            this.refresh();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 根据bean对象的名称获取bean对象
    public Object getBean(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        // 判断IOC容器中是否含指定名称的bean对象
        Object obj = singleObjects.get(name);
        if (obj!=null){
            return obj;
        }
        // 容器中没有,获取BeanDefinition对象通过反射创建
        BeanDefinition beanDefinition = beanDefinitionReader.getRegistry().getBeanDefinition(name);

        // 通过反射创建对象
        String className = beanDefinition.getClassName();
        Class<?> clazz = Class.forName(className);
        Object beanObj = clazz.newInstance();

        // 进行依赖注入
        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            // 获取name属性值
            String propertyName = propertyValue.getName();
            // 获取value值
            String value = propertyValue.getValue();
            // 获取ref属性
            String ref = propertyValue.getRef();

            if (ref!=null&&!"".equals(ref)){
                Object bean = getBean(ref);
                // 拼接方法名  userDao ---> setUserDao
                String methodName = StringUtils.getSetterMethodByFieldName(propertyName);
                // 获取所有的方法对象
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (methodName.equals(method.getName())){
                        // 执行setter方法
                        method.invoke(beanObj,bean);
                    }
                }
            }

            if (value!=null&&!"".equals(value)){
                String methodName = StringUtils.getSetterMethodByFieldName(propertyName);
                Method method = clazz.getMethod(methodName, String.class);
                method.invoke(beanObj,value);
            }
        }

        // 在返回beanObj对象之前,将该对象存储到map容器中
        singleObjects.put(name,beanObj);
        return beanObj;
    }

    public <T> T getBean(String name, Class<? extends T> clzz) throws ClassNotFoundException, InvocationTargetException, InstantiationException, NoSuchMethodException, IllegalAccessException {
        Object bean = getBean(name);
        if (bean==null){
            return null;
        }
        return clzz.cast(bean);
    }
}
