package com.clarc.main.admin;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.clarc.main.ClarcInfopages;
import com.clarc.main.InfopageCreateController;

@Controller
@RequestMapping("/adminInfopagesCreate")
public class AdminInfopagesCreateController {

   @Autowired
   private JdbcTemplate jdbc;

   @GetMapping
   public String showPage(Model model) {

      String strOutput = "";

      List<ClarcInfopages> results = jdbc.query("SELECT * FROM clarc_infopages ORDER BY id DESC",
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
      
      long maxId = 0;

      for(ClarcInfopages r : results) {

    	 maxId = r.getId();
         break;
      }
      
      InfopageCreateController ic = new InfopageCreateController();
		
	  float fScore = ic.createPage("raw_document1.txt", maxId);

	  if(fScore == 1)
	  {
		 strOutput += "<h4>Страница успешно создана</h4>";
		 strOutput += "<br>&nbsp;<br>";
		 strOutput += "<a href='file:///Users/pavelandreyev/Documents/workspace-sts-3.9.9.RELEASE/Clarc/src/main/resources/static/infopages/infopage"+maxId+".html' role='button' class='btn btn-primary btn-sm' style='margin-left:5px;'>Посмотреть страницу</a>";
	  }
	  else
	  {
		 strOutput += "<h4>Ошибка!</h4>";
         strOutput += "<b>Попробуйте повторить еще раз.</b>";
	  }

      model.addAttribute("output", strOutput);

      return "admin/adminInfopagesCreate";
   }
}

