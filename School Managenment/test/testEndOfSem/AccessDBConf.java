/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testEndOfSem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CNC
 */
public class AccessDBConf {

    public static void main(String[] args) {
        testWriting();
        testReading();
    }

    private static void testReading() {
        InputStream stream = null;
        try {
            stream = AccessDBConf.class.getResource("/Resource/configurations/databaseconnect.dat").openStream();
            Scanner sc = new Scanner(stream);
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        } catch (IOException ex) {
            Logger.getLogger(AccessDBConf.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(AccessDBConf.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void testWriting() {
        PrintWriter pw = null;
        try {
            URI uri = ClassLoader.getSystemResource("Resource/configurations/databaseconnect.dat").toURI();
            System.out.println(uri.toString());
            OutputStream stream = Files.newOutputStream(Paths.get(uri));
            pw = new PrintWriter(stream);
            pw.println("user--bankalicoq");
            pw.println("password--password");
            pw.print("url--myconnector");
        } catch (IOException ex) {
            Logger.getLogger(AccessDBConf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AccessDBConf.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }
    }
    
    public static void writingToDB(String user, String password, String url) {
        PrintWriter pw = null;
        try {
            URI uri = ClassLoader.getSystemResource("Resource/configurations/databaseconnect.dat").toURI();
            OutputStream stream = Files.newOutputStream(Paths.get(uri));
            pw = new PrintWriter(stream);
            pw.println("user--"+user);
            pw.println("password--"+password);
            pw.print("url--"+url);
        } catch (IOException ex) {
            Logger.getLogger(AccessDBConf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(AccessDBConf.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw.close();
        }
    }
}
