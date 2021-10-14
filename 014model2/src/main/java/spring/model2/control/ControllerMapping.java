package spring.model2.control;

public class ControllerMapping {
	
	private static ControllerMapping controllerMapping;
	
	private ControllerMapping() {		
	}
	
	public static ControllerMapping getInstance() {
		if(controllerMapping ==null) {
			controllerMapping = new ControllerMapping();
			
		}
		return controllerMapping;
		
	}
	
	public Controller getController(String actionPage) {
		
		System.out.println("[ControllerMapping.getController() start...]");
		
		Controller controller = null;
		
		if (actionPage.equals("logon")) {
			
			controller = new LogonController();
		} else if (actionPage.equals("logonAction")) {
			controller = new LogonActionController();
			
		} else if(actionPage.equals("home")) {
			controller = new HomeController();
		}
		/*
		 * }else if(actionPage.equals("member")){
		 * 	action = new MemberAction();
		 * }else if(actionPage.equals("logout")){
		 * 	action = new LogoutAction();
		 * }
		 */
		System.out.println("[ControllerMapping.getController() end]");
		
		return controller;
	}

}// end of class
