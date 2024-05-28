//일단 그냥 복붙만 했음. 2024.


// Source code is decompiled from a .class file using FernFlower decompiler.


package com.prix.homepage.constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JobProcess {
   private final PrixDataWriter prixDataWriter;
   // private static String jobDir = "/home/PRIX/data"; 이게 진짜임
   String jobDir = "C:/Users/KYH/Desktop/";

   private String m_code = "";
   private String m_indx = "";
   private String m_user = "";
   private String m_time = "";
   private String m_file = "";
   private int m_prog = 0;
   private boolean isNumJob = false;
   private boolean isCorrect = false;
   private String job_description = "";
   private String message = "";

   public String toString() {
      return this.m_indx + " | " + this.m_user + " | " + this.m_time + " | " + this.isNumJob + " | " + this.isCorrect + " | " + this.m_file;
   }

   public String getDescription() {
      return this.job_description;
   }

   public String getMessage() {
      return this.message;
   }

   public String getIndex() {
      return this.m_indx;
   }

   public String getUser() {
      return this.m_user;
   }

   public String getTime() {
      return this.m_time;
   }

   public int getProgress() {
      return this.m_prog;
   }

   public boolean isNumJob() {
      return this.isNumJob;
   }

   public boolean isCorrectJob() {
      return this.isCorrect;
   }

   public boolean isVaildUser(String id) {
      return this.m_user.compareTo(id) == 0;
   }

   public void setUser(String id) {
      if (this.isNumJob) {
         this.m_user = id;
      }

   }

   public static void main(String[] args) throws Exception {
      int index = 29;
      String id = "4";
      long date = 1420612496152L;

      for(int i = 0; i < 10000; ++i) {
         String toto = generateCode(index, id, date);
         System.out.println(toto);
         String[] part = decodeCodeUserDate(toto);
         System.out.println(part[0] + " | " + part[1] + " | " + part[2]);
         if (index != Integer.parseInt(part[0]) || id.compareTo(part[1]) != 0 || date != Long.parseLong(part[2])) {
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX!!");
            System.exit(1);
         }
      }

      String[] part = decodeCodeUserDate("0P7j8216800q715wdgLcgN68wh192utOvti7877a4qw98");
      System.out.println(part[0] + " | " + part[1] + " | " + part[2]);
      System.out.println("BYE!!");
      System.out.println(System.currentTimeMillis());
   }

   public JobProcess(String jobCode, String id) {
      this.prixDataWriter = null;
      this.m_code = jobCode;
      String[] comp = decodeCodeUserDate(jobCode);
      this.m_indx = comp[0];
      this.m_user = comp[1];
      this.m_time = comp[2];
      if (jobCode != null && jobCode.length() < 31) {
         this.isNumJob = true;
         this.m_user = id;
      }

      if ("0".compareTo(this.m_indx) != 0) {
         this.isCorrect = true;
      }

      this.m_file = jobDir + "/ms_" + this.m_user + "_" + this.m_time + ".prix";
   }

   public boolean checkJobCompleted() throws Exception {
      boolean completed = this.checkJob();
      if (this.m_prog < 0) {
         this.deleteJobFromQueue();
      }

      return completed;
   }

   public String sendRequest(String command) throws Exception {
      String HDRequest = jobDir + "/modp_HD" + this.m_user + "_" + this.m_time;
      String killRequest = null;
      File prevReq = new File(HDRequest + ".txt");
      if (prevReq.exists()) {
         PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(HDRequest + "." + command + ".txt")));
         BufferedReader in = new BufferedReader(new FileReader(prevReq));

         String buf;
         while((buf = in.readLine()) != null) {
            if (buf.startsWith("Searchtool")) {
               out.println("Searchtool=" + command);
               killRequest = HDRequest + "." + command + ".txt";
            } else {
               out.println(buf);
            }
         }

         in.close();
         out.close();
         prevReq.delete();
      } else {
         this.message = "You have already CANCELED this search.";
      }

      return killRequest;
   }

   public boolean removeTraces() throws Exception {
      File xml = new File(jobDir + "/modi_input_" + this.m_user + "_" + this.m_time + ".xml");
      return xml.exists() && this.deleteJobFromQueue() ? xml.delete() : false;
   }

   private boolean checkJob() throws Exception {
      int req = 0;
      String input_xml = jobDir + "/modi_input_" + this.m_user + "_" + this.m_time + ".xml";
      String title = "";
      String msIndex = "";
      String dbIndex = "";
      String engine = "modeye";
      String msName = "";
      String dbName = "";
      File xml = new File(input_xml);
      if (xml.exists()) {
         try {
            Document doc = (new SAXBuilder()).build(input_xml);
            Element search = doc.getRootElement();
            if (search.getAttributeValue("title") != null) {
               title = search.getAttributeValue("title");
               ++req;
            }

            Element dataset = search.getChild("dataset");
            if (dataset != null) {
               if (dataset.getAttributeValue("db_index") != null) {
                  msIndex = dataset.getAttributeValue("db_index");
                  ++req;
               }

               if (dataset.getAttributeValue("name") != null) {
                  msName = dataset.getAttributeValue("name");
               }
            }

            Element database = search.getChild("database");
            if (database != null) {
               if (database.getAttributeValue("db_index") != null) {
                  dbIndex = database.getAttributeValue("db_index");
                  ++req;
               }

               if (database.getAttributeValue("name") != null) {
                  dbName = database.getAttributeValue("name");
               }
            }
         } catch (JDOMException var21) {
         }

         if (req != 3) {
            this.m_prog = -1;
            this.message = "Your search had unexpected problems.";
            return false;
         } else {
            this.job_description = "Title: " + title + "<br>Dataset: " + msName + "<br>Database: " + dbName;
            String log_file = jobDir + "/modi_output_" + this.m_user + "_" + this.m_time + ".log";
            File log = new File(log_file);
            if (log.exists()) {
               BufferedReader in = new BufferedReader(new FileReader(log_file));

               label243:
               while(true) {
                  String buf;
                  do {
                     if ((buf = in.readLine()) == null) {
                        in.close();
                        break label243;
                     }

                     if (buf.contains("ERROR")) {
                        if (buf.startsWith("File Transfer")) {
                           this.message = "Your files were NOT transferred correctly";
                        } else {
                           this.message = "Your search was aborted unexpectedly.";
                        }

                        this.m_prog = -1;
                        in.close();
                        return false;
                     }

                     if ("File Transfer Complete".compareTo(buf) == 0) {
                        ++req;
                     }

                     if ("Complete".compareTo(buf) == 0) {
                        ++req;
                     }
                  } while(buf.charAt(buf.length() - 1) != '%');

                  StringBuffer pstr = new StringBuffer();

                  int pint;
                  for(pint = buf.length() - 2; pint > 0 && Character.isDigit(buf.charAt(pint)); --pint) {
                     pstr.append(buf.charAt(pint));
                  }

                  if (pstr.length() > 0) {
                     pint = Integer.parseInt(pstr.reverse().toString());
                     if (this.m_prog < pint) {
                        this.m_prog = pint;
                     }
                  }
               }
            }

            if ((new File(this.m_file)).exists()) {
               ++req;
            }

            if (req != 6) {
               return false;
            } else {
               prixDataWriter.replace(Integer.parseInt(this.m_indx), new FileInputStream(new File(this.m_file)));
               Connection conn = PrixConnector.getConnection();
               if (conn == null) {
                  return false;
               } else {
                  String result = "OK";

                  try {
                     Statement state = conn.createStatement();
                     state.executeUpdate("insert into px_search_log (user_id, title, date, msfile, db, result, engine) values (" + this.m_user + ", '" + title.replace("'", "\\'") + "', now(), " + msIndex + ", " + dbIndex + ", " + this.m_indx + ", '" + engine + "')");
                     state.executeUpdate("delete from px_job_queue where job_code='" + this.m_code + "';");
                     state.close();
                  } catch (Exception var19) {
                     result = var19.toString();
                  } finally {
                     conn.close();
                  }

                  return "OK".compareTo(result) == 0;
               }
            }
         }
      } else {
         this.m_prog = -1;
         this.message = "This search was CANSELED or this link has EXPIRED.";
         return false;
      }
   }

   private boolean deleteJobFromQueue() throws Exception {
      Connection conn = PrixConnector.getConnection();
      if (conn == null) {
         return false;
      } else {
         String result = "OK";

         try {
            Statement state = conn.createStatement();
            state.executeUpdate("delete from px_job_queue where job_code='" + this.m_code + "';");
            state.close();
         } catch (Exception var7) {
            result = var7.toString();
         } finally {
            conn.close();
         }

         return "OK".compareTo(result) == 0;
      }
   }

   private int howOldJobInDay() {
      return (int)((System.currentTimeMillis() - Long.parseLong(this.m_time)) / 1000L / 3600L / 24L);
   }

   public static String generateCode(int index, String id, long date) {
      if (id != null && date > 0L) {
         StringBuffer code = new StringBuffer();
         int shift = randomNum();
         code.append(shift);
         code.append(randomChar());
         code.append(randomChar());
         code.append(randomDigit());
         String str = String.valueOf(date);
         int mid = str.length() / 2;
         String dthead = str.substring(0, mid);
         StringBuffer dttail = new StringBuffer(str.substring(mid));
         code.append(digitToRandomStr(dttail.reverse().toString(), 0));

         int i;
         for(i = 0; i < 4; ++i) {
            code.append(randomChar());
         }

         code.append(randomLetter());
         code.append(digitToLetterStr(dthead, shift));
         code.append(randomDigit());
         code.append(randomDigit());

         for(i = 0; i < 3; ++i) {
            code.append(randomChar());
         }

         str = String.valueOf(index + shift * shift * 24);
         mid = str.length() / 2;
         String head = str.substring(0, mid);
         String tail = str.substring(mid);
         code.append(tail.charAt(0));
         code.append(head);
         code.append(randomLetter());
         code.append((char)(109 - shift));

         for(i = 0; i < 7; ++i) {
            code.append(randomChar());
         }

         code.append(randomLetter());
         code.append(randomLetter());
         String du = String.valueOf(Integer.parseInt(id) + shift * index);
         code.append((new StringBuffer(du)).reverse());
         code.append((char)(78 + shift));
         code.append(randomLetter());

         for(i = tail.length() - 1; i > -1; --i) {
            code.append(tail.charAt(i));
         }

         code.append(randomChar());
         return code.toString();
      } else {
         return "0";
      }
   }

   public static int decodeCode(String jobCode) {
      return Integer.parseInt(decodeCodeUserDate(jobCode)[0]);
   }

   private static String[] decodeCodeUserDate(String jobCode) {
      String[] cid = new String[]{"0", "0", "0"};
      if (jobCode == null) {
         return cid;
      } else {
         String index = "";
         String tag = "";
         String user = "";
         String date = "";
         int tp = 0;
         int i;
         if (jobCode.length() <= 30) {
            try {
               i = Integer.parseInt(jobCode);
               cid[0] = jobCode;
               return cid;
            } catch (NumberFormatException var11) {
               return cid;
            }
         } else {
            StringBuffer dttail = new StringBuffer();
            if (Character.isDigit(jobCode.charAt(0)) && Character.isDigit(jobCode.charAt(3)) && Character.isDigit(jobCode.charAt(22))) {
               if (!Character.isLetter(jobCode.charAt(15))) {
                  return cid;
               } else {
                  int shift = Integer.parseInt(jobCode.substring(0, 1));

                  for(i = 4; i < 11; ++i) {
                     dttail.append(jobCode.charAt(i));
                  }

                  for(i += 5; i < jobCode.length() && Character.isLetter(jobCode.charAt(i)); ++i) {
                     date = date + jobCode.charAt(i);
                  }

                  date = letterToDigitStr(date, shift);
                  date = date + randomToDigitStr(dttail.reverse().toString(), 0);
                  if (date.length() != 13) {
                     return cid;
                  } else {
                     int k;
                     for(k = 0; k < date.length(); ++k) {
                        if (!Character.isDigit(date.charAt(k))) {
                           return cid;
                        }
                     }

                     i += 5;
                     ++i;

                     while(i < jobCode.length() && Character.isDigit(jobCode.charAt(i))) {
                        index = index + jobCode.charAt(i);
                        ++i;
                     }

                     tag = tag + (char)(jobCode.charAt(i + 1) + shift);

                     for(i = jobCode.length() - 2; i > 0 && Character.isDigit(jobCode.charAt(i)); --i) {
                        index = index + jobCode.charAt(i);
                     }

                     if (index.length() == 0) {
                        return cid;
                     } else {
                        for(k = 0; k < index.length(); ++k) {
                           if (!Character.isDigit(index.charAt(k))) {
                              return cid;
                           }
                        }

                        index = String.valueOf(Integer.parseInt(index) - shift * shift * 24);
                        StringBuilder var10000 = new StringBuilder(String.valueOf(tag));
                        --i;
                        tag = var10000.append((char)(jobCode.charAt(i) - shift)).toString();
                        --i;

                        while(i > 0 && Character.isDigit(jobCode.charAt(i))) {
                           user = user + jobCode.charAt(i);
                           --i;
                        }

                        if (user.length() == 0) {
                           return cid;
                        } else {
                           for(k = 0; k < user.length(); ++k) {
                              if (!Character.isDigit(user.charAt(k))) {
                                 return cid;
                              }
                           }

                           user = String.valueOf(Integer.parseInt(user) - shift * Integer.parseInt(index));
                           int tpNum = 27;
                           if (jobCode.charAt(tpNum) == jobCode.charAt(jobCode.length() - 2) && "mN".compareTo(tag) == 0) {
                              cid[0] = index;
                              cid[1] = user;
                              cid[2] = date;
                              return cid;
                           } else {
                              return cid;
                           }
                        }
                     }
                  }
               }
            } else {
               return cid;
            }
         }
      }
   }

   private static String digitToLetterStr(String dstr, int shift) {
      StringBuffer letstr = new StringBuffer();

      for(int i = 0; i < dstr.length(); ++i) {
         if (Math.random() < 0.5) {
            letstr.append((char)(dstr.charAt(i) + 49 + shift));
         } else {
            letstr.append((char)(dstr.charAt(i) + 24 + shift));
         }
      }

      return letstr.toString();
   }

   private static String letterToDigitStr(String lstr, int shift) {
      StringBuffer digstr = new StringBuffer();

      for(int i = 0; i < lstr.length(); ++i) {
         if (Character.isLowerCase(lstr.charAt(i))) {
            digstr.append((char)(lstr.charAt(i) - 49 - shift));
         } else {
            digstr.append((char)(lstr.charAt(i) - 24 - shift));
         }
      }

      return digstr.toString();
   }

   private static String digitToRandomStr(String dstr, int shift) {
      StringBuffer letstr = new StringBuffer();

      for(int i = 0; i < dstr.length(); ++i) {
         double ran = Math.random();
         if (ran < 0.4) {
            letstr.append(dstr.charAt(i));
         } else if (ran < 0.7) {
            letstr.append((char)(dstr.charAt(i) + 55 + shift));
         } else {
            letstr.append((char)(dstr.charAt(i) + 17 + shift));
         }
      }

      return letstr.toString();
   }

   private static String randomToDigitStr(String lstr, int shift) {
      StringBuffer digstr = new StringBuffer();

      for(int i = 0; i < lstr.length(); ++i) {
         if (Character.isDigit(lstr.charAt(i))) {
            digstr.append(lstr.charAt(i));
         } else if (Character.isLowerCase(lstr.charAt(i))) {
            digstr.append((char)(lstr.charAt(i) - 55 - shift));
         } else {
            digstr.append((char)(lstr.charAt(i) - 17 - shift));
         }
      }

      return digstr.toString();
   }

   private static int randomNum() {
      return (int)Math.round(Math.random() * 9.0);
   }

   private static char randomLetter() {
      double ran = Math.random();
      return ran < 0.5 ? (char)(97 + (int)Math.round(ran * 50.0)) : (char)(65 + (int)Math.round((ran - 0.5) * 50.0));
   }

   private static char randomDigit() {
      return (char)(48 + (int)Math.round(Math.random() * 9.0));
   }

   private static char randomChar() {
      double ran = Math.random();
      if (ran < 0.5) {
         return (char)(48 + (int)Math.round(ran * 18.0));
      } else {
         return ran < 0.75 ? (char)(97 + (int)Math.round((ran - 0.5) * 100.0)) : (char)(65 + (int)Math.round((ran - 0.75) * 100.0));
      }
   }

   public boolean XXXXcheckJobCompleted() throws Exception {
      int req = 0;
      String log_file = jobDir + "/modi_output_" + this.m_user + "_" + this.m_time + ".log";
      File f = new File(log_file);
      if (!f.exists()) {
         return false;
      } else {
         BufferedReader in = new BufferedReader(new FileReader(log_file));

         String title;
         while((title = in.readLine()) != null) {
            if ("ERROR".compareToIgnoreCase(title) == 0) {
               in.close();
               this.m_prog = -1;
               return false;
            }

            if ("File Transfer Complete".compareTo(title) == 0) {
               ++req;
            }

            if ("Complete".compareTo(title) == 0) {
               ++req;
            }

            if (title.charAt(title.length() - 1) == '%') {
               StringBuffer pstr = new StringBuffer();

               int pint;
               for(pint = title.length() - 2; pint > 0 && Character.isDigit(title.charAt(pint)); --pint) {
                  pstr.append(title.charAt(pint));
               }

               if (pstr.length() > 0) {
                  pint = Integer.parseInt(pstr.reverse().toString());
                  if (this.m_prog < pint) {
                     this.m_prog = pint;
                  }
               }
            }
         }

         in.close();
         if ((new File(this.m_file)).exists()) {
            ++req;
         }

         title = "";
         String msIndex = "";
         String dbIndex = "";
         String engine = "modeye";
         if (req == 3) {
            String input_xml = jobDir + "/modi_input_" + this.m_user + "_" + this.m_time + ".xml";
            File xml = new File(input_xml);
            if (xml.exists()) {
               try {
                  Document doc = (new SAXBuilder()).build(input_xml);
                  Element search = doc.getRootElement();
                  if (search.getAttributeValue("title") != null) {
                     title = search.getAttributeValue("title");
                     ++req;
                  }

                  Element dataset = search.getChild("dataset");
                  if (dataset != null && dataset.getAttributeValue("db_index") != null) {
                     msIndex = dataset.getAttributeValue("db_index");
                     ++req;
                  }

                  Element database = search.getChild("database");
                  if (database != null && database.getAttributeValue("db_index") != null) {
                     dbIndex = database.getAttributeValue("db_index");
                     ++req;
                  }
               } catch (JDOMException var19) {
                  return false;
               }
            }

            if (req != 6) {
               return false;
            } else {
               prixDataWriter.replace(Integer.parseInt(this.m_indx), new FileInputStream(new File(this.m_file)));
               Connection conn = PrixConnector.getConnection();
               if (conn == null) {
                  return false;
               } else {
                  String result = "OK";

                  try {
                     Statement state = conn.createStatement();
                     state.executeUpdate("insert into px_search_log (user_id, title, date, msfile, db, result, engine) values (" + this.m_user + ", '" + title.replace("'", "\\'") + "', now(), " + msIndex + ", " + dbIndex + ", " + this.m_indx + ", '" + engine + "')");
                     state.executeUpdate("delete from px_job_queue where job_code='" + this.m_code + "';");
                     state.close();
                  } catch (Exception var17) {
                     result = var17.toString();
                  } finally {
                     conn.close();
                  }

                  return true;
               }
            }
         } else {
            return false;
         }
      }
   }
}
