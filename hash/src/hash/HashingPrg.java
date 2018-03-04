package hash;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class HashingPrg {
	
	static ArrayList<Block> block = new ArrayList<Block>();
	
	public static void main(String[] args) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		String message1, firstPreviousHash = "empty";
		int i = 2;
		
		//genesis block
		System.out.print("Enter the data: ");
		message1 = sc.nextLine();
		
		block.add(new Block(message1, firstPreviousHash));
		
		System.out.println("data: " + message1 + "\nhash: " + block.get(block.size()-1).hash + "\nprevious hash: " + block.get(block.size()-1).previousHash + "\n\n");
		
		FileWriter fileWritermsg1 = new FileWriter("E:\\blockchain\\block1.txt");
		fileWritermsg1.write(message1);
		fileWritermsg1.close();
		
		FileWriter fileWriterhash = new FileWriter("E:\\blockchain\\hash.txt", true);
		fileWriterhash.write(block.get(0).hash + "\n");
		fileWriterhash.close();
		
		//other blocks
		while(true) {
			
			int count = 1;
			boolean condition = true;
			System.out.print("Enter the data: ");
			message1 = sc.nextLine();
			block.add(new Block(message1, block.get(block.size()-1).hash));
			
			//comparing previous block's hash and current block's hash
			while(count < block.size()) {
				String text = new String(Files.readAllBytes(Paths.get("E:\\blockchain\\block" + count +".txt")), "UTF-8");
				Block now = new Block(text, block.get(count-1).previousHash);
				if(!(now.hash.equalsIgnoreCase(block.get(count).previousHash))) {
					condition = false;
					System.out.println("hash: " + now.hash + "\n" + "previous hash: " + block.get(count).previousHash);
				}
				count++;
			}
			
			//storing data in blocks
			if(condition) {
				
				System.out.println("data: " + message1 + "\nhash: " + block.get(block.size()-1).hash + "\nprevious hash: " + block.get(block.size()-1).previousHash + "\n\n");
				
				FileWriter writer = new FileWriter("E:\\blockchain\\block" + i + ".txt");
				writer.write(message1);
				writer.close();
				
				FileWriter fileWriterhash1 = new FileWriter("E:\\blockchain\\hash.txt", true);
				fileWriterhash1.write(block.get(block.size()-1).hash + "\n");
				fileWriterhash1.close();
				i++;
			} else {
				System.out.println("Data altered");
				break;
			}
		}
	}
	
}