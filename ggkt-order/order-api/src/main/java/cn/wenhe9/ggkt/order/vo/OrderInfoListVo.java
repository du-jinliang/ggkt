package cn.wenhe9.ggkt.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author DuJinliang
 * 2022/08/21
 */
@Data
@ApiModel(description = "OrderInfoDto")
public class OrderInfoListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    private String phone;

    @ApiModelProperty(value = "最终金额")
    private BigDecimal finalAmount;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "订单状态")
    private String orderStatus;

    @ApiModelProperty(value = "订单交易编号（第三方支付用)")
    private String outTradeNo;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
}
