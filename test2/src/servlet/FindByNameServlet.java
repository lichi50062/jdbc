package servlet;

import service.UserService;
import service.UserServiceImpl;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindByNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        UserService userService = new UserServiceImpl();
        User user = userService.findByName(name);

        //將查詢結果放入request作用域
        request.setAttribute("userInfo",user);
        request.getRequestDispatcher("/jsp/index.jsp").forward(request,response);
    }
}
