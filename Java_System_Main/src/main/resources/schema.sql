CREATE TABLE clarc_users (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   userFirstName char(64) NOT NULL default '',
   userMiddleName char(64) NOT NULL default '',
   userLastName char(64) NOT NULL default '',
   userEmail char(128) NOT NULL default '',
   userLogin char(64) NOT NULL default '',
   userPassword char(128) NOT NULL default '',
   userPasswordMatch char(128) NOT NULL default '',
   userCompanyId int(11) NOT NULL default '0',
   userCompanyName char(255) NOT NULL default '',
   userCompanyPosition char(255) NOT NULL default '',
   userAddress char(255) NOT NULL default '',
   userPhone char(64) NOT NULL default '',
   userCity char(32) NOT NULL default '',
   userZip char(32) NOT NULL default '',
   userState char(32) NOT NULL default '',
   userCountry char(32) NOT NULL default '',
   userAboutMe BLOB,
   profilePhotoUrl char(128) NOT NULL default '',
   userType int(2) NOT NULL default '0',
   userAuthority char(16) NOT NULL default '',
   userMembership int(2) NOT NULL default '0',
   userActivated int(2) NOT NULL default '0',
   activationCode char(128) NOT NULL default '',
   lastLoginTime datetime not null,
   prevLoginTime datetime not null,
   fireBaseToken char(128) NOT NULL default '',
   status int(2) NOT NULL default '1',
   agree int(2) NOT NULL default '1',
   addedTime datetime not null,
   PRIMARY KEY (id)
);

CREATE TABLE clarc_infopages (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   pageName char(255) NOT NULL default '',
   pageCategory int(11) NOT NULL default '0',
   pageDescription BLOB,
   pageAccessRights int(11) NOT NULL default '0',
   status int(2) NOT NULL default '1',
   addedTime datetime not null,
   PRIMARY KEY (id)
);

CREATE TABLE clarc_infopages_content (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   pageId int(11) NOT NULL default '0',
   pageContentBlockId int(11) NOT NULL default '0',
   pageContentBlock BLOB,
   status int(2) NOT NULL default '1',
   addedTime datetime not null,
   PRIMARY KEY (id)
);

CREATE TABLE clarc_infopage_categories (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   pageCategoryName char(255) NOT NULL default '',
   status int(2) NOT NULL default '1',
   addedTime datetime not null,
   PRIMARY KEY (id)
);

CREATE TABLE clarc_sources (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   sourceName char(255) NOT NULL default '',
   sourceUrl char(255) NOT NULL default '',
   sourceType int(2) NOT NULL default '1',
   status int(2) NOT NULL default '1',
   addedTime datetime not null,
   PRIMARY KEY (id)
);

CREATE TABLE clarc_infopage_source_connect (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   sourceId int(11) NOT NULL default '0',
   pageId int(11) NOT NULL default '0',
   status int(2) NOT NULL default '1',
   addedTime datetime not null,
   PRIMARY KEY (id)
);

CREATE TABLE clarc_processing_performance (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   sourceId int(11) NOT NULL default '0',
   pageId int(11) NOT NULL default '0',
   performanceLog BLOB,
   status int(2) NOT NULL default '1',
   addedTime datetime not null,
   PRIMARY KEY (id)
);

CREATE TABLE clarc_source_updates (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   sourceId int(11) NOT NULL default '0',
   updatedUserId int(11) NOT NULL default '0',
   status int(2) NOT NULL default '1',
   updatedTime datetime not null,
   PRIMARY KEY (id)
);

CREATE TABLE clarc_infopage_access (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   userId int(11) NOT NULL default '0',
   pageId int(11) NOT NULL default '0',
   status int(2) NOT NULL default '1',
   addedTime datetime not null,
   PRIMARY KEY (id)
);

CREATE TABLE clarc_infopage_access_totals (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   pageId int(11) NOT NULL default '0',
   pageViews int(8) NOT NULL default '0',
   accessDate datetime not null,
   PRIMARY KEY (id)
);

CREATE TABLE clarc_admins (
   id int(11) unsigned NOT NULL AUTO_INCREMENT,
   adminType int(2) NOT NULL default '0',
   adminName char(55) NOT NULL default '',
   adminEmail char(255) NOT NULL default '',
   adminAccessAreas char(25) NOT NULL default '',
   adminLogin char(55) NOT NULL default '',
   adminPassword char(128) NOT NULL default '',
   adminAuthority char(16) NOT NULL default '',
   adminStatus int(2) NOT NULL default '1',
   lastLoginTime datetime not null,
   addedTime datetime not null,
   PRIMARY KEY (id)
);
