package com.yupi.yuoj.judge.codesandbox.impl;

import com.yupi.yuoj.judge.codesandbox.CodeSandbox;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱（调用接口）
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCoderequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}
