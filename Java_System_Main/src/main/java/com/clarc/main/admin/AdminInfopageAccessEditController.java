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
import com.clarc.main.ClarcInfopageAccess;

@Controller
@RequestMapping("/adminInfopageAccessEdit")
public class AdminInfopageAccessEditController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcInfopageAccess> getRecord(String id)
   {
      List<ClarcInfopageAccess> r = jdbc.query("SELECT * FROM clarc_infopage_access WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcInfopageAccess>() {
		     @Override
		     public ClarcInfopageAccess mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcInfopageAccess(rs.getLong("id"),
                   rs.getLong("userId"),
                   rs.getLong("pageId"),
                   rs.getInt("status"),
                   rs.getString("addedTime"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   public String showPage(@RequestParam("id") String recordId, Model model) {

      List<ClarcInfopageAccess> results = getRecord(recordId);

      if(!results.isEmpty())
      {
         model.addAttribute("clarcInfopageAccessRecord", results.get(0));
      }
      else
      {
         model.addAttribute("errorOutput", "Запись не найдена.");
         return "admin/adminErrorGeneral";
      }

	  model.addAttribute("clarcInfopageAccess", new ClarcInfopageAccess());

      return "admin/adminInfopageAccessEdit";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcInfopageAccess") @Valid ClarcInfopageAccess clarcInfopageAccess, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminadminInfopageAccessEdit";
      }

      int n = jdbc.update("UPDATE clarc_infopage_access SET id=?,"
            + "userId=?,"
            + "pageId=?,"
            + "status=?,"
            + "addedTime=CURRENT_TIMESTAMP WHERE id=?",
            clarcInfopageAccess.getId(),
            clarcInfopageAccess.getUserId(),
            clarcInfopageAccess.getPageId(),
            clarcInfopageAccess.getStatus(),
            clarcInfopageAccess.getAddedTime(),
            clarcInfopageAccess.getId());

	  if(n > 0)
	  {
		 List<ClarcInfopageAccess> results = getRecord(String.valueOf(clarcInfopageAccess.getId()));

	     if(!results.isEmpty())
	     {
	    	 model.addAttribute("clarcInfopageAccessRecord", results.get(0));
	     }
	     else
	     {
	    	 model.addAttribute("operationError", "Запись не найдена.");
	    	 return "admin/adminErrorGeneral";
	     }

		 model.addAttribute("clarcInfopageAccess", new ClarcInfopageAccess());

		 model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminInfopageAccessEdit";
   }
}

