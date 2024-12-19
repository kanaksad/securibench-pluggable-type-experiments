/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Datastructures1.java,v 1.1 2006/04/21 17:14:24 livshits Exp $
 */
package securibench.micro.datastructures;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;
import edu.ucr.cs.riple.taint.ucrtainting.qual.RUntainted;

/** 
 *  @servlet description="simple test of field assignment" 
 *  @servlet vuln_count = "1" 
 *  */
public class Datastructures1 extends BasicTestCase implements MicroTestCase {
    public class C {
    	private @RUntainted String str;
    	private String tag = "abc";
    	
    	public @RUntainted String getData(){return this.str;}
    	public @RUntainted String getTag(){return this.str;}
    	public void setData(@RUntainted String str){this.str = str;}
	}

	private static final String FIELD_NAME = "name";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       String name = req.getParameter(FIELD_NAME);
       C c = new C();
       c.setData(name);
       String str = c.getData();
       String tag = c.getTag();
       
       PrintWriter writer = resp.getWriter();
       writer.println(str);                              /* BAD */
       writer.println(tag);                              /* OK */
    }
    
    public String getDescription() {
        return "simple test of field assignment";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}
