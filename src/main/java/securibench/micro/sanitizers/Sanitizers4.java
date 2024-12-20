/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Sanitizers4.java,v 1.6 2006/04/04 20:00:41 livshits Exp $
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
 *  @servlet description="buggy sanitizer" 
 *  @servlet vuln_count = "2" 
 *  */
public class Sanitizers4 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";
    private PrintWriter writer;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter(FIELD_NAME);
        String clean = clean(name);
        
        writer = resp.getWriter();
        resp.setContentType("text/html");
        
        writer.println("<html>" + name  + "</html>");                  /* BAD */
        writer.println("<html>" + clean + "</html>");                  /* BAD */        
    }
    
    /** 
     * buggy javascript sanitization routine 
     * */
    private @RUntainted String clean(String name) {
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            switch (ch) {
                case '&':
                    buf.append("&amp;"); break;
                default:
                    buf.append(ch); break;
            }
        }
        
        return buf.toString();
    }

    public String getDescription() {
        return "buggy sanitizer";
    }
    
    public int getVulnerabilityCount() {
        return 2;
    }
}
