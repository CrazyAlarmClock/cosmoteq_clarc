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
import com.clarc.main.ClarcSources;

@Controller
@RequestMapping("/adminSourcesEdit")
public class AdminSourcesEditController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcSources> getRecord(String id)
   {
      List<ClarcSources> r = jdbc.query("SELECT * FROM clarc_sources WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcSources>() {
		     @Override
		     public ClarcSources mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcSources(rs.getLong("id"),
                   rs.getString("sourceName"),
                   rs.getString("sourceUrl"),
                   rs.getInt("sourceType"),
                   rs.getInt("status"),
                   rs.getString("addedTime"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   public String showPage(@RequestParam("id") String recordId, Model model) {

      List<ClarcSources> results = getRecord(recordId);

      if(!results.isEmpty())
      {
         model.addAttribute("clarcSourcesRecord", results.get(0));
      }
      else
      {
         model.addAttribute("errorOutput", "Запись не найдена.");
         return "admin/adminErrorGeneral";
      }

	  model.addAttribute("clarcSources", new ClarcSources());

      return "admin/adminSourcesEdit";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcSources") @Valid ClarcSources clarcSources, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminadminSourcesEdit";
      }

      int n = jdbc.update("UPDATE clarc_sources SET id=?,"
            + "sourceName=?,"
            + "sourceUrl=?,"
            + "sourceType=?,"
            + "status=?,"
            + "addedTime=CURRENT_TIMESTAMP WHERE id=?",
            clarcSources.getId(),
            clarcSources.getSourceName(),
            clarcSources.getSourceUrl(),
            clarcSources.getSourceType(),
            clarcSources.getStatus(),
            clarcSources.getAddedTime(),
            clarcSources.getId());

	  if(n > 0)
	  {
		 List<ClarcSources> results = getRecord(String.valueOf(clarcSources.getId()));

	     if(!results.isEmpty())
	     {
	    	 model.addAttribute("clarcSourcesRecord", results.get(0));
	     }
	     else
	     {
	    	 model.addAttribute("operationError", "Запись не найдена.");
	    	 return "admin/adminErrorGeneral";
	     }

		 model.addAttribute("clarcSources", new ClarcSources());

		 model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminSourcesEdit";
   }
}

