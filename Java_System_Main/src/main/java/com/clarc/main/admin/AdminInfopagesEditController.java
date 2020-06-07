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
import com.clarc.main.ClarcInfopages;

@Controller
@RequestMapping("/adminInfopagesEdit")
public class AdminInfopagesEditController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcInfopages> getRecord(String id)
   {
      List<ClarcInfopages> r = jdbc.query("SELECT * FROM clarc_infopages WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcInfopages>() {
		     @Override
		     public ClarcInfopages mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcInfopages(rs.getLong("id"),
                   rs.getString("pageName"),
                   rs.getLong("pageCategory"),
                   rs.getString("pageDescription"),
                   rs.getLong("pageAccessRights"),
                   rs.getInt("status"),
                   rs.getString("addedTime"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   public String showPage(@RequestParam("id") String recordId, Model model) {

      List<ClarcInfopages> results = getRecord(recordId);

      if(!results.isEmpty())
      {
         model.addAttribute("clarcInfopagesRecord", results.get(0));
      }
      else
      {
         model.addAttribute("errorOutput", "Запись не найдена.");
         return "admin/adminErrorGeneral";
      }

	  model.addAttribute("clarcInfopages", new ClarcInfopages());

      return "admin/adminInfopagesEdit";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcInfopages") @Valid ClarcInfopages clarcInfopages, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminadminInfopagesEdit";
      }

      int n = jdbc.update("UPDATE clarc_infopages SET id=?,"
            + "pageName=?,"
            + "pageCategory=?,"
            + "pageDescription=?,"
            + "pageAccessRights=?,"
            + "status=?,"
            + "addedTime=CURRENT_TIMESTAMP WHERE id=?",
            clarcInfopages.getId(),
            clarcInfopages.getPageName(),
            clarcInfopages.getPageCategory(),
            clarcInfopages.getPageDescription(),
            clarcInfopages.getPageAccessRights(),
            clarcInfopages.getStatus(),
            clarcInfopages.getAddedTime(),
            clarcInfopages.getId());

	  if(n > 0)
	  {
		 List<ClarcInfopages> results = getRecord(String.valueOf(clarcInfopages.getId()));

	     if(!results.isEmpty())
	     {
	    	 model.addAttribute("clarcInfopagesRecord", results.get(0));
	     }
	     else
	     {
	    	 model.addAttribute("operationError", "Запись не найдена.");
	    	 return "admin/adminErrorGeneral";
	     }

		 model.addAttribute("clarcInfopages", new ClarcInfopages());

		 model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminInfopagesEdit";
   }
}

