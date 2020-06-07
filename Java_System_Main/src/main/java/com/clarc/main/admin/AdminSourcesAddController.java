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
import com.clarc.main.ClarcSources;

@Controller
@RequestMapping("/adminSourcesAdd")
public class AdminSourcesAddController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      model.addAttribute("clarcSources", new ClarcSources());

      return "admin/adminSourcesAdd";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcSources") @Valid ClarcSources clarcSources, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminSourcesAdd";
      }

	  int n = jdbc.update("INSERT INTO clarc_sources (id,"
            + "sourceName,"
            + "sourceUrl,"
            + "sourceType,"
            + "status,"
            + "addedTime) values(?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "CURRENT_TIMESTAMP)",
	  		clarcSources.getId(),
	  		clarcSources.getSourceName(),
	  		clarcSources.getSourceUrl(),
	  		clarcSources.getSourceType(),
	  		clarcSources.getStatus(),
	  		clarcSources.getAddedTime());
	  if(n > 0)
	  {
	     model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminSourcesAdd";
   }
}

