/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Datastructures3.java,v 1.1 2006/04/21 17:14:24 livshits Exp $
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
 *  @servlet description="simple nexted data" 
 *  @servlet vuln_count = "1" 
 *  */
public class Datastructures3 extends BasicTestCase implements MicroTestCase {
    public class C {
    	private @RUntainted String str;
    	private C next;
    	
    	public String getData(){return this.str;}
    	public void setData(@RUntainted String str){this.str = str;}
    	public void setNext(C next){this.next = next;}
	}

	private static final String FIELD_NAME = "name";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       String name = req.getParameter(FIELD_NAME);
       C c1 = new C();
       c1.setData("anbc");
       
       C c2 = new C();
       c2.setData(name);
       c1.setNext(c2);
       
       String str = c1.next.str;
       
       PrintWriter writer = resp.getWriter();
       writer.println(str);                              /* BAD */
    }
    
    public String getDescription() {
        return "simple nexted data";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}
