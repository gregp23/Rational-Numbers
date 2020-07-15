class main{
  public static void main(String[]args){

  }
}


public class Rational {
    public int numerator;
    public int denominator;
   
    public Rational(int numerator, int denominator){
     
        if(denominator == 0) { //Throwing an exception
            throw new RationalException("Denominator can't be zero");
        } 
    	
    	
        this.numerator=numerator;
        this.denominator=denominator;
        normalize(numerator,denominator);

    }

    public Rational(int numerator) {
      
        this.numerator = numerator;
        this.denominator = 1;
        normalize(numerator,denominator);

    }

    public Rational() {
    	this.numerator=0;
    	this.denominator=1;
        normalize(numerator,denominator);

    }

    public int getNumerator() {
    	
        return this.numerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    
    public Rational(Rational rational) {
        normalize(rational.numerator,rational.denominator);

    	rational.numerator = numerator;
    	rational.denominator = denominator;

        
    }

    public Rational negate(){
        return new Rational(-1 * getNumerator(), getDenominator());
    }

    public Rational inverse(){
        return new Rational(getDenominator(), getNumerator());
    }

    private static int gcd(int a,int b) {
    	   if (b == 0) {
    		   return a;
    	   }
    		return gcd(b, a%b);
    }
    
	void normalize(int a, int b) {
		int gcd = gcd(a, b);
		
		numerator=a/gcd;
		denominator=b/gcd; 
		
	}

    public Rational add(Rational rational){
        int num1 = getNumerator();
        int denom1 = getDenominator();

        int num2 = rational.getNumerator();
        int denom2 = rational.getDenominator();

        num2 = num1 * denom2 + num2 * denom1;
        denom2 = denom1 * denom2;

        int gcd = gcd(num2, denom2); 
        return new Rational(num2 / gcd, denom2 / gcd);
    }

    public void addInPlace(Rational rational) {
        numerator = numerator * rational.getDenominator() + denominator * rational.getNumerator();
        denominator = denominator * rational.getDenominator();

        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    }

    public Rational sub(Rational rational){
        int num1 = getNumerator();
        int denom1 = getDenominator();

        int num2 = rational.getNumerator();
        int denom2 = rational.getDenominator();

        num2 = num1 * denom2 - num2 * denom1;
        denom2 = denom1 * denom2;

        int gcd = (gcd(num2, denom2));
        return new Rational(num2 / gcd, denom2 / gcd);
    }

    public void subInPlace(Rational rational) {
        numerator = numerator * rational.getDenominator() - denominator * rational.getNumerator();
        denominator = denominator * rational.getDenominator();

        int gcd = (gcd(numerator, denominator));
        numerator /= gcd;
        denominator /= gcd;
        
    }

    public Rational mul(Rational rational){
        int num = numerator * rational.getNumerator();
        int denom = denominator * rational.getDenominator();

        int gcd = gcd(num, denom);
        return new Rational(num / gcd, denom / gcd);
    }

    public void mulInPlace(Rational rational) {
        numerator *= rational.getNumerator();
        denominator *= rational.getDenominator();

        int gcd = gcd(numerator, denominator);
        
        numerator /= gcd;
        denominator /= gcd;
    }

    public Rational div(Rational rational)    {
        int num = numerator * rational.getDenominator();
        int denom = denominator * rational.getNumerator();

        int gcd = gcd(num, denom);
        return new Rational(num / gcd, denom / gcd);
    }

    public void divInPlace(Rational rational) {
        numerator *= rational.getDenominator();
        denominator *= rational.getNumerator();

        int gcd = gcd(numerator, denominator);
        
        numerator /= gcd;
        denominator /= gcd;
    }

    public int compareTo(Rational rational) {
        double val1 = 1.0 * numerator / denominator;
        double val2 = 1.0 * rational.getNumerator() / rational.getDenominator();

        if(val1 == val2) {
            return 0;
        } else {
            return val1 > val2 ? 1 : -1;
        }
    }

    public boolean equals(Rational rational){
        return rational.getNumerator() == numerator && rational.getDenominator() == denominator;
    }

    @Override
    public String toString() {if(numerator==0) {
    	return 0+"";
    }
    if(numerator==denominator) {
    	return numerator+"";
    }
    if(denominator ==1) {
    	return numerator+"";
    }
    return numerator+"/"+denominator;
    }}