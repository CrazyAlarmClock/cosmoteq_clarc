INSERT INTO clarc_users (userFirstName,
   userEmail,
   userLogin,
   userPassword,
   userAuthority,
   userCompanyName,
   lastLoginTime,
   prevLoginTime,
   status,
   addedTime) VALUES ('Tester1',
   'pavel.andreyev@gmail.com',
   'pavel.andreyev@gmail.com',
   '$2a$10$jaxjRiwTb8HvknANX95IleSBcDcbtbdgSeXYoJ0zGhpp5eUik2QVe',
   'ROLE_USER',
   'Demo Company',
   NOW(),
   NOW(),
   '1',
   NOW());

INSERT INTO clarc_admins (adminType,
   adminName,
   adminEmail,
   adminAccessAreas,
   adminLogin,
   adminPassword,
   adminAuthority,
   adminStatus,
   lastLoginTime,
   addedTime) VALUES ('1',
   'admin',
   'pavel.andreyev@gmail.com',
   '1:1:1:1:1:1:1:1:',
   'admin',
   '$2a$10$jaxjRiwTb8HvknANX95IleSBcDcbtbdgSeXYoJ0zGhpp5eUik2QVe',
   'ROLE_ADMIN',
   '1',
   NOW(),
   NOW());