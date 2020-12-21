package cn.ccb.framework.beans;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 存储和管理多个PropertyValue对象
 */
public class MutablePropertyValues implements Iterable<PropertyValue> {

    // 定义list集合对象，用来存储PropertyValue对象
    private final List<PropertyValue> propertyValueList;

    public MutablePropertyValues(){
        this.propertyValueList=new ArrayList<PropertyValue>();
    }

    public MutablePropertyValues(List<PropertyValue> propertyValueList){
        if (propertyValueList==null){
            this.propertyValueList=new ArrayList<PropertyValue>();
        }else {
            this.propertyValueList=propertyValueList;
        }
    }

    // 获取所有的PropertyValue对象
    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    // 根据name属性获取PropertyValue对象
    public PropertyValue getPropertyValue(String propertyName){
        for (PropertyValue propertyValue : propertyValueList) {
            if (propertyValue.getName().equals(propertyName)){
                return propertyValue;
            }
        }
        return null;
    }

    // 判断集合是否为空
    public boolean isEmpty(){
        return this.propertyValueList.isEmpty();
    }

    // 添加PropertyValue对象
    public MutablePropertyValues addPropertyValue(PropertyValue pv){
        // 判断集合中存储的PropertyValue对象是否和传递的重复了，如果重复，进行覆盖
        for (int i = 0; i < propertyValueList.size(); i++) {
            PropertyValue currentPv = propertyValueList.get(i);
            if (currentPv.getName().equals(pv.getName())){
                propertyValueList.set(i,pv);
                return this; //链式编程
            }
        }
        this.propertyValueList.add(pv);
        return this;
    }

    // 判断是否含有指定name属性的PropertyValue
    public boolean contains(String name){
        return getPropertyValue(name)!=null;
    }

    // 获取迭代器对象
    public Iterator<PropertyValue> iterator() {
        return propertyValueList.iterator();
    }
}
