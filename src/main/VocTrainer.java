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
	boolean showVocsAtStart = false;

	
	public VocTrainer(String filePath, String selectedSection) {
		this.filePath = filePath;
		this.selectedSection = selectedSection;
	}
	
	public VocTrainer(String filePath, String selectedSection, boolean showVocsAtStart) {
		this.filePath = filePath;
		this.selectedSection = selectedSection;
		this.showVocsAtStart = showVocsAtStart;
	}

	public static void main(String[] args) throws FileNotFoundException {
		VocTrainer vocTrainer;
		
		if(args.length == 1) {
			if(args[0].equals("help")) {
				help();
				return;
			}
			vocTrainer = new VocTrainer(args[0], null);
		}else if(args.length == 2) {
			vocTrainer = new VocTrainer(args[0], args[1]);
		}else if(args.length == 3 && args[2].equals("true")) {
			vocTrainer = new VocTrainer(args[0], args[1], true);
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
		
		if(vocs.isEmpty() || vocs.stream().allMatch(str -> str.equals(""))) {
			myReader.close();
			throw new IllegalArgumentException("The selected section had no vocabulary");
		}else if(showVocsAtStart) {
			System.out.println(vocs);
		}
		
		Scanner in = new Scanner(System.in);
		
		int i = 0;
		String voc = null;
		List<Integer> unusedIndizies = new ArrayList<>();
		for(int j = 0; j < vocs.size(); j++) {
			unusedIndizies.add(j);
		}
		
		while((voc = in.nextLine()) != null) {
			if(voc.isEmpty()) {
				if(unusedIndizies.size() == 0) {
					for(int j = 0; j < vocs.size(); j++) {
						unusedIndizies.add(j);
					}
				}
				i = new Random().nextInt(unusedIndizies.size());
				
				System.out.println(vocs.get(unusedIndizies.get(i)));
				unusedIndizies.remove(i);
			}else if(voc.equals("exit")) {
				break;
			}
			
		}
		
		myReader.close();
		in.close();
	}
	
	public static void help() {
		System.out.println("run with 1 arg: file path");
		System.out.println("run with 2 arg: file path, section");
		System.out.println("run with 3 arg: file path, section, show all vocs at the start (type 'true' if you want this)");
		Scanner in = new Scanner(System.in);
		in.hasNext();
		in.close();
	}
	
}
