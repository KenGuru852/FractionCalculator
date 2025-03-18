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

    public FractionNumber divide(FractionNumber left, FractionNumber right) {
        if (right.numerator == 0) {
            throw new IllegalArgumentException("Cannot divide by zero.");
        }
        int newNumerator = left.numerator * right.denominator;
        int newDenominator = left.denominator * right.numerator;
        return new FractionNumber(newNumerator, newDenominator).optimize();
    }

    public FractionNumber multiple(FractionNumber left, FractionNumber right) {
        int newNumerator = left.numerator * right.numerator;
        int newDenominator = left.denominator * right.denominator;
        return new FractionNumber(newNumerator, newDenominator).optimize();
    }

    public FractionNumber square(FractionNumber number) {
        int newNumerator = number.numerator * number.numerator;
        int newDenominator = number.denominator * number.denominator;
        return new FractionNumber(newNumerator, newDenominator).optimize();
    }

    public FractionNumber reverse(FractionNumber number) {
        if (number.numerator == 0) {
            throw new IllegalArgumentException("Cannot reverse a fraction with zero numerator.");
        }
        return new FractionNumber(number.denominator, number.numerator).optimize();
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
}
