package cn.wenhe9.ggkt.live.vo;

import cn.wenhe9.ggkt.live.entity.LiveCourseConfig;
import cn.wenhe9.ggkt.live.entity.LiveCourseGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "LiveCourseConfig")
public class LiveCourseConfigVo extends LiveCourseConfig {

	@ApiModelProperty(value = "商品列表")
	private List<LiveCourseGoods> liveCourseGoodsList;
}