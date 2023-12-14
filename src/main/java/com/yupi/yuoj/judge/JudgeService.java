package com.yupi.yuoj.judge;

import com.yupi.yuoj.model.vo.QuestionSubmitVO;

public interface JudgeService {

    public QuestionSubmitVO doJudge(long questionSubmitId);
}
