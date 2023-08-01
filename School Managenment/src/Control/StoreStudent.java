/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Steve Meudje
 */
public class StoreStudent {
    public static void storestudents (CRUDStudent l){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("src/Resource/student.txt");
            try (PrintWriter dos = new PrintWriter(fos)) {
                System.out.println(l.toString());
                System.out.println("Trying to save");
                for (int i = 0; i < l.getList().size(); i++) {
                    dos.print(l.getList().get(i).getStuID()+" ");
                    dos.print(l.getList().get(i).getName()+" ; ");
                    dos.print(l.getList().get(i).getAddress()+" ");
                    dos.print(l.getList().get(i).getPhoneNumber()+" ");
                    dos.print(l.getList().get(i).getEmailAddress()+" ");
                    dos.print(l.getList().get(i).getStatus()+" ");
                    dos.print(l.getList().get(i).getGender()+" ");
                    dos.print(l.getList().get(i).getDep()+" ; ");
                    dos.print(l.getList().get(i).getBirth().getDay()+" ");
                    dos.print(l.getList().get(i).getBirth().getMonth()+" ");
                    dos.println(l.getList().get(i).getBirth().getYear()+" ");
                    
                }
                dos.close();
            
        }    } catch (FileNotFoundException ex) {
            Logger.getLogger(StoreStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
