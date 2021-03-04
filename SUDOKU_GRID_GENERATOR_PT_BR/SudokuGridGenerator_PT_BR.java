package program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Caixa
 *
 * Caixa é uma matriz 3x3 preenchida com números de 1 a 9 sem repetição.
 * Você pode ver um exemplo de como uma Caixa se parece logo abaixo: 
 *  
 * 			[1] [7] [4]
 * 			[6] [5] [8]
 *			[2] [9] [3]
 * 
 * Fileira
 *
 * Fileira é a linha da matriz preenchida com números de 1 a 9 sem repetição.
 * Você pode ver um exemplo de como uma Fileira se parece logo abaixo:
 *
 *	[3] [8] [4] [2] [6] [5] [1] [9] [7] 
 *
 * Coluna
 *
 * A Coluna é uma coluna da matriz preenchida com números de 1 a 9 sem repetição.
 * Você pode ver um exemplo de como uma Coluna se parece logo abaixo:
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
 * Game Grid é uma matriz 9x9 que contém 9 Caixas, 9 Linhas e 9 Colunas,
 * exatamente como o exemplo logo abaixo:
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

public class SudokuGridGenerator_PT_BR {

	static int[][] grid = new int[9][9];
	
	public static void main(String[] args) 
	{		
		while(isValid() == false)
		{
			// VERIFICANDO SE O GAME GRID É VALIDO
		}
		// IMPRIMINDO O GAME GRID
		printGameGrid();
	}
	
	public static int[][] FillGridBoxes()
	{
		/** Este método irá preencher a grade do jogo com 9 caixas preenchidas com
		números de 1 a 9 sem que se eles repitam */ 
		
		List<Integer> boxNums = new ArrayList<Integer>();
		int index;
		
		// Preenchendo a boxNums com números de 1 a 9
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
		/** Este método irá receber e organizar as linhas e colunas do Game Grid
		que vem do método fillGridBoxes de forma que as linhas e colunas
		não podem ter nenhum número duplicado */
		
		FillGridBoxes();// Recebe o Game Grid desse método
		
		List<Integer> checker = new ArrayList<Integer>();
		int container;
		
		
		//ORGANIZAR FILEIRA
		for(int y = 0; y < 9; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				// Preencha o checker com todos os números da Fileira Y
				for(int a = 0; a < 9; a++)checker.add(grid[y][a]);
				// Remova o valor na posição X da Fileira Y
				checker.remove(x);
				
				if(checker.contains(grid[y][x]))
				{
					container = grid[y][x]; // Para tornar este valor trocável
					
					// Este loop irá verificar se há um número válido dentro do
					// Caixa da coordenada Y, X
					CHECK:for(int i = y+1; i < y-y%3+3; i++)
					{
						for(int j = x-x%3 ; j < x-x%3 + 3; j++)
						{
							if(!checker.contains(grid[i][j]))
							{
								// Se houver um número válido dentro da Caixa,
								// este número mudará de posição com o
								// número inválido e o loop irá parar
								
								/* Exemplo de troca de número:
								 * 
								 *	 *4 9 7 5 8 1 3 *4 2 
								 *	  2 5 6
								 *	  1 8 3 
								 *
								 * O número 4 na primeira Fileira é um número duplicado,
								 * então ele mudará de posição com um número válido das
								 * duas Fileiras abaixo. Como esse exemplo:
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
		
		checker.clear(); // Para reaproveitar a LISTA
		
		// ORGANIZAR A COLUNA
		for(int y = 0; y < 9; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				// Preencha o checker com todos os números da Coluna X
				for(int a = 0; a < 9; a++)checker.add(grid[a][x]);
				// Remova o valor na posição Y da Coluna X
				checker.remove(y);
				
				if(checker.contains(grid[y][x]))
				{
					container = grid[y][x]; // Para tornar este valor trocável
					
					// Este loop irá verificar se há um número válido na Fileira Y
					CHECK:for(int i = x; i < x - x%3+3; i++)
					{
						if(!checker.contains(grid[y][i]))
						{
							// Se houver um número válido na Fileira Y,
							// este número mudará de posição com o
							// número inválido e o loop irá parar
							
							/*  Exemplo de troca de número:
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
							 * O número 9 na primeira Fileira é um número duplicado,
							 * então ele mudará de posição com um número válido da
							 * sua Fileira. Como esse exemplo:
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
		/** Este método receberá o Game Grid proveniente do metodo organizeGridOut
		e então verificará se o Game Grid é válido */
		
		sortGridOut();// Recebe o Game Grid desse método
		
		List<Integer>checker = new ArrayList<Integer>();

		
		// Checar Fileira
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
		
		checker.clear(); // Para reaproveitar a LISTA
		
		//Checar Coluna
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
		/**Esse irá imprimir o Game Grid */
		
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
