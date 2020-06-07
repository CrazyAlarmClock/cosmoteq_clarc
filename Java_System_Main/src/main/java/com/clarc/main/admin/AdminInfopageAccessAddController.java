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
import com.clarc.main.ClarcInfopageAccess;

@Controller
@RequestMapping("/adminInfopageAccessAdd")
public class AdminInfopageAccessAddController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      model.addAttribute("clarcInfopageAccess", new ClarcInfopageAccess());

      return "admin/adminInfopageAccessAdd";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcInfopageAccess") @Valid ClarcInfopageAccess clarcInfopageAccess, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminInfopageAccessAdd";
      }

	  int n = jdbc.update("INSERT INTO clarc_infopage_access (id,"
            + "userId,"
            + "pageId,"
            + "status,"
            + "addedTime) values(?,"
            + "?,"
            + "?,"
            + "?,"
            + "CURRENT_TIMESTAMP)",
	  		clarcInfopageAccess.getId(),
	  		clarcInfopageAccess.getUserId(),
	  		clarcInfopageAccess.getPageId(),
	  		clarcInfopageAccess.getStatus(),
	  		clarcInfopageAccess.getAddedTime());
	  if(n > 0)
	  {
	     model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminInfopageAccessAdd";
   }
}

