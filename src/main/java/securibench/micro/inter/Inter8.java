/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Inter8.java,v 1.1 2006/04/21 17:14:26 livshits Exp $
 */
package securibench.micro.inter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;
import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;

/** 
 *  @servlet description="multi-level context sensitivity test" 
 *  @servlet vuln_count = "1" 
 *  */
public class Inter8 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String s1 = req.getParameter(FIELD_NAME);
        
        String s2 = foo(s1);
        String s3 = bar("abc");
        
        PrintWriter writer = resp.getWriter();  
        writer.println(s2);                    /* BAD */
        writer.println(s3);                    /* OK */
    }
    
    private @RUntainted String foo(@RUntainted String s1) {
		return id(s1);
	}

	private @RUntainted String bar(@RUntainted String string) {
		return id(string);
	}

	private @RUntainted String id(@RUntainted String string) {
        return id2(string);
    }
    
    private @RUntainted String id2(@RUntainted String string) {
        return string;
    }

    public String getDescription() {
        return "multi-level context sensitivity test";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}
