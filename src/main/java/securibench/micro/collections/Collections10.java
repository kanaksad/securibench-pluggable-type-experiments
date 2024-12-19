/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Collections10.java,v 1.1 2006/04/21 17:14:26 livshits Exp $
 */
package securibench.micro.collections;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;

/** 
 *  @servlet description = "more complex collection copying" 
 *  @servlet vuln_count = "0" 
 *  */
public class Collections10 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String s1 = req.getParameter(FIELD_NAME);
        LinkedList<String> c1 = new LinkedList<String>();
        c1.addLast(s1);
        ArrayList<String> c2 = new ArrayList<String>();
        c2.add("abc");
        
        Iterator<String> iter = c1.iterator();
        PrintWriter writer = resp.getWriter();  
        while(iter.hasNext()){
        	String str = iter.next();
        	
        	writer.println(str);                    /* BAD */
        }
        
        iter = c2.iterator();
        while(iter.hasNext()){
        	String str = iter.next();
        	
        	writer.println(str);                    /* OK */
        }
    }
    
    public String getDescription() {
        return "more complex collection copying";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}
