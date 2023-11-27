package ru.sberbank.edu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Hello world!
 *
 */
@WebServlet(urlPatterns = "/finance")
public class FinancialServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/getFinance.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int sum = 0;
        int procent = 0;
        int time = 0;

        String amount = req.getParameter("amount");
        String proc = req.getParameter("proc");
        String year = req.getParameter("year");

        try {
            sum = Integer.parseInt(amount);
            procent = Integer.parseInt(proc);
            time = Integer.parseInt(year);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            req.getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
        }

        if (sum < 50000) {
            req.getRequestDispatcher("/WEB-INF/errorPageMoney.jsp").forward(req, resp);
        }

        float totalSum = (float) sum * procent * time / 100;

        req.setAttribute("total", totalSum);

        req.getRequestDispatcher("/WEB-INF/financeResult.jsp").forward(req, resp);

    }
}
