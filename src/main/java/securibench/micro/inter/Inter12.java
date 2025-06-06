/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Inter12.java,v 1.1 2006/04/21 17:14:26 livshits Exp $
 */
package securibench.micro.inter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;

/** 
 *  @servlet description="collection as a static field" 
 *  @servlet vuln_count = "1" 
 *  */
public class Inter12 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";
    static final Collection<String> COLLECTION1 = new LinkedList<String>();
    static final Collection<String> COLLECTION2 = new LinkedList<String>();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String s1 = req.getParameter(FIELD_NAME);
        
        foo("abc");
        bar(s1);
        
        PrintWriter writer = resp.getWriter();
        String s2 = (String) COLLECTION1.iterator().next();
        String s3 = (String) COLLECTION2.iterator().next();
        
        writer.println(s2);                    /* BAD */
        writer.println(s3);                    /* OK */
    }
    
	private void foo(String s) {
		COLLECTION2.add(s);
	}
	
	private void bar(String s) {
		COLLECTION1.add(s);
	}
    
    public String getDescription() {
        return "collection as a static field";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}
