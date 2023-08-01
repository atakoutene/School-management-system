/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.util.regex.Pattern;

/**
 *
 * @author metch
 */
public class PatternMatching {
    public static boolean isEmail(String email){
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }
    
    public static boolean isValidPhone(String phone){
        String regex = "^(?:6|2)[0-9]{8}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(phone).matches();
    }
    
}
