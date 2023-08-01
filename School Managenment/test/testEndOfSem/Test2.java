/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testEndOfSem;

import Control.PatternMatching;

/**
 *
 * @author metch
 */
public class Test2 {

    public static void main(String[] args) {
        System.out.println(PatternMatching.isEmail("laura@gmail.com") ? "Yes" : "No");
        System.out.println(PatternMatching.isValidPhone("621110054")? "Yes" : "No");
    }
}
