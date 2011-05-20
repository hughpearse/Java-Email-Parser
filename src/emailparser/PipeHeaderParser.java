package emailparser;

import java.io.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;

import java.util.Enumeration;

import javax.mail.internet.InternetHeaders;

/**
 *
 * @author goonmaster 2011
 * Description:
 * This application takes a system pipe and passes the message headers to the standard output.
 */
public class PipeHeaderParser {
    public void PipeHeaderParser(){

    }
    
    public void scan() throws MessagingException {
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        char c1 = 0xA;
        String headers = "";
        String converted = "";
        String text = null;

        //convert contents of text file to a string object
        try {
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
        converted = contents.toString();
        
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
