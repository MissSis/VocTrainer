package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class VocTrainer {
	
	String filePath;
	String selectedSection;
	boolean stopAtSectionEnd = false;

	
	public VocTrainer(String filePath, String selectedSection) {
		this.filePath = filePath;
		this.selectedSection = selectedSection;
	}

	public static void main(String[] args) throws FileNotFoundException {
		VocTrainer vocTrainer;
		
		if(args.length == 1) {
			vocTrainer = new VocTrainer(args[0], null);
		}else if(args.length == 2) {
			vocTrainer = new VocTrainer(args[0], args[1]);
		}else {
			throw new IllegalArgumentException();
		}
		
		try {
			vocTrainer.trainVocabulary();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void testHardcoded() {
		try {
			new VocTrainer("D:\\Documents\\test.txt", null).trainVocabulary();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void trainVocabulary() throws FileNotFoundException {
		File myObj = new File(filePath);
		Scanner myReader = new Scanner(myObj);
		
		List<String> vocs = new ArrayList<>();
		
		while (myReader.hasNextLine()) {
			String line = myReader.nextLine();
			
			if(line.startsWith("#")) {
				if(selectedSection != null && !selectedSection.isEmpty() && line.substring(1).equalsIgnoreCase(selectedSection)) {
					stopAtSectionEnd = true;
					vocs.clear();
				}
				continue;
			}
			
			if(!line.isEmpty()) {
				vocs.add(line);
			}else if(stopAtSectionEnd) {
				break;
			}
		}
		
		System.out.println(vocs);
		
		Scanner in = new Scanner(System.in);
		
		int i = 0;
		String voc = null;
		List<Integer> usedIndizies = new ArrayList<>();
		
		while((voc = in.nextLine()) != null) {
			if(voc.isEmpty()) {
				i = new Random().nextInt(vocs.size());
				if(usedIndizies.size() == vocs.size()) {
					usedIndizies.clear();
				}
				while(usedIndizies.contains(i)) {
					i = new Random().nextInt(vocs.size());
				}
				usedIndizies.add(i);
				System.out.println(vocs.get(i));
			}else if(voc.equals("exit")) {
				break;
			}
			
		}
		
		myReader.close();
		in.close();
	}
	
}
