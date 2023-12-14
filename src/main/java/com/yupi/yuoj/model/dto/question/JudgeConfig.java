package com.yupi.yuoj.model.dto.question;

import lombok.Data;

@Data
public class JudgeConfig {

    /**
    *  时间限制（毫秒）
    */
    public Long timeLimit;

    /**
    * 内存限制（KB）
    */
    public Long memoryLimit;

    /**
    * 堆栈限制（KB）
    */
    public Long stackLimit;
}
