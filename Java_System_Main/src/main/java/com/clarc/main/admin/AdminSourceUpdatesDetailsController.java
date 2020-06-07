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
import com.clarc.main.ClarcSourceUpdates;

@Controller
@RequestMapping(value = "/adminSourceUpdatesDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminSourceUpdatesDetailsController {

   @Autowired
   private JdbcTemplate jdbc;

   @PostMapping
   public ResponseEntity<String> showJSON(@RequestParam("id") String recordId) {

      String strOutput = "<table class='table table-striped'>";

      List<ClarcSourceUpdates> results = jdbc.query("SELECT * FROM clarc_source_updates WHERE id=?",
         new Object[] { recordId },
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
      for(ClarcSourceUpdates r : results) {
         strOutput += "<tr><td>ID:</td><td>"+r.getId()+"</td></tr>";
         strOutput += "<tr><td>ID источника:</td><td>"+r.getSourceId()+"</td></tr>";
         strOutput += "<tr><td>ID пользователя:</td><td>"+r.getUpdatedUserId()+"</td></tr>";
         strOutput += "<tr><td>Статус:</td><td>"+r.getStatus()+"</td></tr>";
         strOutput += "<tr><td>Дата обновления:</td><td>"+r.getUpdatedTime()+"</td></tr>";
         strOutput += "<tr><td></td><td><a href='/adminSourceupdatesEdit?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm' style='margin-left:5px;'><i class='fas fa-edit'></i></a><a href='/adminSourceupdatesDelete?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm confirm' style='margin-left:5px;'><i class='fas fa-trash'></i></a></nobr></td><tr>";
      }

	  strOutput += "</table>";

	  return ResponseEntity
	            .ok()
	            .cacheControl(CacheControl.noCache())
	            .body("{\"status\" : \"OK\", \"output\" : "+JSONObject.quote(strOutput)+"}");
   }
}

