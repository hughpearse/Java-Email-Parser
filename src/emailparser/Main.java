/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emailparser;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author goonmaster
 */
public class Main {
    
    public static void help(){
        System.out.println("");
        System.out.println("RFC822 Email Parser - Hugh Pearse");
        System.out.println("");
        System.out.println("File input:");
        System.out.println("\tuse the -f parameter followed by the filename\n\tand then the header/body parameter -[h/b]");
        System.out.println("\tExample:");
        System.out.println("\t\t java -jar EmailParser.jar -f email.txt -h");
        System.out.println("");
        System.out.println("Pipe input:");
        System.out.println("\tuse the -p parameter followed by the header/body parameter -[h/b]");
        System.out.println("\tExample:");
        System.out.println("\t\t java -jar EmailParser.jar -p -b < email.txt");
        System.out.println("");
    }
    
    public static void main(String[] args){
        int success=0;
        //files
        if(args[0].contains("-f") && args.length == 3){
            success++;
            //body
            if(args[2].contains("-b")){
                success++;
                FileBodyParser fb = new FileBodyParser(args[1]);
                try {
                    fb.scan();
                } catch (MessagingException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //headers
            if(args[2].contains("-h")){
                success++;
                FileHeaderParser fh = new FileHeaderParser(args[1]);
                try {
                    fh.scan();
                } catch (MessagingException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //pipes
        if(args[0].contains("-p") && args.length == 2){
            success++;
            //body
            if(args[1].contains("-b")){
                success++;
                PipeBodyParser pb = new PipeBodyParser();
                try {
                    pb.scan();
                } catch (MessagingException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //headers
            if(args[1].contains("-h")){
                success++;
                PipeHeaderParser ph = new PipeHeaderParser();
                try {
                    ph.scan();
                } catch (MessagingException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if(success<2)
            help();
    }
}
