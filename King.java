import java.util.ArrayList;

public class King extends Piece{

	int dxs[] = {0, 1, 1, 1, 0, -1, -1, -1};
	int dys[] = {1, 1, 0, -1, -1, -1, 0, 1};
	
	public King(boolean team) {
		super(team); this.rank = 0;
	}
	
	public String getImgName() {
		if(team) return "images/bk.png";
		else return "images/wk.png";
	}
	
	public boolean isValidMove(int sx, int sy, int ex, int ey) {
		if(!Move.onBoard(ex, ey) || inCheck(this.team, sx, sy, ex, ey)) return false;
		if(cb.board[ex][ey] != null && !(this.team ^ cb.board[ex][ey].team)) return false;
		int dx = Math.abs(sy - ey); int dy = Math.abs(sx - ex);
		return dx <= 1 && dy <= 1;
	}
	
	public ArrayList<Move> getValidMoves(int sx, int sy){
		ArrayList<Move> a = new ArrayList<Move>();
		for(int i = 0; i < 8; i++) {
			Move m = new Move(sx + dxs[i], sy + dys[i]);
			if(m.onBoard() && (cb.board[m.x][m.y] == null || (this.team ^ cb.board[m.x][m.y].team)) && !inCheck(this.team, sx, sy, m.x, m.y)) a.add(m);
		}
		return a;
	}
}