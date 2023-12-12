package ru.sberbank.edu.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/finance")
public class FinanceController {

    /**
     * Get-controller
     * @return main page with form
     */
    @GetMapping
    public String mainPage() {
        return "main";
    }

    /**
     * Post-controller
     * @param amount - сумма вклада
     * @param proc - процент вклада
     * @param year - кол-во лет вклада
     * @param minLimit - мин/ сумма вклада
     * @param model - модель (MVC)
     * @return расчетную страницу или страницу с ошибкой
     */
    @PostMapping
    public String financePage(
            @RequestParam(value = "amount", required = true) String amount,
            @RequestParam(value = "proc", required = true) String proc,
            @RequestParam(value = "year", required = true) String year,
            @Value("${minLimit}") int minLimit,
            Model model
    ) {

        int sum = 0;
        int procent = 0;
        int time = 0;

        try {
            sum = Integer.parseInt(amount);
            procent = Integer.parseInt(proc);
            time = Integer.parseInt(year);

            if (sum < 0 || procent < 0 || time < 0) {
                return "errorCustom";
            }
        } catch (NumberFormatException nfe) {
            return "errorCustom";
        }

        if (sum < minLimit) {
            return "errorMoney";
        }

        float totalSum = (float) sum * procent * time / 100;

        model.addAttribute("totalSum", totalSum);

        return "finance";
    }
}
