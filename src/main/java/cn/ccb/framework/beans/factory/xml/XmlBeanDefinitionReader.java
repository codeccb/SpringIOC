package cn.ccb.framework.beans.factory.xml;

import cn.ccb.framework.beans.BeanDefinition;
import cn.ccb.framework.beans.MutablePropertyValues;
import cn.ccb.framework.beans.PropertyValue;
import cn.ccb.framework.beans.factory.support.BeanDefinitionReader;
import cn.ccb.framework.beans.factory.support.BeanDefinitionRegistry;
import cn.ccb.framework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * 读取xml配置文件进行解析
 */
public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    // 声明注册表对象
    private BeanDefinitionRegistry registry;

    public XmlBeanDefinitionReader() {
        registry=new SimpleBeanDefinitionRegistry();
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    // 加载配置文件并将bean对象在注册表中注册
    public void loadBeanDefinition(String configLocation) throws DocumentException {
        // 使用dom4j进行xml配置文件的解析
        SAXReader reader=new SAXReader();
        // 获取类路径下配置文件输入流
        InputStream is = XmlBeanDefinitionReader.class.getClassLoader().getResourceAsStream(configLocation);
        Document document = reader.read(is);

        // 根据document对象获取根标签对象(beans)
        Element rootElement = document.getRootElement();

        // 获取所有bean标签对象
        List<Element> beanElements = rootElement.elements("bean");

        // 针对每一个Bean标签进行解析,并封装成BeanDefinition对象,注册到beanDefinitionRegistry
        for (Element beanElement : beanElements) {
            // 获取id和class属性
            String id = beanElement.attributeValue("id");
            String className = beanElement.attributeValue("class");

            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setId(id);
            beanDefinition.setClassName(className);

            // 获取每个bean标签下所有property标签对象
            List<Element> propertyElements = beanElement.elements("property");

            MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();

            for (Element propertyElement : propertyElements) {
                String name = propertyElement.attributeValue("name");
                String ref = propertyElement.attributeValue("ref");
                String value = propertyElement.attributeValue("value");

                PropertyValue propertyValue = new PropertyValue();
                propertyValue.setName(name);
                propertyValue.setRef(ref);
                propertyValue.setValue(value);

                mutablePropertyValues.addPropertyValue(propertyValue);
            }

            beanDefinition.setPropertyValues(mutablePropertyValues);
            registry.registerBeanDefinition(id,beanDefinition);
        }
    }
}
