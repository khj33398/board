package kr.co.hk.board.repository;

import kr.co.hk.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository{
    //Member와 Board Entity의 JOIN을 수행하는 메서드를 생성
    //Board Entity에는 Member Entity와 연관 관계를 갖는 writer가 존재

    //bno에 해당하는 Board를 가져올 때 Member에 대한 정보도 가져오기
    @Query("select b, w from Board b left join b.writer w where b.bno= :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);
    
    //Reply와 Board Entity의 JOIN을 수행하는 메서드를 생성
    //Board Entity에서는 Reply Entity와 연관 관계를 가지고 있지 않음
    //양쪽의 공통된 속성을 찾아야 함
    //Reply가 Board의 정보를 board라는 속성으로 가지고 있음

    //bno에 해당하는 Board를 가져올 때 Reply에 대한 정보도 가져오기
    @Query("select b, r from Board b left join Reply r On r.board=b where b.bno = :bno")
    //하나의 게시글에 댓글이 여러 개 있을 수 있어서 리턴 타입은 List
    List<Object []> getBoardWithReply(@Param("bno") Long bno);

    //목록 보기를 위한 메서드
    //JPQL에서는 Page 단위로 리턴할 때 countQuery가 필수
    @Query(value="select b, w, count(r) "+
            "from Board b left join b.writer w left join Reply r on r.board = b "+
            "GROUP BY b", countQuery = "select count(b) from Board b")
    Page<Object []> getBoardWithReplyCount(Pageable pageable);

    //게시글 상세보기를 위한 메서드
    @Query("select b, w, count(r) from Board b " +
            "left join b.writer w left outer join Reply r on r.board=b " +
            "where b.bno=:bno")
    Object getBoardByBno(@Param("bno") Long bno);

}
