package club.dulaoshi.blog.conf.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @author djg
 * @date 2019/5/21 10:28
 * @des 自定义sessionId获取
 */
public class MySessionManager extends DefaultWebSessionManager  {
    private static final String AUTHORIZATION = "Token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public MySessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //获取请求头获取请求参数中的token
        String id = StringUtils.isEmpty(WebUtils.toHttp(request).getHeader(AUTHORIZATION))
                ? request.getParameter(AUTHORIZATION) : WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }


    /**
     * 获取session 优化单次请求需要多次访问redis的问题
     * @param sessionKey
     * @return
     * @throws UnknownSessionException
     */
//    @Override
//    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
//        Serializable sessionId = getSessionId(sessionKey);
//
//        ServletRequest request = null;
//        if (sessionKey instanceof WebSessionKey) {
//            request = ((WebSessionKey) sessionKey).getServletRequest();
//        }
//
//        if (request != null && null != sessionId) {
//            Object sessionObj = request.getAttribute(sessionId.toString());
//            if (sessionObj != null) {
//                return (Session) sessionObj;
//            }
//        }
//
//        Session session = super.retrieveSession(sessionKey);
//        if (request != null && null != sessionId) {
//            request.setAttribute(sessionId.toString(), session);
//        }
//        return session;
//    }
}
