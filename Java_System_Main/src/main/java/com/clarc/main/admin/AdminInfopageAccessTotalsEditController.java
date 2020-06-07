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
import com.clarc.main.ClarcInfopageAccessTotals;

@Controller
@RequestMapping("/adminInfopageAccessTotalsEdit")
public class AdminInfopageAccessTotalsEditController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcInfopageAccessTotals> getRecord(String id)
   {
      List<ClarcInfopageAccessTotals> r = jdbc.query("SELECT * FROM clarc_infopage_access_totals WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcInfopageAccessTotals>() {
		     @Override
		     public ClarcInfopageAccessTotals mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcInfopageAccessTotals(rs.getLong("id"),
                   rs.getLong("pageId"),
                   rs.getInt("pageViews"),
                   rs.getString("accessDate"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   public String showPage(@RequestParam("id") String recordId, Model model) {

      List<ClarcInfopageAccessTotals> results = getRecord(recordId);

      if(!results.isEmpty())
      {
         model.addAttribute("clarcInfopageAccessTotalsRecord", results.get(0));
      }
      else
      {
         model.addAttribute("errorOutput", "Запись не найдена.");
         return "admin/adminErrorGeneral";
      }

	  model.addAttribute("clarcInfopageAccessTotals", new ClarcInfopageAccessTotals());

      return "admin/adminInfopageAccessTotalsEdit";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcInfopageAccessTotals") @Valid ClarcInfopageAccessTotals clarcInfopageAccessTotals, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminadminInfopageAccessTotalsEdit";
      }

      int n = jdbc.update("UPDATE clarc_infopage_access_totals SET id=?,"
            + "pageId=?,"
            + "pageViews=?,"
            + "accessDate=CURRENT_TIMESTAMP WHERE id=?",
            clarcInfopageAccessTotals.getId(),
            clarcInfopageAccessTotals.getPageId(),
            clarcInfopageAccessTotals.getPageViews(),
            clarcInfopageAccessTotals.getAccessDate(),
            clarcInfopageAccessTotals.getId());

	  if(n > 0)
	  {
		 List<ClarcInfopageAccessTotals> results = getRecord(String.valueOf(clarcInfopageAccessTotals.getId()));

	     if(!results.isEmpty())
	     {
	    	 model.addAttribute("clarcInfopageAccessTotalsRecord", results.get(0));
	     }
	     else
	     {
	    	 model.addAttribute("operationError", "Запись не найдена.");
	    	 return "admin/adminErrorGeneral";
	     }

		 model.addAttribute("clarcInfopageAccessTotals", new ClarcInfopageAccessTotals());

		 model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminInfopageAccessTotalsEdit";
   }
}

