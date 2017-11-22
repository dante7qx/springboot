package org.dante.springboot.data.utils;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class JpaEntityConvertUtils {
	public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz) throws Exception {    
        List<T> returnList = new ArrayList<T>();    
        Object[] co = list.get(0);    
        Class<?>[] c2 = new Class[co.length];    
            
        //确定构造方法    
        for(int i = 0; i < co.length; i++){    
            c2[i] = co[i].getClass();    
        }    
            
        for(Object[] o : list){    
            Constructor<T> constructor = clazz.getConstructor(c2);    
            returnList.add(constructor.newInstance(o));    
        }    
            
        return returnList;    
    } 
}
