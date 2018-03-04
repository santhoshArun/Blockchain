package hash;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class Block {
	
	public String hash;
	private String data;
	public String previousHash;
	
	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.hash = calculateHash();
	}

	private String calculateHash() {
		try {
			String total = data + previousHash;
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(total.getBytes("UTF-8"));
			return DatatypeConverter.printHexBinary(hash);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
