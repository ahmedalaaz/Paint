package paint.model;

public interface Shape {
	public void setPosition(java.awt.Point position);

	public java.awt.Point getPosition();

	/* update shape specific properties (e.g., radius) */
	public void setProperties(java.util.Map<String, Double> properties);

	public java.util.Map<String, Double> getProperties();

	public void setColor(Object color);

	public Object getColor();

	public void setFillColor(Object color);

	public Object getFillColor();
	
	public void turnOnSelectListener();
	
	public void turnOffSelectListener();
	
	public void removeResizableRectangle();
	

	/*
	 * redraw the shape on the canvas, for swing, you will cast canvas to
	 * java.awt.Graphics
	 */
	public void draw(Object canvas);

	/* create a deep clone of the shape */
	public Object clone() throws CloneNotSupportedException;

	public boolean isSelected();

	public void setStrokeWidth(Integer value);
	public void getStrokeWidth(Integer value);
		

}