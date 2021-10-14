package spring.model2.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spring.model2.service.user.dao.UserDao;
import spring.model2.service.user.vo.UserVO;

public class HomeController implements Controller {


	//@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		System.out.println("[HomeController.getController() start]");
		
		HttpSession session = req.getSession(true);
		
		if (session.isNew()||session.getAttribute("userVO")==null) {
			session.setAttribute("userVO", new UserVO());
			
		}
		
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		
		String requestPage = "/user/logon.jsp";
		
		if (userVO.isActive()) {
			
			requestPage = "/user/home.jsp";
		}
		else {
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
		
		//RequestDispatcher rd = req.getRequestDispatcher(requestPage);
		//rd.forward(req, res);
		
		System.out.println("[HomeController.getController() end]");
			
		return new ModelAndView(requestPage,"info","[LogonActionController Message] :: welcome");
		
		}
}// end of class