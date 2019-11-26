package nuc.jyg.hikariy.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author Ji YongGuang.
 * @date 23:34 2019-04-28.
 * @description Controller 切
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    private static Long executeLong = null;

    @Pointcut("execution(* nuc.jyg.hikariy.controller.*Controller.*(..))")
    public void controllerLog() {

    }


    @Before(value = "controllerLog()")
    public void beforeMethod(JoinPoint joinPoint) {
        executeLong = System.currentTimeMillis();

        Signature targetClass = joinPoint.getSignature();
        log.info("目标方法所属类的简单类名:{} - 目标方法名为:{}", targetClass.getDeclaringType().getSimpleName(), targetClass.getName());

        StringBuffer argsBuffer = new StringBuffer();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (!ObjectUtils.isEmpty(args[i])) {
                argsBuffer.append(String.format("目标方法的第%d个参数是:", (i + 1)) + args[i].toString() + " ");
            }
        }
        log.info(argsBuffer.toString());
    }

    @After(value = "controllerLog()")
    public void afterMethod() {
        executeLong = System.currentTimeMillis() - executeLong;
        log.info("目标方法执行时间:{}", executeLong + "毫秒");
    }
}
