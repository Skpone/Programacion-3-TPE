package main;

public class Main {

	public static void main(String[] args) {

		String path = "../datasets";
		CSVReader reader = new CSVReader(path);
		reader.read();
		
	}

}
