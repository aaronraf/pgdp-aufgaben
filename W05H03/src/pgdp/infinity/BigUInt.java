package pgdp.infinity;

import java.util.Arrays;

public class BigUInt {

	// Some useful BigUInt constants
	public static final BigUInt ZERO = new BigUInt();
	public static final BigUInt ONE = new BigUInt(1);
	public static final BigUInt TWO = new BigUInt(2);
	public static final BigUInt TEN = new BigUInt(10);
	public static final BigUInt SIXTEEN = new BigUInt(16);

	/**
	 * The BigUInt is internally stored as a long array. The least significant block
	 * is at index 0, the most significant block is at index digits.length - 1.
	 */
	private long[] digits;

	/**
	 * Constructs a new BigUInt with the value 0
	 */
	public BigUInt() {
		this(0);
	}

	/**
	 * Constructs a new BigUInt with the given value
	 * 
	 * @param val
	 */
	public BigUInt(long val) {
		digits = new long[] { val };
	}

	/**
	 * Constructs a new BigUInt from the given string. Decimal format and
	 * hexadecimal format (beginning with "0x" or "0X") are supported. Other inputs
	 * default to 0. Supports upper and lower case.
	 * 
	 * @param val
	 */
	public BigUInt(String val) {
		if (val.startsWith("0x") || val.startsWith("0X")) {
			fromHexString(val.substring(2));
		} else {
			fromDecimalString(val);
		}
	}

	/**
	 * Interprets the String as a decimal number. Throws IllegalArgumentException if
	 * any other character is recognized.
	 * 
	 * @param val
	 */
	private void fromDecimalString(String val) {
		BigUInt n = ZERO;

		for (int i = 0; i < val.length(); i++) {
			char digit = val.charAt(i);
			if (digit < '0' || digit > '9') {
				throw new IllegalArgumentException("The value \"" + val + "\" cannot be parsed to BigUInt.");
			}
			BigUInt dig = new BigUInt(digit - '0');
			n = n.mul(TEN);
			n = n.add(dig);
		}

		this.digits = n.digits;
	}

	/**
	 * Interprets the String as a hexadecimal number. Throws
	 * IllegalArgumentException if any other character is recognized.
	 * 
	 * @param val
	 */
	private void fromHexString(String val) {
		BigUInt n = ZERO;
		BigUInt digitVal;

		for (int i = 0; i < val.length(); i++) {
			char digit = val.charAt(i);
			if (digit >= '0' && digit <= '9') {
				digitVal = new BigUInt(digit - '0');
			} else if (digit >= 'a' && digit <= 'f') {
				digitVal = new BigUInt(digit - 'a' + 10);
			} else if (digit >= 'A' && digit <= 'F') {
				digitVal = new BigUInt(digit - 'A' + 10);
			} else {
				throw new IllegalArgumentException("The value \"" + val + "\" cannot be parsed to BigUInt.");
			}
			n = n.mul(SIXTEEN);
			n = n.add(digitVal);
		}

		this.digits = n.digits;
	}

	/**
	 * Constructs a new BigUInt from the given array
	 * 
	 * @param digits
	 */
	private BigUInt(long[] digits) {
		int size = digits.length;
		while (size > 0 && digits[size - 1] == 0) {
			size--;
		}

		if (size == 0) {
			this.digits = new long[] { 0 };
		} else if (size == digits.length) {
			this.digits = digits;
		} else {
			this.digits = Arrays.copyOf(digits, size);
		}
	}

	/**
	 * Trims the array by removing all leading blocks that are 0
	 * 
	 * @param digits
	 * @return trimmed array
	 */
	private static long[] trim(long[] digits) {
		int size = digits.length;
		while (size > 0 && digits[size - 1] == 0) {
			size--;
		}

		if (size == 0) {
			digits = new long[] { 0 };
		} else if (size < digits.length) {
			digits = Arrays.copyOf(digits, size);
		}
		return digits;
	}

	/**
	 * Adds the other BigUInt to this and returns the result as a new BigUInt
	 * 
	 * @param other
	 * @return sum of this and other
	 */
	public BigUInt add(BigUInt other) {
		if (digits.length >= other.digits.length) {
			return new BigUInt(add(digits, other.digits));
		} else {
			return new BigUInt(add(other.digits, digits));
		}
	}

	/**
	 * Adds both arrays block-wise and returns the result as a new array. It is
	 * assumed that a.length >= b.length
	 * 
	 * @param a
	 * @param b
	 * @return new array as result of a + b
	 */
	private static long[] add(long[] a, long[] b) {
		long[] result = new long[a.length + 1];
		long carry = 0;

//		a.length >= b.length
		for (int i = 0; i < b.length; i++) {
			long[] temp = safeAdd(a[i], b[i]);
			long[] tempCarry = safeAdd(temp[0], carry);
			result[i] = tempCarry[0];
			carry = tempCarry[1] + temp[1];
		}
		for (int i = b.length; i < a.length; i++) {
			long[] tempCarry = safeAdd(a[i], carry);
			result[i] = tempCarry[0];
			carry = tempCarry[1];
		}

		if (carry > 0) {
			result[a.length] = carry;
		}
		return trim(result);
	}

