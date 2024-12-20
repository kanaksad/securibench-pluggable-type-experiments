/**
   @author Benjamin Livshits <livshits@cs.stanford.edu>
   
   $Id: Basic37.java,v 1.2 2006/04/04 20:00:40 livshits Exp $
 */
package securibench.micro.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;
import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;

/**
 * @servlet description="StringTokenizer test"
 * @servlet vuln_count = "1"
 */
public class Basic37 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";
      
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter(FIELD_NAME);
        StringTokenizer tok = new StringTokenizer(name, "\t");
        while(tok.hasMoreTokens()) {
            PrintWriter writer = resp.getWriter();        
            writer.println(tok.nextToken());              /* BAD */    
        }
    }

    public String getDescription() {
        return "StringTokenizer test";
    }

    public int getVulnerabilityCount() {
        return 1;
    }
}
