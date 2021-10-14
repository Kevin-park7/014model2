package spring.model2.control;

public class ModelAndView {
	
	//Field
	private String viewName;
	private String modelName;
	private Object modelObject;
	
	//Constructor
	public ModelAndView() {
		// TODO Auto-generated constructor stub
	}
	public ModelAndView(String viewName) {
		
		this.viewName = viewName;
	}
	public ModelAndView(String viewName,String modelName, Object modelObject) {
		this.modelName = modelName;
		this.modelObject = modelObject;
		this.viewName = viewName;
	}
	
	//Method
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Object getModelObject() {
		return modelObject;
	}
	public void setModelObject(Object modelObject) {
		this.modelObject = modelObject;
	}
	
	
	
	

}
