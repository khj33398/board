package kr.co.hk.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long bno;
    private String title;
    private String content;

    //작성자 정보
    private String writerEmail;
    private String writerName;

    //작성된 날짜와 수정된 날짜
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    //댓글의 수
    private int replyCount;

}
