package cn.ccb.framework.beans.context;

import cn.ccb.framework.beans.factory.BeanFactory;

/**
 * 定义非延时加载的容器
 */
public interface ApplicationContext extends BeanFactory {

    void refresh() throws Exception;

}
