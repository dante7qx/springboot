package org.dante.springboot.quartz.util;

/**
 * 异常处理工具类
 * 
 * @author dante
 *
 */
public final class ExceptionUtils {

	/**
	 * 将异常堆栈信息转为字符串
	 * 
	 * @param e
	 * @return
	 */
    public static String getStackMsg(Exception e) {    
        StringBuffer sb = new StringBuffer();    
        StackTraceElement[] stackArray = e.getStackTrace();    
        for (int i = 0; i < stackArray.length; i++) {    
            StackTraceElement element = stackArray[i];    
            sb.append(element.toString() + "\n");    
        }    
        return sb.toString();    
    }    
    
    /**
     * 将异常堆栈信息转为字符串
     * 
     * @param e
     * @return
     */
    public static String getStackMsg(Throwable e) {    
        StringBuffer sb = new StringBuffer();    
        StackTraceElement[] stackArray = e.getStackTrace();    
        for (int i = 0; i < stackArray.length; i++) {    
            StackTraceElement element = stackArray[i];    
            sb.append(element.toString() + "\n");    
        }    
        return sb.toString();    
    } 
	
}
