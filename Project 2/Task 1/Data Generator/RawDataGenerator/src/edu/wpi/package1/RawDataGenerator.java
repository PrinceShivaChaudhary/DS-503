package edu.wpi.package1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import edu.wpi.utils.*;

public class RawDataGenerator {

	private static final char DEFAULT_SEPARATOR = ',';
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Problem 1 task 1: data generation");
        System.out.println("------------------------------------");

        generateDataPoints(10000);
        System.out.println("data points generation done.");
        System.out.println("");
        
        
        generateRactanglePoints(10000);
        System.out.println("regions generation done.");
        System.out.println("");
        
	}

	
	private static void generateDataPoints( int k) throws Exception {
        
        int MIN_VALUE = 1;
        int MAX_VALUE = 10000;

        System.out.println(String.format("---------- Generating %d data points now ---------", k));
        PrintWriter out = new PrintWriter("DataPoints.txt");
        
        for (int i=0; i<=1000000; i++) {
            //String x = Integer.toString(getRandomIntegerBetween(MIN_VALUE, MAX_VALUE));
            //String y = Integer.toString(getRandomIntegerBetween(MIN_VALUE, MAX_VALUE));
            out.println("<" + getRandomIntegerBetween(MIN_VALUE, MAX_VALUE) + "," + getRandomIntegerBetween(MIN_VALUE, MAX_VALUE) + ">");
            
        }
        System.out.print("Data generated Successfully");
        
    }

    private static int getRandomIntegerBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    
    private static void generateRactanglePoints(int k) throws Exception {
    	
    	int MIN_VALUE = 1;
        int MAX_VALUE = 10000;
        int MIN_VALUE_FOR_WIDTH = 1;
        int MAX_VALUE_FOR_WIDTH = 5;
        int MIN_VALUE_FOR_HEIGHT = 1;
        int MAX_VALUE_FOR_HEIGHT = 20;
        
    	System.out.println(String.format("---------- Generating %d regions now ---------", k));
        PrintWriter out = new PrintWriter("Regions.txt");
    	
    	//creating 10000 regions
    	for (int i=0; i<=10000; i++) {
    		int accessId = Generator.genAccessId();
    		int x1 = getRandomIntegerBetween(MIN_VALUE, MAX_VALUE);
            String x1String = Integer.toString(x1);
            int y1 = getRandomIntegerBetween(MIN_VALUE, MAX_VALUE);
            String y1String = Integer.toString(y1);
            
            int width = getRandomIntegerBetween(MIN_VALUE_FOR_WIDTH, MAX_VALUE_FOR_WIDTH);
            int height = getRandomIntegerBetween(MIN_VALUE_FOR_HEIGHT, MAX_VALUE_FOR_HEIGHT);
            
            int x2 = x1 + width;
            int y2 = y1 + height;
   
            out.println("<" + accessId + ","+ x1 + "," + y1 + "," + x2 + "," + y2 +">");
            
        }
        System.out.print("Regions generated Successfully");
    	
    }


    
    
    
}
