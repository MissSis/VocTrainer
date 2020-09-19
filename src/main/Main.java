package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException {
		File myObj = new File("D:\\voc englisch.txt");
		Scanner myReader = new Scanner(myObj);
		
		List<String> vocs = new ArrayList<>();
		
		while (myReader.hasNextLine()) {
			vocs.add(myReader.nextLine());
		}
		
		
		Scanner in = new Scanner(System.in);
		
		int i = 0;
		
		while(in.hasNext()) {
			if(in.nextLine().endsWith("exit")) {
				break;
			}
			i = new Random().nextInt(vocs.size());
			System.out.println(vocs.get(i));
		}
	}

}
