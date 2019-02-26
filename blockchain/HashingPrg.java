package blockchain;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class HashingPrg {

	public static void main(String[] args) throws Exception {
		
		Scanner sc = new Scanner(System.in);
		Properties prop = new Properties();
		Properties p = new Properties();
		
		ArrayList<Block> block = new ArrayList<Block>();
		int count = 0, difficulty = 3;
		String data = "", previousHash = "empty";
		boolean condition = true;
		
		FileOutputStream fos = new FileOutputStream("E:\\blockchain\\block" + count + ".txt");
		PrintStream ps = new PrintStream("E:\\blockchain\\hash.txt");
		
		System.out.print("Enter the data: ");
		data = sc.nextLine();
		
		block.add(Block.mineBlock(data, previousHash, difficulty));
		
		prop.setProperty("data", data);
		prop.setProperty("previousHash", previousHash);
		prop.setProperty("currentHash", block.get(count).hash);
		prop.setProperty("nonce", String.valueOf(block.get(count).nonce));
		prop.setProperty("difficulty", String.valueOf(difficulty));
		prop.store(fos, null);
		
		System.setOut(ps);
		System.out.println("block_" + count + "=" + block.get(count).hash);
		fos.close();
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		
		while(condition) {
			
			System.out.print("Enter the data: ");
			data = sc.nextLine();
			
			block.add(Block.mineBlock(data, block.get(count).hash, difficulty));
			
			count++;
			
			int i = 0;
			
			while(i < block.size()-1) {
				FileInputStream fis = new FileInputStream("E:\\blockchain\\block" + i + ".txt");
				p.load(fis);
				
				String checkData = p.getProperty("data");
				String checkPreviousHash = p.getProperty("previousHash");
				String checkNonce = p.getProperty("nonce");
				
				String total = checkData + checkPreviousHash + checkNonce;
				String hash = "";
				
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] hashByte = digest.digest(total.getBytes("UTF-8"));
				hash = DatatypeConverter.printHexBinary(hashByte);
				
				if(!hash.equals(block.get(i).hash)) {
					System.out.println(hash.equals(block.get(i).hash));
					condition = false;
				}
				i++;
			}
			
			if(condition) {
				fos = new FileOutputStream("E:\\blockchain\\block" + count + ".txt");
				
				prop.setProperty("data", data);
				prop.setProperty("previousHash", block.get(count).previousHash);
				prop.setProperty("currentHash", block.get(count).hash);
				prop.setProperty("nonce", String.valueOf(block.get(count).nonce));
				prop.setProperty("difficulty", String.valueOf(difficulty));
				prop.store(fos, null);
				
				System.setOut(ps);
				System.out.println("block_" + count + "=" + block.get(count).hash);
				fos.close();
				System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
			} else {
				System.out.println("Data altered...");
				ps.close();
				break;
			}
		}
	}
}
