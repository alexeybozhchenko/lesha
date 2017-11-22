package cs5.bozhchenko.servlet;

/**
 * Created by motorcrue on 22.11.2017.
 */
import cs5.bozhchenko.User;
import cs5.bozhchenko.db.DaoFactory;
import cs5.bozhchenko.db.DatabaseException;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddServlet extends EditServlet {

    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDao().create(user);
    }

    protected void showPage(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/add.jsp").forward(req, resp);
    }
}