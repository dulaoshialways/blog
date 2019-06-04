package club.dulaoshi.blog.controller.qiniu;

import club.dulaoshi.blog.conf.prop.QiniuProp;
import club.dulaoshi.blog.result.Result;
import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author djg
 * @date 2019/5/30 15:13
 * @des
 */
@RestController
@RequestMapping("/admin/qiniu")
public class QiniuController {

    private final QiniuProp qiniuProp;

    public QiniuController(QiniuProp qiniuProp) {
        this.qiniuProp = qiniuProp;
    }

    @GetMapping("/qiniuUpToken")
    public Object qiniuUpToken(@RequestParam("suffix") String suffix){
        Map<String, Object> result = new HashMap<>(16);
        try {
            //验证七牛云身份是否通过
            Auth auth = Auth.create(qiniuProp.getAccessKey(), qiniuProp.getSecretKey());
            //生成凭证
            String upToken = auth.uploadToken(qiniuProp.getBucketName());
            result.put("token", upToken);
            //存入外链默认域名，用于拼接完整的资源外链路径
            result.put("domain", qiniuProp.getEndPoint());

            // 是否可以上传的图片格式
            /*boolean flag = false;
            String[] imgTypes = new String[]{"jpg","jpeg","bmp","gif","png"};
            for(String fileSuffix : imgTypes) {
                if(suffix.substring(suffix.lastIndexOf(".") + 1).equalsIgnoreCase(fileSuffix)) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                throw new Exception("图片：" + suffix + " 上传格式不对！");
            }*/

            //生成实际路径名
            String randomFileName = "blog_"+System.currentTimeMillis()+ "." + suffix;
            result.put("imgUrl", randomFileName);
        } catch (Exception e) {
            result.put("message", "获取凭证失败，"+e.getMessage());
        }
        return Result.success(result);
    }
}
