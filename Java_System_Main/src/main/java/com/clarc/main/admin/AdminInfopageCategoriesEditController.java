package com.clarc.main.admin;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.clarc.main.ClarcInfopageCategories;

@Controller
@RequestMapping("/adminInfopageCategoriesEdit")
public class AdminInfopageCategoriesEditController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcInfopageCategories> getRecord(String id)
   {
      List<ClarcInfopageCategories> r = jdbc.query("SELECT * FROM clarc_infopage_categories WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcInfopageCategories>() {
		     @Override
		     public ClarcInfopageCategories mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcInfopageCategories(rs.getLong("id"),
                   rs.getString("pageCategoryName"),
                   rs.getInt("status"),
                   rs.getString("addedTime"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   public String showPage(@RequestParam("id") String recordId, Model model) {

      List<ClarcInfopageCategories> results = getRecord(recordId);

      if(!results.isEmpty())
      {
         model.addAttribute("clarcInfopageCategoriesRecord", results.get(0));
      }
      else
      {
         model.addAttribute("errorOutput", "Запись не найдена.");
         return "admin/adminErrorGeneral";
      }

	  model.addAttribute("clarcInfopageCategories", new ClarcInfopageCategories());

      return "admin/adminInfopageCategoriesEdit";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcInfopageCategories") @Valid ClarcInfopageCategories clarcInfopageCategories, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminadminInfopageCategoriesEdit";
      }

      int n = jdbc.update("UPDATE clarc_infopage_categories SET id=?,"
            + "pageCategoryName=?,"
            + "status=?,"
            + "addedTime=CURRENT_TIMESTAMP WHERE id=?",
            clarcInfopageCategories.getId(),
            clarcInfopageCategories.getPageCategoryName(),
            clarcInfopageCategories.getStatus(),
            clarcInfopageCategories.getAddedTime(),
            clarcInfopageCategories.getId());

	  if(n > 0)
	  {
		 List<ClarcInfopageCategories> results = getRecord(String.valueOf(clarcInfopageCategories.getId()));

	     if(!results.isEmpty())
	     {
	    	 model.addAttribute("clarcInfopageCategoriesRecord", results.get(0));
	     }
	     else
	     {
	    	 model.addAttribute("operationError", "Запись не найдена.");
	    	 return "admin/adminErrorGeneral";
	     }

		 model.addAttribute("clarcInfopageCategories", new ClarcInfopageCategories());

		 model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminInfopageCategoriesEdit";
   }
}

