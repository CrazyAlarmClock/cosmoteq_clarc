package com.clarc.main;

public class ClarcProcessingPerformance {

   private long id;
   private long sourceId;
   private long pageId;
   private String performanceLog;
   private int status;
   private String addedTime;

    public ClarcProcessingPerformance() {

      this.id = 0;
      this.sourceId = 0;
      this.pageId = 0;
      this.performanceLog = "";
      this.status = 0;
      this.addedTime = "";
   }

    public ClarcProcessingPerformance(long id, long sourceId, long pageId, String performanceLog, int status, String addedTime) {

      this.id = id;
      this.sourceId = sourceId;
      this.pageId = pageId;
      this.performanceLog = performanceLog;
      this.status = status;
      this.addedTime = addedTime;
   }

   public long getId() {

      return id;
   }

   public void setId(long id) {

      this.id = id;
   }

   public long getSourceId() {

      return sourceId;
   }

   public void setSourceId(long sourceId) {

      this.sourceId = sourceId;
   }

   public long getPageId() {

      return pageId;
   }

   public void setPageId(long pageId) {

      this.pageId = pageId;
   }

   public String getPerformanceLog() {

      return performanceLog;
   }

   public void setPerformanceLog(String performanceLog) {

      this.performanceLog = performanceLog;
   }

   public int getStatus() {

      return status;
   }

   public void setStatus(int status) {

      this.status = status;
   }

   public String getAddedTime() {

      return addedTime;
   }

   public void setAddedTime(String addedTime) {

      this.addedTime = addedTime;
   }

   public String toString() {

      return String.format("ClarcProcessingPerformance[id='%d', sourceId='%d', pageId='%d', performanceLog='%s', status='%d', addedTime='%s']",
         id, sourceId, pageId, performanceLog, status, addedTime);
   }
}

