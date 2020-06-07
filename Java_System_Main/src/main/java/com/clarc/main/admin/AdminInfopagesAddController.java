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
import com.clarc.main.ClarcInfopages;

@Controller
@RequestMapping("/adminInfopagesAdd")
public class AdminInfopagesAddController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      model.addAttribute("clarcInfopages", new ClarcInfopages());

      return "admin/adminInfopagesAdd";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcInfopages") @Valid ClarcInfopages clarcInfopages, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminInfopagesAdd";
      }

	  int n = jdbc.update("INSERT INTO clarc_infopages (id,"
            + "pageName,"
            + "pageCategory,"
            + "pageDescription,"
            + "pageAccessRights,"
            + "status,"
            + "addedTime) values(?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "CURRENT_TIMESTAMP)",
	  		clarcInfopages.getId(),
	  		clarcInfopages.getPageName(),
	  		clarcInfopages.getPageCategory(),
	  		clarcInfopages.getPageDescription(),
	  		clarcInfopages.getPageAccessRights(),
	  		clarcInfopages.getStatus(),
	  		clarcInfopages.getAddedTime());
	  if(n > 0)
	  {
	     model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminInfopagesAdd";
   }
}

