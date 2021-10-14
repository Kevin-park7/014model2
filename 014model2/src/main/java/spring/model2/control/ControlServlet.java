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
		//==>web.xml ����::<load-on-startup>1</load-on-startup> Ȯ��
		System.out.println("\n\n=========================");
		System.out.println("ControlServlet�� init() Method");
		System.out.println("\n\n=========================");
		
	}

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		System.out.println("\n[ ControlServlet.service() start ]");
		
		//==> Controller :: client �䱸���� �Ǵ� :: URL/URI �̿�
		String actionPage = this.getURI(req.getRequestURI());
		System.out.println("::URI? =>:" +req.getRequestURI());
		System.out.println("::client�� �䱸������? => : " + actionPage);
		
		/// => CONTROLLER :: ��ó��/��������� �ִٸ�
		//=> �� ����: �ѱ�ó�� / SESSION ����, ó�� / ��, ����ó��
		req.setCharacterEncoding("euc-kr");
		HttpSession session = req.getSession(true);
		
		/// => CONTROLLER :: navigation (forward/sendRedirect view page ����)
		// navigation ����Ʈ ������ ����
		String requestPage = "/user/logon.jsp";
		
		/// => CONTROLLER :: ����/ ����ó��
		//==> session ObjectScope ����� UserVO ��ü �̿� ����
		//==> ��� 1 : session ObjectScope �� userVO �ν��Ͻ� ���� �� ����
		if (session.isNew()||session.getAttribute("userVO")==null) {
			session.setAttribute("userVO", new UserVO());
			
		}
		//==> ��� 2 : session ObjectScope userVO ����
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		
		//==> userVO.active �̿� �α��� ���� �Ǵ�
		if (userVO !=null && userVO.isActive()) {
			requestPage = "/user/home.jsp";
		}
			
		//==> CONTROLLER :: Client �䱸���� ó�� Business layer ����
		
		//1. logon.do ��� :: Business ~~
		
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
		
		System.out.println(":: ���� ������ View page�� : [["+requestPage+".jsp]]");
		
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