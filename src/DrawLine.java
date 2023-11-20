

public class DrawLine {
	public static int[][] drawObs(String s ,int num , int[][]  matrix){
		String[] l = s.split(" ");
		for (int i = 1 ; i < l.length ; i++) {
			matrix = drawLine(matrix,l[i-1] , l[i] , num);

		}
		return matrix;
	}
	
	public static int[][] drawLine(int[][] matrix , String p1, String p2 , int num){
		String[] l1= p1.split(",");
		String[] l2 = p2.split(",");
		
		int x1 = Integer.parseInt(l1[0]);
		int y1 = Integer.parseInt(l1[1]);
		int x2 = Integer.parseInt(l2[0]);
		int y2 = Integer.parseInt(l2[1]);
		
		if (x2<x1) {
			int temp = x1;
			x1 = x2;
			x2 = temp;
		}
		
		if (y2<y1) {
			int temp = y1;
			y1 = y2;
			y2 = temp;
		}
		
		
		if (x1 == x2){
			for (int i = y1 ; i <= y2 ; i++) {
				matrix[x1][i] = num;
			}
		}else if (y1 == y2) {
			for (int i = x1; i <= x2 ; i++) {
				matrix[i][y1] = num;
			}
		}
		return matrix;
	}
	
	public static int[][] drawSnake(String s ,int num , int[][]  matrix){
		String[] l = s.split(" ");
		for (int i = 1 ; i < l.length ; i++) {
			matrix = drawLine(matrix,l[i-1] , l[i] , num);
			if (i == 1) {
				extendhead(matrix,l[i-1], l[i]  , num);
			}
		}
		return matrix;
	}

	public static void extendhead(int[][] matrix , String p1, String p2 , int num) {
		String[] l1= p1.split(",");
		String[] l2= p1.split(",");
		
		int x1 = Integer.parseInt(l1[0]);
		int y1 = Integer.parseInt(l1[1]);
		int NextX = Integer.parseInt(l2[0]);
		int NextY = Integer.parseInt(l2[1]);
		int MiHeadX = x1 ;
		int MiHeadY = y1;
    	int dir = 0;
        if (MiHeadX == NextX) {
            if (MiHeadY < NextY) 
                dir = 0;
            else if (MiHeadY > NextY) 
                dir = 1;	
        }else if (MiHeadY == NextY) {
            if (MiHeadX < NextX) 
                dir = 2;
            else if (MiHeadX > NextX) 
                dir = 3;
        }
		
		int[] dx = {0, 0, -1, 1 ,0,0,-2,2}; // Directions: up, down, right, left move : 0=north 1=south 2=west 3=east
	    int[] dy = {-1, 1, 0, 0,-2,2,0,0};
	    
		for (int i = 0; i < 4; i++) {
	        int nextX = x1 + dx[i];
	        int nextY = y1 + dy[i];
		
			if (nextX >= 0 && nextX < 50 && nextY >= 0 && nextY < 50  && matrix[nextX][nextY] ==0) {
				matrix[nextX][nextY] = 2;
				if (dir == i) {
					int j = i*2;
					nextX = x1 + dx[j];
			        nextY = y1 + dy[i];
			        matrix[nextX][nextY] = 2;
				}
				
			}
		}
		
	}
	
}
