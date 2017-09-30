package edu.wpi.package1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Classification {

	public static void main(String[] args) throws Exception {
		
		int x = 15;
		int y = 7;
		checkIfPointInRectangle(x,y);
		
	}
	
	private static void checkIfPointInRectangle(int x, int y) throws FileNotFoundException {
		//<15,915,9934,920,9954>
		String region = "<14,3,3,15,9>";
		
		String str = region.replace("<","");
		String str1 = str.replace(">","");
		
		String[] coordinates = str1.split(",");
		
		int x1 = Integer.parseInt(coordinates[1]);
		int y1 = Integer.parseInt(coordinates[2]);
		int x2 = Integer.parseInt(coordinates[3]);
		int y2 = Integer.parseInt(coordinates[4]);
		
		Boolean insideRectangle = false;
		
		if( (x>x1) && (y>y1) && (x<x2) && (y<y2)) 
		{
			insideRectangle = true;
		}else {
			insideRectangle = false;
		}
	
		PrintWriter out = new PrintWriter("Result.txt");
		if(insideRectangle) {
			//out.println("<" + Integer.parseInt(coordinates[0]) + "," + "(" + x + "," + y+ ")" +">");
			System.out.print("<" + Integer.parseInt(coordinates[0]) + "," + "(" + x + "," + y+ ")" +">");
		}else {
			System.out.print("point out of bounds");
		}
			
		
		
	}






}
