package cn.wenhe9.ggkt.user.service.impl;

import cn.wenhe9.ggkt.user.entity.UserInfo;
import cn.wenhe9.ggkt.user.mapper.UserInfoMapper;
import cn.wenhe9.ggkt.user.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-21
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public UserInfo getUserInfoByOpenId(String openId) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getOpenId, openId);
        return this.getOne(queryWrapper);
    }
}
