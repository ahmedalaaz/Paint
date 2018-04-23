package paint.model;

public interface MultipleResizeState {
	public void trigger(Shape shape ,double deltaX,double deltaY);
}

class WestResize implements MultipleResizeState{

	@Override
	public void trigger(Shape shape, double deltaX, double deltaY) {
		// TODO Auto-generated method stub
		shape.getResizableRectangle().triggerWestCircleMove(deltaX, deltaY);
	}
	
}

class NorthWestResize implements MultipleResizeState{

	@Override
	public void trigger(Shape shape, double deltaX, double deltaY) {
		// TODO Auto-generated method stub
		shape.getResizableRectangle().triggerNorthWestCircleMove(deltaX, deltaY);
			
	}
	
}

class NorthResize implements MultipleResizeState{

	@Override
	public void trigger(Shape shape, double deltaX, double deltaY) {
		// TODO Auto-generated method stub
		shape.getResizableRectangle().triggerNorthCircleMove(deltaX, deltaY);
		
	}
	
}

class NorthEastResize implements MultipleResizeState{

	@Override
	public void trigger(Shape shape, double deltaX, double deltaY) {
		// TODO Auto-generated method stub
		shape.getResizableRectangle().triggerNorthEastCircleMove(deltaX, deltaY);
		
	}
	
}

class EastResize implements MultipleResizeState{

	@Override
	public void trigger(Shape shape, double deltaX, double deltaY) {
		// TODO Auto-generated method stub
		shape.getResizableRectangle().triggerEastCircleMove(deltaX, deltaY);
		
	}
	
}

class SouthEastResize implements MultipleResizeState{

	@Override
	public void trigger(Shape shape, double deltaX, double deltaY) {
		// TODO Auto-generated method stub
		shape.getResizableRectangle().triggerSouthEastCircleMove(deltaX, deltaY);
		
	}
	
}

class SouthResize implements MultipleResizeState{

	@Override
	public void trigger(Shape shape, double deltaX, double deltaY) {
		// TODO Auto-generated method stub
		shape.getResizableRectangle().triggerSouthCircleMove(deltaX, deltaY);
		
	}
	
}

class SouthWestResize implements MultipleResizeState{

	@Override
	public void trigger(Shape shape, double deltaX, double deltaY) {
		// TODO Auto-generated method stub
		shape.getResizableRectangle().triggerSouthWestCircleMove(deltaX, deltaY);
		
	}
	
}

class MovingHandle implements MultipleResizeState{

	@Override
	public void trigger(Shape shape, double deltaX, double deltaY) {
		// TODO Auto-generated method stub
		shape.getResizableRectangle().triggerMovingHandleCircleMove(deltaX, deltaY);
		
	}
	
}

