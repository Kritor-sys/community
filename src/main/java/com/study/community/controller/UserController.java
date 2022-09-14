package com.study.community.controller;

import com.study.community.annotation.LoginRequired;
import com.study.community.entity.User;
import com.study.community.service.FollowService;
import com.study.community.service.LikeService;
import com.study.community.service.UserService;
import com.study.community.util.CommunityConstant;
import com.study.community.util.CommunityUtil;
import com.study.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/user")
public class UserController  implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;

    @LoginRequired
    @RequestMapping(path = "/setting",method = RequestMethod.GET)
    public String getSettingPage(){
        return "/site/setting";
    }

    @LoginRequired
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headImage, Model model){
        if(headImage==null){
            model.addAttribute("error","您还没有选择图片");
            return "/site/setting";
        }
        String filename = headImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if(StringUtils.isBlank(suffix)){
            model.addAttribute("error","文件格式错误");
            return "/site/setting";
        }

        //生成随机文件名
        filename = CommunityUtil.generateUUID() + suffix;
        //确定文件存放位置
        File dest = new File(uploadPath+"/"+filename);
        try {
            headImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败："+e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发挥异常",e);
        }
        //更新当前用户的头像路径(web路径)
        //http://localhost:8090/community/user/header/xx.png
        User user = hostHolder.getUser();
        String headerUrl = domain+contextPath+"/user/header/"+filename;
        userService.updateHeader(user.getId(),headerUrl);


        return "redirect:/index";
    }

    /**
     * 打开首页时查询加载图片信息并输出到页面
     * @param fileName
     * @param response
     */
    @RequestMapping(path = "/header/{fileName}",method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response){
        //服务器存放路径
        fileName = uploadPath+"/"+fileName;
        //文件名后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));

        response.setContentType("image/"+suffix);
        try (
                OutputStream os = response.getOutputStream();
                FileInputStream in = new FileInputStream(fileName);
        )
        {

            byte[] buffer = new byte[1024];
            int b = 0;
            while((b = in.read(buffer))!=-1){
                os.write(buffer,0,b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败："+e.getMessage());
        }
    }

    @RequestMapping(path = "/modifyPassword",method = RequestMethod.POST)
    public String modifyPassword(String originalPassword, String newPassword, String ConfirmPassword, Model model, @CookieValue("ticket") String ticket){
        if(originalPassword==null){
            model.addAttribute("originalPasswordMsg:","请输入原密码");
            return "/site/setting";
        }
        if(newPassword==null){
            model.addAttribute("newPasswordMsg:","请输入新密码");
            return "/site/setting";
        }
        if(ConfirmPassword==null){
            model.addAttribute("ConfirmPasswordMsg:","请确认新密码");
            return "/site/setting";
        }

        User user = hostHolder.getUser();
        if(!CommunityUtil.MD5(originalPassword+user.getSalt()).equals(user.getPassword())){
            model.addAttribute("originalPasswordMsg：","密码错误");
            return "/site/setting";
        }

        if(!ConfirmPassword.equals(newPassword)){
            model.addAttribute("ConfirmPasswordMsg：","密码不一致");
            return "/site/setting";
        }
        userService.updatePassword(user.getId(),CommunityUtil.MD5(newPassword+user.getSalt()));
        userService.logout(ticket);
        return  "redirect:/login";
    }

    //个人主页
    @RequestMapping(path = "/profile/{userId}",method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId,Model model){
        User user = userService.findUserById(userId);
        if(user==null){
            throw new RuntimeException("该用户不存在");
        }
        //用户
        model.addAttribute("user",user);

        //点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount",likeCount);

        //关注的数量
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount",followeeCount);

        //粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount",followerCount);
        //是否已关注
        boolean hasFollowed = false;
        if(hostHolder.getUser()!=null){
            hasFollowed =  followService.hasFollowed(hostHolder.getUser().getId(),ENTITY_TYPE_USER,userId);
        }
        model.addAttribute("hasFollowed",hasFollowed);
        return  "/site/profile";

    }


}
