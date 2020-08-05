import java.util.ArrayList;

public class Pawn extends Piece{

	public Pawn(boolean team) {
		super(team); this.rank = 5; this.val = 100;
	}
	
	public String getImgName() {
		if(team) return "images/bp.png";
		else return "images/wp.png";
	}
	
	public boolean isValidMove(int sx, int sy, int ex, int ey) {
		if(!Move.onBoard(ex, ey) || inCheck(this.team, sx, sy, ex, ey)) {return false;}
		if(this.team) { //black
			if(ey == sy - 1 && ex == sx && cb.board[ex][ey] == null) return true;
			if(sy == 6 && ey == sy - 2 && ex == sx && cb.board[sx][sy-1] == null && cb.board[sx][sy-2] == null) return true;
			if(ey == sy - 1 && ex == sx - 1 && (cb.board[sx-1][sy-1] != null && !cb.board[sx-1][sy-1].team)) return true;
			if(ey == sy - 1 && ex == sx + 1 && (cb.board[sx+1][sy-1] != null && !cb.board[sx+1][sy-1].team)) return true;
		} else { //white
			if(ey == sy + 1 && ex == sx && cb.board[sx][sy+1] == null) return true;
			if(sy == 1 && ey == sy + 2 && ex == sx && cb.board[sx][sy+1] == null && cb.board[sx][sy+2] == null) return true;
			if(ey == sy + 1 && ex == sx - 1 && (cb.board[sx-1][sy+1] != null && cb.board[sx-1][sy+1].team)) return true;
			if(ey == sy + 1 && ex == sx + 1 && (cb.board[sx+1][sy+1] != null && cb.board[sx+1][sy+1].team)) return true;
		}
		return false;
	}
	
	public ArrayList<Move> getValidMoves(int sx, int sy){
		ArrayList<Move> a = new ArrayList<Move>();
		if(this.team) { //black
			if(Move.onBoard(sx, sy - 1) && cb.board[sx][sy-1] == null) {
				if(!inCheck(this.team, sx, sy, sx, sy - 1)) a.add(new Move(sx, sy - 1));
				if(Move.onBoard(sx, sy -2 ) && cb.board[sx][sy-2] == null && 
						sy == 6 && !inCheck(this.team, sx, sy, sx, sy - 2)) a.add(new Move(sx, sy - 2));
			}
			if(Move.onBoard(sx - 1, sy - 1) && (cb.board[sx-1][sy-1] != null && !cb.board[sx-1][sy-1].team)
					&& !inCheck(this.team, sx, sy, sx - 1, sy - 1)) a.add(new Move(sx-1, sy-1));
			if(Move.onBoard(sx + 1, sy - 1) && (cb.board[sx+1][sy-1] != null && !cb.board[sx+1][sy-1].team)
					&& !inCheck(this.team, sx, sy, sx + 1, sy - 1)) a.add(new Move(sx+1, sy-1));
		} else { //white
			if(Move.onBoard(sx, sy + 1) && cb.board[sx][sy+1] == null) {
				if(!inCheck(this.team, sx, sy, sx, sy + 1)) a.add(new Move(sx, sy + 1));
				if(Move.onBoard(sx, sy + 2) && cb.board[sx][sy+2] == null && 
						sy == 1 && !inCheck(this.team, sx, sy, sx, sy + 2)) a.add(new Move(sx, sy + 2));
			}
			if(Move.onBoard(sx - 1, sy + 1) && (cb.board[sx-1][sy+1] != null && cb.board[sx-1][sy+1].team)
					&& !inCheck(this.team, sx, sy, sx - 1, sy + 1)) a.add(new Move(sx-1, sy+1));
			if(Move.onBoard(sx + 1, sy + 1) && (cb.board[sx+1][sy+1] != null && cb.board[sx+1][sy+1].team)
					&& !inCheck(this.team, sx, sy, sx + 1, sy + 1)) a.add(new Move(sx+1, sy+1));
		}
		return a;
	}
}