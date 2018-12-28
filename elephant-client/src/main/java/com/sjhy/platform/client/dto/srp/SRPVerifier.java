package com.sjhy.platform.client.dto.srp;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * POJO for holding the random salt and verifier<br>
 * 
 * Released into the public domain
 * 
 * @author Jordan Zimmerman - jordan@srp.com
 * @see SRPFactory Full Documentation
 * @version 1.1
 */
public class SRPVerifier implements Serializable
{
	public SRPVerifier(BigInteger verifier, BigInteger salt )
	{
		verifier_v = SRPUtils.getUnsignedNum( verifier );
		salt_s = SRPUtils.getUnsignedNum( salt );
	}

	/**
	 * v
	 */
	public final BigInteger verifier_v;

	/**
	 * s
	 */
	public final BigInteger salt_s;
}
