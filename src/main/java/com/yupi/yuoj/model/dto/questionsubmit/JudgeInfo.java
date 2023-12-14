package com.yupi.yuoj.model.dto.questionsubmit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* 判题信息
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JudgeInfo {

    /**
    *  程序执行信息
    */
    public String message;

    /**
    * 消耗内存
    */
    public Long memory;

    /**
    * 消耗时间（KB）
    */
    public Long time;
}
