/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Inter9.java,v 1.1 2006/04/21 17:14:26 livshits Exp $
 */
package securibench.micro.inter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;
import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;

/** 
 *  @servlet description="simple object sensitivity" 
 *  @servlet vuln_count = "2" 
 *  */
public class Inter9 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String s1 = req.getParameter(FIELD_NAME);
        
        String s2 = foo(s1);
        String s3 = foo("abc");
        
        PrintWriter writer = resp.getWriter();  
        writer.println(s2);                    /* BAD */
        writer.println(s3);                    /* OK */
        
        String s4 = bar(s1);
        String s5 = bar("abc");
        
        writer.println(s4);                    /* BAD */
        writer.println(s5);                    /* OK */
    }
    
    private @RUntainted String foo(@RUntainted String s1) {
		return s1.toLowerCase();
	}

    private @RUntainted String bar(@RUntainted String s1) {
		return s1.toLowerCase(Locale.ENGLISH);
	}
    
    public String getDescription() {
        return "simple object sensitivity";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}
