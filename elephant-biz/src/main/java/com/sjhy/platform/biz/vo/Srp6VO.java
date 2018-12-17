package com.sjhy.platform.biz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Srp6VO
{
	private BigInteger b = BigInteger.ZERO;

	private BigInteger salt = BigInteger.ZERO;

	private BigInteger m2 = BigInteger.ZERO;

	public BigInteger getB()
	{
		return b;
	}

	public void setB( BigInteger b )
	{
		this.b = b;
	}

	public BigInteger getSalt()
	{
		return salt;
	}

	public void setSalt( BigInteger salt )
	{
		this.salt = salt;
	}

	public BigInteger getM2()
	{
		return m2;
	}

	public void setM2( BigInteger m2 )
	{
		this.m2 = m2;
	}

}
