package kr.or.ddit.annotation.dummy;

import kr.or.ddit.annotation.Controller;
import kr.or.ddit.annotation.RequestMapping;
import kr.or.ddit.vo.MemberVO;

@Controller
public class DummyCommandHandler {
	
	private String dummy() {
		return null;
	}
	
	@RequestMapping("/testInsert")
	public String form() {
		return "test/form";
	}
	
	@RequestMapping(value="/testInsert", method="post")
	public String process() {
		
		return "redirect:/testInsert";
	}
}
