package cafe.ebs.springboard.controller;

//import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
/*import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;*/
import org.springframework.web.bind.annotation.RequestParam;

import cafe.ebs.springboard.service.BoardService;
import cafe.ebs.springboard.vo.Board;

@Controller
public class BoardController {
	
	@Autowired
    private BoardService boardService;
    
    // 리스트 요청
    //@RequestMapping(value="/boardList", method = RequestMethod.GET)
    
	
	@GetMapping(value="/boardList")
    public String boardList(Model model,
                            @RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage) {
    	//list 타임리프로 만들기
    	Map<String, Object> map = (Map<String, Object>)boardService.selectBoardList(currentPage);
    	//map타입 map에 selectBoardList에 currentPage를 입력값으로 한 리턴값(returnMap)을 저장한다
    	model.addAttribute("map",map);
    	//model클래스 이용 map객체에 map에 담긴 값을 저장
    	model.addAttribute("currentPage",currentPage);
    	//currentPage 객체에 입력값으로한 currentPage값을 저장한다
    	
    	
    	System.out.println(map+" map값");
    	
        return "boardList";
        //일반적으로 적으면 포워드, redirect적으면 리다이렉트
    }
	//상세보기
	//get으로 입력받은 boardDetail을 실행한다
	//수정화면과 같은 getboard메서드 사용한다
	@GetMapping(value="boardDetail")
    public String getBoardDetail(Model model, @RequestParam(value="boardNo")int boardNo) {
    	//Model타입 model과 boardNo를 입력값으로 받아 상세보기를 출력한다
    	Board board = boardService.getBoard(boardNo);
    	//입력값으로 getBoard메서드 이용하여 상세화면에 뿌릴 정보를 board에 저장한다
		model.addAttribute("board", board);
		//board객체에 상세화면 정보를 저장한다
		System.out.println("NO : "+boardNo+"  상세화면 출력");
    	return "boardDetail";
    	//정보를 가지고 boardDetail.html로 이동한다
    }
	
    // 입력페이지 요청
	//get으로 입력받은 boardAdd를 실행한다
    @GetMapping(value="/boardAdd")
    public String boardAdd() {
        System.out.println("boardAdd 폼 요청");
        return "boardAdd";
        //boardAdd.html로 리턴한다
    }
    
    // 입력(액션) 요청
    // post로 입력받은 boardAdd는 board를 입력받아 입력액션을 한다
	@PostMapping(value="/boardAdd")
    public String boardAdd(Board board) {
    	//커맨드 객체 활용 위해 -> 필드=name(input type) -> setter
        boardService.addBoard(board);
        //addBoard 메서드에 board 객체를 입력값으로 받아 입력메서드를 실행한다
    	return "redirect:/boardList"; // 글입력후 "/boardList"로 리다이렉트(재요청)
    }
    
    //수정화면
	//get으로 입력받은 modifyBoard는 수정화면을 출력한다
    @GetMapping(value="/modifyBoard")
    public String getBoardModify(Model model, @RequestParam(value="boardNo")int boardNo) {
    	//Model타입 model과 boardNo를 입력값으로 받아 수정화면을 출력한다
    	Board board = boardService.getBoard(boardNo);
    	//입력값으로 getBoard메서드 이용하여 수정화면에 뿌릴 정보를 board에 저장한다
		model.addAttribute("board", board);
		//board객체에 수정화면 정보를 저장한다
		System.out.println("NO : "+boardNo+"  수정화면 출력");
    	return "modifyBoard";
    	//정보를 가지고 modifyBoard.html로 이동한다
    }
    
    //수정액션
    //post로 입력받은 modifyBoard는 수정액션을 한다
	@PostMapping("/modifyBoard")
	public String modifyBoard(Board board) {
		//board를 입력값으로 하여 수정액션 하는데 이용한다
		System.out.println("post mapping modifyBoard");
		boardService.modifyBoard(board);
		//modifyBoard메서드에 입력값 board를 사용하여 수정액션을 한다
		System.out.println("수정한 board 정보 : "+board);
		return "redirect:/boardList";
		//수정 후 boardList로 경로 요청한다.
	}
	//삭제화면
	@GetMapping(value="/removeBoard")
    public String getBoardRemove(Model model, @RequestParam(value="boardNo")int boardNo) {
    	//Model타입 model과 boardNo를 입력값으로 받아 삭제화면을 출력한다
    	Board board = boardService.getBoard(boardNo);
    	//입력값으로 getBoard메서드 이용하여 삭제화면에 뿌릴 정보를 board에 저장한다
		model.addAttribute("board", board);
		//board객체에 삭제화면 정보를 저장한다
		System.out.println("NO : "+boardNo+"  삭제화면 출력");
    	return "removeBoard";
    	//정보를 가지고 removeBoard.html로 이동한다
	}
	
    //삭제액션
	//post으로 입력받은 removeBoard
    @PostMapping(value="/removeBoard")
    public String removeBoard(Board board) {
		System.out.println("get mapping removeSample");
		boardService.removeBoard(board);
		//리턴은 String 타입, 리스트로 이동
		System.out.println("삭제한 board 정보 : " + board);
		return "redirect:/boardList";
		//삭제 액션 후 다시 리스트로 이동시킨다
	}

}
