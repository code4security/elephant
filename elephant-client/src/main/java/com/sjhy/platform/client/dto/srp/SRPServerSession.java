package com.sjhy.platform.client.dto.srp;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Manages a server SRP session
 * 
 * <p>
 * Released into the public domain
 * 
 * @author Jordan Zimmerman - jordan@srp.com
 * @see SRPFactory Full Documentation
 * @version 1.4 Make sure safeguards are checked: abort if A == 0 (mod N) or u == 0 - 2/27/07
 * @version 1.3 Updated to use the SRP-6 spec 2/21/07
 * @version 1.2
 */
public class SRPServerSession implements Serializable
{

	// private static final Logger logger = LoggerFactory.getLogger( SRPServerSession.class );

	/**
	 * @param constants
	 *            constants to use
	 * @param verifier
	 *            the verifier as returned from {@link SRPFactory#makeVerifier(byte[])}
	 */
	public SRPServerSession( SRPConstants constants, SRPVerifier verifier )
	{
		fConstants = constants;
		fVerifier = verifier;
		fRandom_b = SRPUtils.random( fConstants );
		// logger.info( "b= " + fRandom_b.toString( 16 ) );
		fSRP6_u = null;
		fPublicKey_A = null;
		fCommonValue_S = null;
		fEvidenceValue_M1 = null;
		fSessionKey_K = null;

		// B = 3v + g^b
		fPublicKey_B = fVerifier.verifier_v.multiply( constants.srp6Multiplier_k )
				.add( fConstants.primitiveRoot_g.modPow( fRandom_b, fConstants.largePrime_N ) )
				.mod( fConstants.largePrime_N );
	}

	/**
	 * When the client sends the public key (value A in the docs) call this method to store the value
	 * 
	 * @param publicKey_A
	 *            A
	 * @throws SRPAuthenticationFailedException
	 *             if A is invalid
	 */
	public void setClientPublicKey_A( BigInteger publicKey_A ) throws SRPAuthenticationFailedException
	{
		if ( publicKey_A.mod( fConstants.largePrime_N ).equals( BigInteger.ZERO ) )
			throw new SRPAuthenticationFailedException( "A%N == 0" );

		fPublicKey_A = SRPUtils.getUnsignedNum( publicKey_A );
		fSRP6_u = SRPUtils.calc_u( fPublicKey_A, fPublicKey_B );
		// logger.info( "u=" + fSRP6_u.toString( 16 ) );
		if ( fSRP6_u.mod( fConstants.largePrime_N ).equals( BigInteger.ZERO ) )
			throw new SRPAuthenticationFailedException( "u%N == 0" );
	}

	/**
	 * Returns the public key that should be sent to the client (value B in the docs).
	 * 
	 * @return B
	 */
	public BigInteger getPublicKey_B()
	{
		return fPublicKey_B;
	}

	/**
	 * Call to calculate the fixed session key (S/K in the docs)
	 */
	public void computeCommonValue_S()
	{
		System.out.println("============[][][c1][][]===========");
		if ( fPublicKey_A == null ) {
			System.out.println("============[][][c2][][]==========="+fPublicKey_A);
			throw new IllegalStateException("setClientPublicKey_A() has not been called yet.");
		}

		// BigInteger A = SRPUtils.getRealBigInteger( fPublicKey_A );
		// BigInteger B = SRPUtils.getRealBigInteger( fPublicKey_B );
		// BigInteger v = SRPUtils.getRealBigInteger( fVerifier.verifier_v );
		// BigInteger u = SRPUtils.getRealBigInteger( fSRP6_u );
		// BigInteger b = SRPUtils.getRealBigInteger( fRandom_b );
		// logger.info( "A=" + A.toString( 16 ) );
		// logger.info( "B=" + B.toString( 16 ) );
		// logger.info( "v=" + v.toString( 16 ) );
		// logger.info( "u=" + u.toString( 16 ) );
		// logger.info( "b=" + b.toString( 16 ) );
		// S = (A * v^u)^b
		fCommonValue_S = fPublicKey_A.multiply( fVerifier.verifier_v.modPow( fSRP6_u, fConstants.largePrime_N ) )
				.modPow( fRandom_b, fConstants.largePrime_N );
		// logger.info( "S = " + fCommonValue_S.toString( 16 ) );
		System.out.println("fPublicKey_A=================11111111111111111=================>"+fPublicKey_A);
		System.out.println("fPublicKey_B=================11111111111111111=================>"+fPublicKey_B);
		System.out.println("fCommonValue_S===============11111111111111111===================>"+fCommonValue_S);
		fEvidenceValue_M1 = SRPUtils.calcM1( fPublicKey_A, fPublicKey_B, fCommonValue_S );
		System.out.println("============[][][c3][][]==========="+fEvidenceValue_M1);

		// logger.info( "M1=" + fEvidenceValue_M1.toString( 16 ) );

		// the MD5 output is the same as the AES key length
		fSessionKey_K = SRPUtils.hashToBytesMD5( fCommonValue_S );
	}

	/**
	 * When M(1) is received from the client, call this method to validate it
	 * 
	 * @param m1
	 *            M(1) as recevied from the client
	 * @throws SRPAuthenticationFailedException
	 *             if M(1) is incorrect
	 */
	public void validateClientEvidenceValue_M1( BigInteger m1 ) throws SRPAuthenticationFailedException
	{
		if ( fEvidenceValue_M1 == null ){
			System.out.println("============[][][1][][]==========="+fEvidenceValue_M1);
			throw new IllegalStateException( "computeCommonValue_S() has not been called yet." );
		}

		// logger.info( "M1 fromC=" + m1.toString( 16 ) );

		if ( !fEvidenceValue_M1.equals( m1 ) )
			throw new SRPAuthenticationFailedException( "M(1) incorrect" );
	}

	/**
	 * Return the value M(2) that should be sent to the client
	 * 
	 * @return M(2)
	 */
	public BigInteger getEvidenceValue_M2()
	{
		System.out.println("===============[][][][fEvidenceValue_M1][][][][===============:"+fEvidenceValue_M1);
		if ( fEvidenceValue_M1 == null ) {
			System.out.println("============[][][2][][]===========" + fEvidenceValue_M1);
			throw new IllegalStateException("computeCommonValue_S() has not been called yet.");
		}

		return SRPUtils.calcM2( fPublicKey_A, fEvidenceValue_M1, fCommonValue_S );
	}

	/**
	 * Returns the session fixed value which is the pre-hashed version of K
	 * 
	 * @return fixed value
	 */
	public BigInteger getSessionCommonValue()
	{
		return fCommonValue_S;
	}

	/**
	 * The 16 byte session key suitable for encryption
	 * 
	 * @return session key - K
	 */
	public byte[] getSessionKey_K()
	{
		return fSessionKey_K;
	}

	SRPConstants getConstants()
	{
		return fConstants;
	}

	SRPVerifier getVerifier()
	{
		return fVerifier;
	}

	private final SRPConstants fConstants;
	private final SRPVerifier fVerifier;
	private final BigInteger fRandom_b;
	private BigInteger fSRP6_u;
	private BigInteger fPublicKey_A;
	private final BigInteger fPublicKey_B;
	private BigInteger fCommonValue_S;
	private byte[] fSessionKey_K;
	private BigInteger fEvidenceValue_M1;
}
