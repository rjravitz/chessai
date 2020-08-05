import java.util.ArrayList;

public class Queen extends Piece{

	public Queen(boolean team) {
		super(team); this.rank = 1; this.val = 900;
	}
	
	public String getImgName() {
		if(team) return "images/bq.png";
		else return "images/wq.png";
	}
	
	public boolean isValidMove(int sx, int sy, int ex, int ey) {
		if(!Move.onBoard(ex, ey) || inCheck(this.team, sx, sy, ex, ey)
				|| ((sx == ex) && (sy == ey))) return false;
		if(cb.board[ex][ey] != null && !(this.team ^ cb.board[ex][ey].team)) return false;
		if(Math.abs(sx - ex) == Math.abs(sy - ey)) { //same as bishop
			int i, j;
			if(sx < ex && sy > ey) { 
				i = sx + 1; j = sy - 1;
				while(i < ex) {
					if(cb.board[i][j] != null) return false;
					i++; j--;
				}
			} else if(sx < ex && sy < ey) { 
				i = sx + 1; j = sy + 1;
				while(i < ex) {
					if(cb.board[i][j] != null) return false;
					i++; j++;
				}
			} else if(sy < ey) { 
				i = sx - 1; j = sy + 1;
				while(i > ex) {
					if(cb.board[i][j] != null) return false;
					i--; j++;
				}
			} else { 
				i = sx - 1; j = sy - 1;
				while(i > ex) {
					if(cb.board[i][j] != null) return false;
					i--; j--;
				}
			}
			return true;
		} else { //same as rook
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
	}
	public ArrayList<Move> getValidMoves(int sx, int sy){
		ArrayList<Move> a = new ArrayList<Move>();
		//Bishop moves first
		int i, j;
		i = sx + 1; j = sy - 1; 
		while(Move.onBoard(i, j)) {
			if((cb.board[i][j] == null || (cb.board[i][j].team ^ this.team)) && !inCheck(this.team, sx, sy, i, j)) a.add(new Move(i, j));			if(cb.board[i][j] != null) break;
			i++; j--;
		}
		i = sx + 1; j = sy + 1; 
		while(Move.onBoard(i, j)) {
			if((cb.board[i][j] == null || (cb.board[i][j].team ^ this.team)) && !inCheck(this.team, sx, sy, i, j)) a.add(new Move(i, j));			if(cb.board[i][j] != null) break;
			i++; j++;
		}
		i = sx - 1; j = sy + 1; 
		while(Move.onBoard(i, j)) {
			if((cb.board[i][j] == null || (cb.board[i][j].team ^ this.team)) && !inCheck(this.team, sx, sy, i, j)) a.add(new Move(i, j));			if(cb.board[i][j] != null) break;
			i--; j++;
		}
		i = sx - 1; j = sy - 1; 
		while(Move.onBoard(i, j)) {
			if((cb.board[i][j] == null || (cb.board[i][j].team ^ this.team)) && !inCheck(this.team, sx, sy, i, j)) a.add(new Move(i, j));			if(cb.board[i][j] != null) break;
			i--; j--;
		}
		
		//Then rook moves
		for(int k = sy + 1; k < 8; k++) { 
			if((cb.board[sx][k] == null || (cb.board[sx][k].team ^ this.team)) && !inCheck(this.team, sx, sy, sx, k)) a.add(new Move(sx, k));
			if(cb.board[sx][k] != null) break;
		}
		for(int k = sy - 1; k >= 0; k--) { 
			if((cb.board[sx][k] == null || (cb.board[sx][k].team ^ this.team)) && !inCheck(this.team, sx, sy, sx, k)) a.add(new Move(sx, k));
			if(cb.board[sx][k] != null) break;
		}
		for(int k = sx + 1; k < 8; k++) { 
			if((cb.board[k][sy] == null || (cb.board[k][sy].team ^ this.team)) && !inCheck(this.team, sx, sy, k, sy)) a.add(new Move(k, sy));
			if(cb.board[k][sy] != null) break;
		}
		for(int k = sx - 1; k >= 0; k--) { 
			if((cb.board[k][sy] == null || (cb.board[k][sy].team ^ this.team)) && !inCheck(this.team, sx, sy, k, sy)) a.add(new Move(k, sy));
			if(cb.board[k][sy] != null) break;
		}
		return a;
	}
}