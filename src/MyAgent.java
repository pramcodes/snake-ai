import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import za.ac.wits.snake.DevelopmentAgent;

public class MyAgent extends DevelopmentAgent {
	
	public int steps= 0;

    public static void main(String args[]) {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }
    int [][] board ;
    
    void printBoard() {
        System.err.println();
        for(int y = 0; y < 50; y++) {
            for(int x = 0; x < 50; x++) {
            	System.err.print(board[x][y]);
            }
            System.err.println();
        }
        System.err.println();
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            String initString = br.readLine();
//            System.err.print(initString);
            
            String[] temp = initString.split(" ");
            
            int nSnakes = Integer.parseInt(temp[0]);
            int width = Integer.parseInt(temp[1]);
            int height = Integer.parseInt(temp[2]);
            
            int numObstacles = 3;
            
//            Timer T = new Timer();
            
            while (true) {
//            	T.start();
            	board =new int [width][height];
            	
                String line = br.readLine();
                
                if (line.contains("Game Over")) {
                    break;
                }
                
               
                String[] Applecoords = line.split(" ");
                int Xapple = Integer.parseInt(Applecoords[0]);
                int Yapple = Integer.parseInt(Applecoords[1]);
                
                 
                
                for (int j=0; j<numObstacles; j++) {
                	String obsLine = br.readLine();
                	//code to move around walls
                	board = DrawLine.drawObs(obsLine, 1, board);//drawing obstacles
                }


                
                int mySnakeNum = Integer.parseInt(br.readLine());
                
                int MiHeadX = 0, MiHeadY = 0, NextX = 0, NextY = 0;
                int dir = 0;
                
//                int[] dist = new int[4];
                String[] heads = new String[4];
                int numsnakes = 0;

                for (int i = 0; i < nSnakes; i++) {
                	
                    String snakeLine = br.readLine();
//                  System.err.println(snakeLine);
                    String[] enemy =snakeLine.split(" "); //drawing snakes alive 2 1 12,13 12,14
                    if (enemy[0].equals("alive")) {
                    	numsnakes++;
	                    String enemyline = "";
	                    for (int j = 3 ; j<enemy.length ; j++) {
	                    	enemyline+= enemy[j] + " ";
	                    	
	                    }
	                    
	                    heads[numsnakes-1] = enemy[3];
	                    
	                    if (i == mySnakeNum) {
	                    	board = DrawLine.drawObs( enemyline , 9, board);
	                    	
	                    	String[] Snakie = snakeLine.split(" ");
	                    	String[] MiHead = Snakie[3].split(",");
	                    	MiHeadX = Integer.parseInt( MiHead[0] );
	                    	MiHeadY = Integer.parseInt( MiHead[1]  );
	                    	String[] snakeNext = Snakie[4].split(",");
	                    	NextX = Integer.parseInt(snakeNext[0]);
	                    	NextY = Integer.parseInt(snakeNext[1]);
	                    	//dir : 0=north 1=south 2=west 3=east
	                    	dir = CalcDir(MiHeadX,MiHeadY,NextX,NextY);
	                    }
	                    else {
	                    	board = DrawLine.drawSnake( enemyline , 5, board);
	                    	
	                    }
                    }
                    
                   //code to move around other snakes
                }
                //printing obs and snakes
                
                board[Xapple][Yapple] = -1; //apple
                
//                printBoard();
                
//                for (int i = 0 ; i < numsnakes ; i ++) {
//                	String[] head = heads[i].split(",");
//	                int headx = Integer.parseInt(head[0]);
//	                int heady = Integer.parseInt(head[1]);
//	
//	                dist[i] = findDisttoapple(headx,heady, board, width, height , Xapple , Yapple);
//                }
//        		int move;
//                int smallestdist = dist[0];
//                for (int i = 1 ; i < numsnakes ; i++) {
//                	if (smallestdist > dist[i]) {
//                		smallestdist = dist[i];
//                	}
//                }
//                
//                if (dist[mySnakeNum] <= smallestdist) {
//                	Cell start = new Cell(MiHeadX, MiHeadY , 0 , null , dir);
//                	move = findAppleAndDetermineMove(start, board, width, height , Xapple , Yapple);
//                }
//                else {
//                	Cell start = new Cell(MiHeadX, MiHeadY, 0 , null ,dir );
//                	move = avoidObstacles(start, board, width, height );
//                }
                
                Cell start = new Cell(MiHeadX, MiHeadY , 0 , null , dir);
                int move = findAppleAndDetermineMove(start, board, width, height , Xapple , Yapple);
//                System.err.println("my move: "+move);
//                System.err.println("my direction: "+dir);
//                if (MiHeadX < Xapple) 
//                    move = 3;
//                else if (MiHeadX > Xapple) 
//                    move = 2;
//                else if (MiHeadY < Yapple) 
//                    move = 1;
//                else if (MiHeadY > Yapple) 
//                    move = 0;
//                
//                if (move == 0 && dir == 1) 
//                    move = 3;
//                else if (move == 1 && dir == 0) 
//                    move = 2;
//                else if (move == 2 && dir == 3) 
//                    move = 1;
//                else if (move == 3 && dir == 2) 
//                    move = 0;
                System.out.println(move);
                
//                T.stop();
//                System.err.println("Time taken to play: " +T.getTime() );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
    
    private int findAppleAndDetermineMove(Cell start, int[][] obstacles, int width, int height , int goalx , int goaly) {
        Queue<Cell> queue = new LinkedList<>();

        boolean[][] visited = new boolean[width][height];
        int startX = start.x;
        int startY = start.y;

        queue.add(new Cell(startX, startY, 0 , null , -1));
        visited[startX][startY] = true;

        int[] dx = {0, 0, -1, 1}; // Directions: up, down, right, left move : 0=north 1=south 2=west 3=east
        int[] dy = {-1, 1, 0, 0};
        
        Cell curr = new Cell();
        
        while (!queue.isEmpty() && visited[goalx][goaly] == false) {
            Cell current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nextX = current.x + dx[i];
                int nextY = current.y + dy[i];

                if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height && !visited[nextX][nextY] && obstacles[nextX][nextY] <=0) {
                    if (obstacles[nextX][nextY] == -1) {
                        // Found the apple, determine the move
                        curr = new Cell(nextX, nextY, current.distance + 1,current , i);
                    }
                    queue.add(new Cell(nextX, nextY, current.distance + 1,current , i));
                    visited[nextX][nextY] = true;
                }
            }
        }
        
        if (queue.isEmpty()) {
        	Cell survive = new Cell(startX, startY, 0 , null , -1);
        	return avoidObstacles(survive, board, width, height );
        }
        else {
        	ArrayList<Cell> goal = new ArrayList<>();
//        	System.err.println("Curr coords: "+ curr.x +" "+curr.y);
        	while(curr.x != startX || curr.y != startY) {
        		goal.add(curr);
        		curr = curr.Parent;
//        		System.err.println("parent coords: "+ curr.x +" "+curr.y);
        	}
//        	System.err.println("goal list size is: " + goal.size());
        	return goal.get(goal.size()-1).dir;	
        }
        	
    }
    
//    private int avoidObstacles(Cell start, int[][] obstacles, int width, int height) {
//        // Implement a strategy to avoid obstacles (walls or other snakes)
//        // Return a safe move direction (e.g., move up or to the nearest open space)
//        int[] dx = {0, 0, -1, 1}; // Directions: 0 :up, 1: down,2: left,3: right
//        int[] dy = {-1, 1, 0, 0};
//
//        
//        Cell current = start;
//        int nextX ;
//        int nextY ;
//        
//        for (int i = 0; i < 4; i++) {
//            nextX = current.x + dx[i];
//            nextY = current.y + dy[i];
//            
//            
//            if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height && obstacles[nextX][nextY] <= 0 ) {
//            	steps++;
//            	return i;
//                // Return the first safe direction found
//            }
//            
//        }
//		return 0;
//    }
    private int avoidObstacles(Cell start, int[][] obstacles, int width, int height) {
        int[] dx = {0, 0, -1, 1}; // Directions: up, down, left, right
        int[] dy = {-1, 1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int nextX = start.x + dx[i];
            int nextY = start.y + dy[i];

            if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height && obstacles[nextX][nextY] <= 0) {
                // Create a copy of the board with the potential move
                int[][] copyBoard = copyBoard(obstacles, width, height);
                copyBoard[nextX][nextY] = 9; // Assume it's part of the snake

                // Perform a flood-fill to check if the new move creates a closed loop
                if (!isClosedLoop(nextX, nextY, copyBoard, new boolean[width][height], width, height)) {
                    return i; // Return a safe direction
                }
            	return i;
            }
        }

