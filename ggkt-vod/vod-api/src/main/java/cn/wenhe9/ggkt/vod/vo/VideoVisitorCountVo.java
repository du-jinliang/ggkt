package cn.wenhe9.ggkt.vod.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author DuJinliang
 * 2022/08/16
 */

@Data
public class VideoVisitorCountVo {

	@ApiModelProperty(value = "进入时间")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date joinTime;

	@ApiModelProperty(value = "用户个数")
	private Integer userCount;


}

