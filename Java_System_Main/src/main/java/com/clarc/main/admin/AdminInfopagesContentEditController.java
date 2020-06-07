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
import com.clarc.main.ClarcInfopagesContent;

@Controller
@RequestMapping("/adminInfopagesContentEdit")
public class AdminInfopagesContentEditController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcInfopagesContent> getRecord(String id)
   {
      List<ClarcInfopagesContent> r = jdbc.query("SELECT * FROM clarc_infopages_content WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcInfopagesContent>() {
		     @Override
		     public ClarcInfopagesContent mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcInfopagesContent(rs.getLong("id"),
                   rs.getLong("pageId"),
                   rs.getLong("pageContentBlockId"),
                   rs.getString("pageContentBlock"),
                   rs.getInt("status"),
                   rs.getString("addedTime"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   public String showPage(@RequestParam("id") String recordId, Model model) {

      List<ClarcInfopagesContent> results = getRecord(recordId);

      if(!results.isEmpty())
      {
         model.addAttribute("clarcInfopagesContentRecord", results.get(0));
      }
      else
      {
         model.addAttribute("errorOutput", "Запись не найдена.");
         return "admin/adminErrorGeneral";
      }

	  model.addAttribute("clarcInfopagesContent", new ClarcInfopagesContent());

      return "admin/adminInfopagesContentEdit";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcInfopagesContent") @Valid ClarcInfopagesContent clarcInfopagesContent, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminadminInfopagesContentEdit";
      }

      int n = jdbc.update("UPDATE clarc_infopages_content SET id=?,"
            + "pageId=?,"
            + "pageContentBlockId=?,"
            + "pageContentBlock=?,"
            + "status=?,"
            + "addedTime=CURRENT_TIMESTAMP WHERE id=?",
            clarcInfopagesContent.getId(),
            clarcInfopagesContent.getPageId(),
            clarcInfopagesContent.getPageContentBlockId(),
            clarcInfopagesContent.getPageContentBlock(),
            clarcInfopagesContent.getStatus(),
            clarcInfopagesContent.getAddedTime(),
            clarcInfopagesContent.getId());

	  if(n > 0)
	  {
		 List<ClarcInfopagesContent> results = getRecord(String.valueOf(clarcInfopagesContent.getId()));

	     if(!results.isEmpty())
	     {
	    	 model.addAttribute("clarcInfopagesContentRecord", results.get(0));
	     }
	     else
	     {
	    	 model.addAttribute("operationError", "Запись не найдена.");
	    	 return "admin/adminErrorGeneral";
	     }

		 model.addAttribute("clarcInfopagesContent", new ClarcInfopagesContent());

		 model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminInfopagesContentEdit";
   }
}

