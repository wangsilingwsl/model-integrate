package com.wsl.model.llm.api.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通义法睿 请求 DTO
 *
 * @author wsl
 * @date 2024/2/20
 */
@Data
@ApiModel(description = "通义法睿 请求 DTO")
public class FaRuiDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "输入", notes = "输入")
    private FaRuiInputDTO input;

    @ApiModelProperty(value = "系统参数", notes = "和通义千问的系统参数一致")
    private JSONObject parameters;

    @ApiModelProperty(value = "应用ID")
    private String appId;

    @ApiModelProperty(value = "应用标识")
    private String appKey;

    @ApiModelProperty(value = "时间戳", notes = "毫秒数", example = "1710208688")
    private String timestamp;

    @ApiModelProperty(value = "随机数")
    private String random;

    @ApiModelProperty(value = "签名", notes = "MD5(appId+appKey+timestamp+random+secret)，其中secret由服务提供者分配")
    private String signature;

    @ApiModelProperty(value = "业务ID", notes = "业务请求的唯⼀性ID，相同的ID可以查询到最新的⽣成结果")
    private String bizId;

}
