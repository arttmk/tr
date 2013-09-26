package logic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DebugLogic extends HttpServlet {

	private static final long serialVersionUID = -8203225527643544022L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		

		response.setContentType("text/html; charset=Shift_JIS");

        PrintWriter out = response.getWriter();
        out.println("<HTML>");
        out.println("<BODY>");
        
        
		// watch
        out.println("alive");
        
        out.println("</BODY>");
        out.println("</HTML>");
	}
}
