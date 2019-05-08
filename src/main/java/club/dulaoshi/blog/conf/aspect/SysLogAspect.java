package club.dulaoshi.blog.conf.aspect;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.utils.HttpContextUtils;
import club.dulaoshi.blog.utils.IPUtils;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @ClassName: SysLogAspect
 * @Description: 系统日志，切面处理类
 * @author djg
 * @date 2019年04月25日
 *
 */
@Aspect
@Component
public class SysLogAspect {

	private static Logger logger = LoggerFactory.getLogger(SysLogAspect.class);
	
	@Pointcut("@annotation(club.dulaoshi.blog.conf.annotation.SysLog)")
	public void logPointCut() { 
		
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		//保存日志
		saveSysLog(point, time);

		return result;
	}

	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		SysLog syslog = method.getAnnotation(SysLog.class);
		if(syslog != null){

			//注解上的描述
			logger.info("controller方法描述：{}",syslog.value());
		}

		//请求的方法名
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		logger.info("当前时间：{},调用{}.{}()",sdf.format(new Date()),className,methodName );

		//请求的参数
		Object[] args = joinPoint.getArgs();
		try{
			String params = new Gson().toJson(args[0]);
			logger.info("params：{}",params);
		}catch (Exception e){
			e.printStackTrace();
		}

		//获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		//设置IP地址
		logger.info("调用方ip地址：{}", IPUtils.getIpAddr(request));
		logger.info("接口执行时长：{}ms",time);

	}
}
