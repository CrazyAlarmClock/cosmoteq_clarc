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
import com.clarc.main.ClarcSources;

@Controller
@RequestMapping(value = "/adminSourcesDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminSourcesDetailsController {

   @Autowired
   private JdbcTemplate jdbc;

   @PostMapping
   public ResponseEntity<String> showJSON(@RequestParam("id") String recordId) {

      String strOutput = "<table class='table table-striped'>";

      List<ClarcSources> results = jdbc.query("SELECT * FROM clarc_sources WHERE id=?",
         new Object[] { recordId },
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
      for(ClarcSources r : results) {
         strOutput += "<tr><td>ID:</td><td>"+r.getId()+"</td></tr>";
         strOutput += "<tr><td>Наименование источника:</td><td>"+r.getSourceName()+"</td></tr>";
         strOutput += "<tr><td>URL источника:</td><td>"+r.getSourceUrl()+"</td></tr>";
         strOutput += "<tr><td>Тип источника:</td><td>"+r.getSourceType()+"</td></tr>";
         strOutput += "<tr><td>Статус:</td><td>"+r.getStatus()+"</td></tr>";
         strOutput += "<tr><td>Дата создания:</td><td>"+r.getAddedTime()+"</td></tr>";
         strOutput += "<tr><td></td><td><a href='/adminSourcesEdit?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm' style='margin-left:5px;'><i class='fas fa-edit'></i></a><a href='/adminSourcesDelete?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm confirm' style='margin-left:5px;'><i class='fas fa-trash'></i></a></nobr></td><tr>";
      }

	  strOutput += "</table>";

	  return ResponseEntity
	            .ok()
	            .cacheControl(CacheControl.noCache())
	            .body("{\"status\" : \"OK\", \"output\" : "+JSONObject.quote(strOutput)+"}");
   }
}

