package spring.model2.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spring.model2.service.user.dao.UserDao;
import spring.model2.service.user.vo.UserVO;

//@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		System.out.println("\n[ DispatcherServlet.service() start ]");
		
		String actionPage = this.getURI(req.getRequestURI());
		System.out.println("::URI? =>:" +req.getRequestURI());
		System.out.println("::client의 요구사항은? => : " + actionPage);
		
		/// => CONTROLLER :: 선처리/공통사항이 있다면
		//=> 본 예제: 한글처리 / SESSION 관리, 처리 / 선, 공통처리
		req.setCharacterEncoding("euc-kr");
		
		/// => CONTROLLER :: client 요구사항 처리 및 Business logic 처리
		Controller controller = null;
		
		ControllerMapping cf = ControllerMapping.getInstance();
		controller = cf.getController(actionPage);
		
		controller.execute(req,res);
		//===========================================>
		//controller.execute(req,res);
		ModelAndView modelAndView = controller.execute(req, res);
		new ViewResolver().forward(req,res,modelAndView);
		
		System.out.println("[ DispatcherServlet.service() end.... ]");
		
		
	} // end of service
	
	private String getURI(String requestURI) {
		int start = requestURI.lastIndexOf('/')+1;
		int end = requestURI.lastIndexOf(".do");
		String actionPage = requestURI.substring(start,end);
		return actionPage;
	}

}// end of class