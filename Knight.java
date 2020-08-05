import java.util.ArrayList;

public class Knight extends Piece{
	int dxs[] = {1, 2, 2, 1, -1, -2, -2, -1};
	int dys[] = {2, 1, -1, -2, -2, -1, 1, 2};
	
	public Knight(boolean team) {
		super(team); this.rank = 4; this.val = 320;
	}
	
	public String getImgName() {
		if(team) return "images/bn.png";
		else return "images/wn.png";
	}
	
	public boolean isValidMove(int sx, int sy, int ex, int ey) {
		if(!Move.onBoard(ex, ey) || inCheck(this.team, sx, sy, ex, ey)) return false;
		if(cb.board[ex][ey] != null && !(this.team ^ cb.board[ex][ey].team)) return false;
		int dx = Math.abs(sy - ey); int dy = Math.abs(sx - ex);
		return (dx == 1 && dy == 2) || (dx == 2 && dy == 1);
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