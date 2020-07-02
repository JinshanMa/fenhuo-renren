package io.renren.modules.fenhuo.aspect;

import io.renren.common.exception.RRException;
import io.renren.modules.fenhuo.entity.FenhuoOperationlogEntity;
import io.renren.modules.fenhuo.entity.FenhuoProjectinfoEntity;
import io.renren.modules.fenhuo.entity.FenhuoUsersEntity;
import io.renren.modules.fenhuo.obj.FenhuoProjectinfoRequest;
import io.renren.modules.fenhuo.service.FenhuoOperationlogService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Aspect
@Configuration
public class FenhuoOpAspect extends AbstractController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FenhuoOperationlogService fenhuoOperationlogService;

    @Autowired
    private FenhuoUsersService fenhuoUsersService;

    @Autowired
    private SysUserService sysUserService;

    @Around("execution(* io.renren.modules.fenhuo.controller.FenhuoProjectinfoController.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        try{

            String operationOpMethod = point.getSignature().getName();

            Object[] paramsObj = point.getArgs();

            Long userid = getUserId();

            result = point.proceed();

            fenhuoOperationlogService.saveOpByParamThroughAspect(userid, paramsObj, operationOpMethod);

        }catch (Exception e){
            logger.error("aspect error", e);
            throw new RRException("aspect服务异常");
        }
        return result;
    }

}
