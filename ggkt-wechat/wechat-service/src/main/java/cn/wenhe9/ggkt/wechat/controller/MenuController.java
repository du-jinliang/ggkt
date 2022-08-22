package cn.wenhe9.ggkt.wechat.controller;


import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.wechat.entity.Menu;
import cn.wenhe9.ggkt.wechat.service.MenuService;
import cn.wenhe9.ggkt.wechat.utils.ConstantWechatProperties;
import cn.wenhe9.ggkt.wechat.vo.MenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-22
 */
@Api(tags = "公众号目录管理接口")
@RestController
@RequestMapping("/admin/wechat/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    /**
     * 同步菜单
     */
    @ApiOperation("同步菜单")
    @GetMapping("/sync")
    public ResultResponse<Void> syncMenu() {
        menuService.syncMenu();
        return ResultResponse.success();
    }

    /**
     * 获取accessToken
     */
    @ApiOperation("获取accessToken")
    @GetMapping("/accessToken")
    public ResultResponse<String> getAccessToken() {
        String accessToken = menuService.getAccessToken();
        return ResultResponse.success(accessToken);
    }

    /**
     * 获取所有菜单，按照一级和二级菜单封装
     */
    @ApiOperation("获取所有菜单，按照一级和二级菜单封装")
    @GetMapping("/info")
    public ResultResponse<List<MenuVo>> findMenuInfo() {
        List<MenuVo> menuVoList = menuService.findMenuInfo();
        return ResultResponse.success(menuVoList);
    }

    /**
     * 获取所有一级菜单
     */
    @ApiOperation("获取所有一级菜单")
    @GetMapping("/lv1/info")
    public ResultResponse<List<Menu>> findMenuOneInfo() {
        List<Menu> menuList = menuService.findMenuOneInfo();
        return ResultResponse.success(menuList);
    }

    /**
     * 根据id获取目录
     */
    @ApiOperation(value = "根据id获取目录")
    @GetMapping("/{id}")
    public ResultResponse<Menu> getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        return ResultResponse.success(menu);
    }

    /**
     * 新增目录
     */
    @ApiOperation(value = "新增目录")
    @PostMapping
    public ResultResponse<Void> saveMenu(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResultResponse.success(null);
    }

    /**
     * 根据id修改目录
     */
    @ApiOperation(value = "根据id修改目录")
    @PutMapping
    public ResultResponse<Void> updateMenuById(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return ResultResponse.success(null);
    }

    /**
     * 根据删除目录
     */
    @ApiOperation(value = "根据删除目录")
    @DeleteMapping("/{id}")
    public ResultResponse<Void> removeMenuById(@PathVariable Long id) {
        menuService.removeById(id);
        return ResultResponse.success(null);
    }

    /**
     * 根据id列表批量删除目录
     */
    @ApiOperation(value = "根据id列表批量删除目录")
    @DeleteMapping("/batch")
    public ResultResponse<Void> batchRemoveMenu(@RequestBody List<Long> idList) {
        menuService.removeByIds(idList);
        return ResultResponse.success(null);
    }
}

