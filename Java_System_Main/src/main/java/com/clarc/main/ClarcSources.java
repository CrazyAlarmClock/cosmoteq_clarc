package com.clarc.main;

public class ClarcSources {

   private long id;
   private String sourceName;
   private String sourceUrl;
   private int sourceType;
   private int status;
   private String addedTime;

    public ClarcSources() {

      this.id = 0;
      this.sourceName = "";
      this.sourceUrl = "";
      this.sourceType = 0;
      this.status = 0;
      this.addedTime = "";
   }

    public ClarcSources(long id, String sourceName, String sourceUrl, int sourceType, int status, String addedTime) {

      this.id = id;
      this.sourceName = sourceName;
      this.sourceUrl = sourceUrl;
      this.sourceType = sourceType;
      this.status = status;
      this.addedTime = addedTime;
   }

   public long getId() {

      return id;
   }

   public void setId(long id) {

      this.id = id;
   }

   public String getSourceName() {

      return sourceName;
   }

   public void setSourceName(String sourceName) {

      this.sourceName = sourceName;
   }

   public String getSourceUrl() {

      return sourceUrl;
   }

   public void setSourceUrl(String sourceUrl) {

      this.sourceUrl = sourceUrl;
   }

   public int getSourceType() {

      return sourceType;
   }

   public void setSourceType(int sourceType) {

      this.sourceType = sourceType;
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

      return String.format("ClarcSources[id='%d', sourceName='%s', sourceUrl='%s', sourceType='%d', status='%d', addedTime='%s']",
         id, sourceName, sourceUrl, sourceType, status, addedTime);
   }
}

