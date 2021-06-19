package io.github.coolgod;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SimpleHttpServlet extends HttpServlet {
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("SimpleHttpServlet.doGet!");
        HttpSession session = request.getSession();

        session.setAttribute("flag", "test");

        RequestDispatcher requestDispatcher =
                request.getRequestDispatcher("/name?name=servlet");

        requestDispatcher.forward(request, response);
    }
}
