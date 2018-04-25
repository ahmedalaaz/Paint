package paint.model;

public class ShapesFactory {
	public ShapesFactory() {
		
	}
	public <T> T instantiate(final String className, final Class<T> type){
    try{
        return type.cast(Class.forName(className).newInstance());
    } catch(InstantiationException
          | IllegalAccessException
          | ClassNotFoundException e){
        throw new IllegalStateException(e);
    }
	}
}
