package com.wzq.client;

import com.wzq.client.view.*;
import com.wzq.client.*;

public class Game {
	private Game() {}
	private static final Game game = new Game();
	public static Game get(){
		return game;
	}
	
	private static final int N = 15;
	private static final int E = -1;
	private static final int B = 0;
	private static final int W = 1;
	private static final int WIN = 5;
	private static int[][] board = new int[N][N];
	
	public static int status = -1;
	public static int color = -1;
	public static String localIP = "";
	public static String remoteIP = "";
	public static int x = -1,y = -1;
	public BoardController boardcontroller;
	
	public void init(BoardController bc) {
		for(int i=0;i<N;++i)
			for(int j=0;j<N;++j)
				board[i][j] = E;
		status = color = x = y = -1;
		localIP = remoteIP = "";
		boardcontroller = bc;
		new Thread(new Client("127.0.0.1",7777)).start();;
	}
	
	public int get(int x,int y){
		return board[x][y];
	}

	public boolean put(int x,int y,int c){
		if(board[x][y] != E) return false;
		board[x][y] = c;
		if(c != color)
			boardcontroller.putChess(x, y, c);
		else{
			this.x = x;
			this.y = y; 
		}
		return true;
	}

	public boolean JudgeWin(int x,int y,int c){
		int cnt = -1;
		for(int i=x;i<N;++i) 
			if(board[i][y] == c) ++cnt;
			else break;
		for(int i=x;i>=0;--i)
			if(board[i][y] == c) ++cnt;
			else break;
		if(cnt >= WIN) return true;

		cnt = -1;
		for(int i=y;i<N;++i) 
			if(board[x][i] == c) ++cnt;
			else break;
		for(int i=y;i>=0;--i)
			if(board[x][i] == c) ++cnt;
			else break;
		if(cnt >= WIN) return true;

		cnt = -1;
		for(int i=x,j=y;i<N && j<N;++i,++j)
		{
			if(board[i][j] == c) ++cnt;
			else break;
		}
		for(int i=x,j=y;i>=0 && j>=0;++i,++j)
		{
			if(board[i][j] == c) ++cnt;
			else break;
		}
		if(cnt >= WIN) return true;
		
		cnt = -1;
		for(int i=x,j=y;i<N && j>=0;++i,--j)
		{
			if(board[i][j] == c) ++cnt;
			else break;
		}
		for(int i=x,j=y;i>=0 && j<N;--i,++j)
		{
			System.out.println("LOOK: " + i +" "+ j);
			if(board[i][j] == c) ++cnt;
			else break;
		}
		if(cnt >= WIN) return true;
		
		return false;
	}
}