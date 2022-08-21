package cn.wenhe9.ggkt.order;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author DuJinliang
 * 2022/08/21
 */

@Getter
public enum PaymentType {
    ALIPAY(1,"支付宝"),
    WEIXIN(2,"微信" );

    @EnumValue
    private Integer code ;
    private String comment ;

    PaymentType(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }

}
