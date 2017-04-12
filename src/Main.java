import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	public static int N = 0; 
	public static int biggest = 0;
	public static final int MOVE_LEFT = 1;
	public static final int MOVE_RIGHT = 2;
	public static final int MOVE_UP = 3;
	public static final int MOVE_DOWN = 4;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		sc.nextLine();

//		if (N>20 || N<1) {
//			return;
//		}
		
		int [][] NN = new int[N][N];
		
		for (int i=0 ; i<N ; i++){ // insert array
			
			int [] N1 = new int[N];
			
			
			String s = sc.nextLine();
			int j = 0;
			for (String str : s.split(" ")){
				int a = Integer.parseInt(str);
//				if (a<0 || a==1 || a>1024 || !isPowerOfTwo(a)) {
//					return;
//				}
				if (biggest < a) {
					biggest = a;
				}
				N1[j++]=a;
			}
//			if (j != N) {
//				return;
//			}
			
			
			
			
//			Random r = new Random();
//			for (int j = 0 ; j<N ; j++) {
//				N1[j] = (int)Math.pow(2, r.nextInt(10)+1);
//				System.out.printf("%4d ",N1[j]);
//			}
//			System.out.println();
			
			
			
			NN[i] = N1;
		}
		
		move (MOVE_LEFT, arrayClone(NN), 0);
		move (MOVE_RIGHT, arrayClone(NN), 0);
		move (MOVE_UP, arrayClone(NN), 0);
		move (MOVE_DOWN, arrayClone(NN), 0);
		
		
