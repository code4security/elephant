package com.sjhy.platform.client.dto.srp;

import java.math.BigInteger;

/**
 * runner for server sessions<br>
 * 
 * Released into the public domain
 * 
 * @author Jordan Zimmerman - jordan@srp.com
 * @see SRPFactory Full Documentation
 * @version 1.3 Some session methods now throw an authentication exception instead of returning true/false 2/21/07
 * @version 1.2 Updated to use the SRP-6 spec 2/21/07
 * @version 1.1
 */
public class SRPServerSessionRunner implements SRPRunner
{
	public SRPServerSessionRunner( SRPServerSession session )
	{
		fSession = session;
		fState = State.OUTPUT_S;
		fSuccess = false;
		fOutput = null;
		fInput = null;
	}

	@Override
	public boolean needsInput()
	{
		return ( fState == State.INPUT_A ) || ( fState == State.INPUT_M1 );
	}

	@Override
	public void setInput( BigInteger i )
	{
		fInput = i;
	}

	@Override
	public boolean hasOutput()
	{
		return ( fOutput != null );
	}

	@Override
	public BigInteger getOutput()
	{
		return fOutput;
	}

	@Override
	public boolean next() throws SRPAuthenticationFailedException
	{
		switch ( fState )
		{
			case OUTPUT_S:
			{
				fState = State.INPUT_A;
				fOutput = fSession.getVerifier().salt_s;
				break;
			}

			case INPUT_A:
			{
				fState = State.INPUT_M1;
				fSession.setClientPublicKey_A( fInput );
				fSession.computeCommonValue_S();
				fOutput = fSession.getPublicKey_B();
				break;
			}

			case INPUT_M1:
			{
				fSession.validateClientEvidenceValue_M1( fInput );
				fState = State.OUTPUT_M2;
				fSuccess = true;
				fOutput = fSession.getEvidenceValue_M2();
				break;
			}

			case OUTPUT_M2:
			{
				fState = State.DONE;
				fOutput = null;
				break;
			}

			default:
			case DONE:
			{
				// do nothing
				break;
			}
		}

		return fState != State.DONE;
	}

	public BigInteger getValue()
	{
		return fOutput;
	}

	@Override
	public boolean success()
	{
		return fSuccess;
	}

	@Override
	public byte[] getSessionKey()
	{
		return fSession.getSessionKey_K();
	}

	private enum State
	{
		OUTPUT_S, INPUT_A, INPUT_M1, OUTPUT_M2, DONE
	}

	private final SRPServerSession fSession;
	private State fState;
	private boolean fSuccess;
	private BigInteger fInput;
	private BigInteger fOutput;
}
