package com.sjhy.platform.client.dto.base;

import lombok.Data;

import java.math.BigInteger;

/**
 * @HJ
 */
@Data
public class Srp6VO {
    private BigInteger b = BigInteger.ZERO;

    private BigInteger salt = BigInteger.ZERO;

    private BigInteger m2 = BigInteger.ZERO;
}
