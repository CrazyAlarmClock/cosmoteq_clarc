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
import com.clarc.main.ClarcInfopageCategories;

@Controller
@RequestMapping("/adminInfopageCategoriesAdd")
public class AdminInfopageCategoriesAddController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      model.addAttribute("clarcInfopageCategories", new ClarcInfopageCategories());

      return "admin/adminInfopageCategoriesAdd";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcInfopageCategories") @Valid ClarcInfopageCategories clarcInfopageCategories, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminInfopageCategoriesAdd";
      }

	  int n = jdbc.update("INSERT INTO clarc_infopage_categories (id,"
            + "pageCategoryName,"
            + "status,"
            + "addedTime) values(?,"
            + "?,"
            + "?,"
            + "CURRENT_TIMESTAMP)",
	  		clarcInfopageCategories.getId(),
	  		clarcInfopageCategories.getPageCategoryName(),
	  		clarcInfopageCategories.getStatus(),
	  		clarcInfopageCategories.getAddedTime());
	  if(n > 0)
	  {
	     model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminInfopageCategoriesAdd";
   }
}

