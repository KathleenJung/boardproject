package com.example.board.controller;

import com.example.board.dto.MemberJoinDto;
import com.example.board.service.RegisterMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "회원 API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RegisterMemberService registerMemberService;

    @Operation(summary = "회원 가입", description = "회원 가입을 합니다.")
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberJoinDto memberJoinDto) {
        try {
            registerMemberService.join(memberJoinDto.getUserid(), memberJoinDto.getPw());
            return ResponseEntity.ok(memberJoinDto.getUserid() + "님, 회원가입이 완료 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
