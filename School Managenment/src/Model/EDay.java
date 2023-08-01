/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Sokeng Paul (AG)
 */
public enum EDay {
    MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"), THURSDAY("Thursday"), FRIDAY("Friday"), SATURDAY("Saturday"), SUNDAY("Sunday");
    private String code;
    
    EDay(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        return code; //To change body of generated methods, choose Tools | Templates.
    }
    
    public static EDay getValue(String code){
        for(EDay sl: values()) {
            if(sl.getCode().equals(code))
                return sl;
        }
        return null;
    }
    
}
