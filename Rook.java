import java.util.ArrayList;

public class Rook extends Piece{

	public Rook(boolean team) {
		super(team); this.rank = 2;
	}
	
	public String getImgName() {
		if(team) return "images/br.png";
		else return "images/wr.png";
	}
	
	public boolean isValidMove(int sx, int sy, int ex, int ey) {
		if(!Move.onBoard(ex, ey)) return false;
		if(cb.board[ex][ey] != null && !(this.team ^ cb.board[ex][ey].team)) return false;
		if(!((Math.abs(sx - ex) == 0) ^ (Math.abs(sy - ey) == 0))) {return false;}
		if(sy < ey) { 
			for(int i = sy + 1; i < ey; i++) {
				if(cb.board[sx][i] != null) return false;
			}
		} else if (sy > ey) { 
			for(int i = sy - 1; i > ey; i--) {
				if(cb.board[sx][i] != null) return false;
			}
		} else if(sx < ex) { 
			for(int i = sx + 1; i < ex; i++) {
				if(cb.board[i][sy] != null) return false;
			}	
		} else {
			for(int i = sx - 1; i > ex; i--) {
				if(cb.board[i][sy] != null) return false;
			}
		}
		return true;
	}
	
	public ArrayList<Move> getValidMoves(int sx, int sy){
		ArrayList<Move> a = new ArrayList<Move>();
		for(int i = sy + 1; i < 8; i++) { 
			if((cb.board[sx][i] == null || (cb.board[sx][i].team ^ this.team)) && !inCheck(this.team, sx, sy, sx, i)) a.add(new Move(sx, i));
			if(cb.board[sx][i] != null) break;
		}
		for(int i = sy - 1; i >= 0; i--) { 
			if((cb.board[sx][i] == null || (cb.board[sx][i].team ^ this.team)) && !inCheck(this.team, sx, sy, sx, i)) a.add(new Move(sx, i));
			if(cb.board[sx][i] != null) break;
		}
		for(int i = sx + 1; i < 8; i++) { 
			if((cb.board[i][sy] == null || (cb.board[i][sy].team ^ this.team)) && !inCheck(this.team, sx, sy, i, sy)) a.add(new Move(i, sy));
			if(cb.board[i][sy] != null) break;
		}
		for(int i = sx - 1; i >= 0; i--) { 
			if((cb.board[i][sy] == null || (cb.board[i][sy].team ^ this.team)) && !inCheck(this.team, sx, sy, i, sy)) a.add(new Move(i, sy));
			if(cb.board[i][sy] != null) break;
		}
		return a;
	}
	
}