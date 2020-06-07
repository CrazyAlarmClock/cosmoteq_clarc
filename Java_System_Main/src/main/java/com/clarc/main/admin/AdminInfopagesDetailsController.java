package com.clarc.main.admin;

import org.springframework.http.MediaType;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.json.JSONObject;
import com.clarc.main.ClarcInfopages;

@Controller
@RequestMapping(value = "/adminInfopagesDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminInfopagesDetailsController {

   @Autowired
   private JdbcTemplate jdbc;

   @PostMapping
   public ResponseEntity<String> showJSON(@RequestParam("id") String recordId) {

      String strOutput = "<table class='table table-striped'>";

      List<ClarcInfopages> results = jdbc.query("SELECT * FROM clarc_infopages WHERE id=?",
         new Object[] { recordId },
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
      for(ClarcInfopages r : results) {
         strOutput += "<tr><td>ID:</td><td>"+r.getId()+"</td></tr>";
         strOutput += "<tr><td>Имя страницы:</td><td>"+r.getPageName()+"</td></tr>";
         strOutput += "<tr><td>Категория:</td><td>"+r.getPageCategory()+"</td></tr>";
         strOutput += "<tr><td>Описание страницы:</td><td>"+r.getPageDescription()+"</td></tr>";
         strOutput += "<tr><td>Доступ:</td><td>"+r.getPageAccessRights()+"</td></tr>";
         strOutput += "<tr><td>Статус:</td><td>"+r.getStatus()+"</td></tr>";
         strOutput += "<tr><td>Дата создания:</td><td>"+r.getAddedTime()+"</td></tr>";
         strOutput += "<tr><td></td><td><a href='/adminInfopagesEdit?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm' style='margin-left:5px;'><i class='fas fa-edit'></i></a><a href='/adminInfopagesDelete?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm confirm' style='margin-left:5px;'><i class='fas fa-trash'></i></a></nobr></td><tr>";
      }

	  strOutput += "</table>";

	  return ResponseEntity
	            .ok()
	            .cacheControl(CacheControl.noCache())
	            .body("{\"status\" : \"OK\", \"output\" : "+JSONObject.quote(strOutput)+"}");
   }
}