	/**
	 * Safely adds two longs with respect to overflow. a + b = {c, d}, where c is
	 * the result and d the overflow.
	 * 
	 * @param a
	 * @param b
	 * @return the result stored at index 0 and the overflow at index 1
	 */
	private static long[] safeAdd(long a, long b) {
		long aLow = low(a);
		long bLow = low(b);
		long aHigh = high(a);
		long bHigh = high(b);

		long lowSum = aLow + bLow;
		long lowRes = low(lowSum);
		long carryLow = high(lowSum);

		long highSum = aHigh + bHigh + carryLow;
		long highRes = low(highSum);
		long carryHigh = high(highSum);

		long result = (highSum << 32) + lowRes;
		return new long[] {result, carryHigh};
	}

	/**
	 * Subtracts other from this and returns the result as a new BigUInt. If other
	 * is bigger than this, 0 is returned.
	 * 
	 * @param other
	 * @return this - other or 0 if other > this
	 */
	public BigUInt sub(BigUInt other) {
		if (other.isGreaterThan(this)) {
			return ZERO;
		}
		return new BigUInt(sub(digits, other.digits));
	}

	/**
	 * Subtracts both arrays block-wise. It is assumed that a >= b
	 * 
	 * @param a
	 * @param b
	 * @return new array as result of a - b
	 */
	private static long[] sub(long[] a, long[] b) {
		long[] result = new long[a.length];
		long borrow = 0;

//		a.length >= b.length
		for (int i = 0; i < b.length; i++) {
			long delta = a[i] - b[i] - borrow;

//			if a[i] - borrow is smaller than b[i], need to borrow 1
			if (Long.compareUnsigned(a[i] - borrow, b[i]) < 0) {
				borrow = 1;
			} else {
				borrow = 0;
			}

			result[i] = delta;
		}
		for (int i = b.length; i < a.length; i++) {
			long delta = a[i] - borrow;

//			same thing goes again
			if (Long.compareUnsigned(a[i], borrow) < 0) {
				borrow = 1;
			} else {
				borrow = 0;
			}

			result[i] = delta;
		}

		return trim(result);
	}

	/**
	 * Multiplies this with other and returns the result as a new BigUInt.
	 * 
	 * @param other
	 * @return this * other
	 */
	public BigUInt mul(BigUInt other) {
		return new BigUInt(mul(digits, other.digits));
	}

	/**
	 * Multiplies both arrays block- and digit-wise. Returns the result as a new
	 * array.
	 * 
	 * @param a
	 * @param b
	 * @return a * b
	 */
	private static long[] mul(long[] a, long[] b) {
		long[] result = new long[a.length + b.length];

		for (int i = 0; i < a.length; i++) {
			long carry = 0;

			for (int j = 0; j < b.length; j++) {
				long[] mulTemp = safeMul(a[i], b[j]);
				long[] addMulRes = safeAdd(mulTemp[0], result[i + j]);
				long[] addFinal = safeAdd(addMulRes[0], carry);
				result[i + j] = addFinal[0];
				carry = mulTemp[1] + addMulRes[1] + addFinal[1];
			}

			result[i + b.length] += carry;
		}

		return result;
	}

	/**
	 * Safely multiplies two longs with respect to overflow. a * b = {c, d}, where c
	 * is the result and d the overflow.
	 * 
	 * @param a
	 * @param b
	 * @return the result stored at index 0 and the overflow at index 1
	 */
	private static long[] safeMul(long a, long b) {
		long aLow = low(a);
		long bLow = low(b);
		long aHigh = high(a);
		long bHigh = high(b);

		long res0 = aLow * bLow;
		long res1 = aLow * bHigh;
		long res2 = aHigh * bLow;
		long res3 = aHigh * bHigh;

		long lowRes = low(res0);
		long highRes01 = high(res0) + low(res1);
		long hrtCarry = high(highRes01);
		long highRes012 = low(highRes01) + low(res2);
		hrtCarry += high(highRes012);
		long highRes = low(highRes012);

		long ofLow12 = high(res1) + high(res2);
		long ofLowTemp = low(ofLow12);
		long ofLowCarry = high(ofLow12);

		long ofLow123 = ofLowTemp + low(res3);
		long ofLow123Temp = low(ofLow123);
		ofLowCarry += high(ofLow123);
		long ofLow0123 = ofLow123Temp + hrtCarry;
		long ofLow0123Temp = low(ofLow0123);
		ofLowCarry += high(ofLow0123);

		long ofHigh = high(res3) + ofLowCarry;
		long ofHighTemp = low(ofHigh);
		long ofHighCarry = high(ofHigh);

		long res = (highRes << 32) + lowRes;
		long resCarry =  (ofHighTemp << 32) + ofLow0123Temp;

		return new long[] {res, resCarry};
	}

