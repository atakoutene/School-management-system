/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author metch
 */
public enum ETestType {
    QUIZ("Quiz"), ASSIGNMENT("Assignment"), RESEARCH_WORK("Research Work"), TEST("Test"), MIDTERM("Midterm"), LAB("Lab"), FINAL_EXAM("Final Exam");
        private String code;
    
    ETestType(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
    public static ETestType getValue(String code){
        for(ETestType sl: values()) {
            if(sl.getCode().equals(code))
                return sl;
        }
        return null;
    }
    
}
