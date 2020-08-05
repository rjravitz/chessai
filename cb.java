import java.util.ArrayList;

//source for piece-square tables: https://www.chessprogramming.org/Simplified_Evaluation_Function

public class cb { 
	public static Piece[][] board;
	public static int wkx, wky, bkx, bky, twkx, twky, tbkx, tbky; //stores kings positions for real and test boards
	
	public static Piece[][] tb; //test board for check
	
	static int ndxs[] = {1, 2, 2, 1, -1, -2, -2, -1}; //to check if knights put the king in check
	static int ndys[] = {2, 1, -1, -2, -2, -1, 1, 2};
	
	static int kdxs[] = {0, 1, 1, 1, 0, -1, -1, -1}; //to check if opposite king puts the king in check
	static int kdys[] = {1, 1, 0, -1, -1, -1, 0, 1};
	
	
	public static int pfreq[] = {1, 1, 2, 2, 2, 8};
	public static Piece[] bpieces = {new King(true), new Queen(true), new Rook(true), new Bishop(true), new Knight(true), new Pawn(true)};
	public static Piece[] wpieces = {new King(false), new Queen(false), new Rook(false), new Bishop(false), new Knight(false), new Pawn(false)};
	
	public static final double[][] wppts = {
			{0,  0,  0,  0,  0,  0,  0,  0},
			{50, 50, 50, 50, 50, 50, 50, 50},
			{10, 10, 20, 30, 30, 20, 10, 10},
			{5,  5, 10, 25, 25, 10,  5,  5},
			{0,  0,  0, 20, 20,  0,  0,  0},
			{5, -5,-10,  0,  0,-10, -5,  5},
			{5, 10, 10,-20,-20, 10, 10,  5},
			{0,  0,  0,  0,  0,  0,  0,  0}

	}; 
	
	public static final double[][] bppts = rev(wppts);
	    
	public static final double[][] wnpts = {
			{-50,-40,-30,-30,-30,-30,-40,-50},
			{-40,-20,  0,  0,  0,  0,-20,-40},
			{-30,  0, 10, 15, 15, 10,  0,-30},
			{-30,  5, 15, 20, 20, 15,  5,-30},
			{-30,  0, 15, 20, 20, 15,  0,-30},
			{-30,  5, 10, 15, 15, 10,  5,-30},
			{-40,-20,  0,  5,  5,  0,-20,-40},
			{-50,-40,-30,-30,-30,-30,-40,-50},
	 }; 
	
	public static final double[][] bnpts = rev(wnpts);
	    
	public static final double[][] wbpts = {
	        {-20,-10,-10,-10,-10,-10,-10,-20},
	        {-10,  0,  0,  0,  0,  0,  0,-10},
	        {-10,  0,  5, 10, 10,  5,  0,-10},
	        {-10,  5,  5, 10, 10,  5,  5,-10},
	        {-10,  0, 10, 10, 10, 10,  0,-10},
	        {-10, 10, 10, 10, 10, 10, 10,-10},
	        {-10,  5,  0,  0,  0,  0,  5,-10},
	        {-20,-10,-40,-10,-10,-40,-10,-20},
	 }; 
	
	public static final double[][] bbpts = rev(wbpts);
	
