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
import com.clarc.main.ClarcInfopagesContent;

@Controller
@RequestMapping("/adminInfopagesContentAdd")
public class AdminInfopagesContentAddController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      model.addAttribute("clarcInfopagesContent", new ClarcInfopagesContent());

      return "admin/adminInfopagesContentAdd";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcInfopagesContent") @Valid ClarcInfopagesContent clarcInfopagesContent, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminInfopagesContentAdd";
      }

	  int n = jdbc.update("INSERT INTO clarc_infopages_content (id,"
            + "pageId,"
            + "pageContentBlockId,"
            + "pageContentBlock,"
            + "status,"
            + "addedTime) values(?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "CURRENT_TIMESTAMP)",
	  		clarcInfopagesContent.getId(),
	  		clarcInfopagesContent.getPageId(),
	  		clarcInfopagesContent.getPageContentBlockId(),
	  		clarcInfopagesContent.getPageContentBlock(),
	  		clarcInfopagesContent.getStatus(),
	  		clarcInfopagesContent.getAddedTime());
	  if(n > 0)
	  {
	     model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminInfopagesContentAdd";
   }
}

