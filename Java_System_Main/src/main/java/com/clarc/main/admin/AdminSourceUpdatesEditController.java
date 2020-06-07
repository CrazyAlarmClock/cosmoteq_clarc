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
import com.clarc.main.ClarcSourceUpdates;

@Controller
@RequestMapping("/adminSourceUpdatesEdit")
public class AdminSourceUpdatesEditController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcSourceUpdates> getRecord(String id)
   {
      List<ClarcSourceUpdates> r = jdbc.query("SELECT * FROM clarc_source_updates WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcSourceUpdates>() {
		     @Override
		     public ClarcSourceUpdates mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcSourceUpdates(rs.getLong("id"),
                   rs.getLong("sourceId"),
                   rs.getLong("updatedUserId"),
                   rs.getInt("status"),
                   rs.getString("updatedTime"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   public String showPage(@RequestParam("id") String recordId, Model model) {

      List<ClarcSourceUpdates> results = getRecord(recordId);

      if(!results.isEmpty())
      {
         model.addAttribute("clarcSourceUpdatesRecord", results.get(0));
      }
      else
      {
         model.addAttribute("errorOutput", "Запись не найдена.");
         return "admin/adminErrorGeneral";
      }

	  model.addAttribute("clarcSourceUpdates", new ClarcSourceUpdates());

      return "admin/adminSourceUpdatesEdit";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcSourceUpdates") @Valid ClarcSourceUpdates clarcSourceUpdates, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminadminSourceUpdatesEdit";
      }

      int n = jdbc.update("UPDATE clarc_source_updates SET id=?,"
            + "sourceId=?,"
            + "updatedUserId=?,"
            + "status=?,"
            + "updatedTime=CURRENT_TIMESTAMP WHERE id=?",
            clarcSourceUpdates.getId(),
            clarcSourceUpdates.getSourceId(),
            clarcSourceUpdates.getUpdatedUserId(),
            clarcSourceUpdates.getStatus(),
            clarcSourceUpdates.getUpdatedTime(),
            clarcSourceUpdates.getId());

	  if(n > 0)
	  {
		 List<ClarcSourceUpdates> results = getRecord(String.valueOf(clarcSourceUpdates.getId()));

	     if(!results.isEmpty())
	     {
	    	 model.addAttribute("clarcSourceUpdatesRecord", results.get(0));
	     }
	     else
	     {
	    	 model.addAttribute("operationError", "Запись не найдена.");
	    	 return "admin/adminErrorGeneral";
	     }

		 model.addAttribute("clarcSourceUpdates", new ClarcSourceUpdates());

		 model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminSourceUpdatesEdit";
   }
}

