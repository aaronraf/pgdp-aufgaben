package pgdp.rectangles;

public class Vector2D {

	private double x;
	private double y;

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vector2D other) {
		this.x += other.x;
		this.y += other.y;
	}

	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public static void main(String[] args) {

		/*
		 * This part is only for testing purposes and should not be changed.
		 * 
		 * @TODO: Remove the comment symbols (Eclipse: Ctrl + Shift + C) and run the
		 * main-method for testing. If you have not implemented the constructor and
		 * toString-method, your program won't build!
		 */

//		Vector2D myVector = new Vector2D(1.0, 2.0);
//		if (myVector.toString().equals("[1.0, 2.0]"))
//			System.out.println("The String seems to match. Good Job!");
//		else {
//			System.out.println("This is what you created: " + myVector);
//			System.out.println("This is how it should be: [1.0, 2.0]");
//			System.out.println("Better luck next time!");
//		}

		/*
		 * Down here you might want to add code to create your own tests?
		 */

	}

}
