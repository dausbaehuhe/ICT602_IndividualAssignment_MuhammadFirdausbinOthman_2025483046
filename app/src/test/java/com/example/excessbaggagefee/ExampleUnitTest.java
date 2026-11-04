package com.example.excessbaggagefee;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {

    @Test
    public void testExcessBaggageCalculation_Case1() {
        // Sample 1: 25 kg, economy -> automatic rate = 15 RM/kg, allowance = 20 kg
        double weight = 25.0;
        boolean isBusiness = false;

        double allowance = isBusiness ? 40.0 : 20.0;
        double rate = isBusiness ? 12.0 : 15.0;
        double excessWeight = Math.max(0.0, weight - allowance);
        double excessCharge = excessWeight * rate;
        double serviceTax = 0.08 * excessCharge;
        double totalPayable = excessCharge + serviceTax;

        assertEquals(5.0, excessWeight, 0.001);
        assertEquals(75.0, excessCharge, 0.001);
        assertEquals(81.00, totalPayable, 0.001);
    }

    @Test
    public void testExcessBaggageCalculation_Case2() {
        // Sample 2: 18 kg, economy -> automatic rate = 15 RM/kg, allowance = 20 kg (under allowance)
        double weight = 18.0;
        boolean isBusiness = false;

        double allowance = isBusiness ? 40.0 : 20.0;
        double rate = isBusiness ? 12.0 : 15.0;
        double excessWeight = Math.max(0.0, weight - allowance);
        double excessCharge = excessWeight * rate;
        double serviceTax = 0.08 * excessCharge;
        double totalPayable = excessCharge + serviceTax;

        assertEquals(0.0, excessWeight, 0.001);
        assertEquals(0.0, excessCharge, 0.001);
        assertEquals(0.0, totalPayable, 0.001);
    }

    @Test
    public void testExcessBaggageCalculation_Case3() {
        // Sample 3: 55 kg, business -> automatic rate = 12 RM/kg, allowance = 40 kg
        double weight = 55.0;
        boolean isBusiness = true;

        double allowance = isBusiness ? 40.0 : 20.0;
        double rate = isBusiness ? 12.0 : 15.0;
        double excessWeight = Math.max(0.0, weight - allowance);
        double excessCharge = excessWeight * rate;
        double serviceTax = 0.08 * excessCharge;
        double totalPayable = excessCharge + serviceTax;

        assertEquals(15.0, excessWeight, 0.001);
        assertEquals(180.0, excessCharge, 0.001);
        assertEquals(194.40, totalPayable, 0.001);
    }

    @Test
    public void testExcessBaggageCalculation_Case4() {
        // Sample 4: 42.5 kg, business -> automatic rate = 12 RM/kg, allowance = 40 kg
        double weight = 42.5;
        boolean isBusiness = true;

        double allowance = isBusiness ? 40.0 : 20.0;
        double rate = isBusiness ? 12.0 : 15.0;
        double excessWeight = Math.max(0.0, weight - allowance);
        double excessCharge = excessWeight * rate;
        double serviceTax = 0.08 * excessCharge;
        double totalPayable = excessCharge + serviceTax;

        assertEquals(2.5, excessWeight, 0.001);
        assertEquals(30.0, excessCharge, 0.001);
        assertEquals(32.40, totalPayable, 0.001);
    }
}
