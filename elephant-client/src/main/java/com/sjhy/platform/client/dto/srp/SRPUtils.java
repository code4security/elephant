package com.sjhy.platform.client.dto.srp;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Various utilities<br>
 * 
 * Released into the public domain
 * 
 * @author Jordan Zimmerman - jordan@srp.com
 * @see SRPFactory Full Documentation
 * @version 1.2 Updated to use the SRP-6 spec 2/21/07
 * @version 1.1
 */
public class SRPUtils
{

	// private static final Logger logger = Logger.getLogger( SRPUtils.class );

	/**
	 * Validates the given constants. Throws {@link IllegalArgumentException} if the values are not valid.
	 * NOTE: due to prime number calculations, this method can be slow.
	 * 
	 * @param N
	 *            large prime
	 * @param g
	 *            primitve root of N
	 */
	static void validateConstants(BigInteger N, BigInteger g )
	{
		// Developed from "SRP JavaScript Demo" from http://srp.stanford.edu<p>

		if ( !N.isProbablePrime( 10 ) )
			throw new IllegalArgumentException( "isProbablePrime(10) failed for " + N.toString( 16 ) );

		BigInteger n_minus_one_div_2 = N.subtract( BigInteger.ONE ).divide( TWO );

		if ( !n_minus_one_div_2.isProbablePrime( 10 ) )
			throw new IllegalArgumentException( "(N-1)/2 is not prime for " + N.toString( 16 ) );

		if ( g.modPow( n_minus_one_div_2, N ).add( BigInteger.ONE ).compareTo( N ) != 0 )
			throw new IllegalArgumentException( "Not a primitive root: " + g.toString( 16 ) );
	}

	/**
	 * Make a verifier. First, x is generated via x = H(s, P) where H is a hash() function, s is random salt, and P is
	 * the password.
	 * The verifier is then v = g^x
	 * 
	 * @param constants
	 *            the constants to use
	 * @param password
	 *            the password to process
	 * @return the verifier
	 */
	static SRPVerifier makeVerifier( SRPConstants constants, byte[] password )
	{
		BigInteger salt = random( constants );
		BigInteger x = makePrivateKey( password, salt );
		// logger.info( "p = " + bytes2Hex( password ) + " salt=" + salt.toString( 16 ) + " xs="
		// + bytes2Hex( salt.toByteArray() ) );
		BigInteger v = constants.primitiveRoot_g.modPow( SRPUtils.getUnsignedNum( x ), constants.largePrime_N );

		// logger.info( "x=" + x.toString( 16 ) + ", v=" + v.toString( 16 ) );

		return new SRPVerifier( v, salt );
	}

	/**
	 * Make a new private key via x = H(s, P) where H is a hash() function, s is random salt, and P is the password.
	 * 
	 * @param password
	 *            the password
	 * @param salt
	 *            random salt
	 * @return the private key
	 */
	static BigInteger makePrivateKey(byte[] password, BigInteger salt )
	{
		BigInteger passwordInt = new BigInteger( password );
		return hash( combine( salt, passwordInt ) );
	}

	/**
	 * Combine two integers into one. This method uses a novel combining method rather than simple concatenation. My
	 * assumption is
	 * that it will add an additional level of security as a malicious party would not be able to guess this method. The
	 * bytes from
	 * each value are interleaved in pairs. If the first value of the pair is odd, two bytes are taken from the second
	 * value. Any
	 * remaining bytes are appended at the end.
	 * 
	 * @param a
	 *            first value to combine
	 * @param b
	 *            second value to combine
	 * @return combined value
	 */
	static BigInteger combine(BigInteger a, BigInteger b )
	{
		ByteBuffer abuf = ByteBuffer.wrap( removeZeroHead( a.toByteArray() ) );
		ByteBuffer bbuf = ByteBuffer.wrap( removeZeroHead( b.toByteArray() ) );

		byte[] combined = new byte[abuf.capacity() + bbuf.capacity()];
		ByteBuffer combinedbuf = ByteBuffer.wrap( combined );

		abuf.rewind();
		bbuf.rewind();
		combinedbuf.clear();

		combinedbuf.put( abuf );
		combinedbuf.put( bbuf );

		return new BigInteger( 1, combined );
	}

