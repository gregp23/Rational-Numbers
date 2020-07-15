import java.util.*;

class RationalTest {

	public static void main(String [] args) throws Exception {

		System.out.println("Initial tests...");

		//---------- Testing getNumerator/getDenominator on simple fractions -- no normalization needed

		Rational r = new Rational(1, 2);
		if (r.getNumerator() != 1 || r.getDenominator() != 2)
			getError(r, 1, 2);

		normalizationCheck(r, 1, 2);      // Check for normalization of rationals

		toStringCheck(r);          // Check toString method

		r = new Rational(2, 4);
		normalizationCheck(r, 2, 4);      // Check for normalization of rationals

		toStringCheck(r);          // Check toString method

		// Check 1-arg constructor

		r = new Rational(3);
		if (r.getNumerator() != 3 || r.getDenominator() != 1)
			getError(r, 3, 1);

		// Check default constructor

		r = new Rational();
		if (r.getNumerator() != 0 || r.getDenominator() != 1)
			getError(r, 0, 1);

		// Check compareTo method

		Rational 
			r1 = new Rational(1, 3),
			r2 = new Rational(1, 2);
		int val = r1.compareTo(r2);
		if (val != -1) compOpError(r1, r2, val, -1);
		val = r2.compareTo(r1);
		if (val != 1) compOpError(r1, r2, val, 1);
		r2 = new Rational(1,3);
		val = r1.compareTo(r2);
		if (val != 0) compOpError(r1, r2, val, 0);

		// Check equals method
		
		r1 = new Rational(1, 3);
		r2 = new Rational(1, 2);
		boolean b = r1.equals(r2);
		if (b) equalsError(r1, r2, b, false);
		b = r2.equals(r1);
		if (b) equalsError(r1, r2, b, false);
		r2 = new Rational(1,3);
		b = r1.equals(r2);
		if (!b) equalsError(r1, r2, b, true);

		// Check that 0 denominator throws exception
		try {
			r = new Rational(5, 0);
			noExceptionError("Rational(int, int)", r, 5, 0);
		} catch(RationalException re) {
		}

		try {
			r = new Rational(0, 3);
		} catch(RationalException re) {
			exceptionError();
		}

		System.out.println("done!");

		System.out.println("Testing on some simple rationals...");

		for (int num1 = 0; num1 < 4; num1++)
			for (int den1 = 0; den1 < 4; den1++)
			  for (int num2 = 0; num2 < 4; num2++)
				 for (int den2 = 0; den2 < 4; den2++)
					doOneTest(num1, den1, num2, den2);

		System.out.println("done!");

		System.out.println("Testing on 1,000 random pairs of rationals...");

		Random rand = new Random(new Date().getTime());

		for (int i = 0; i < 1000; i++) {
			int
			  num1 = rand.nextInt(1000),
			  denom1 = rand.nextInt(1000),
			  num2 = rand.nextInt(1000),
			  denom2 = rand.nextInt(1000);

			doOneTest(num1, denom1, num2, denom2);
		}

	   System.out.println("done!");

	   System.out.println("**** Success ****");
	}

	static void doOneTest(int num1, int denom1, int num2, int denom2) {

		boolean expectingException = false;

		try {
			/***
			if (!ALLOW_ZERO_ZERO) {
				if (num1 == 0 && denom1 == 0 || num2 == 0 && denom2 == 0) {
					//System.out.println("Ignoring Rational(0, 0)");
					return;
				}
			}
			***/

			expectingException = denom1 == 0;

			Rational opd1 = new Rational(num1, denom1);

			if (expectingException) noExceptionError("Rational(int, int)", opd1, num1, denom1);

			normalizationCheck(opd1, num1, denom1);

			toStringCheck(opd1);

			expectingException = denom2 == 0;

			Rational opd2 = new Rational(num2, denom2);

			if (expectingException) noExceptionError("Rational(int, int)", opd2, num2, denom2);

			normalizationCheck(opd2, num2, denom2);
			toStringCheck(opd2);

			// Testing negate

			Rational result = opd1.negate();
			Rational myResult = new Rational(-opd1.getNumerator(), opd1.getDenominator());
			if (!myResult.equals(result)) unopError("negate", opd1, result);

			expectingException  = opd1.getNumerator() == 0;

			// Testing inverse -- testing on both opd1 and opd2 (the more the better)
			result = opd1.inverse();
			if (result.getNumerator() != opd1.getDenominator() ||
			  result.getDenominator() != opd1.getNumerator())
			  unopError("inverse", opd1, result);

			if (expectingException) noExceptionError("inverse", opd2, opd2.getNumerator(), opd2.getDenominator());

			expectingException = opd2.getNumerator() == 0;

			result = opd2.inverse();
			if (result.getNumerator() != opd2.getDenominator() ||
			  result.getDenominator() != opd2.getNumerator())
			  unopError("inv", opd2, result);

			if (expectingException) noExceptionError("inverse", opd2, opd2.getNumerator(), opd2.getDenominator());

			expectingException = false;

			//Testing add

			result = opd1.add(opd2);
			myResult = new Rational(opd1.getNumerator() * opd2.getDenominator() + opd2.getNumerator() * opd1.getDenominator(),
								opd1.getDenominator() * opd2.getDenominator());
			if (!myResult.equals(result)) binopError("add", opd1, opd2, result, myResult);

			// Testing addInPlace
			result = new Rational(opd1);
			result.addInPlace(opd2);
			myResult = new Rational(opd1.getNumerator() * opd2.getDenominator() + opd2.getNumerator() * opd1.getDenominator(),
								opd1.getDenominator() * opd2.getDenominator());
			if (!myResult.equals(result)) binopError("addInPlace", opd1, opd2, result, myResult);

			//Testing sub

			result = opd1.sub(opd2);
			myResult = new Rational(opd1.getNumerator() * opd2.getDenominator() - opd2.getNumerator() * opd1.getDenominator(),
							  opd1.getDenominator() * opd2.getDenominator());
			if (!myResult.equals(result)) binopError("sub", opd1, opd2, result, myResult);


			// Testing subInPlace
			result = new Rational(opd1);
			result.subInPlace(opd2);
			myResult = new Rational(opd1.getNumerator() * opd2.getDenominator() - opd2.getNumerator() * opd1.getDenominator(),
								opd1.getDenominator() * opd2.getDenominator());
			if (!myResult.equals(result)) binopError("subInPlace", opd1, opd2, result, myResult);


			//Testing mul

			result = opd1.mul(opd2);
			myResult = new Rational(opd1.getNumerator() * opd2.getNumerator(), opd1.getDenominator() * opd2.getDenominator());
			if (!myResult.equals(result)) binopError("mul", opd1, opd2, result, myResult);
