package cn.wenhe9.ggkt.vod.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author DuJinliang
 * 2022/08/16
 */

@Data
public class TeacherQueryVo {
	
	@ApiModelProperty(value = "讲师姓名")
	private String name;

	@ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
	private Integer level;

	@ApiModelProperty(value = "入驻时间")
	private String joinDateBegin;

	@ApiModelProperty(value = "入驻时间")
	private String joinDateEnd;


}

