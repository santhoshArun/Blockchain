package hash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class HashingPrg {
	
	static ArrayList<Block> block = new ArrayList<Block>();

	public static void main(String[] args) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		String message1, firstPreviousHash = "empty";
		int i = 2, difficulty = 1;
		
		//genesis block
		System.out.print("Enter the data: ");
		message1 = sc.nextLine();
		
		
//		block.add(new Block(message1, firstPreviousHash));
		addBlock(message1, firstPreviousHash, difficulty);
		
		System.out.println("data: " + message1 + "\nhash: " + block.get(block.size()-1).hash + "\nprevious hash: " + block.get(block.size()-1).previousHash + "\n\n");
		
		FileWriter fileWritermsg1 = new FileWriter("E:\\blockchain\\block1.txt", true);
		PrintWriter pwmsg = new PrintWriter(fileWritermsg1);
//		fileWritermsg1.write(String.valueOf(Block.nonce));
		pwmsg.println();
		fileWritermsg1.write(message1);
		fileWritermsg1.close();
		
		FileWriter fileWriterhash = new FileWriter("E:\\blockchain\\hash.txt", true);
		fileWriterhash.write(block.get(0).hash + "\n");
		PrintWriter pw = new PrintWriter(fileWriterhash);
		pw.println();
		fileWriterhash.close();
		
		//other blocks
		while(true) {
			
			int count = 1;
			boolean condition = true;
			System.out.print("Enter the data: ");
			message1 = sc.nextLine();
//			block.add(new Block(message1, block.get(block.size()-1).hash));
			addBlock(message1, block.get(block.size()-1).hash, difficulty);
			
			//comparing previous block's hash and current block's hash
			while(count < block.size()) {
				
				BufferedReader br = new BufferedReader(new FileReader("E:\\blockchain\\block" + count +".txt")); 
				String text = "";	
				String preNonce = null;
				for(int i1 = 0; i1 < 1 ; i1++) {
					preNonce = br.readLine();
				}
				br.close();
				BufferedReader brw = new BufferedReader(new FileReader("E:\\blockchain\\block" + count +".txt")); 
				for(int i1 = 0; i1 < 2 ; i1++) {
					text = brw.readLine();
				}
				brw.close();
//				String text = new String(Files.readAllBytes(Paths.get("E:\\blockchain\\block" + count +".txt")), "UTF-8");

				String xyz = text + block.get(count-1).previousHash + Long.parseLong(preNonce);
				String zyx;
				try {
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					byte[] hash = digest.digest(xyz.getBytes("UTF-8"));
					zyx = DatatypeConverter.printHexBinary(hash);
				} catch(Exception e) {
					throw new RuntimeException(e);
				}

				Block now = new Block(text, block.get(count-1).previousHash, Long.parseLong(preNonce));

				if(!(zyx.equalsIgnoreCase(block.get(count).previousHash))) {
					condition = false;
					System.out.println("hash: " + now.hash + "\n" + "previous hash: " + block.get(count).previousHash);
				}
				count++;
			}
			
			//storing data in blocks
			if(condition) {
				
				System.out.println("data: " + message1 + "\nhash: " + block.get(block.size()-1).hash + "\nprevious hash: " + block.get(block.size()-1).previousHash + "\n\n");
				
				FileWriter writer = new FileWriter("E:\\blockchain\\block" + i + ".txt", true);
				PrintWriter pwwr = new PrintWriter(writer);
//				writer.write(String.valueOf(Block.nonce));
				pwwr.println();
				writer.write(message1);
				writer.close();
				
				FileWriter fileWriterhash1 = new FileWriter("E:\\blockchain\\hash.txt", true);
				fileWriterhash1.write(block.get(block.size()-1).hash + "\n");
				PrintWriter pwl = new PrintWriter(fileWriterhash1);
				pwl.println();
				fileWriterhash1.close();
				i++;
			} else {
				System.out.println("Data altered");
				break;
			}
		}
	}
	
	public static void addBlock(String message, String previousHash, int difficulty) throws IOException {
		Block.mineBlock(message, previousHash, difficulty);
	}
}