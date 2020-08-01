import java.util.ArrayList;

public class Bishop extends Piece{

	public Bishop(boolean team) {
		super(team);
		this.rank = 3;
	}
	
	public String getImgName() {
		if(team) return "images/bb.png";
		else return "images/wb.png";
	}
	
	public boolean isValidMove(int sx, int sy, int ex, int ey) {
		if(!Move.onBoard(ex, ey) || inCheck(false, sx, sy, ex, ey)) return false;
		if(cb.board[ex][ey] != null && !(this.team ^ cb.board[ex][ey].team)) return false;
		if(Math.abs(sx - ex) != Math.abs(sy - ey)) return false;
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
	}
	
	
	public ArrayList<Move> getValidMoves(int sx, int sy){
		ArrayList<Move> a = new ArrayList<Move>();
		int i, j;
		i = sx + 1; j = sy - 1; 
		while(Move.onBoard(i, j)) {
			if((cb.board[i][j] == null || (cb.board[i][j].team ^ this.team)) && !inCheck(this.team, sx, sy, i, j)) a.add(new Move(i, j));
			if(cb.board[i][j] != null) break;
			i++; j--;
		}
		i = sx + 1; j = sy + 1; 
		while(Move.onBoard(i, j)) {
			if((cb.board[i][j] == null || (cb.board[i][j].team ^ this.team)) && !inCheck(this.team, sx, sy, i, j)) a.add(new Move(i, j));
			if(cb.board[i][j] != null) break;
			i++; j++;
		}
		i = sx - 1; j = sy + 1; 
		while(Move.onBoard(i, j)) {
			if((cb.board[i][j] == null || (cb.board[i][j].team ^ this.team)) && !inCheck(this.team, sx, sy, i, j)) a.add(new Move(i, j));
			if(cb.board[i][j] != null) break;
			i--; j++;
		}
		i = sx - 1; j = sy - 1; 
		while(Move.onBoard(i, j)) {
			if((cb.board[i][j] == null || (cb.board[i][j].team ^ this.team)) && !inCheck(this.team, sx, sy, i, j)) a.add(new Move(i, j));
			if(cb.board[i][j] != null) break;
			i--; j--;
		}
		return a;
	}
}