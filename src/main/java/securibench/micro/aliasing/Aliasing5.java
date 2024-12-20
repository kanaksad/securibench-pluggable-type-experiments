/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Aliasing5.java,v 1.1 2006/04/21 17:14:27 livshits Exp $
 */
package securibench.micro.aliasing;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;
import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;

/** 
 *  @servlet description="interprocedural argument aliasing" 
 *  @servlet vuln_count = "1" 
 *  */
public class Aliasing5 extends BasicTestCase implements MicroTestCase {
	private static final String FIELD_NAME = "name";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       	StringBuffer buf = new StringBuffer("abc"); 
       	foo(buf, buf, resp, req);
    }
    
    void foo(StringBuffer buf, @RUntainted StringBuffer buf2, ServletResponse resp, ServletRequest req) throws IOException {
    	String name = req.getParameter(FIELD_NAME);
    	buf.append(name);
    	PrintWriter writer = resp.getWriter();
        writer.println(buf2.toString());                              /* BAD */
	}

	public String getDescription() {
        return "interprocedural argument aliasing";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}
