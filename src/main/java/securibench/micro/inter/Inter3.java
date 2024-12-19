/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Inter3.java,v 1.6 2006/04/21 17:14:26 livshits Exp $
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
 *  @servlet description="chains of method calls" 
 *  @servlet vuln_count = "1" 
 *  */
public class Inter3 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";
    private PrintWriter writer;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter(FIELD_NAME);
        
        writer = resp.getWriter();
        f1(name);
    }
    
    private void f1(@RUntainted String name) {
        f2(name);        
    }

    private void f2(@RUntainted String name) {
        f3(name);
        f3("ade".concat(name)); 
    }

    private void f3(@RUntainted String name) {
        f4(name);
    }

    private void f4(@RUntainted String name) {
        f5(name);        
    }

    private void f5(@RUntainted String name) {
        f6(name);
    }

    private void f6(@RUntainted String name) {
        f7(name);
        f7(name + "abc");
        f8("adsf "+ name + "abc");
        f8("adsf "+ name + "abc");
        
    }

    private void f7(@RUntainted String name) {
        f8(name);        
    }

    private void f8(@RUntainted String name) {
        f9(name);        
    }

    // reachable code
    private void f9(@RUntainted String name) {
        writer.println(name);       /* BAD */ 
    }
    
    // dead code
    public void f0(@RUntainted String name) {
        writer.println(name);       /* OK */        
    }

    public String id(@RUntainted String string, PrintWriter writer) {
        writer.println(string);     /* OK */
        
        return string;
    }

    public String getDescription() {
        return "chains of method calls";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}
