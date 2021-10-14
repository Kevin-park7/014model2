package spring.model2.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewResolver {
		
	//Field
	
	//Constructor
	public ViewResolver() {
		// TODO Auto-generated constructor stub
	}
	
	//Method
	public void forward(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView)throws ServletException,IOException{
		System.out.println("[ViewResolver.forward() start.....]");
		if(modelAndView.getModelName() != null) {
			request.setAttribute(modelAndView.getModelName(), modelAndView.getModelObject());
		}
		request.getRequestDispatcher(modelAndView.getViewName()).forward(request, response);
		
		System.out.println("[ViewResolver.forward() end.....]");
	}

}