	public static final double[][] wqpts = {
			{-20,-10,-10, -5, -5,-10,-10,-20},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-10,  0,  5,  5,  5,  5,  0,-10},
			{-5,  0,  5,  5,  5,  5,  0, -5},
			{ 0,  0,  5,  5,  5,  5,  0, -5},
			{-10,  5,  5,  5,  5,  5,  0,-10},
			{-10,  0,  5,  0,  0,  0,  0,-10},
			{-20,-10,-10, -5, -5,-10,-10,-20}
	};
	
	public static final double[][] bqpts = rev(wqpts);
	
	public static final double[][] wrpts = {
			 {0,  0,  0,  0,  0,  0,  0,  0},
			 {5, 10, 10, 10, 10, 10, 10,  5},
			 {-5,  0,  0,  0,  0,  0,  0, -5},
			 {-5,  0,  0,  0,  0,  0,  0, -5},
			 {-5,  0,  0,  0,  0,  0,  0, -5},
			 {-5,  0,  0,  0,  0,  0,  0, -5},
			 {-5,  0,  0,  0,  0,  0,  0, -5},
			 {0,  0,  0,  5,  5,  0,  0,  0}
	};
	
	public static final double[][] brpts = rev(wrpts);
	    
	public static final double[][] wkpts = {
	        {-30, -40, -40, -50, -50, -40, -40, -30},
	        {-30, -40, -40, -50, -50, -40, -40, -30},
	        {-30, -40, -40, -50, -50, -40, -40, -30},
	        {-30, -40, -40, -50, -50, -40, -40, -30},
	        {-20, -30, -30, -40, -40, -30, -30, -20},
	        {-10, -20, -20, -20, -20, -20, -20, -10}, 
	        {20,  20,   0,   0,   0,   0,  20,  20},
	        {20,  30,  10,   0,   0,  10,  30,  20}
	 }; 
	
	public static final double[][] bkpts = rev(wkpts);
	    
	public static final double[][] wkptsend = {
	        {-50,-40,-30,-20,-20,-30,-40,-50},
	        {-30,-20,-10,  0,  0,-10,-20,-30},
	        {-30,-10, 20, 30, 30, 20,-10,-30},
	        {-30,-10, 30, 40, 40, 30,-10,-30},
	        {-30,-10, 30, 40, 40, 30,-10,-30},
	        {-30,-10, 20, 30, 30, 20,-10,-30},
	        {-30,-30,  0,  0,  0,  0,-30,-30},
	        {-50,-30,-30,-30,-30,-30,-30,-50}
	 };
	
	public static final double[][] bkptsend = rev(wkptsend);
	    
	static double[][] rev(double[][] a){
	    double[][] ans = new double[8][8];
	    for(int i = 0; i < 8; i++) {
	    	for(int j = 0; j < 8; j++) {
	    		ans[i][j] = a[7-i][j];
	    	}
	    }
	    return ans;
	}

 	
	public cb() {
		board = new Piece[8][8]; tb = new Piece[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				board[i][j] = null;
			}
		}
		board[0][0] = new Rook(false); board[7][0] = new Rook(false);
		board[0][7] = new Rook(true); board[7][7] = new Rook(true);
		board[1][0] = new Knight(false); board[6][0] = new Knight(false);
		board[1][7] = new Knight(true); board[6][7] = new Knight(true);
		board[2][0] = new Bishop(false); board[5][0] = new Bishop(false);
		board[2][7] = new Bishop(true); board[5][7] = new Bishop(true);
		board[3][0] = new Queen(false); board[3][7] = new Queen(true);
		board[4][0] = new King(false); board[4][7] = new King(true);
		wkx = 4; wky = 0; bkx = 4; bky = 7;
		twkx = wkx; twky = wky; tbkx = bkx; tbky = bky;
		
		for(int i = 0; i < 8; i++) {
			board[i][1] = new Pawn(false);
			board[i][6] = new Pawn(true);
		}
	}
	
	
	void draw() {
		int n = 8;
        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                drawSquare(i, j);
                if(board[i][j] != null) StdDraw.picture(i + 0.5, j + 0.5, board[i][j].getImgName());
            }
        }    
	}
	
	static void pawnPromotion(Piece[][] b, boolean team, int x, int y) {
		int freq[] = {0, 0, 0, 0, 0, 0};
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(b[i][j] != null && b[i][j].team == team) freq[b[i][j].rank]++;
			}
		}
		freq[5]--;
		for(int i = 0; i <= 5; i++) {
			if(freq[i] < pfreq[i]) {
				if(team) b[x][y] = bpieces[i];
				else b[x][y] = wpieces[i];
				break;
			}
		}
	}
	
    static void drawSquare(int i, int j) {
    	if ((i + j) % 2 != 0) StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        else  StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.filledSquare(i + 0.5, j + 0.5, 0.5);
    }
    
    public static boolean bseenEndgame = false, wseenEndgame;
    
    static boolean endgame(boolean team) {
    	int queens = 0, rooks = 0, minors = 0;
    	for(int i = 0; i < 8; i++) {
    		for(int j = 0; j < 8; j++) {
    			if(board[i][j] != null && (board[i][j].team == team)) {
    				if(board[i][j].rank == 1) queens++;
    				else if(board[i][j].rank == 2) rooks++;
    				else if (board[i][j].rank == 3 || board[i][j].rank == 4) minors++;
    			}
    		}
    	}
    	return queens == 0 || ((rooks == 0) && (minors <= 1));
    }
    
    static int boardscore() {
    	int score = 0;
    	for(int i = 0; i < 8; i++) {
    		for(int j = 0; j < 8; j++){
    			score += piecescore(i, j);
    		}
    	}
    	return score;
    }
    
    static int piecescore(int i, int j) {
    	int score = 0;
    	if(board[i][j] != null) {
			if(board[i][j].team) {
				if(board[i][j].rank == 0) {
					if(bseenEndgame) score += bkptsend[i][j] + board[i][j].val;
					else score += bkpts[i][j] + board[i][j].val;
				}
				else if(board[i][j].rank == 1) score += bqpts[i][j] + board[i][j].val;
				else if(board[i][j].rank == 2) score += brpts[i][j] + board[i][j].val;
				else if(board[i][j].rank == 3) score += bbpts[i][j] + board[i][j].val;
				else if(board[i][j].rank == 4) score += bnpts[i][j] + board[i][j].val;
				else score += bppts[i][j] + board[i][j].val;
			} else {
				if(board[i][j].rank == 0) {
					if(wseenEndgame) score -= (wkptsend[i][j] + board[i][j].val);
					else score -= (wkpts[i][j] + board[i][j].val);
				}
				else if(board[i][j].rank == 1) score -= (wqpts[i][j] + board[i][j].val);
				else if(board[i][j].rank == 2) score -= (wrpts[i][j] + board[i][j].val);
				else if(board[i][j].rank == 3) score -= (wbpts[i][j] + board[i][j].val);
				else if(board[i][j].rank == 4) score -= (wnpts[i][j] + board[i][j].val);
				else score -= (wppts[i][j] + board[i][j].val);
			}
		}
    	return score;
    }
    
    static void execMove(Piece[][] b, int sx, int sy, int ex, int ey) {
    	b[ex][ey] = b[sx][sy];
		b[sx][sy] = null;
		if(ey == 0 && b[ex][ey] != null &&b[ex][ey].team && b[ex][ey].rank == 5) pawnPromotion(b, b[ex][ey].team, ex, ey);
		else if(ey == 7 && b[ex][ey] != null && !b[ex][ey].team && b[ex][ey].rank == 5) pawnPromotion(b, b[ex][ey].team, ex, ey);
		if(b[ex][ey] != null && b[ex][ey].team && b[ex][ey].rank == 0) {bkx = ex; bky = ey;}
		else if(b[ex][ey] != null && !b[ex][ey].team && b[ex][ey].rank == 0) {wkx = ex; wky = ey;}
		tbkx = bkx; tbky = bky; twkx = wkx; twky = wky;
    }

	
	//returns an array of 5 elements containing the score, sx, sy, ex, ey
	public static int[] minimax() {
		bseenEndgame = endgame(true); wseenEndgame = endgame(false);
		int score = boardscore();
		return maxie(3, score, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	public static int[] maxie(int depth, int score, int al, int be) {
		int alpha = al; int beta = be;
		int res = score; boolean endgameFlag = false; boolean pieceTaken = false;
		int[] ans = {(int)-1e9, -1, -1, -1, -1};
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] != null && board[i][j].team) {
					int ps = piecescore(i, j);
					ArrayList<Move> a = board[i][j].getValidMoves(i, j);
					for(int k = 0; k < a.size(); k++) {
						int ex = a.get(k).x; int ey = a.get(k).y;
						Piece spiece = board[i][j]; Piece epiece = board[ex][ey];
						if(epiece != null) {res -= piecescore(ex, ey); pieceTaken = true;}
						res -= ps;
						execMove(board, i, j, ex, ey);
						if(pieceTaken && endgame(false)) {wseenEndgame = true; endgameFlag = true;}
						int[] minChoice = minnie(depth, res, alpha, beta);
						if(minChoice[0] > ans[0]) {
							ans[0] = minChoice[0]; ans[1] = i; ans[2] = j;
							ans[3] = ex; ans[4] = ey;
						}
						board[i][j] = spiece; board[ex][ey] = epiece;
						if(pieceTaken) res += piecescore(ex, ey);
						if(board[i][j].rank == 0) {bkx = i; bky = j; tbkx = bkx; tbky = bky;}
						res += ps;
						if(endgameFlag) wseenEndgame = false;
						if(minChoice[0] > alpha && minChoice[0] < beta) alpha = minChoice[0];
						else if(minChoice[0] >= beta) return ans;
					}
				}
			}
		}
		return ans;
	}
	
	public static int[] minnie(int depth, int score, int al, int be) {
		int alpha = al; int beta = be;
		int res = score; boolean endgameFlag = false; boolean pieceTaken = false;
		int[] ans = {(int)1e9, -1, -1, -1, -1};
		if(depth == 0) {ans[0] = score; return ans;}
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] != null && !board[i][j].team) {
					int ps = piecescore(i, j);
					ArrayList<Move> a = board[i][j].getValidMoves(i, j);
					for(int k = 0; k < a.size(); k++) {
						int ex = a.get(k).x; int ey = a.get(k).y;
						Piece spiece = board[i][j]; Piece epiece = board[ex][ey];
						if(epiece != null) {res -= piecescore(ex, ey); pieceTaken = true;}
						res -= ps;
						execMove(board, i, j, ex, ey);
						if(pieceTaken && endgame(false)) {bseenEndgame = true; endgameFlag = true;}
						int[] maxChoice = maxie(depth - 1, res, alpha, beta);
						if(maxChoice[0] < ans[0]) {
							ans[0] = maxChoice[0]; ans[1] = i; ans[2] = j;
							ans[3] = ex; ans[4] = ey;
						}
						board[i][j] = spiece; board[ex][ey] = epiece;
						if(pieceTaken) res += piecescore(ex, ey);
						if(board[i][j].rank == 0) {wkx = i; wky = j; twkx = wkx; twky = wky;}
						res += ps;
						if(endgameFlag) bseenEndgame = false;
						if(maxChoice[0] > alpha && maxChoice[0] < beta) beta = maxChoice[0];
						else if(maxChoice[0] <= alpha) return ans;
					}
				}
			}
		}
		return ans;
	}
	
	public static void main(String[] args) { 
        cb b = new cb();
        StdDraw.enableDoubleBuffering();
        b.draw(); StdDraw.show();
        
        boolean selected = false; boolean pressed1 = false; boolean ready = false;
        int selectedX = -1, selectedY = -1; 
        while(true) {
        	b.draw();
        	double mx = StdDraw.mouseX(); double my = StdDraw.mouseY();
        	if(mx < 0 || mx >= 8 || my < 0 || my >= 8) continue;
        	int x = (int)Math.round(mx - 0.5); int y = (int)Math.round(my - 0.5);
        	if(!StdDraw.isMousePressed() && !selected && !pressed1) ready = true;
        	else if(StdDraw.isMousePressed() && !selected 
        			&& !pressed1 && ready && board[x][y] != null && !board[x][y].team) {
        		selected = true; selectedX = x; selectedY = y;
        		StdDraw.setPenColor(StdDraw.RED);
                StdDraw.circle(selectedX + 0.5, selectedY + 0.5, 0.5);
                StdDraw.show();
        	} else if(!StdDraw.isMousePressed() && selected) pressed1 = true;
        	else if(StdDraw.isMousePressed() && selected && pressed1) {
        		if(board[selectedX][selectedY].isValidMove(selectedX, selectedY, x, y)) {
        			execMove(board, selectedX, selectedY, x, y);
            		b.draw(); StdDraw.show(); selected = false; pressed1 = false; ready = false;
            		int[] bmove = minimax();
            		if(bseenEndgame) System.out.println("endgame");
            		if(bmove[0] > (int)-1e9) {
            			execMove(board, bmove[1], bmove[2], bmove[3], bmove[4]);
            			b.draw(); StdDraw.pause(20); StdDraw.show();
            		}
        		}
        		b.draw(); StdDraw.show(); selected = false; pressed1 = false; ready = false;
        	}
        	
        }
        
    }
}

