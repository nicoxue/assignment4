/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.fastenalcompany.servlet;

import ca.fastenalcompany.config.PropertyManager;
import ca.fastenalcompany.datesource.DBManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

/**
 *
 * @author JIAJUN XUE <nicoxue0324@gmail.com>
 */
@WebServlet(name = "ProductServlet")
public class ProductServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductServlet at " + request.getContextPath() + "</h1>");
            out.println("<h1>The last person is : </h1>");
            out.println("</body>");
            out.println("</html>");

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Provides GET /products and GET /products?id={id}
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //processRequest(request, response);
        response.setContentType("application/json");
        Set<String> keySet = request.getParameterMap().keySet();
        String id = request.getParameter("id");
        if (!keySet.contains("id")) {
            response.getWriter().write(query(PropertyManager.getProperty("db_selectAll")).toString());
        } else if (!id.isEmpty()) {
            JSONArray products = query(PropertyManager.getProperty("db_select"), id);
            response.getWriter().write(products.isEmpty() ? new JSONObject().toString() : products.get(0).toString());
        }
    }

    /**
     * Provides POST /products?name={n}&description={d}&quantity={q}
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain-text");
        Set<String> keySet = request.getParameterMap().keySet();
        if (keySet.contains("name") && keySet.contains("description") && keySet.contains("quantity")) {
            int productid = update(PropertyManager.getProperty("db_insert"), request.getParameter("name"), request.getParameter("description"), request.getParameter("quantity"));
            if (productid > 0) {
                String url = request.getRequestURL().toString() + "/" + productid;
                response.getWriter().write(url);
            } else {
                response.setStatus(500);
            }
        }
    }

    /**
     * PUT /products?id={id}&&name={n}&description={d}&quantity={q}
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain-text");
        Set<String> keySet = request.getParameterMap().keySet();
        if (keySet.contains("id") && keySet.contains("name") && keySet.contains("description") && keySet.contains("quantity") && !request.getParameter("id").isEmpty()) {
            int productid = update(PropertyManager.getProperty("db_update"), request.getParameter("name"), request.getParameter("description"), request.getParameter("quantity"), request.getParameter("id"));
            if (productid > 0) {
                String url = request.getRequestURL().toString() + "/" + productid;
                response.getWriter().write(url);
            } else {
                response.setStatus(500);
            }
        }
    }

    /**
     * DELETE /products?id={id}
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain-text");
        Set<String> keySet = request.getParameterMap().keySet();
        if (keySet.contains("id") && !request.getParameter("id").isEmpty()) {
            int productid = update(PropertyManager.getProperty("db_delete"), request.getParameter("id"));
            if (productid > 0) {
                response.getWriter().write("");
            } else {
                response.setStatus(500);
            }
        }
    }

    public JSONArray query(String query, String... params) {
        Connection conn = null;
        JSONArray products = new JSONArray();
        try {
            conn = DBManager.getMysqlConn();
            PreparedStatement pstmt = conn.prepareStatement(query);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            System.out.println(query);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                JSONObject product = new JSONObject();
                for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
                    String textLabel = rs.getMetaData().getColumnLabel(i);
                    String textValue = rs.getString(textLabel);
                    product.put(textLabel, textValue);
                }
                products.add(product);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                System.out.println("DB connection closed");
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return products;
    }

    public int update(String query, String... params) {
        Connection conn = null;
        int result = -1;
        try {
            conn = DBManager.getMysqlConn();
            PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int i = 1; i <= params.length; i++) {
                pstmt.setString(i, params[i - 1]);
            }
            System.out.println(query);
            int rowsEffected = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getInt(1);
            } else if (rowsEffected > 0) {
                result = Integer.parseInt(params[params.length - 1]);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                System.out.println("DB connection closed");
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
