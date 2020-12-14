package com.lx.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CoursePage {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课时数量")
    private String number;

    @ApiModelProperty(value = "课程创建时间")
    private String start;

    @ApiModelProperty(value = "课程结束时间")
    private String over;
}
