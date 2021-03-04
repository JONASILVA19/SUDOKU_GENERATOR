package program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Box
 * 
 *  Box is a 3x3 matrix filled with numbers from 1 to 9 without repetition.
 *  You can see a example of how a Box looks like below:  
 *  
 * 			[1] [7] [4]
 * 			[6] [5] [8]
 *			[2] [9] [3]
 * 
 * Row
 * 
 *	Row is matrix row filled with number from 1 to 9 without repetition.
 *	You can see a example of how a Row looks like below:  
 *
 *	[3] [8] [4] [2] [6] [5] [1] [9] [7] 
 *
 * Column 
 *  
 *  Column is matrix column filled with number from 1 to 9 without repetition.
 *	You can see a example of how a Column looks like below:  
 *
 *	[2]
 *	[3]
 *	[5]
 *	[1]
 *	[8]
 *	[7]
 *	[9]
 *	[4]
 *	[6]
 *
 * Game Grid 
 * 
 * Game Grid is a 9x9 matrix that contains 9 Boxes, 9 Rows, and 9 Column, 
 * just like the example below: 
 * 
 * 	[8] [5] [4]  [3] [7] [9]  [6] [1] [2]  
 *	[3] [6] [1]  [8] [2] [4]  [9] [5] [7]  
 *	[7] [2] [9]  [1] [5] [6]  [4] [3] [8]  
 *
 *	[1] [8] [6]  [4] [9] [7]  [5] [2] [3]  
 *	[5] [7] [2]  [6] [3] [8]  [1] [9] [4]  
 *	[9] [4] [3]  [5] [1] [2]  [8] [7] [6]  
 *
 *	[2] [1] [8]  [7] [4] [5]  [3] [6] [9]  
 *	[4] [3] [7]  [9] [6] [1]  [2] [8] [5]  
 *	[6] [9] [5]  [2] [8] [3]  [7] [4] [1] 
 * 
 */

public class SudokuGridGenerator_ENG {

	static int[][] grid = new int[9][9];
	
	public static void main(String[] args) 
	{		
		while(isValid() == false)
		{
			// VERIFYING IF THE GAME GRID IS VALID
		}
		//PRINTING THE GAME GRID
		printGameGrid();
	}
	
	public static int[][] FillGridBoxes()
	{
		/** This method will fill the Game Grid with 9 Boxes filled with
		 numbers from 1 to 9 without them repeating themselves */
		
		List<Integer> boxNums = new ArrayList<Integer>();
		int index;
		
		// Filling the boxNums with numbers from 1 to 9
		for(int a = 1; a <= 9; a++) boxNums.add(a);
		
		for(int y = 0; y < 9; y+=3)
		{
			for (int x = 0; x < 9; x+=3)
			{
				Collections.shuffle(boxNums);
				index = 0;
				
				for(int i = y; i < y+3; i++)
				{
					for(int j = x; j < x+3; j++)
					{
						grid[i][j] = boxNums.get(index);
						index++;
					}
				}
			}
		}
		return grid;
	}
	
	public static int[][] sortGridOut()
	{
		/** This method will receive and organize the Rows and Columns of the Game Grid
		 	that comes from the fillGridBoxes method in a way that the Rows and Columns
		 	can't have any duplicated number */
		
		FillGridBoxes();// Get the Game Grid from this method
		
		List<Integer> checker = new ArrayList<Integer>();
		int container;
		
		
		//SORT ROW
		for(int y = 0; y < 9; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				// Filling checker with all numbers from the Row Y
				for(int a = 0; a < 9; a++)checker.add(grid[y][a]);
				// Remove the value in position X of the Row Y
				checker.remove(x);
				
				if(checker.contains(grid[y][x]))
				{
					container = grid[y][x]; // In order to make this value exchangeable
					
					// This loop will check if there is a valid number inside the
					// Box from coordinate Y, X
					CHECK:for(int i = y+1; i < y-y%3+3; i++)
					{
						for(int j = x-x%3 ; j < x-x%3 + 3; j++)
						{
							if(!checker.contains(grid[i][j]))
							{
								// If there is a valid number inside the Box,
								// this number will change positions with the
								// invalid number and the loop will stop
								
								/* Example of the number exchange:
								 * 
								 *	 *4 9 7 5 8 1 3 *4 2 
								 *	  2 5 6
								 *	  1 8 3 
								 *
								 * The number 4 in the first Row is a duplicated number,
								 * so it will change positions with a valid number from
								 * the two Rows below. Like this:
								 * 
								 *   6 9 7 5 8 1 3 4 2 
								 *	 2 5 4
								 *	 1 8 3 
								 * 
								 *	 4 -> 6
								*/
								
								grid[y][x] = grid[i][j];
								grid[i][j] = container;
								break CHECK;
							}
						}
					}
				}
				checker.clear();
			}
		}
		
		checker.clear(); // In order to use it again
		
		//SORT COLUMN
		for(int y = 0; y < 9; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				// Filling checker with all numbers from the Column X
				for(int a = 0; a < 9; a++)checker.add(grid[a][x]);
				// Remove the value in position Y of the Column Y
				checker.remove(y);
				
				if(checker.contains(grid[y][x]))
				{
					container = grid[y][x];// In order to make this value exchangeable
					
					// This loop will check if there is a valid number in the Row Y
					CHECK:for(int i = x; i < x - x%3+3; i++)
					{
						if(!checker.contains(grid[y][i]))
						{
							// If there is a valid number in the Row Y,
							// this number will change positions with the
							// invalid number and the loop will stop
							
							/* Example of the number exchange:
							 * 
							 *  *9 4 5
							 *   3 
							 *   7 
							 *   6 
							 *   4 
							 *   2 
							 *   1 
							 *  *9 
							 *   8 
							 *
							 * The number 9 in the first Row is a duplicated number,
							 * so it will change positions with a valid number from
							 * its Row. Like this:
							 * 
							 *   5 4 9
							 *   3 
							 *   7 
							 *   6 
							 *   4 
							 *   2 
							 *   1 
							 *   9 
							 *   8 
							 *   
							 *   9 -> 5
							*/
							
							grid[y][x] = grid[y][i];
							grid[y][i] = container;
							break CHECK;
						}
					}
				}
				checker.clear();
			}
		}
		return grid;
	}
	
	public static boolean isValid()
	{
		/** This method will receive the Game Grid coming from the organizeGridOut
		 	method and then it will verify if the Game Grid is valid */
		
		sortGridOut();// Get the Game Grid from this method
		
		List<Integer>checker = new ArrayList<Integer>();

		
		// Check Row
		for(int y = 0; y < 9; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				for(int a = 0; a < 9; a++)checker.add(grid[y][a]);
				checker.remove(x);
				if(checker.contains(grid[y][x]))
				{
					return false;
				}
				checker.clear();
			}
		}
		
		checker.clear(); // In order to use it again
		
		//Check Column
		for(int y = 0; y < 9; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				for(int a = 0; a < 9; a++)checker.add(grid[a][x]);
				checker.remove(y);
				if(checker.contains(grid[y][x]))
				{
					return false;
				}
				checker.clear();
			}
		}
		
		return true;
	}
	
	public static void printGameGrid()
	{
		/**This method will print the Game Grid */
		
		for(int y = 0; y < 9; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				System.out.print("[" + grid[y][x] + "]");
				
				if(x%3 == 2)System.out.print(" ");
			}
			System.out.println();
			if(y%3 == 2)System.out.println();
		}
	}

}
