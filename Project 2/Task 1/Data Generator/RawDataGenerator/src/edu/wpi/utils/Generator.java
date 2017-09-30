package edu.wpi.utils;




public class Generator {
	private static int REGION_ID_INDENTIFIER = 1;
	//private static Random random = new Random();
	
	public static int genAccessId()
	{
		int Id =  REGION_ID_INDENTIFIER;
		REGION_ID_INDENTIFIER++;
		return Id;
	}
	
}
