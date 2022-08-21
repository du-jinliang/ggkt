package cn.wenhe9.ggkt.user.service.impl;

import cn.wenhe9.ggkt.user.entity.UserInfo;
import cn.wenhe9.ggkt.user.mapper.UserInfoMapper;
import cn.wenhe9.ggkt.user.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
