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
import com.clarc.main.ClarcProcessingPerformance;

@Controller
@RequestMapping("/adminProcessingPerformanceEdit")
public class AdminProcessingPerformanceEditController {

   @Autowired
   private JdbcTemplate jdbc;

   private List<ClarcProcessingPerformance> getRecord(String id)
   {
      List<ClarcProcessingPerformance> r = jdbc.query("SELECT * FROM clarc_processing_performance WHERE id = ?",
		  new Object[] { id },
		  new RowMapper<ClarcProcessingPerformance>() {
		     @Override
		     public ClarcProcessingPerformance mapRow(ResultSet rs, int rowNum) throws SQLException {
		        return new ClarcProcessingPerformance(rs.getLong("id"),
                   rs.getLong("sourceId"),
                   rs.getLong("pageId"),
                   rs.getString("performanceLog"),
                   rs.getInt("status"),
                   rs.getString("addedTime"));
			 }
		  }
	   );

      return r;
   }

   @GetMapping
   public String showPage(@RequestParam("id") String recordId, Model model) {

      List<ClarcProcessingPerformance> results = getRecord(recordId);

      if(!results.isEmpty())
      {
         model.addAttribute("clarcProcessingPerformanceRecord", results.get(0));
      }
      else
      {
         model.addAttribute("errorOutput", "Запись не найдена.");
         return "admin/adminErrorGeneral";
      }

	  model.addAttribute("clarcProcessingPerformance", new ClarcProcessingPerformance());

      return "admin/adminProcessingPerformanceEdit";
   }

   @PostMapping
   public String processRequest(@ModelAttribute("clarcProcessingPerformance") @Valid ClarcProcessingPerformance clarcProcessingPerformance, BindingResult bindingResult, Model model, Errors errors) {

	  if(errors.hasErrors()) {

	     return "admin/adminadminProcessingPerformanceEdit";
      }

      int n = jdbc.update("UPDATE clarc_processing_performance SET id=?,"
            + "sourceId=?,"
            + "pageId=?,"
            + "performanceLog=?,"
            + "status=?,"
            + "addedTime=CURRENT_TIMESTAMP WHERE id=?",
            clarcProcessingPerformance.getId(),
            clarcProcessingPerformance.getSourceId(),
            clarcProcessingPerformance.getPageId(),
            clarcProcessingPerformance.getPerformanceLog(),
            clarcProcessingPerformance.getStatus(),
            clarcProcessingPerformance.getAddedTime(),
            clarcProcessingPerformance.getId());

	  if(n > 0)
	  {
		 List<ClarcProcessingPerformance> results = getRecord(String.valueOf(clarcProcessingPerformance.getId()));

	     if(!results.isEmpty())
	     {
	    	 model.addAttribute("clarcProcessingPerformanceRecord", results.get(0));
	     }
	     else
	     {
	    	 model.addAttribute("operationError", "Запись не найдена.");
	    	 return "admin/adminErrorGeneral";
	     }

		 model.addAttribute("clarcProcessingPerformance", new ClarcProcessingPerformance());

		 model.addAttribute("operationSuccess", "Операция успешно выполнена!");
	  }
	  else
	  {
		 model.addAttribute("operationError", "Ошибка операции с БД.");
	  }

      return "admin/adminProcessingPerformanceEdit";
   }
}

