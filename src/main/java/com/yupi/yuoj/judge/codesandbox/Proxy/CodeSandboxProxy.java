package com.yupi.yuoj.judge.codesandbox.Proxy;

import com.yupi.yuoj.judge.codesandbox.CodeSandbox;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandbox{

    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox){
        this.codeSandbox=codeSandbox;
    }


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCoderequest) {
        log.info("输入代码信息"+executeCoderequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCoderequest);
        log.info("执行代码信息"+executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
