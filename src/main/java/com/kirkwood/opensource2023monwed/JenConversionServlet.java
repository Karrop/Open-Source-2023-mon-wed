package com.kirkwood.opensource2023monwed;

import com.kirkwood.utilities.Helpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "jenConversionServlet", value="/jen-convert")

public class JenConversionServlet extends HttpServlet {

    Map<String, String> results = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jen-convert.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String quantity = req.getParameter("quantity");
        String conversion = req.getParameter("conversion");
        results.clear();
        results.put("quantity", quantity);
        results.put("conversion", conversion);
        convertMeasurement(quantity, conversion);
        req.setAttribute("results", results);
        req.getRequestDispatcher("WEB-INF/jen-convert.jsp").forward(req, resp);
    }

    private void convertMeasurement(String quantity, String conversion) {
        if (conversion == null || !conversion.equals("OtoC") && !conversion.equals("OtoP") &&
                !conversion.equals("CtoO") && !conversion.equals("CtoP") && !conversion.equals("PtoC")
                && !conversion.equals("PtoO")) {
            results.put("conversionError", "Select a conversion type");
        }
        if (!Helpers.isANumber(quantity)) {
            results.put("quantityError", "Please input a valid quantity");
        }

        if (!results.containsKey("conversionError") && !results.containsKey("quantityError")) {
            double quantityDouble = Double.parseDouble(quantity);
            if (conversion.equals("OtoC")) {
                double quantityConverted = (quantityDouble * 0.125);
                results.put("quantityConverted", String.format("%s Ounce(s) = %s Cup(s)", quantity, Helpers.round(quantityConverted)));
            }

            if (conversion.equals("OtoP")) {
                double quantityConverted = quantityDouble * 0.0625;
                results.put("quantityConverted", String.format("%s Ounce(s) = %s Pint(s)", quantity, Helpers.round(quantityConverted)));
            }

            if (conversion.equals("CtoO")) {
                double quantityConverted = quantityDouble / 0.125;
                results.put("quantityConverted", String.format("%s Cup(s) = %s Ounce(s)", quantity, Helpers.round(quantityConverted)));
            }

            if (conversion.equals("CtoP")) {
                double quantityConverted = quantityDouble / 2 ;
                results.put("quantityConverted", String.format("%s Cup(s) = %s Pint(s)", quantity, Helpers.round(quantityConverted)));
            }

            if (conversion.equals("PtoC")) {
                double quantityConverted = quantityDouble * 2 ;
                results.put("quantityConverted", String.format("%s Pint(s) = %s Cup(s)", quantity, Helpers.round(quantityConverted)));
            }

            if (conversion.equals("PtoO")) {
                double quantityConverted = quantityDouble / 0.0625;
                results.put("quantityConverted", String.format("%s Pint(s) = %s Ounce(s)", quantity, Helpers.round(quantityConverted)));
            }
        }
    }
}
