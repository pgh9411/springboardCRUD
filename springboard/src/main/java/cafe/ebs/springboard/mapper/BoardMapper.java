package cafe.ebs.springboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cafe.ebs.springboard.vo.Board;
@Mapper
public interface BoardMapper {
	//1.수정할때,읽기(상세정보)
	Board selectBoard(int boardNo);
	//2.목록(페이징 되어있다)
	List<Board> selectBoardList(Map<String, Integer> map);
	//Integer는 int의 wrapper타입
	//Map으로 묶기(리절트,파라미터 하니씩만 받기 때문에)
	
	
	//3.전체글자 수(페이징 위해)
	int selectBoardCount();
	//4.추가 액션
	int insertBoard(Board board);
	//5.삭제 액션
	int deleteBoard(Board board);
	//6.수정 액션
	int updateBoard(Board board);




	




}
