/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Basic10.java,v 1.5 2006/04/04 20:00:40 livshits Exp $
 */
package securibench.micro.basic;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;
import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;

/** 
 *  @servlet description="chains of value assignments" 
 *  @servlet vuln_count = "1" 
 *  */
public class Basic10 extends BasicTestCase implements MicroTestCase {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String s1 = req.getParameter("name");
        String s2 = s1;
        String s3 = s2;
        String s4 = s3;
        StringBuffer b1 = new StringBuffer(s4);
        //StringBuffer b2 = b1;
        StringBuffer b3 = b1;
        String s5 = b3.toString();
        String s6 = s5;
        
        PrintWriter writer = resp.getWriter();
        writer.println(s6);    /* BAD */
    }
    
    public String getDescription() {
        return "chains of value assignments";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}
