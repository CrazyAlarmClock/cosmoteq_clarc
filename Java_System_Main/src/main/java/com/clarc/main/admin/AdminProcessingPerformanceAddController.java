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
import com.clarc.main.ClarcProcessingPerformance;

@Controller
@RequestMapping("/adminProcessingPerformanceAdd")
public class AdminProcessingPerformanceAddController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      model.addAttribute("clarcProcessingPerformance", new ClarcProcessingPerformance());

      return "admin/adminProcessingPerformanceAdd";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcProcessingPerformance") @Valid ClarcProcessingPerformance clarcProcessingPerformance, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminProcessingPerformanceAdd";
      }

	  int n = jdbc.update("INSERT INTO clarc_processing_performance (id,"
            + "sourceId,"
            + "pageId,"
            + "performanceLog,"
            + "status,"
            + "addedTime) values(?,"
            + "?,"
            + "?,"
            + "?,"
            + "?,"
            + "CURRENT_TIMESTAMP)",
	  		clarcProcessingPerformance.getId(),
	  		clarcProcessingPerformance.getSourceId(),
	  		clarcProcessingPerformance.getPageId(),
	  		clarcProcessingPerformance.getPerformanceLog(),
	  		clarcProcessingPerformance.getStatus(),
	  		clarcProcessingPerformance.getAddedTime());
	  if(n > 0)
	  {
	     model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminProcessingPerformanceAdd";
   }
}

