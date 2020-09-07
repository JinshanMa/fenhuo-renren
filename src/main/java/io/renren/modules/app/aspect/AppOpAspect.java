package io.renren.modules.app.aspect;

import io.jsonwebtoken.Claims;
import io.renren.common.exception.RRException;
import io.renren.modules.app.utils.JwtUtils;
import io.renren.modules.fenhuo.service.FenhuoOperationlogService;
import io.renren.modules.fenhuo.service.FenhuoUsersService;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
public class AppOpAspect extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FenhuoOperationlogService fenhuoOperationlogService;

    @Autowired
    private JwtUtils jwtUtils;

    @Around("execution(* io.renren.modules.app.controller.AppProjectController.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        try{

            String operationOpMethod = point.getSignature().getName();

            Object[] paramsObj = point.getArgs();

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //获取用户凭证
            String token = request.getHeader(jwtUtils.getHeader());
            if(StringUtils.isBlank(token)){
                token = request.getParameter(jwtUtils.getHeader());
            }

            Claims claims = jwtUtils.getClaimByToken(token);
            String userid = claims.getSubject();

            result = point.proceed();

            fenhuoOperationlogService.saveAppOpByParamThroughAspect(Long.parseLong(userid), paramsObj, operationOpMethod);

        }catch (Exception e){
            logger.error("aspect error", e);
            throw new RRException("aspect服务异常");
        }
        return result;
    }

}
