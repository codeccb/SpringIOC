package cn.ccb.framework.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用来封装bean标签数据
 */
@Data
@AllArgsConstructor
public class BeanDefinition {

    private String id;
    private String className;

    private MutablePropertyValues propertyValues;

    public BeanDefinition(){
        propertyValues=new MutablePropertyValues();
    }
}
