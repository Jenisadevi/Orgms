package com.orgms.util;

import org.mindrot.jbcrypt.BCrypt;

public class BcryptGen {
  public static void main(String[] args){
    // default password "admin123" - change if you want a different password
    String password = (args != null && args.length>0) ? args[0] : "admin123";
    String hash = BCrypt.hashpw(password, BCrypt.gensalt());
    System.out.println(hash);
  }
}
