package cn.wenhe9.ggkt.vod.controller;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理员登陆接口
 * @author DuJinliang
 * 2022/08/16
 */
@CrossOrigin
@RestController
@Api(tags = "管理员登陆接口")
@RequestMapping("/admin/vod/user")
public class UserLoginController {
    /**
     * 管理员登陆
     */
    @ApiOperation("管理员登陆")
    @PostMapping("/login")
    public ResultResponse<Map<String, Object>> login() {
        //{"code":20000,"data":{"token":"admin-token"}}
        Map<String, Object> map = new HashMap<>();
        map.put("token", "admin-token");
        return ResultResponse.success(map);
    }

    /**
     * 获取管理员信息
     */
    @ApiOperation("获取管理员信息")
    @GetMapping("/info")
    public ResultResponse info() {
        //{"code":20000,"data":
        // {"roles":["admin"],
        // "introduction":"I am a super administrator",
        // "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
        // "name":"Super Admin"}}
        Map<String,Object> map = new HashMap<>();
        map.put("roles","admin");
        map.put("introduction","I am a super administrator");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name","Super Admin");
        return ResultResponse.success(map);
    }
}
