package com.example.mvccrudoperationer.contoller;

import com.example.mvccrudoperationer.model.UserEntry;
import com.example.mvccrudoperationer.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * @author Tobias Heidlund
 */
@WebServlet(value = "/login/*")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html");

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getPathInfo().contains("logout")){
            LogOutUser(req, resp);
        }else {
            LoginOrReset(req, resp);
        }
    }

    private static void LoginOrReset(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        UserService service = new UserService();
        UserEntry entry= service.get(username,password);
        if(entry == null){
            resp.sendRedirect("/index.jsp?error=invalid login");
        }else{
            HttpSession session = req.getSession(true);
            session.setAttribute("user",entry);
            resp.sendRedirect("/invoice/read");
        }
    }

    private static void LogOutUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(true);
        session.setAttribute("username", null);
        session.invalidate();
        resp.sendRedirect("/index.jsp");
    }
}