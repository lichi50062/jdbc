package servlet;

import service.UserService;
import service.UserServiceImpl;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        User user = null;
        int result = 0;
        //1【呼叫請求物件】讀取【請求頭】引數資訊，得到使用者註冊資訊
        String userName, password, age;
        userName = request.getParameter("userName");
        password = request.getParameter("password");
        age = request.getParameter("age");
        user = new User(userName, password, Integer.valueOf(age));
        //2 呼叫userService——>userDao
        // 先查詢使用者是否存在
        User byName = userService.findByName(userName);
        if (byName!=null){
            request.setAttribute("info","使用者已存在！");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request,response);
        }
        // 註冊
        result = userService.register(user);

        //3 設定編碼格式，防止亂碼
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        //註冊成功：——>跳轉至登入頁面進行登入
        //註冊失敗：——>註冊頁面提示：註冊失敗
        if (result == 1) {
            response.sendRedirect("/login.html");
        } else {
            request.setAttribute("info","註冊失敗！");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request,response);
        }
    }
}