//		move (MOVE_LEFT, arrayClone(NN), 0, new ArrayList<Integer>());
//		move (MOVE_RIGHT, arrayClone(NN), 0, new ArrayList<Integer>());
//		move (MOVE_UP, arrayClone(NN), 0, new ArrayList<Integer>());
//		move (MOVE_DOWN, arrayClone(NN), 0, new ArrayList<Integer>());
		
		System.out.print(biggest);
		
		return;
	}
	
	public static boolean isPowerOfTwo (int arg){
		byte count = 0;
		byte twoPower = 0;
		while (twoPower < 10) {
			if (((arg >> twoPower)&0x01)==1) {
				count++;
			}
			if (count>1) {
				return false;
			}
			twoPower++;
		}
		return true;
	}
	
	
	public static int[][] moveLeft (int[][] arg) {
		int[][] nextNN = new int[N][N];
		
		for (int i = 0 ; i<N ; i++) {
			nextNN[i] = moveLeftRow(arg[i], true);
		}
		
		return nextNN;
	}
	
	public static int[][] moveRight (int[][] arg) {
		int[][] nextNN = new int[N][N];
		
		for (int i = 0 ; i<N ; i++) {
			nextNN[i] = moveLeftRow(arg[i], false);
		}
		
		return nextNN;
	}	
	
	public static int[][] moveUp (int[][] arg) {
		int[][] nextNN = new int[N][N];
		
		nextNN = arg;
		for (int i = 0 ; i<N ; i++) {
			nextNN = moveUpCol(nextNN, true, i);
		}
		
		return nextNN;
	}
	
	public static int[][] moveDown (int[][] arg) {
		int[][] nextNN = new int[N][N];
		
		nextNN = arg;
		for (int i = 0; i<N ; i++) {
			nextNN = moveUpCol(nextNN, false, i);
		}
		
		return nextNN;
	}
	
	public static int[] moveLeftRow (int[] arg, boolean alignLeft) {
		int [] nextNRow = new int[N];
		if (alignLeft) {
			int nextI = 0;
			
			int lastNonZero = 0;
			outer: for (int i = 0 ; i<N ; i++) {
				if (arg[i] == 0) {
					continue outer;
				}
				
				lastNonZero = i;
				
				inner: for (int j = i+1; j<N ; j++) {
					if (arg[j]!=0 && arg[i]!=arg[j]) {
						nextNRow[nextI]=arg[i];
						nextI++;
						break inner;
					} else if (arg[i]==arg[j]) {
						nextNRow[nextI]=(int) (arg[i]<<1);
						arg[i]=0;
						arg[j]=0;
						i=j;
						if (biggest < nextNRow[nextI]) {
							biggest = nextNRow[nextI];
						}
						nextI++;
						
						break inner;
					}
				}
				
			}
			
			nextNRow[nextI] = arg[lastNonZero];
			
			return nextNRow;
		} else {
			int nextI = N-1;
			int lastNonZero = N-1;
			outer: for (int i = N-1 ; i>=0 ; i--) {
				if (arg[i] == 0) {
					continue outer;
				}
				lastNonZero = i;
				
				inner: for (int j = i-1; j>=0 ; j--) {
					if (arg[j]!=0 && arg[i]!=arg[j]) {
						nextNRow[nextI]=arg[i];
						nextI--;
						break inner;
					} else if (arg[i]==arg[j]) {
						nextNRow[nextI]=(int) (arg[i]<<1);
						arg[i]=0;
						arg[j]=0;
						i=j;
						if (biggest < nextNRow[nextI]) {
							biggest = nextNRow[nextI];
						}
						nextI--;
						
						break inner;
					}
				}
			}
			nextNRow[nextI] = arg[lastNonZero];
			
			return nextNRow;
		}
	}
	
	public static int[][] moveUpCol (int[][] arg, boolean alignUp, int colNum) {
		int [][] nextColN = new int[N][N];
		int [] temp = new int[N];
		
		nextColN = arg;
		for (int i = 0 ; i<N ; i++) {
			temp[i] = arg[i][colNum];
			nextColN[i][colNum] = 0;
		}

		if (alignUp) {
			int nextI = 0;
			int lastNonZero = 0;
			outer: for (int i = 0 ; i<N ; i++) {
				
				if (temp[i] == 0) {
					continue outer;
				}
				lastNonZero = i;
				
				inner: for (int j = i+1; j<N ; j++) {
					if (temp[j]!=0 && temp[i]!=temp[j]) {
						nextColN[nextI][colNum]=temp[i];
						nextI++;
						break inner;
					} else if (temp[i]==temp[j]) {
						nextColN[nextI][colNum]=(int) (temp[i]<<1);
						temp[i]=0;
						temp[j]=0;
						i=j;
						
						if (biggest < nextColN[nextI][colNum]) {
							biggest = nextColN[nextI][colNum];
						}
						nextI++;

						break inner;
					}
				}
			}
			
			nextColN[nextI][colNum] = temp[lastNonZero];
			
			return nextColN;
		} else {
			int nextI = N-1;
			int lastNonZero = N-1;
			outer: for (int i = N-1 ; i>=0 ; i--) {
				
				if (temp[i] == 0) {
					
					continue outer;
				}
				lastNonZero = i;
				
				inner: for (int j = i-1; j>=0 ; j--) {
					if (temp[j]!=0 && temp[i]!=temp[j]) {
						nextColN[nextI][colNum]=temp[i];
						nextI--;
						break inner;
					} else if (temp[i]==temp[j]) {
						nextColN[nextI][colNum]=(int) (temp[i]<<1);
						temp[i]=0;
						temp[j]=0;
						i=j;
						
						if (biggest < nextColN[nextI][colNum]) {
							biggest = nextColN[nextI][colNum];
						}
						nextI--;
						
						break inner;
					}
				}
			}
			
			nextColN[nextI][colNum] = temp[lastNonZero];
			
			return nextColN;
		}
	}
	
	public static void move (int direction, int arg[][], int depth){
//	public static void move (int direction, int arg[][], int depth, ArrayList<Integer> history) {
		int[][] nextNN = new int[N][N];
		
//		System.out.println();
//		for (int i = 0 ; i<N ; i++) {
//			
//			for (int j = 0 ; j<N ; j++) {
//				System.out.print(arg[i][j]+" ");
//			}
//			System.out.println();
//		}
		
		//in order to prevent doing meaningless job
		boolean moveLeftTrying = true;
		boolean moveRightTrying = true;
		boolean moveUpTrying = true;
		boolean moveDownTrying = true;

		switch(direction) {
		case MOVE_LEFT: nextNN = moveLeft(arg);
						if (Arrays.deepEquals(arg, nextNN)){
							moveLeftTrying = false;
							
						}//in order to prevent doing meaningless job
						break;
		case MOVE_RIGHT: nextNN = moveRight(arg);
						if (Arrays.deepEquals(arg, nextNN)){
							moveRightTrying = false;
							
						}//in order to prevent doing meaningless job
						break;
		case MOVE_UP: nextNN = moveUp(arg);
						if (Arrays.deepEquals(arg, nextNN)){
							moveUpTrying = false;
							
						}//in order to prevent doing meaningless job
						break;
		case MOVE_DOWN: nextNN = moveDown(arg);
						if (Arrays.deepEquals(arg, nextNN)){
							moveDownTrying = false;
							
						}//in order to prevent doing meaningless job
						break;
		}
		
//		Scanner sc = new Scanner(System.in);
//		sc.nextLine();
//		history.add(direction);
//		System.out.println();
//		for (int i = 0 ; i<history.size() ; i++) {
//			System.out.print(history.get(i)+" ");
//		}
//		System.out.println();
//		for (int i = 0 ; i<N ; i++) {
//			
//			for (int j = 0 ; j<N ; j++) {
//				System.out.printf("%4d ",nextNN[i][j]);
//			}
//			System.out.println();
//		}
//		
//		if (!moveLeftTrying || !moveRightTrying || !moveUpTrying || !moveDownTrying) {
//			System.out.println("nothing to do!");
//		}
		
	
		if (depth == 4) {
			return;
		} else {
			depth++;
			//in order to prevent doing meaningless job
			if (moveLeftTrying) {move (MOVE_LEFT, arrayClone(nextNN), depth);}
			if (moveRightTrying) {move (MOVE_RIGHT, arrayClone(nextNN), depth);}
			if (moveUpTrying) {move (MOVE_UP, arrayClone(nextNN), depth);}
			if (moveDownTrying) {move (MOVE_DOWN, arrayClone(nextNN), depth);}
			
			//in order to trace operations
//			if (moveLeftTrying) {move (MOVE_LEFT, arrayClone(nextNN), depth, (ArrayList<Integer>)history.clone());}
//			if (moveRightTrying) {move (MOVE_RIGHT, arrayClone(nextNN), depth, (ArrayList<Integer>)history.clone());}
//			if (moveUpTrying) {move (MOVE_UP, arrayClone(nextNN), depth, (ArrayList<Integer>)history.clone());}
//			if (moveDownTrying) {move (MOVE_DOWN, arrayClone(nextNN), depth, (ArrayList<Integer>)history.clone());}
			
//			{move (MOVE_LEFT, arrayClone(nextNN), depth);}
//			{move (MOVE_RIGHT, arrayClone(nextNN), depth);}
//			{move (MOVE_UP, arrayClone(nextNN), depth);}
//			{move (MOVE_DOWN, arrayClone(nextNN), depth);}
			
			return;
		}
	}
	
	public static int[][] arrayClone (int[][] arg) {
		int [][] ret = new int[N][N];
		for (int i = 0 ; i<N ; i++) {
			int [] arr = new int[N];
			System.arraycopy(arg[i], 0, arr, 0, N);
			ret[i] = arr;
		}
		return ret;
	}
}
