package com.clarc.main.admin;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.clarc.main.ClarcInfopageAccessTotals;

@Controller
@RequestMapping("/adminInfopageAccessTotalsAdd")
public class AdminInfopageAccessTotalsAddController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      model.addAttribute("clarcInfopageAccessTotals", new ClarcInfopageAccessTotals());

      return "admin/adminInfopageAccessTotalsAdd";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcInfopageAccessTotals") @Valid ClarcInfopageAccessTotals clarcInfopageAccessTotals, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminInfopageAccessTotalsAdd";
      }

	  int n = jdbc.update("INSERT INTO clarc_infopage_access_totals (id,"
            + "pageId,"
            + "pageViews,"
            + "accessDate) values(?,"
            + "?,"
            + "?,"
            + "CURRENT_TIMESTAMP)",
	  		clarcInfopageAccessTotals.getId(),
	  		clarcInfopageAccessTotals.getPageId(),
	  		clarcInfopageAccessTotals.getPageViews(),
	  		clarcInfopageAccessTotals.getAccessDate());
	  if(n > 0)
	  {
	     model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminInfopageAccessTotalsAdd";
   }
}

