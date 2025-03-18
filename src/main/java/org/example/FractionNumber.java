package org.example;

public class FractionNumber {

    int numerator;
    int denominator;

    public FractionNumber(int num, int den) {
        if (den == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.numerator = num;
        this.denominator = den;
        optimize();
    }

    public FractionNumber(String num, String den) {
        if (Integer.parseInt(den) == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.numerator = Integer.parseInt(num);
        this.denominator = Integer.parseInt(den);
        optimize();
    }


    public FractionNumber add(FractionNumber left, FractionNumber right) {
        int newNumerator = left.numerator * right.denominator + right.numerator * left.denominator;
        int newDenominator = left.denominator * right.denominator;
        return new FractionNumber(newNumerator, newDenominator).optimize();
    }

    public FractionNumber add(FractionNumber right) {
        return add(this, right);
    }

    public FractionNumber subtract(FractionNumber left, FractionNumber right) {
        int newNumerator = left.numerator * right.denominator - right.numerator * left.denominator;
        int newDenominator = left.denominator * right.denominator;
        return new FractionNumber(newNumerator, newDenominator).optimize();
    }

    public FractionNumber subtract(FractionNumber right) {
        int newNumerator = this.numerator * right.denominator - right.numerator * this.denominator;
        int newDenominator = this.denominator * right.denominator;
        return new FractionNumber(newNumerator, newDenominator).optimize();
    }

    public FractionNumber divide(FractionNumber right) {
        if (right.numerator == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        int newNumerator = this.numerator * right.denominator;
        int newDenominator = this.denominator * right.numerator;
        return new FractionNumber(newNumerator, newDenominator).optimize();
    }

    public FractionNumber multiple(FractionNumber left, FractionNumber right) {
        int newNumerator = left.numerator * right.numerator;
        int newDenominator = left.denominator * right.denominator;
        return new FractionNumber(newNumerator, newDenominator).optimize();
    }

    public FractionNumber multiple(FractionNumber right) {
        int newNumerator = this.numerator * right.numerator;
        int newDenominator = this.denominator * right.denominator;
        return new FractionNumber(newNumerator, newDenominator).optimize();
    }

    public FractionNumber square() {
        int newNumerator = this.numerator * this.numerator;
        int newDenominator = this.denominator * this.denominator;
        return new FractionNumber(newNumerator, newDenominator).optimize();
    }

    public FractionNumber reverse() {
        if (this.numerator == 0) {
            throw new IllegalArgumentException("Cannot reverse a fraction with zero numerator.");
        }
        return new FractionNumber(this.denominator, this.numerator).optimize();
    }

    private FractionNumber optimize() {
        int gcd = gcd(this.numerator, this.denominator);
        this.numerator /= gcd;
        this.denominator /= gcd;
        if (this.denominator < 0) {
            this.numerator *= -1;
            this.denominator *= -1;
        }
        return this;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    @Override
    public String toString(){
        return (this.numerator + "|" + this.denominator);
    }
}
