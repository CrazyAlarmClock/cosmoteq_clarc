package com.clarc.main;

public class ClarcInfopagesContent {

   private long id;
   private long pageId;
   private long pageContentBlockId;
   private String pageContentBlock;
   private int status;
   private String addedTime;

    public ClarcInfopagesContent() {

      this.id = 0;
      this.pageId = 0;
      this.pageContentBlockId = 0;
      this.pageContentBlock = "";
      this.status = 0;
      this.addedTime = "";
   }

    public ClarcInfopagesContent(long id, long pageId, long pageContentBlockId, String pageContentBlock, int status, String addedTime) {

      this.id = id;
      this.pageId = pageId;
      this.pageContentBlockId = pageContentBlockId;
      this.pageContentBlock = pageContentBlock;
      this.status = status;
      this.addedTime = addedTime;
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

   public long getPageContentBlockId() {

      return pageContentBlockId;
   }

   public void setPageContentBlockId(long pageContentBlockId) {

      this.pageContentBlockId = pageContentBlockId;
   }

   public String getPageContentBlock() {

      return pageContentBlock;
   }

   public void setPageContentBlock(String pageContentBlock) {

      this.pageContentBlock = pageContentBlock;
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

      return String.format("ClarcInfopagesContent[id='%d', pageId='%d', pageContentBlockId='%d', pageContentBlock='%s', status='%d', addedTime='%s']",
         id, pageId, pageContentBlockId, pageContentBlock, status, addedTime);
   }
}

