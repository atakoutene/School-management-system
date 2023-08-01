/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Steve Meudje
 */
public class Reference_Number {
    public static String lastpart(){
            try {
                File f = new File("src/Resource/StudentIdlastnum.txt");
                boolean b = f.createNewFile();
                if (b) {
                    try (PrintWriter output = new PrintWriter(f)) {
                        output.print("0");
                        output.close();
                    }
                }
                Scanner scanFile = new Scanner(f);
                int i = scanFile.nextInt();
                try (PrintWriter output = new PrintWriter(f)) {
                    output.print(i+1);
                }
                return (i+1) + (".pdf");
            }   catch (IOException ex) {
            Logger.getLogger(Reference_Number.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            return "0";
    }
    
    public static int firstpart(){
        MyDate m = new MyDate(System.currentTimeMillis());
        m.getYear();
        int a = m.getYear()- 2000; 
       return a;
        
       
    }
    
}
