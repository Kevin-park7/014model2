package spring.model2.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spring.model2.service.user.dao.UserDao;
import spring.model2.service.user.vo.UserVO;


//@WebServlet("/ControlServlet")
public class ControlServlet extends HttpServlet {
	
   // @Override
	public void init(ServletConfig sc) throws ServletException {
		
		super.init(sc);
		//==>web.xml 설정::<load-on-startup>1</load-on-startup> 확인
		System.out.println("\n\n=========================");
		System.out.println("ControlServlet의 init() Method");
		System.out.println("\n\n=========================");
		
	}

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		System.out.println("\n[ ControlServlet.service() start ]");
		
		//==> Controller :: client 요구사항 판단 :: URL/URI 이용
		String actionPage = this.getURI(req.getRequestURI());
		System.out.println("::URI? =>:" +req.getRequestURI());
		System.out.println("::client의 요구사항은? => : " + actionPage);
		
		/// => CONTROLLER :: 선처리/공통사항이 있다면
		//=> 본 예제: 한글처리 / SESSION 관리, 처리 / 선, 공통처리
		req.setCharacterEncoding("euc-kr");
		HttpSession session = req.getSession(true);
		
		/// => CONTROLLER :: navigation (forward/sendRedirect view page 결정)
		// navigation 디폴트 페이지 지정
		String requestPage = "/user/logon.jsp";
		
		/// => CONTROLLER :: 권한/ 인증처리
		//==> session ObjectScope 저장된 UserVO 객체 이용 인증
		//==> 경우 1 : session ObjectScope 에 userVO 인스턴스 생성 및 저장
		if (session.isNew()||session.getAttribute("userVO")==null) {
			session.setAttribute("userVO", new UserVO());
			
		}
		//==> 경우 2 : session ObjectScope userVO 추출
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		
		//==> userVO.active 이용 로그인 유무 판단
		if (userVO !=null && userVO.isActive()) {
			requestPage = "/user/home.jsp";
		}
			
		//==> CONTROLLER :: Client 요구사항 처리 Business layer 접근
		
		//1. logon.do 경우 :: Business ~~
		
		else if(actionPage.equals("logon")) {
		}
		
		else if(actionPage.equals("logonAction")) {
			
			String userId = req.getParameter("userId");
			String userPasswd = req.getParameter("userPasswd");
			
			userVO.setUserId(userId);
			userVO.setUserPasswd(userPasswd);
			
			UserDao userDao = new UserDao();
			userDao.getUser(userVO);
			
			if (userVO.isActive()) {
				requestPage = "/user/home.jsp";
				
			}
		}
		
		else if(actionPage.equals("home")) {
			
		}
		
		System.out.println(":: 최종 결정된 View page는 : [["+requestPage+".jsp]]");
		
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher(requestPage);
		rd.forward(req, res);
		
		System.out.println("[ ControlServlet.service() end ]");
	} // end of service
	
	private String getURI(String requestURI) {
		int start = requestURI.lastIndexOf('/')+1;
		int end = requestURI.lastIndexOf(".do");
		String actionPage = requestURI.substring(start,end);
		return actionPage;
	}

}// end of class
