package paint.model;

import paint.controller.ShapesController;
import paint.view.Main;

public class ShapesFactory {
	public ShapesFactory() {
		
	}
	@SuppressWarnings("unchecked")
	public <T> Shape instantiate(final String className, final Class<Shape> type){
    try{
    	CustomClassLoader loader = ShapesController.getInstance(Main.getController()).getLoader(className);
    	if(loader == null) {
    		//TODO show error
    		return null;
    	}
        Class<?> cl =  loader.getClassObject();
        CommandPane node = (CommandPane)cl.newInstance();
        Class<?> shapeClass =  node.getToolClass();
    	return type.cast(shapeClass.newInstance());
    } catch(Exception e){
        throw new IllegalStateException(e);
    }
	}
}
