package com.example.board.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Board API", description = "게시판 API 목록입니다.", version = "1.0"))
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

}