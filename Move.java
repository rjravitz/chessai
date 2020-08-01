public class Move{
	int x; int y;
	public Move(int x, int y) {
		this.x = x; this.y = y;
	}
	
	public boolean onBoard() {
		return this.x >= 0 && this.x < 8 && this.y >= 0 && this.y < 8;
	}
	
	public static boolean onBoard(int x, int y) {
		return x >= 0 && x < 8 && y >= 0 && y < 8;
	}
	
	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}
}