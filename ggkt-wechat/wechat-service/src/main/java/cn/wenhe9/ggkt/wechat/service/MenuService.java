package cn.wenhe9.ggkt.wechat.service;

import cn.wenhe9.ggkt.wechat.entity.Menu;
import cn.wenhe9.ggkt.wechat.vo.MenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-22
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取所有一级菜单
     */
    List<Menu> findMenuOneInfo();

    /**
     * 获取所有菜单，按照一级和二级菜单封装
     */
    List<MenuVo> findMenuInfo();

    /**
     * 获取accessToken
     */
    String getAccessToken();

    /**
     * 同步菜单
     */
    void syncMenu();
}
