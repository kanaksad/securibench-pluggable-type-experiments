/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Basic4.java,v 1.4 2006/04/04 20:00:40 livshits Exp $
 */
package securibench.micro.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;
import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;

/** 
 *  @servlet description="test path sensitivity just a bit" 
 *  @servlet vuln_count = "1" 
 *  */
public class Basic4 extends BasicTestCase implements MicroTestCase {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String str = req.getParameter("name");
        Random r = new Random();
        int choice = r.nextInt();
        PrintWriter writer = resp.getWriter();
        
        switch (choice) {   
            case 1: break;
            case 2: break;
            case 3: 
                writer.println(str);    /* BAD */
                break;
            default:
        }
    }
    
    public String getDescription() {
        return "test path sensitivity just a bit";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}
