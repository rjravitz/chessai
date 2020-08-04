import java.util.ArrayList;

//NOTE: I'm currently assuming the user will not try to move a piece to its original
//spot as a move. So make sure to check for that.


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
    
    static void execMove(Piece[][] b, int sx, int sy, int ex, int ey) {
    	b[ex][ey] = b[sx][sy];
		b[sx][sy] = null;
		if(ey == 0 && b[ex][ey].team && b[ex][ey].rank == 5) pawnPromotion(b, b[ex][ey].team, ex, ey);
		else if(ey == 7 && !b[ex][ey].team && b[ex][ey].rank == 5) pawnPromotion(b, b[ex][ey].team, ex, ey);
		if(b[ex][ey] != null && b[ex][ey].team && b[ex][ey].rank == 0) {tbkx = ex; tbky = ey; twkx = wkx; twky = wky;}
		else if(b[ex][ey] != null && !b[ex][ey].team && b[ex][ey].rank == 0) {tbkx = bkx; tbky = bky; twkx = ex; twky = ey;}
		else {tbkx = bkx; tbky = bky; twkx = wkx; twky = wky;}
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
        			if(board[x][y].rank == 0) {wkx = x; wky = y;}
            		b.draw(); StdDraw.show(); selected = false; pressed1 = false; ready = false;
            		boolean done = false;
            		for(int i = 0; i < 8; i++) {
            			for(int j = 0; j < 8; j++) {
            				if(board[i][j] != null && board[i][j].team) {
            					ArrayList<Move> a = board[i][j].getValidMoves(i, j);
            					if(!a.isEmpty()) {
            						execMove(board, i, j, a.get(0).x, a.get(0).y);
            						if(board[a.get(0).x][a.get(0).y].rank == 0) {wkx = a.get(0).x; wky = a.get(0).y;}
            	        			done = true;
            	        			b.draw(); StdDraw.pause(20); StdDraw.show(); 
            	        			break;
            					}
            				}
            			}
            			if(done) break;
            		}
        		}
        		b.draw(); StdDraw.show(); selected = false; pressed1 = false; ready = false;
        	}
        	
        }
        
    }
}

