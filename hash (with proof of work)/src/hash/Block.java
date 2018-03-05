package hash;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class Block {
	
	static int i = 1;
	public String hash;
	private String data;
	public String previousHash;
	public static long nonce = 25;//
	
	public Block(String data, String previousHash, long nonce) {
		this.data = data;
		this.previousHash = previousHash;
		this.hash = calculateHash();
		this.nonce = nonce;
	}

	private String calculateHash() {
		try {
			String total = data + previousHash + nonce;
//			System.out.println(total);
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(total.getBytes("UTF-8"));
			return DatatypeConverter.printHexBinary(hash);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void mineBlock(String message, String previousHash, int difficulty) throws IOException {
		String target = new String(new char[difficulty]).replace('\0', '0');
		Block now;
		now = new Block(message, previousHash, nonce);
		System.out.println("mining block...");
		while(!(now.hash.substring(0, difficulty).equalsIgnoreCase(target))) {
			nonce++;
			
			now = new Block(message, previousHash, nonce);
			
			System.out.println(now.hash);
			
		}
		FileWriter writer = new FileWriter("E:\\blockchain\\block" + i + ".txt", true);
		writer.write(String.valueOf(Block.nonce));
		writer.close();
//		System.out.println(i);
		i++;
		HashingPrg.block.add(now);
	}
	
	public static long returnNonce() {
		return nonce;
	}
}
