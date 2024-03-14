package com.wsl.model.llm.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 讯飞星火 聊天 参数 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
public class SparkDeskParameterChatDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "指定访问的领域", notes = "generalv3指向V3版本;generalv3.5指向V3.5版本")
    private String domain;

    @ApiModelProperty(value = "温度", notes = "较高的数值会使输出更加随机，而较低的数值会使其更加集中和确定。默认0.8，范围 [0, 2.0]，不能为0", example = "0.8")
    private Float temperature;

    @ApiModelProperty(value = "最大标记", notes = "模型回答的tokens的最大长度；取值范围[1,8192]，默认为2048", example = "2048")
    private Integer max_tokens;

}
