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
import com.clarc.main.ClarcInfopageAccessTotals;

@Controller
@RequestMapping(value = "/adminInfopageAccessTotalsDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminInfopageAccessTotalsDetailsController {

   @Autowired
   private JdbcTemplate jdbc;

   @PostMapping
   public ResponseEntity<String> showJSON(@RequestParam("id") String recordId) {

      String strOutput = "<table class='table table-striped'>";

      List<ClarcInfopageAccessTotals> results = jdbc.query("SELECT * FROM clarc_infopage_access_totals WHERE id=?",
         new Object[] { recordId },
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
      for(ClarcInfopageAccessTotals r : results) {
         strOutput += "<tr><td>ID:</td><td>"+r.getId()+"</td></tr>";
         strOutput += "<tr><td>ID инфостраницы:</td><td>"+r.getPageId()+"</td></tr>";
         strOutput += "<tr><td>Просмотры:</td><td>"+r.getPageViews()+"</td></tr>";
         strOutput += "<tr><td>Дата доступа:</td><td>"+r.getAccessDate()+"</td></tr>";
         strOutput += "<tr><td></td><td><a href='/adminInfopageaccesstotalsEdit?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm' style='margin-left:5px;'><i class='fas fa-edit'></i></a><a href='/adminInfopageaccesstotalsDelete?id="+r.getId()+"' role='button' class='btn btn-primary btn-sm confirm' style='margin-left:5px;'><i class='fas fa-trash'></i></a></nobr></td><tr>";
      }

	  strOutput += "</table>";

	  return ResponseEntity
	            .ok()
	            .cacheControl(CacheControl.noCache())
	            .body("{\"status\" : \"OK\", \"output\" : "+JSONObject.quote(strOutput)+"}");
   }
}

