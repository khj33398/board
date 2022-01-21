package kr.co.hk.board.repository;

import kr.co.hk.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    //테스트 용 메서드
    public Board search();

    //목록 보기를 위한 메서드
    Page<Object []> searchPage(String type, String keyword, Pageable pageable);



}
