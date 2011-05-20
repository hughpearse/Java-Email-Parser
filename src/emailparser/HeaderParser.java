package emailparser;

import java.util.Enumeration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;

/**
 *
 * @author goonmaster 2011
 */
public class HeaderParser {

     public static void main(String[] args) throws MessagingException {
        if(args.length <= 0){
             System.out.println("RFC822 message header parser.");
             System.out.println("Incorrect usage, try adding an rfc822 compliant email file as a parameter.");
             System.out.println("Example: java -jar programName filename.txt");
             System.exit(1);
         }

        File file = new File(args[0]);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;
        char c1 = 0xA;
        String headers = "";

        //convert contents of text file to a string object
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text).append(c1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //convert String Buffer to a regular string
        //newlines still identical to origional message
        String converted = contents.toString();

        try {
            //create internet headers object
            InternetHeaders emailVar = new InternetHeaders();

            //convert string to input stream
            InputStream streamVar = new ByteArrayInputStream(converted.getBytes("US-ASCII"));

            //load input stream into internet headers object
            emailVar.load(streamVar);

            //print all lines
            Enumeration num = emailVar.getAllHeaderLines();
            while(num.hasMoreElements()){
                //The rfc822 email header parser library
                //adds the hex character "0D" to headers that contain the character "0A"
                //The character "0D" must be removed
                System.out.print(num.nextElement().toString().replaceAll("\r", ""));
                System.out.print(c1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }//end main method
}
