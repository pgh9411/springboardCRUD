package cafe.ebs.springboard.service;

import java.util.HashMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cafe.ebs.springboard.mapper.BoardMapper;
import cafe.ebs.springboard.vo.Board;

@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	
	public Board getBoard(int boardNo) {
		return boardMapper.selectBoard(boardNo);
	}
	
	
	public Map<String,Object> selectBoardList(int currentPage){
		// 1.
		final int ROW_PER_PAGE = 10;
		//페이지당 행 갯수 10
		Map<String, Integer> map 
							= new HashMap<String, Integer>();
		int startPage = (currentPage-1)*10;
		//startPage를이용 2페이지에는 startPage가 10, 3페이지에는 20부터 시작하게 만든다 
		map.put("currentPage", currentPage);
		map.put("rowPerPage", ROW_PER_PAGE);
		map.put("startPage", startPage);
		//map에 각 값을 저장해준다
		
		// 2.
		int boardCount = boardMapper.selectBoardCount();
		//boardCouunt객체에 selectBoardCount이용 총 데이터 갯수를 넣는다
		int lastPage = (int)(Math.ceil(boardCount/ROW_PER_PAGE));
		//lastPage계산은 총 데이터 갯수를 페이지당 행수(10)으로 나눈 값이다
		/* map.put("lastPage", lastPage); */
		Map<String, Object> returnMap = new HashMap<String, Object>();
		//returnMap을 선언,생성해준다
		returnMap.put("list", boardMapper.selectBoardList(map));
		returnMap.put("boardCount",boardCount);
		returnMap.put("lastPage",lastPage);
		//returnMap에 각 값을 넣어준다, list객체에는 selectBoardList에 map을 넣은 리턴값을 넣는다
		System.out.println("lastPage---------------"+lastPage);
		System.out.println("boardCount-----------"+boardCount);
		/* returnMap.put("currentPage", currentPage); */
		return returnMap;
	}
	
	public int getBoardCount() {
		return boardMapper.selectBoardCount();
	}
	
	public int addBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
	public int removeBoard(Board board) {
		return boardMapper.deleteBoard(board);

	}
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);

	}
}
