package com.yupi.yuoj.judge;

import cn.hutool.json.JSONUtil;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.judge.codesandbox.CodeSandbox;
import com.yupi.yuoj.judge.codesandbox.CodeSandboxFactory;
import com.yupi.yuoj.judge.codesandbox.Proxy.CodeSandboxProxy;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.yuoj.model.dto.question.JudgeCase;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.QuestionSubmit;
import com.yupi.yuoj.model.enums.QuestionSubmitLanguageEnum;
import com.yupi.yuoj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.yuoj.model.vo.QuestionSubmitVO;
import com.yupi.yuoj.service.QuestionService;
import com.yupi.yuoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService{

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmitVO doJudge(long questionSubmitId) {
//      1传入题目提交的Id，获取到对应的题目、提交信息（包含代码和编程语言）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"提交信息不存在");
        }

        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question==null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        Integer status = questionSubmit.getStatus();
        if (!status.equals(QuestionSubmitStatusEnum.RUNNING)){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"正在判题中请勿重复提交");
        }
//      更新判题状态
        QuestionSubmit questionSubmit1 = new QuestionSubmit();
        questionSubmit1.setId(questionId);
        questionSubmit1.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmit1);
//      如果更新失败
        if (!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"判题状态更新错误");
        }
//      2调用沙箱获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newIntance(type);
        CodeSandboxProxy proxy = new CodeSandboxProxy(codeSandbox);
        String code="int main(){}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaselist = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaselist.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
//      todo 根据获取到的执行结果，返回前端所需要展示的判题状态和执行信息
        List<String> outputList = executeCodeResponse.getOutputList();

        Long id = questionSubmit.getId();
        String judgeInfo = questionSubmit.getJudgeInfo();

        Long userId = questionSubmit.getUserId();
        Date createTime = questionSubmit.getCreateTime();
        Date updateTime = questionSubmit.getUpdateTime();
//      todo 修改数据库中的判题结果



        return null;
    }
}
