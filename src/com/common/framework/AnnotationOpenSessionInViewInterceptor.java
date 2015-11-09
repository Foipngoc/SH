package com.common.framework;

import com.common.utils.LogUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.orm.hibernate4.support.OpenSessionInViewInterceptor;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

/**
 * Created by 25019 on 2015/11/7.
 */
public class AnnotationOpenSessionInViewInterceptor implements HandlerInterceptor {
    private Logger logger = LogUtils.getLogger(this.getClass());
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            OpenSessionInView annotation = method.getAnnotation(OpenSessionInView.class);
            if (annotation != null) {
                Session s = sessionFactory.openSession();
                this.logger.debug("Opening Hibernate Session in OpenSessionInViewInterceptor");
                TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
            }
        } catch (Exception e) {
            this.logger.debug("Opening Hibernate Session exception!!!!");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            OpenSessionInView annotation = method.getAnnotation(OpenSessionInView.class);
            if (annotation != null) {
                SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.unbindResource(this.getSessionFactory());
                this.logger.debug("Closing Hibernate Session in OpenSessionInViewInterceptor");
                SessionFactoryUtils.closeSession(sessionHolder.getSession());
            }
        } catch (Exception exp) {
            this.logger.debug("Closing Hibernate Session exception!!!!");
        }
    }
}
