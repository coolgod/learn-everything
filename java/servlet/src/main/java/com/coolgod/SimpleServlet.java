package com.coolgod;

import javax.servlet.GenericServlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class SimpleServlet extends GenericServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws IOException {

        System.out.println("SimpleServlet.service!");
        String name = req.getParameter("name");

        res.getWriter().write("<html><body>Hello, "  + name + "!</body></html>");
    }
}
