package blockchain;

import java.security.MessageDigest;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

public class Block {

	String hash;
	String previousHash;
	String data;
	static long nonce = 0;
	
	//Initialise blocks
	public Block(String data, String previousHash, long nonce) {
		
		this.data = data;
		this.previousHash = previousHash;
		this.hash = getHash();
		this.nonce = nonce;
		
	}
	
	//returns the hash of the current block
	public String getHash() {
		
		String total = data + previousHash + nonce;
		String hash = "";
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashByte = digest.digest(total.getBytes("UTF-8"));
			hash = DatatypeConverter.printHexBinary(hashByte);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return hash;
		
	}
	
	//set the block hash with correct leading zeros
	public static Block mineBlock(String data, String previousHash, int difficulty) {
		
		String target = new String(new char[difficulty]).replace('\0', '0');
		
		Block currentBlock = new Block(data, previousHash, 0);
		
		while(!currentBlock.hash.substring(0, difficulty).equalsIgnoreCase(target)) {
			nonce++;
			currentBlock = new Block(data, previousHash, nonce);
			if(nonce%10000000==0)
			System.out.println(nonce);
		}
		
		return currentBlock;
	}
	
}
