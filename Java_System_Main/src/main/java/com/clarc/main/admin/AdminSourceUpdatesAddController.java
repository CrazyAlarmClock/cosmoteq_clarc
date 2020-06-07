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
import com.clarc.main.ClarcSourceUpdates;

@Controller
@RequestMapping("/adminSourceUpdatesAdd")
public class AdminSourceUpdatesAddController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      model.addAttribute("clarcSourceUpdates", new ClarcSourceUpdates());

      return "admin/adminSourceUpdatesAdd";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcSourceUpdates") @Valid ClarcSourceUpdates clarcSourceUpdates, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminSourceUpdatesAdd";
      }

	  int n = jdbc.update("INSERT INTO clarc_source_updates (id,"
            + "sourceId,"
            + "updatedUserId,"
            + "status,"
            + "updatedTime) values(?,"
            + "?,"
            + "?,"
            + "?,"
            + "CURRENT_TIMESTAMP)",
	  		clarcSourceUpdates.getId(),
	  		clarcSourceUpdates.getSourceId(),
	  		clarcSourceUpdates.getUpdatedUserId(),
	  		clarcSourceUpdates.getStatus(),
	  		clarcSourceUpdates.getUpdatedTime());
	  if(n > 0)
	  {
	     model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminSourceUpdatesAdd";
   }
}