	/**
	 * hash a big int. Use SHA 256.
	 * 
	 * @param i
	 *            int to hash
	 * @return the hash
	 */
	public static BigInteger hash(BigInteger i )
	{
		return SRPUtils.getUnsignedNum( ( hashToBytes( i ) ) );
	}

	/**
	 * hash a big int. Use SHA 256.
	 * 
	 * @param i
	 *            int to hash
	 * @return the hash
	 */
	static byte[] hashToBytes( BigInteger i )
	{
		try
		{
			// logger.info( i.toString( 16 ) );
			MessageDigest sha = MessageDigest.getInstance( "SHA-256" );
			byte[] b = removeZeroHead( i.toByteArray() );
			// logger.info( "from " + bytes2Hex( b ) );
			sha.update( b, 0, b.length );
			byte[] r = sha.digest();
			// logger.info( "to " + bytes2Hex( r ) );
			return r;
		}
		catch ( NoSuchAlgorithmException e )
		{
			throw new UnsupportedOperationException( e );
		}
	}

	/**
	 * hash a big int. Use MD5.
	 * 
	 * @param i
	 *            int to hash
	 * @return the hash
	 */
	static byte[] hashToBytesMD5( BigInteger i )
	{
		try
		{
			MessageDigest sha = MessageDigest.getInstance( "MD5" );
			byte[] b = i.toByteArray();
			sha.update( b, 0, b.length );
			return sha.digest();
		}
		catch ( NoSuchAlgorithmException e )
		{
			throw new UnsupportedOperationException( e );
		}
	}

	/**
	 * Return a random number that satsifies: 1 < r < n
	 * 
	 * @param constants
	 *            constants to use
	 * @return the random number
	 */
	static BigInteger random(SRPConstants constants )
	{
		int numberOfBytes = ( constants.largePrime_N.bitLength() + ( constants.largePrime_N.bitLength() - 1 ) ) / 8;
		byte[] b = new byte[numberOfBytes];
		fRandom.nextBytes( b );
		BigInteger i = new BigInteger( 1, b );

		// random numbers must be: 1 < r < n
		BigInteger max = constants.largePrime_N.subtract( TWO );
		return i.mod( max ).add( TWO );
	}

	/**
	 * Calculate M(1) - H(A, B, K)
	 * 
	 * @param publicKey_A
	 *            generated public key - A
	 * @param publicKey_B
	 *            generated public key - B
	 * @param commonValue_S
	 *            the session fixed value - S
	 * @return M(1)
	 */
	static BigInteger calcM1(BigInteger publicKey_A, BigInteger publicKey_B, BigInteger commonValue_S )
	{
		return hash( combine( combine( publicKey_A, publicKey_B ), commonValue_S ) );
	}

	/**
	 * Calculate M(1) - H(A, M[1], K)
	 * 
	 * @param publicKey_A
	 *            generated public key - A
	 * @param evidenceValue_M1
	 *            generated hash - M(1)
	 * @param commonValue_S
	 *            the session fixed value - S
	 * @return M(1)
	 */
	static BigInteger calcM2(BigInteger publicKey_A, BigInteger evidenceValue_M1, BigInteger commonValue_S )
	{
		return hash( combine( combine( publicKey_A, evidenceValue_M1 ), commonValue_S ) );
	}

	/**
	 * Return the SRP-6 version of u - H(A, B)
	 * 
	 * @param A
	 *            Public Key A
	 * @param B
	 *            Public Key B
	 * @return u
	 */
	public static BigInteger calc_u(BigInteger A, BigInteger B )
	{
		// logger.info( A.toString( 16 ) );
		return hash( combine( A, B ) );
	}

	public static byte[] removeZeroHead( byte[] n )
	{
		byte[] N_out = null;

		if ( n[0] == 0 )
		{
			N_out = new byte[n.length - 1];
			System.arraycopy( n, 1, N_out, 0, N_out.length );
		}
		else
		{
			N_out = n;
		}
		return N_out;
	}

	public static BigInteger getUnsignedNum(BigInteger n )
	{
		return getUnsignedNum( n.toByteArray() );
	}

	public static BigInteger getUnsignedNum(byte[] n )
	{
		return new BigInteger( 1, n );
	}

	private SRPUtils()
	{
	}

	private static final BigInteger TWO = BigInteger.valueOf( 2 );

	private static final SecureRandom fRandom = new SecureRandom();
}
