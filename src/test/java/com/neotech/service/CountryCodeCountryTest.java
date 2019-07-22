package com.neotech.service;

import com.neotech.infrastructure.ValidationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CountryCodeCountryTest {

    private CountryCodeTree tree = new CountryCodeTree();

    @Before
    public void setup() {
        tree.add("USA", "+1 3 4  5 6 7 8");
        tree.add("USA, Virgin", "+1 34");
        tree.add("LV", "+371");
        tree.add("Lt", "+370");

        tree.add("BRT", "+9");
        tree.add("BRT gtr, not m", "+9 34");
    }

    @Test
    public void testAddCountryHappyPath() {

        assertEquals(tree.get("+1 3 4  5 6 7 8").getCountry(), "USA");
        assertEquals(tree.get("+1 3 4 777777").getCountry(), "USA, Virgin");
        assertEquals(tree.get("+371").getCountry(), "LV");
        assertEquals(tree.get("+370").getCountry(), "Lt");
        assertEquals(tree.get("+37056797098").getCountry(), "Lt");
        assertEquals(tree.get("+954674574567").getCountry(), "BRT");
        assertEquals(tree.get("+9 345634563456").getCountry(), "BRT gtr, not m");
        assertEquals(tree.get("+13456").getCountry(), "USA, Virgin");

    }

    @Test(expected = ValidationException.class)
    public void testCheckNumberWithLetter() {
        assertEquals(tree.get("+37122667m87").getCountry(), "LV");
    }

    @Test(expected = ValidationException.class)
    public void testCheckNumberShort() {
        assertEquals(tree.get("+37").getCountry(), "LV");
    }

    @Test(expected = ValidationException.class)
    public void testCheckNumberNotExists() {
        assertEquals(tree.get("+30000000000").getCountry(), "LV");
    }

    @Test(expected = ValidationException.class)
    public void testCheckNumberNotExists2() {
        assertEquals(tree.get("+0000000000").getCountry(), "LV");
    }

    @Test(expected = ValidationException.class)
    public void testCheckEmpty() {
        assertEquals(tree.get("").getCountry(), "LV");
    }

    @Test(expected = ValidationException.class)
    public void testCheckNull() {
        assertEquals(tree.get(null).getCountry(), "LV");
    }

    @Test
    public void testAddMultipleCodes() {

        tree.add("France", "+098, +097, + 096");
        assertEquals(tree.get("+098").getCountry(), "France");
        assertEquals(tree.get("+097").getCountry(), "France");
        assertEquals(tree.get("+096").getCountry(), "France");
    }    @Test
    public void testAddMultipleCountriesWithSameCode() {

        tree.add("China", "+098, +097, + 096");
        tree.add("Tibet", "+098, +097, + 096");
        tree.add("Nepal", "+098, +097, + 096");
        assertEquals(tree.get("+098").getCountry(), "China, Tibet, Nepal");
    }

}
