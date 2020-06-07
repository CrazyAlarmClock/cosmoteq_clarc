package com.clarc.main;

public class ClarcInfopageAccessTotals {

   private long id;
   private long pageId;
   private int pageViews;
   private String accessDate;

    public ClarcInfopageAccessTotals() {

      this.id = 0;
      this.pageId = 0;
      this.pageViews = 0;
      this.accessDate = "";
   }

    public ClarcInfopageAccessTotals(long id, long pageId, int pageViews, String accessDate) {

      this.id = id;
      this.pageId = pageId;
      this.pageViews = pageViews;
      this.accessDate = accessDate;
   }

   public long getId() {

      return id;
   }

   public void setId(long id) {

      this.id = id;
   }

   public long getPageId() {

      return pageId;
   }

   public void setPageId(long pageId) {

      this.pageId = pageId;
   }

   public int getPageViews() {

      return pageViews;
   }

   public void setPageViews(int pageViews) {

      this.pageViews = pageViews;
   }

   public String getAccessDate() {

      return accessDate;
   }

   public void setAccessDate(String accessDate) {

      this.accessDate = accessDate;
   }

   public String toString() {

      return String.format("ClarcInfopageAccessTotals[id='%d', pageId='%d', pageViews='%d', accessDate='%s']",
         id, pageId, pageViews, accessDate);
   }
}