	/**
	 * Divides other from this. The result of the division is stored at index 0, the
	 * modulus at index 1. Relies on mul and sub to work properly.
	 * 
	 * @param other
	 * @return {this / other, this % other}
	 */
	public BigUInt[] divMod(BigUInt other) {
		if (other.isEqual(ZERO)) {
			throw new ArithmeticException("Tried to divide by 0.");
		}

		long[] n = digits;
		long[] d = other.digits;
		long[] q = new long[n.length];
		long[] r = new long[d.length];

		for (int i = bitLength() - 1; i >= 0; i--) {
			r = trim(mul(r, TWO.digits));
			r[0] |= (n[i / 64] & (1L << (i % 64))) >>> (i % 64);

			if (isGreaterOrEqual(r, d)) {
				r = trim(sub(r, d));
				q[i / 64] |= (1L << (i % 64));
			}
		}
		return new BigUInt[] { new BigUInt(q), new BigUInt(r) };
	}

	/**
	 * Calculates this to the power of the exponent and returns the result as a new
	 * BigUInt.
	 * 
	 * @param exp the exponent
	 * @return this ** exp
	 */
	public BigUInt pow(int exp) {
		if (exp < 0) {
			return null;
		} else if (exp == 0) {
//			everything power of 0 is 1
			return BigUInt.ONE;
		}

		BigUInt current = this;
		BigUInt result = BigUInt.ONE;
		while (exp > 0) {
			if (exp % 2 == 1) {
				result = result.mul(current);
			}

			exp /= 2;
			current = current.mul(current);
		}

		return result;
	}

	//
	// Helper Methods
	//

	/**
	 * @return true if lsb is set to 1
	 */
	public boolean isOdd() {
		return digits[0] % 2 == 1;
	}

	/**
	 * @return true if lsb is set to 0
	 */
	public boolean isEven() {
		return !isOdd();
	}

	/**
	 * @param other
	 * @return 1, 0 or -1 if this is greater, equal or smaller than other
	 */
	public int compareTo(BigUInt other) {
		return compareTo(digits, other.digits);
	}

	private static int compareTo(long[] a, long[] b) {
		if (a.length < b.length) {
			return -1;
		}
		if (a.length > b.length) {
			return 1;
		}
		for (int i = a.length - 1; i >= 0; i--) {
			int cmp = Long.compareUnsigned(a[i], b[i]);
			if (cmp != 0) {
				return cmp;
			}
		}
		return 0;
	}

	//
	// Comparison operators
	//

	public boolean isEqual(BigUInt other) {
		return isEqual(digits, other.digits);
	}

	private boolean isEqual(long[] a, long[] b) {
		return Arrays.equals(a, b);
	}

	public boolean isNotEqual(BigUInt other) {
		return !isEqual(other);
	}

	public boolean isGreaterThan(BigUInt other) {
		return isGreaterThan(digits, other.digits);
	}

	private boolean isGreaterThan(long[] a, long[] b) {
		return compareTo(a, b) > 0;
	}

	public boolean isGreaterOrEqual(BigUInt other) {
		return isGreaterOrEqual(digits, other.digits);
	}

	private boolean isGreaterOrEqual(long[] a, long[] b) {
		return isGreaterThan(a, b) || isEqual(a, b);
	}

	public boolean isSmallerThan(BigUInt other) {
		return !isGreaterOrEqual(other);
	}

	public boolean isSmallerOrEqual(BigUInt other) {
		return !isSmallerThan(other);
	}

	/**
	 * Sets the lower 32 bits to 1
	 */
	private static final long LOW_MASK = (1L << 32) - 1;

	/**
	 * @param val
	 * @return the lower 32 bits of val
	 */
	private static long low(long val) {
		return val & LOW_MASK;
	}

	/**
	 * @param val
	 * @return the higher 32 bits of val shifted to the lower 32 bits
	 */
	private static long high(long val) {
		return val >>> 32;
	}

	/**
	 * @return the total length of this BigUInt in bits
	 */
	private int bitLength() {
		return (digits.length - 1) * 64 + 64 - Long.numberOfLeadingZeros(digits[digits.length - 1]);
	}

	/**
	 * @return String representation of this BigUInt in binary
	 */
	public String toBinaryString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%1$s", Long.toBinaryString(digits[digits.length - 1])));
		for (int i = digits.length - 2; i >= 0; i--) {
			sb.append(String.format("%1$64s", Long.toBinaryString(digits[i])).replaceAll(" ", "0"));
		}
		return sb.toString();
	}

	/**
	 * @return String representation of this BigUInt in decimal. Relies on divMod,
	 *         mul and sub to work properly.
	 */
	public String toDecimalString() {
		StringBuilder sb = new StringBuilder();
		BigUInt[] div = { this, new BigUInt() };

		while (div[0].isNotEqual(ZERO)) {
			div = div[0].divMod(TEN);
			sb.append(div[1].digits[0]);
		}

		return sb.reverse().toString();
	}

	/**
	 * @return String representation of this BigUInt in hexadecimal
	 */
	public String toHexString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%1$X", digits[digits.length - 1]));
		for (int i = digits.length - 2; i >= 0; i--) {
			sb.append(String.format("%1$016X", digits[i]));
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return toDecimalString();
	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString(add(new long[]{15, 1}, new long[]{15, 1})));
	}
}
