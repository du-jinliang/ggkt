package cn.wenhe9.ggkt.wechat.controller;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.common.utils.AuthContextHolder;
import cn.wenhe9.ggkt.wechat.utils.Base64Util;
import cn.wenhe9.ggkt.wechat.vo.WxJsapiSignatureVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author DuJinliang
 * 2022/09/07
 */

@Slf4j
@RestController
@Api(tags = "微信分享接口")
@RequestMapping("/api/wechat/share")
public class ShareController {
    @Resource
    private WxMpService wxMpService;

    @GetMapping("/signature")
    public ResultResponse<WxJsapiSignatureVo> getSignature(@RequestParam("url") String url) throws WxErrorException {
        String currentUrl = url.replace("guiguketan", "#");
        WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(currentUrl);

        WxJsapiSignatureVo wxJsapiSignatureVo = new WxJsapiSignatureVo();
        BeanUtils.copyProperties(jsapiSignature, wxJsapiSignatureVo);
        wxJsapiSignatureVo.setUserEedId(Base64Util.base64Encode(AuthContextHolder.getUserId()+""));
        return ResultResponse.success(wxJsapiSignatureVo);
    }
}
