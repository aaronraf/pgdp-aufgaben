package pgdp.infinity;

import java.math.BigInteger;

public class Main {

	public static void main(String[] args) {
		// If you have implemented mul and add correctly, this BigUInt should correctly
		// initialize. You can verify your result with the BigInteger implementation
		// from java.math
		BigUInt b1 = new BigUInt("12345678901234567890");
		BigInteger b2 = new BigInteger("12345678901234567890");

		System.out.println(b1.toHexString());
		System.out.println(b2.toString(16).toUpperCase());
	}
}
