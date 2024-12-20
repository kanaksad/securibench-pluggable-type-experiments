/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Basic18.java,v 1.4 2006/04/04 20:00:40 livshits Exp $
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
 *  @servlet description="protect agains simple loop unrolling" 
 *  @servlet vuln_count = "1" 
 *  */
public class Basic18 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String s = req.getParameter(FIELD_NAME);

        for(int i = 0; i < 100; i++) {
            PrintWriter writer = resp.getWriter();
            if(i > 5 && (i % 17 == 0)) {
                writer.println(s);                    /* BAD */
            }
        }
    }
    
    public String getDescription() {
        return "protect agains simple loop unrolling";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}
