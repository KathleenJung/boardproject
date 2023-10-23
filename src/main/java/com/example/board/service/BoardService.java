package com.example.board.service;

import com.example.board.domain.Board;

import java.util.List;

public interface BoardService {

    List<Board> getAllBoards();

    Board getBoardById(Long id);

    Board createBoard(Board board);

    Board updateBoard(Long id, Board board);

    void deleteBoard(Long id);
}