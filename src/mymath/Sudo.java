package mymath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Sudo {

	// {0, 0, 0, 0, 0, 0, 0, 0, 0},//1
	// {0, 0, 0, 0, 0, 0, 0, 0, 0},//2
	// {0, 0, 0, 0, 0, 0, 0, 0, 0}, //3
	// {0, 0, 0, 0, 0, 0, 0, 0, 0}, //4
	// {0, 0, 0, 0, 0, 0, 0, 0, 0}, //5
	// {0, 0, 0, 0, 0, 0, 0, 0, 0}, //6
	// {0, 0, 0, 0, 0, 0, 0, 0, 0},//7
	// {0, 0, 0, 0, 0, 0, 0, 0, 0}, //8
	// {0, 0, 0, 0, 0, 0, 0, 0, 0}, //9
	//

	static class Position {
		int row;
		int col;
		public Position(int row, int col) {
			super();
			this.row = row;
			this.col = col;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + col;
			result = prime * result + row;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (col != other.col)
				return false;
			if (row != other.row)
				return false;
			return true;
		}
	}
	
	static class Cell implements Comparable<Cell> {
		int row;
		int col;
		Set<Integer> options;
		
		public Cell(int row, int col, Set<Integer> options) {
			super();
			this.row = row;
			this.col = col;
			this.options = options;
		}
		
		@Override
		public int compareTo(Cell o) {
			if (this.options.size() != o.options.size()) {
				return this.options.size() - o.options.size();
			}
			if (this.row != o.row) {
				return this.row - o.row;
			}
			return this.col - o.col;
		}

		@Override
		public String toString() {
			return "[row=" + row + ", col=" + col + ", options=" + options + "]";
		} 
	}
	
	
	public static Set<Integer> availableNumbers(int[][] arr, int row, int col) {
		Set<Integer> set = new HashSet<>();

		if (arr[row][col] != 0) {
			// 已填充
			return set;
		}
		set.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		// 同一行不能有相同的
		for (int a : arr[row]) {
			if (a != 0) {
				set.remove(a);
			}
		}
		// 同一列不能有相同的
		for (int i = 0; i < arr[0].length; i++) {
			int num = arr[i][col];
			if (num != 0) {
				set.remove(num);
			}
		}
		// 同一个宫里不能有相同的
		int startRow = (row / 3) * 3;
		int startCol = (col / 3) * 3;
		for (int i = startRow; i < startRow + 3; i++) {
			for (int j = startCol; j < startCol + 3; j++) {
				set.remove(arr[i][j]);
			}
		}
		return set;
	}

	// 返回填充的个数
	private static int fillUnique(int[][] arr, List<Cell> sortedCells) {
		int uniqueNumbers = 0;
		Map<Position, Cell> map = new HashMap<>();
		while (true) {
			int found = 0;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (arr[i][j] ==  0){
						Set<Integer> nums = availableNumbers(arr, i, j);
						if (nums.size() == 1) {
							arr[i][j] = nums.iterator().next();
							System.out.println("填充行:" + (i + 1) + "，列:" + (j + 1) + "，值为:" + arr[i][j]);
							found++;
						}
						map.put(new Position(i,j), new Cell(i,j, nums));
					}
				}
			}
			if (found == 0) {
				break;
			}
			uniqueNumbers += found;
		}
		for(Entry<Position, Cell> kv : map.entrySet()) {
			sortedCells.add(kv.getValue());
		}
		Collections.sort(sortedCells);
		return uniqueNumbers;
	}

	private static int tryFill(int[][] arr, Cell cell){
		for (Integer o : cell.options) {
			arr[cell.row][cell.col] = o;
			
		}
		return 0;
	}
	private static int missingNumbers(int[][] arr) {
		int missing = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				if (arr[i][j] == 0) {
					missing++;
				}
			}
		}
		return missing;
	}

	public static void main(String[] args) {
		int[][] arr = new int[][] { 
//			 {0, 6, 0, 0, 1, 0, 0, 7, 0},//1
//			 {0, 7, 3, 0, 0, 4, 1, 0, 0},//2
//			 {8, 0, 0, 0, 6, 0, 9, 0, 0}, //3
//			 {3, 0, 0, 0, 5, 1, 0, 0, 2}, //4
//			 {0, 9, 0, 2, 0, 6, 0, 0, 7}, //5
//			 {0, 0, 6, 0, 0, 0, 8, 1, 0}, //6
//			 {0, 0, 2, 6, 0, 0, 4, 0, 1},//7
//			 {1, 8, 0, 9, 2, 0, 0, 0, 0}, //8
//			 {6, 0, 0, 0, 0, 0, 0, 5, 9}, //9
			 {0, 2, 0, 0, 5, 0, 0, 0, 1},//1
			 {3, 0, 0, 7, 9, 0, 8, 0, 0},//2
			 {0, 8, 4, 0, 0, 0, 3, 0, 0}, //3
			 {0, 0, 0, 0, 0, 1, 0, 7, 2}, //4
			 {4, 0, 0, 0, 0, 5, 0, 0, 0}, //5
			 {0, 7, 6, 0, 0, 0, 0, 0, 0}, //6
			 {6, 0, 2, 0, 0, 0, 0, 1, 0},//7
			 {0, 4, 0, 0, 0, 0, 0, 0, 3}, //8
			 {0, 3, 0, 1, 0, 0, 0, 0, 0}, //9
		};
		System.out.println("没有确定的个数：" + missingNumbers(arr));
		// 先把哪些能唯一确定的空添上
		List<Cell> sortedOptions = new ArrayList<>();
		int uniqueNumbers = fillUnique(arr, sortedOptions);
		System.out.println("唯一确定的个数：" + uniqueNumbers);
		System.out.println("剩余没有确定的个数：" + missingNumbers(arr));
		System.out.println("顺序考虑元素");
		for (Cell o : sortedOptions) {
			System.out.println(o);
		}
	}

}
