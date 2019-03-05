package cafe.ebs.springboard.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogingController {
	@GetMapping("login")
	
	public String login() {
		return "login";
	}
	
	@PostMapping("login")
	public String login(HttpSession session, @RequestParam("id") String id, @RequestParam("pw") String pw) {
		//세션값에 서블릿 리퀘스트가져오기, 리퀘스트를 받는지? 세션을 받는지? 차이있다
		//db에서 받아오기
		
		final String dbId = "admin";
		final String dbPw = "1234";
		
		//같은것을 비교할지 같지않은 것을 비교할지 코드가읽기 편한쪽으로 선택하자
		if(id.equals(dbId) && pw.equals(dbPw)) {
			//로그인 성공 -> 로그인 정보를 세션 등록 후 -> .....
			session.setAttribute("id", id);
			return "redirect:/index";

		}else {
			//로그인 실패 -> 로그인 폼으로 리다이렉트(이동)
			return "redirect:/login";

		}
	}
}
