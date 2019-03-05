package cafe.ebs.springboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cafe.ebs.springboard.vo.Board;
import cafe.ebs.springboard.vo.BoardFile;

@Mapper
public interface BoardFileMapper {
	void boardfileInsert(BoardFile boardfile);
	
	// 파일 List를 SELECT하는 쿼리문을 호출하는 메서드 선언
	List<BoardFile> boardfileSelect(Board board);
	
}