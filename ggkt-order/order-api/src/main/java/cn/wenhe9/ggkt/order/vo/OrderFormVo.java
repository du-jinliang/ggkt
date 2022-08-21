package cn.wenhe9.ggkt.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author DuJinliang
 * 2022/08/21
 */

@Data
public class OrderFormVo  {

	@ApiModelProperty(value = "课程id")
	private Long courseId;

	@ApiModelProperty(value = "优惠券id")
	private Long couponId;

	@ApiModelProperty(value = "优惠券领取表id")
	private Long couponUseId;
}

