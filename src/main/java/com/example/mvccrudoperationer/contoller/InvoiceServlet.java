package com.example.mvccrudoperationer.contoller;

import com.example.mvccrudoperationer.model.Category;
import com.example.mvccrudoperationer.model.InvoiceEntry;
import com.example.mvccrudoperationer.model.UserEntry;
import com.example.mvccrudoperationer.service.InvoiceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Tobias Heidlund
 */
@WebServlet(value = "/invoice/*")
public class InvoiceServlet extends HttpServlet {
    private UserEntry user;
    private final InvoiceService service = new InvoiceService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession(true);
        user = getUserFromSession(session);
        if(user == null){ //ID not found
            response.sendRedirect("/index.jsp");
        }
        List<InvoiceEntry> entryList= service.getAll(user.getId());
        if(!entryList.isEmpty()){
            sendCurrentEntries(response.getWriter(), entryList);
            try {
                request.getRequestDispatcher("/Invoice.jsp").include(request,response);
            } catch (ServletException e) {
                throw new RuntimeException(e);
            }
        }else{
            response.sendRedirect("/Invoice.jsp");
        }


    }

    private void sendCurrentEntries(PrintWriter out, List<InvoiceEntry> entryList) {
        out.println("<form method='post' action='/invoice/getForUpdate'><table><thead>" +
                "    <tr>" +
                "        <th>Titel</th>" +
                "        <th>Datum</th>" +
                "        <th>Beskrivning</th>" +
                "        <th>Kategori</th>" +
                "        <th>Pris</th>" +
                "    </tr></thead>");
        for (InvoiceEntry entry: entryList) {
            out.println("<tr><tbody>");
            for (Object object: entry.toString().split("\t")) {
                out.println("<td>" + object.toString() + "</td>");
            }
            out.println("<td><button name='id' value='"+entry.getId()+"'>Edit</button></td>");
            out.println("</tr></tbody></form>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        user = getUserFromSession(session);
        if (user == null) { //ID not found
            resp.sendRedirect("/index.jsp");
        } else {
            switch (req.getPathInfo()) {
                case "/create":
                    create(req, resp);
                    break;
                case "/update":
                    update(req, resp);
                    break;
                case "/remove":
                    delete(req, resp);
                    break;
                case "/getForUpdate":
                    getForUpdate(req, resp);
                    break;
            }
        }
    }

    private void getForUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        InvoiceEntry entry = service.get(id,user.getId());
        if(entry == null){
            resp.sendRedirect("/invoice");
        }else {
            HashMap<String, Object> map = entry.getMap();
            for (String key : map.keySet()) {
                req.setAttribute(key, map.get(key));
            }
            req.setAttribute("id",entry.getId());
            req.getRequestDispatcher("/Edit.jsp").forward(req,resp);
        }
    }

    private InvoiceEntry.InvoiceEntryBuilder parseInput(HttpServletRequest req) {

        InvoiceEntry.InvoiceEntryBuilder builder = new InvoiceEntry.InvoiceEntryBuilder();
        builder.setTitle(req.getParameter("title"))
               .setDate(Date.valueOf(req.getParameter("date")))
               .setCategory(Category.valueOfLabel(req.getParameter("category")))
               .setDescription(req.getParameter("description"))
               .setPrice(Double.parseDouble(req.getParameter("price")))
               .setOwner(user.getId());
        return builder;
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        service.delete(id,user.getId());
        resp.sendRedirect("/invoice/read");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        InvoiceEntry.InvoiceEntryBuilder builder = parseInput(req);
        builder.setId(Integer.parseInt(req.getParameter("id")));
        service.update(builder.build());
        resp.sendRedirect("/invoice/read");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        InvoiceEntry.InvoiceEntryBuilder builder = parseInput(req);
        service.create(builder.build());
        resp.sendRedirect("/invoice/read");
    }


    @SuppressWarnings("CallToPrintStackTrace")
    private UserEntry getUserFromSession(HttpSession session) {
        Object idObject = session.getAttribute("user");
        if(idObject != null){
           try {
               return (UserEntry) idObject;
           }catch (ClassCastException e){
               e.printStackTrace();
           }
        }
        return null;
    }


}