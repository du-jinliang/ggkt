package cn.wenhe9.ggkt.user.controller.api;

import cn.wenhe9.ggkt.common.utils.JwtHelper;
import cn.wenhe9.ggkt.user.entity.UserInfo;
import cn.wenhe9.ggkt.user.service.UserInfoService;
import cn.wenhe9.ggkt.user.utils.ConstantWeChatPropertiesUtils;
import io.swagger.annotations.Api;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * @author DuJinliang
 * 2022/08/24
 */
@Api(tags = "微信授权管理接口")
@Controller
@RequestMapping("/api/user/wechat")
public class WeChatApiController {
    @Resource
    private UserInfoService userInfoService;

    @Resource
    private WxMpService wxMpService;

    /**
     * 授权跳转
     */
    @GetMapping("/authorize")
    public String authorize(String returnUrl, HttpServletRequest request) {
        String url = wxMpService.oauth2buildAuthorizationUrl(
                ConstantWeChatPropertiesUtils.USER_INFO_URL,
                WxConsts.OAuth2Scope.SNSAPI_USERINFO,
                URLEncoder.encode(returnUrl.replace("guiguketang", "#")));
        return "redirect:" + url;
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/userInfo")
    public String userInfo(
            @RequestParam("code") String code,
            @RequestParam("state") String returnUrl
    ) {
        try {
            // 拿着code发送请求
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

            // 获取openId
            String openId = wxMpOAuth2AccessToken.getOpenId();

            // 获取微信信息
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);

            // 获取微信信息添加到数据库
            UserInfo userInfo =  userInfoService.getUserInfoByOpenId(openId);

            if (null == userInfo) {
                userInfo = new UserInfo();
                userInfo.setNickName(wxMpUser.getNickname());
                userInfo.setSex(wxMpUser.getSex());
                userInfo.setAvatar(wxMpUser.getHeadImgUrl());
                userInfo.setProvince(wxMpUser.getProvince());
                userInfo.setOpenId(wxMpUser.getOpenId());

                userInfoService.save(userInfo);
            }

            //授权完成后，跳转到具体的功能页面
            //生成token
            String token = JwtHelper.createToken(userInfo.getId(), userInfo.getNickName());
            if (!returnUrl.contains("?")) {
                return "redirect:" + returnUrl + "?token=" + token;
            }
            return "redirect:" + returnUrl + "&token=" + token;
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }
}
