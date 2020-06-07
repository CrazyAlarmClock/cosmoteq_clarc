package com.clarc.main.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;
import com.clarc.main.ClarcInfopageCategories;

@Controller
@RequestMapping("/adminInfopageCategoriesDelete")
public class AdminInfopageCategoriesDeleteController {

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
   @PostMapping
   public ModelAndView showPage(@RequestParam("id") String recordId, Model model) {

	  List<ClarcInfopageCategories> results = getRecord(recordId);

	  ModelAndView mv = new ModelAndView("redirect:/adminInfopageCategories");

      if(!results.isEmpty())
      {
         int n = jdbc.update("DELETE FROM clarc_infopage_categories WHERE id=?", recordId);
         if(n > 0)
         {
        	 mv.addObject("operationSuccess", "Операция произведена успешно!");
         }
         else
         {
            mv.addObject("operationError", "Ошибка удаления записи.");
         }
      }
      else
      {
    	  mv.addObject("operationError", "Запись не найдена.");
      }

      return mv;
   }
}

