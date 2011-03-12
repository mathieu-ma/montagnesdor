package fr.mch.mdo.restaurant.dao.authentication;

public interface IAuthenticationPasswordLevel {

    String getPassword();
    void setPassword(String password);
    
    String getLevelPass1();
    void setLevelPass1(String levelPass1);
    
    String getLevelPass2();
    void setLevelPass2(String levelPass2);
    
    String getLevelPass3();
    void setLevelPass3(String levelPass3);
}