package com.yupi.yuoj.judge.codesandbox;

import com.yupi.yuoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.yuoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodeSandboxTest {

    @Value("${codesandbox.type:example}")


    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String type = scanner.next();
                CodeSandbox codeSandbox = CodeSandboxFactory.newIntance(type);
                String code="int main(){ }";
                String language = QuestionSubmitLanguageEnum.JAVA.getValue();
                List<String> inputList = Arrays.asList("1 2", "3 4");
                ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                        .code(code)
                        .language(language)
                        .inputList(inputList)
                        .build();
                ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(request);
            }
        }
}