        // No safe direction found, return the default move (up)
        return 0;
    }

    private int[][] copyBoard(int[][] board, int width, int height) {
        int[][] copy = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    private boolean isClosedLoop(int startX, int startY, int[][] board, boolean[][] visited, int width, int height) {
        // Implement a flood-fill algorithm to check for closed loops
        Stack<Cell> stack = new Stack<>();
        stack.push(new Cell(startX, startY , 0 , null , -1));

        while (!stack.isEmpty()) {
            Cell current = stack.pop();
            int x = current.x;
            int y = current.y;
			int[] dx = {0, 0, -1, 1}; // Directions: up, down, left, right
            int[] dy = {-1, 1, 0, 0};            

            if (visited[x][y] == false) {
            	for (int i = 0; i < 4; i++) {
	                int nextX = x + dx[i];
	                int nextY = y + dy[i];
	                
		            if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height && !visited[nextX][nextY] && board[nextX][nextY] <= 0) {
	                    visited[nextX][nextY] = true;
	                    stack.push(new Cell(nextX, nextY , current.distance+1 , current , i));
	                }    
            	}
    
            }
        }

        return false; //??
    }

//  if (steps %10 == 0) {
//	Random random = new Random();
//    int i = random.nextInt(4);
//    nextX = current.x + dx[i];
//    nextY = current.y + dy[i];
//    while ((nextX >= 0 && nextX < width && nextY >= 0 && nextY < height && obstacles[nextX][nextY] <= 0)==false ) {
//    	i = random.nextInt(4);
//    }	
//	steps++;
//	return i;
//    // Return the first safe direction found
//}
    
    private int findDisttoapple(int startX, int startY, int[][] obstacles, int width, int height , int goalx , int goaly) {
        Queue<Cell> queue = new LinkedList<>();

        boolean[][] visited = new boolean[width][height];

        queue.add(new Cell(startX, startY, 0 , null , -1));
        visited[startX][startY] = true;

        int[] dx = {0, 0, -1, 1}; // Directions: up, down, right, left move : 0=north 1=south 2=west 3=east
        int[] dy = {-1, 1, 0, 0};
        
        Cell curr = new Cell();
        
        while (!queue.isEmpty() && visited[goalx][goaly] == false) {
            Cell current = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nextX = current.x + dx[i];
                int nextY = current.y + dy[i];

                if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height && !visited[nextX][nextY] && (obstacles[nextX][nextY] <=0 || obstacles[nextX][nextY]==2)) {
                    if (obstacles[nextX][nextY] == -1) {
                        // Found the apple, determine the move
                        curr = new Cell(nextX, nextY, current.distance + 1,current , i);
                    }
                    queue.add(new Cell(nextX, nextY, current.distance + 1,current , i));
                    visited[nextX][nextY] = true;
                }
            }
        }
        
        if (queue.isEmpty()) {
        	return 0;
        }
        else {
        	return curr.distance;	
        }
        	
    }
    
    public int CalcDir(int MiHeadX, int MiHeadY , int NextX , int NextY) {
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
        return dir;
    }

    
}