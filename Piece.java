import java.util.ArrayList;

public class Piece{
	boolean team; //0 is white, 1 is black
	int rank; //0 - king, 1 - queen, 2 - rook, 3 - bishop, 4 - knight, 5 - pawn
	int val;
	
	public Piece() {}
	
	public Piece(boolean team) {
		this.team = team;
	}
	
	public boolean getTeam() {
		return team;
	}
	
	public int getRank() {
		return rank;
	}
	
	public String getImgName() {return "";}
	public boolean isValidMove(int sx, int sy, int ex, int ey) {return false;}
	public ArrayList<Move> getValidMoves(int sx, int sy){return new ArrayList<Move>();}
	
	boolean inCheck(boolean team, int sx, int sy, int ex, int ey) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				cb.tb[i][j] = cb.board[i][j];
			}
		}
		
		cb.execMove(cb.tb, sx, sy, ex, ey);
		int kx, ky;
		if(team) {kx = cb.tbkx; ky = cb.tbky;}
		else {kx = cb.twkx; ky = cb.twky;}
		//knight checks
		for(int i = 0; i < 8; i++) {
			int x = kx + cb.ndxs[i]; int y = ky + cb.ndys[i];
			if(!Move.onBoard(x, y)) continue;
			if(cb.tb[x][y] != null && (cb.tb[x][y].team ^ team) && cb.tb[x][y].rank == 4) {return true;}
		}
		
		//king/pawn checks
		for(int i = 0; i < 8; i++) {
			int x = kx + cb.kdxs[i]; int y = ky + cb.kdys[i];
			if(!Move.onBoard(x, y)) continue;
			if(cb.tb[x][y] != null && (cb.tb[x][y].team ^ team)) {
				if(cb.tb[x][y].rank == 0) return true;
				else if(cb.tb[x][y].rank == 5 && ((team && (i == 3 || i == 5)) || (!team && (i == 1 || i == 7)))) {return true;}
			}
		}
		
		//rook/queen checks (N, S, E, W)
		for(int i = ky + 1; i < 8; i++) {
			if(cb.tb[kx][i] != null) {
				if((cb.tb[kx][i].team ^ team) && (cb.tb[kx][i].rank == 1 || cb.tb[kx][i].rank == 2)) {return true;}
				break;
			}
		}
		for(int i = ky - 1; i >= 0; i--) {
			if(cb.tb[kx][i] != null) {				
				if((cb.tb[kx][i].team ^ team) && (cb.tb[kx][i].rank == 1 || cb.tb[kx][i].rank == 2)) {return true;}
				break;
			}
		}
		for(int i = kx + 1; i < 8; i++) {
			if(cb.tb[i][ky] != null) {
				if((cb.tb[i][ky].team ^ team) && (cb.tb[i][ky].rank == 1 || cb.tb[i][ky].rank == 2)) {;return true;}
				break;
			}
		}
		for(int i = kx - 1; i >= 0; i--) {
			if(cb.tb[i][ky] != null) {
				if((cb.tb[i][ky].team ^ team) && (cb.tb[i][ky].rank == 1 || cb.tb[i][ky].rank == 2)) {return true;}
				//somethings fucked up with the king position
				break;
			}
		}
		
		//bishop/queen checks (NW, NE, SW, SE)
		int i, j;
		i = kx + 1; j = ky - 1; 
		while(Move.onBoard(i, j)) {
			if(cb.tb[i][j] != null) {
				if((cb.tb[i][j].team ^ team) && (cb.tb[i][j].rank == 1 || cb.tb[i][j].rank == 3)) {return true;}
				break;
			}
			i++; j--;
		}
		i = kx + 1; j = ky + 1; 
		while(Move.onBoard(i, j)) {
			if(cb.tb[i][j] != null) {
				if((cb.tb[i][j].team ^ team) && (cb.tb[i][j].rank == 1 || cb.tb[i][j].rank == 3)) {return true;}
				break;
			}
			i++; j++;
		}
		i = kx - 1; j = ky + 1;
		while(Move.onBoard(i, j)) {
			if(cb.tb[i][j] != null) {
				if((cb.tb[i][j].team ^ team) && (cb.tb[i][j].rank == 1 || cb.tb[i][j].rank == 3)) {return true;}
				break;
			}
			i--; j++;
		}
		i = kx - 1; j = ky - 1;
		while(Move.onBoard(i, j)) {
			if(cb.tb[i][j] != null) {
				if((cb.tb[i][j].team ^ team) && (cb.tb[i][j].rank == 1 || cb.tb[i][j].rank == 3)) {return true;}
				break;
			}
			i--; j--;
		}
		return false;
	}
	
}