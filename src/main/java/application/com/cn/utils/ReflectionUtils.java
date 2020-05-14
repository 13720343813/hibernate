package application.com.cn.utils;

import application.com.cn.annotations.Params;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ReflectionUtils {

    private final Class<?> clazz;

    public ReflectionUtils(Class<?> clazz) {
        this.clazz = clazz;
    }

    public List<?> convertClass(List<LinkedHashMap<String, Object>> linkedHashMapList) {
        List list = new ArrayList();
        try {
            //获取类中所有属性
            Field[] fields = clazz.getDeclaredFields();
            //遍历Map
            for (LinkedHashMap<String, Object> linkedHashMap : linkedHashMapList) {
                Object object = clazz.newInstance();
                for (Field field : fields) {
                    String name = field.getName();
                    //判断是否存在注解
                    if (field.isAnnotationPresent(Params.class)) {
                        Params params = field.getAnnotation(Params.class);
                        name = params.alias();//获取注解名称
                    }
                    PropertyDescriptor descriptor;
                    try {
                        descriptor = new PropertyDescriptor(field.getName(), clazz);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    //判断Map中是否存在
                    if (linkedHashMap.containsKey(name)) {
                        //获取写方法
                        Method wM = descriptor.getWriteMethod();
                        wM.invoke(object, linkedHashMap.get(name));
                    }
                }
                list.add(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
