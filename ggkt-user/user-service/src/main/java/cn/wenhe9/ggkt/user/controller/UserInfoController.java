package cn.wenhe9.ggkt.user.controller;


import cn.wenhe9.ggkt.user.entity.UserInfo;
import cn.wenhe9.ggkt.user.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-21
 */
@Api(tags = "用户信息管理接口")
@RestController
@RequestMapping("/admin/user/userInfo")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    /**
     * 根据id获取用户信息
     */
    @ApiOperation("根据id获取用户信息")
    @GetMapping("/inner/{id}")
    public UserInfo getUserInfoById(@PathVariable(name = "id") long id) {
        return userInfoService.getById(id);
    }
}

