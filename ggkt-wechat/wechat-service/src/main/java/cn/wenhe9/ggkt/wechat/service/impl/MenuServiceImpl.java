package cn.wenhe9.ggkt.wechat.service.impl;

import cn.wenhe9.ggkt.common.exception.GgktException;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.wechat.entity.Menu;
import cn.wenhe9.ggkt.wechat.mapper.MenuMapper;
import cn.wenhe9.ggkt.wechat.service.MenuService;
import cn.wenhe9.ggkt.wechat.utils.ConstantWechatProperties;
import cn.wenhe9.ggkt.wechat.vo.MenuVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.google.gson.annotations.JsonAdapter;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单明细 订单明细 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-22
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private WxMpService wxMpService;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public List<Menu> findMenuOneInfo() {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getParentId, 0);
        return this.list(queryWrapper);
    }

    @Override
    public List<MenuVo> findMenuInfo() {
        // 创建list集合，用户最终封装数据
        List<MenuVo> finalMenuList = new ArrayList<>();

        // 查询所有菜单数据，包含一级、二级
        List<Menu> menuList = this.list();

        //从所有菜单数据中获取所有一级菜单数据 parent_id = 0, 封装成 menuVo
        List<MenuVo> menuVoOneList =
                menuList.stream()
                        .filter(item -> item.getParentId() == 0)
                        .map(item -> {
                            MenuVo menuVoOne = new MenuVo();
                            BeanUtils.copyProperties(item, menuVoOne);
                            return menuVoOne;
                        })
                        .collect(Collectors.toList());

        // 封装二级菜单数据，判断一级菜单和二级菜单 id = parent_id 是否相同
        for (MenuVo menuVo : menuVoOneList) {
            List<MenuVo> menuTwoList =
                    menuList.stream()
                            .filter(item -> item.getParentId().equals(menuVo.getId()))
                            .map(item -> {
                                MenuVo menuVoTwo = new MenuVo();
                                BeanUtils.copyProperties(item, menuVoTwo);
                                return menuVoTwo;
                            })
                            .collect(Collectors.toList());

            // 把二级菜单放到一级菜单里面
            menuVo.setChildren(menuTwoList);

            // 把一级菜单放到最终list集合中
            finalMenuList.add(menuVo);
        }

        return finalMenuList;
    }

    @Override
    public String getAccessToken() {
        //拼接请求地址
        StringBuffer buffer = new StringBuffer();
        buffer.append("https://api.weixin.qq.com/cgi-bin/token");
        buffer.append("?grant_type=client_credential");
        buffer.append("&appid=%s");
        buffer.append("&secret=%s");
        //设置路径参数
        String url = String.format(buffer.toString(), ConstantWechatProperties.APP_ID, ConstantWechatProperties.APP_SECRET);
        try {
            //get请求
            String tokenString = restTemplate.getForObject(url, String.class);
            //获取access_token
            JSONObject jsonObject = JSON.parseObject(tokenString);

            return jsonObject.getString("access_token");
        }catch (Exception e) {
            e.printStackTrace();
            throw new GgktException(ResultResponseEnum.GET_ACCESS_TOKEN_ERROR);
        }
    }

    @Override
    public void syncMenu() {
        // 获取所有菜单数据
        List<MenuVo> menuVoList = this.findMenuInfo();
        // 封装button里面结构，数组格式
        JSONArray buttonList = new JSONArray();
        for (MenuVo menuOneVo : menuVoList) {
            // json对象 一级菜单
            JSONObject menuOne = new JSONObject();
            menuOne.put("name", menuOneVo.getName());

            // json数组 二级菜单
            JSONArray subButton = new JSONArray();
            for (MenuVo menuTwoVo : menuOneVo.getChildren()) {
                JSONObject view = new JSONObject();
                view.put("type", menuTwoVo.getType());
                if("view".equals(menuTwoVo.getType())) {
                    view.put("name", menuTwoVo.getName());
                    view.put("url", "http://ggkt2.vipgz1.91tunnel.com/#"
                            +menuTwoVo.getUrl());
                } else {
                    view.put("name", menuTwoVo.getName());
                    view.put("key", menuTwoVo.getMeunKey());
                }
                subButton.add(view);
            }
            menuOne.put("sub_button", subButton);
            buttonList.add(menuOne);
        }
        //封装最外层button部分
        JSONObject button = new JSONObject();
        button.put("button", buttonList);

        try {
            wxMpService.getMenuService().menuCreate(button.toJSONString());
        } catch (WxErrorException e) {
            e.printStackTrace();
            throw new GgktException(ResultResponseEnum.SYNC_MENU_ERROR);
        }
    }
}
