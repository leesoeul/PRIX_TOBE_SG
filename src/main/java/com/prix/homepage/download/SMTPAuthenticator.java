package com.prix.homepage.download;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
   public SMTPAuthenticator() {
   }

   protected PasswordAuthentication getPasswordAuthentication() {
      String user = "prix@hanyang.ac.kr";
      String pswd = "ttbtlaixlsodixct";
      return new PasswordAuthentication(user, pswd);
   }
}
