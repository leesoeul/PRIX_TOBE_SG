-- h2 db용 임시 데이터
INSERT INTO px_account (level, affiliation, email, name, password, userid)
VALUES (2, NULL, 'prixadmin', 'prixadmin', '1234', 'prixadmin');
INSERT INTO px_account (level, affiliation, email, name, password, userid)
VALUES (1, NULL, 'prix', 'prix', '1234', 'prix');


INSERT INTO px_database (name, file, data_id)
VALUES ('example name', 'example file', NULL);
INSERT INTO px_database (name, file, data_id)
VALUES ('example name2', 'example file2', NULL);
INSERT INTO px_database (name, file, data_id)
VALUES ('example name3', 'example file3', NULL);


INSERT INTO px_enzyme (user_id, name, nt_cleave, ct_cleave)
VALUES (0, '0 uploaded 1', 'nt1', 'ct1');
INSERT INTO px_enzyme (user_id, name, nt_cleave, ct_cleave)
VALUES (0, '0 uploaded 2', 'nt2', 'ct2');
INSERT INTO px_enzyme (user_id, name, nt_cleave, ct_cleave)
VALUES (1, 'it is error', 'nt3', 'ct3');


INSERT INTO px_modification_log (date, version, file)
VALUES (NULL, 'version1', 'example file1');
INSERT INTO px_modification_log (date, version, file)
VALUES (CURRENT_DATE(), 'version2', 'example file2');
INSERT INTO px_modification_log (date, version, file)
VALUES (CURRENT_DATE(), 'version3', 'example file3');


INSERT INTO px_software_log (name, date, version, file)
VALUES ('example1', CURRENT_DATE(), 'version1', 'example file1');
INSERT INTO px_software_log (name, date, version, file)
VALUES ('example2', CURRENT_DATE(), 'version2', 'example file2');
INSERT INTO px_software_log (name, date, version, file)
VALUES ('example3', CURRENT_DATE(), 'version3', 'example file3');


INSERT INTO px_software_msg (id, message)
VALUES ('mode', 'examplemessage1');
INSERT INTO px_software_msg (id, message)
VALUES ('dbond', 'examplemessage1');
INSERT INTO px_software_msg (id, message)
VALUES ('nextsearch', 'examplemessage1');
INSERT INTO px_software_msg (id, message)
VALUES ('signature', 'examplemessage1');