package servlet;

import service.UserService;
import service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1 獲取
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        //2 service呼叫dao對資料庫操作
        UserService userService = new UserServiceImpl();
        int result = userService.login(userName, password);

        //3 成功跳轉到查詢頁面，失敗跳轉到失敗頁面
        if (result>0){
            response.sendRedirect("/jsp/index.jsp");
        }else{
            response.sendRedirect("/login_error.html");
        }
    }
}
