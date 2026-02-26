package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.DBUtil;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ================== GET : DISPLAY DATA ==================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, String>> resumeList = new ArrayList<>();

        String sql =
            "SELECT sl_no, name, mobile_no, post_applied_for, qualification, experience " +
            "FROM candidate_recruitment";

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("sl_no", rs.getString("sl_no"));
                row.put("name", rs.getString("name"));
                row.put("mobile_no", rs.getString("mobile_no"));
                row.put("post_applied_for", rs.getString("post_applied_for"));
                row.put("qualification", rs.getString("qualification"));
                row.put("experience", rs.getString("experience"));

                resumeList.add(row);
            }

            // 🔎 DEBUG — check Tomcat console
            System.out.println("Records fetched = " + resumeList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("resumeList", resumeList);

        // IMPORTANT: JSP should not be opened directly
        request.getRequestDispatcher("resume.jsp")
               .forward(request, response);
    }

    // ================== POST : UPDATE DATA ==================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String slNo = request.getParameter("sl_no");

        String sql =
            "UPDATE candidate_recruitment " +
            "SET name=?, mobile_no=?, post_applied_for=?, qualification=?, experience=? " +
            "WHERE sl_no=?";

        try (
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, request.getParameter("name"));
            ps.setString(2, request.getParameter("mobile_no"));
            ps.setString(3, request.getParameter("post_applied_for"));
            ps.setString(4, request.getParameter("qualification"));
            ps.setString(5, request.getParameter("experience"));
            ps.setInt(6, Integer.parseInt(slNo));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/resume");
    }
}