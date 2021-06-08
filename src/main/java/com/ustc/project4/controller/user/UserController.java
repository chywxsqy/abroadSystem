package com.ustc.project4.controller.user;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ustc.project4.entity.User;
import com.ustc.project4.service.UserService;
import com.ustc.project4.util.HostHolder;
import com.ustc.project4.util.Project4Constant;
import com.ustc.project4.util.Project4Util;
import com.ustc.project4.util.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController implements Project4Constant {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;

    @Value("${aliyun.oss.file.secretid}")
    private String secretid;

    @Value("${aliyun.oss.file.bucket.name}")
    private String bucket;

    @Value("${aliyun.oss.file.bucket.dir}")
    private String dirName;

    @Value("${rsa.privatekey}")
    private String privateKey;

    @GetMapping("/myInfo")
    @ResponseBody
    public String getUserInfo() {
        User user = hostHolder.getUser();
        user.setPassword(null);
        return Project4Util.getJSONString(0, null, "loginUser", user);
    }

    @PostMapping("/myInfo")
    @ResponseBody
    public String setUserInfo(@RequestBody User user) {
        int id = hostHolder.getUser().getId();
        userService.updateInfo(id, user);
        return Project4Util.getJSONString(CODE_SUCCESS, null, null);
    }

    /**
     * 更新头像到Oss
     * @param headerimage
     * @return
     */
    @PostMapping("/avatar")
    @ResponseBody
    public String uploadHeaderOss(@RequestParam("file") MultipartFile headerimage) {
        if(headerimage == null) {
            return Project4Util.getJSONString(CODE_FAILURE, "您未选择图片！", null);
        }

        String filename = headerimage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));  //.png等后缀
        if(StringUtils.isBlank(suffix)) {
            return Project4Util.getJSONString(CODE_FAILURE, "文件格式不正确！", null);
        }

        //生成随机访问url
        filename = Project4Util.generateUUID() + suffix;

        //文件名加上头像所在文件夹名
        filename = dirName + "/" + filename;

        //上传头像
        OSS ossClient = new OSSClientBuilder().build(endpoint, keyid, secretid);
        try(
                InputStream is = headerimage.getInputStream();
        ) {
            ossClient.putObject(bucket, filename, is);
            ossClient.shutdown();
        } catch (IOException e) {
            logger.error("上传文件失败:" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器异常！", e);
        }

        //更新headerUrl(web访问路径)
        //文件位置(web访问路径)http://${bucket}.oss-cn-shanghai.aliyuncs.com/${dirName}/${filename}
        User user = hostHolder.getUser();
        String headerUrl = "http://" + bucket + "." + endpoint + "/" + filename;        //服务器实际存放头像位置
        userService.updateHeader(user.getId(), headerUrl);

        return Project4Util.getJSONString(CODE_SUCCESS, "上传头像成功！", null);
    }

    @PostMapping("/password")
    @ResponseBody
    public String changePassword(@RequestBody Map<String, String> params) {
        String oldPassword = params.get("oldPassword");
        String password = params.get("password");
        User loginUser = hostHolder.getUser();
        if(!loginUser.getPassword().equals(Project4Util.md5(oldPassword))) {
            return Project4Util.getJSONString(CODE_FAILURE, "原密码错误！", null);
        }
        int id = loginUser.getId();
        userService.updatePassword(id, password);
        return Project4Util.getJSONString(CODE_SUCCESS, "密码修改成功！", null);
    }

    @PostMapping("/passwordS")
    @ResponseBody
    public String changePasswordS(@RequestBody Map<String, String> params) {
        try {
            String oldPassword = params.get("oldPassword");
            String password = params.get("password");
            oldPassword = new String(RSAUtil.decryptByPrivateKey(oldPassword, privateKey));
            password = new String(RSAUtil.decryptByPrivateKey(password, privateKey));
            params.put("oldPassword",oldPassword);
            params.put("password",password);

        } catch (Exception e) {
            return Project4Util.getJSONString(CODE_SERVER_FAILURE, "服务器解密失败，请重试！", null);
        }
        return changePassword(params);


    }
}
