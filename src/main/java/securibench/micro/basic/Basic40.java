/**
   @author Benjamin Livshits <livshits@cs.stanford.edu>
   
   $Id: Basic40.java,v 1.3 2006/04/21 17:14:26 livshits Exp $
 */
package securibench.micro.basic;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;
import com.oreilly.servlet.MultipartRequest;
import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;

/**
 * @servlet description="MultipartRequest test"
 * @servlet vuln_count = "1"
 */
public class Basic40 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";
      
    protected void doGet(@RUntainted HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // TODO: this expects multipart input 
        MultipartRequest mreq = new MultipartRequest(req, System.getenv("HOME"));
        String name = mreq.getParameter(FIELD_NAME);
        
        PrintWriter writer = resp.getWriter();
        writer.println(name);									/* BAD */
    }
    

    public String getDescription() {
        return "MultipartRequest test";
    }

    public int getVulnerabilityCount() {
        return 1;
    }
}
