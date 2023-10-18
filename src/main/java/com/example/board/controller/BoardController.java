package com.example.board.controller;

import com.example.board.model.Board;
import com.example.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Board API", description = "게시판 API")
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Operation(summary = "모든 게시물 조회", description = "게시판에 있는 모든 게시물을 조회합니다.")
    @GetMapping
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @Operation(summary = "특정 게시물 조회", description = "게시판에서 특정 ID의 게시물을 조회합니다.")
    @GetMapping("/{id}")
    public Board getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @Operation(summary = "게시물 작성", description = "게시판에 새로운 게시물을 작성합니다.")
    @PostMapping
    public Board createBoard(@RequestBody Board board) {
        return boardService.createBoard(board);
    }

    @Operation(summary = "게시물 수정", description = "게시판에서 특정 ID의 게시물을 수정합니다.")
    @PutMapping("/{id}")
    public Board updateBoard(@PathVariable Long id, @RequestBody Board board) {
        return boardService.updateBoard(id, board);
    }

    @Operation(summary = "게시물 삭제", description = "게시판에서 특정 ID의 게시물을 삭제합니다.")
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
    }
}
