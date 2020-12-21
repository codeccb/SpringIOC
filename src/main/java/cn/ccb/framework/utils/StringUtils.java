package cn.ccb.framework.utils;

public class StringUtils {

    //userDao ---> setUserDao
    public static String getSetterMethodByFieldName(String fieldName){
        String methodName="set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
        return methodName;
    }

}
