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
public enum EStudentLevel {
    PREPARSEM("Preparatory Semester"), FRESHMAN1("Freshman I"), FRESHMAN2("Freshman II"), SOPH1("Sophomore I"),
    SOPH2("Sophomore II"), JUNIOR1("Junior I"), JUNIOR2("Junior II"), MASTER1("Master I"), MASTER2("Master II"), 
    MBA1("MBA I"), MBA2("MBA II"), PHD("PHD");
    
    private String code;
    
    EStudentLevel(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    
    public static EStudentLevel getValue(String code){
        for(EStudentLevel sl: values()) {
            if(sl.getCode().equals(code))
                return sl;
        }
        return null;
    }
    
}
