/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Sanitizers6.java,v 1.4 2006/04/04 20:00:41 livshits Exp $
 */
package securibench.micro.sanitizers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;
import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;

/** 
 *  @servlet description="sanitizers for directory traversal" 
 *  @servlet vuln_count = "0" 
 *  */
public class Sanitizers6 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";
    private PrintWriter writer;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter(FIELD_NAME);
        String clean = clean(name);
        
        writer = resp.getWriter();
        resp.setContentType("text/html");
        
        writer.println("<html>" + clean + "</html>");                  /* OK */        
    }
    
    /** 
     * @sanitizer 
     * sanitization routine for removing . and /\ characters from strings.
     * This routine performs white-listing by only allowing letters and digits through.  
     * */
    private static @RUntainted String clean(@RUntainted String name) {
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            
            if(Character.isLetter(ch) || Character.isDigit(ch) || ch == '_') {
                buf.append(ch);
            } else {
                buf.append('?');
            }
        }
        
        return buf.toString();
    }

    public String getDescription() {
        return "simple sanitization check";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
    
    public static void main(String[] args) {
        System.out.println(clean("xx/../yy"));  // xx????yy
        System.out.println(clean("~xx"));       // ?xx
        System.out.println(clean("xx_yy"));     // xx_yy
    }
}
