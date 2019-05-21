package club.dulaoshi.blog.conf.shiro;

import club.dulaoshi.blog.entity.Blogger;
import club.dulaoshi.blog.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author djg
 * @date 2019/5/6 16:45
 * @des 自定义Realm
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private BloggerService bloggerService;

    /**
     * 为当前的登录用户授予角色和权限，这个项目简单没有，复杂点的要从数据库中获取为当前用户授予
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
    /**
     * 验证当前登陆的用户
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        Blogger blogger = bloggerService.getByUserName(userName);
        if(userName!=null){
            //把当前用户信息存到session中
//            SecurityUtils.getSubject().getSession().setAttribute("currentUser", blogger);
            AuthenticationInfo authenInfo = new SimpleAuthenticationInfo(blogger.getUserName(), blogger.getPassword(), "xxx");
            return authenInfo;
        }else{
            return null;
        }

    }
}
