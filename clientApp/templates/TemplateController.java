/**@author Sven**/
package templates;


public abstract class TemplateController {

	final private TemplateModel m;
	final private TemplateView v;
		
	public TemplateController(TemplateModel model, TemplateView view){
		this.m = model;
		this.v = view;
	}
	
}
