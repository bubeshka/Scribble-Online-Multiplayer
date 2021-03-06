package home.servlets.queue;

import game.classes.Game;
import home.classes.Matchmaker;
import login.classes.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(value = "/RankedplayServlet", name = "RankedplayServlet")
public class RankedplayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(!(boolean) session.getAttribute("INGAME") || (session.getAttribute("GAME") == null || ((Game) session.getAttribute("GAME")).isOver()))
        {
            Matchmaker mm = (Matchmaker) getServletContext().getAttribute("MATCHMAKER");
            Game game = null;
            try
            {
                game = mm.addToRankedQueue(((User) session.getAttribute("USER")).getRating());
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            session.setAttribute("GAME", game);
            session.setAttribute("INGAME", false);
        }
        RequestDispatcher rd = request.getRequestDispatcher("game.jsp");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
