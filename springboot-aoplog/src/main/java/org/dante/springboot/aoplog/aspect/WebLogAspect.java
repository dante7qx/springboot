package org.dante.springboot.aoplog.aspect;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * Web层日志切面
 * 
 * @author dante
 *
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);
	
	ThreadLocal<Long> startTime = new ThreadLocal<>();
	
	@Pointcut("execution(public * org.dante.springboot.aoplog.web..*.*(..))")
    public void webLog(){}
	
	@Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        
        String classType = joinPoint.getTarget().getClass().getName();    
        Class<?> clazz = Class.forName(classType);    
        String clazzName = clazz.getName();    
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        this.getFieldsName(this.getClass(), clazzName, methodName, args);   
        
        final String method = request.getMethod();
        /*
        if (HttpMethod.POST.matches(method)) {
        	String parameters = RequestParamterUtils.getRequestPostStr(request);
        	System.out.println(parameters);
        }
        */
        
        // 记录下请求内容
        LOGGER.info("URL : {}", request.getRequestURL().toString());
        LOGGER.info("HTTP_METHOD : {}", method);
        LOGGER.info("IP : {}", request.getRemoteAddr());
        LOGGER.info("CLASS_METHOD : {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("ARGS : {}", Arrays.toString(joinPoint.getArgs()));
//        LOGGER.info("ARGS : {}", mapper.writeValueAsString(joinPoint.getArgs()));
        
    }
	
	@AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
		LOGGER.info("RESPONSE : {}", ret);
        LOGGER.info("SPEND TIME : {} ms.",  (System.currentTimeMillis() - startTime.get()));
    }
	
	@AfterThrowing(pointcut = "webLog()", throwing = "ex")  
    public void afterThrow(JoinPoint joinPoint, Exception ex) {  
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String name = joinPoint.getSignature().getName();// 获得目标方法名  
        LOGGER.info("<=============" + name + "方法--AOP 异常后通知=============>");  
        LOGGER.info(name + "方法抛出异常为：" + "\t" + ex.getMessage());  
        HttpServletResponse response = attributes.getResponse();
        try {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write("<h2>您的访问不合法，请检查！</h2>");
			pw.flush();
			pw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }  
	
	@SuppressWarnings("rawtypes")
	private Map<String,Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws Exception {   
        Map<String,Object > map=new HashMap<String,Object>(); 

        ClassPool pool = ClassPool.getDefault();    
        ClassClassPath classPath = new ClassClassPath(cls);    
        pool.insertClassPath(classPath);    
            
        CtClass cc = pool.get(clazzName);    
        CtMethod cm = cc.getDeclaredMethod(methodName);    
        MethodInfo methodInfo = cm.getMethodInfo();  
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();    
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);    
        if (attr == null) {    
            // exception    
        }    
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;    
        for (int i = 0; i < cm.getParameterTypes().length; i++){    
            map.put( attr.variableName(i + pos),args[i]);//paramNames即参数名    
        }    
        return map;    
    }    
	
	/**
     * 根据传入的类型获取spring管理的对应Bean
     * @param clazz 类型
     * @param request 请求对象
     * @param <T>
     * @return
     */
    @SuppressWarnings("unused")
	private <T> T getDAO(Class<T> clazz, HttpServletRequest request) {
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        return factory.getBean(clazz);
    }
}
