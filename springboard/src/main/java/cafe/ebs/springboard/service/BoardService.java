package cafe.ebs.springboard.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cafe.ebs.springboard.mapper.BoardMapper;
import cafe.ebs.springboard.vo.Board;
import cafe.ebs.springboard.vo.BoardFile;
import cafe.ebs.springboard.vo.BoardRequest;

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
	
	public void addBoard(BoardRequest boardRequest, String path) {
		
		
		/*
		 * 1. BoardRequest를 분리한다 : board,file,file정보
		 * 2. board -> boardVo
		 * 3. file 정보 -> boardFileVo
		 * 4. file -> path를 이용해 물리적 장치에 저장(하드디스크) *경로(path)가 필요하다
		 * 3. file형태로 저장한다
		 * Map map; boardMapper.insertBoard(board);
		 * boardFileMapper.isnertBoard(boardFile);
		 * 
		 * return map;
		 */
		//1
		Board board = new Board();
		board.setBoardNo(boardRequest.getBoardNo());
		board.setBoardPw(boardRequest.getBoardPw());
		board.setBoardTitle(boardRequest.getBoardTitle());
		board.setBoardContent(boardRequest.getBoardContent());
		board.setBoardUser(boardRequest.getBoardUser());
		board.setBoardDate(boardRequest.getBoardDate());
		boardMapper.insertBoard(board); //cafe 1280번 글(mybatis에서 자동생성 키 값 받아오기)
		//2
		
		
		List<MultipartFile> files = boardRequest.getFiles();
		for(MultipartFile f: files) {
			BoardFile boardFile = new BoardFile();
			boardFile.setBoardNo(board.getBoardNo());
			boardFile.setFileSize(f.getSize());
			boardFile.setFileType(f.getContentType());
			String originalFileName = f.getOriginalFilename();
			int i = originalFileName.lastIndexOf(".");
			String ext = originalFileName.substring(i+1);//확장자 자르기
			System.out.println("확장자 : "+ext);
			boardFile.setFileExt("ext");
			String fileName = UUID.randomUUID().toString();//파일이름 중복되지 않게 하려고 random 문자 toString으로 저장(문자열)
			boardFile.setFileName("fileName");
			//전체 작업이 롤백되면 파일삭제작업 직접!
			
			//3 파일 저장
			//
			try {
				f.transferTo(new File(path+"/"+fileName+"."+ext));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	public int removeBoard(Board board) {
		return boardMapper.deleteBoard(board);

	}
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);

	}
}
