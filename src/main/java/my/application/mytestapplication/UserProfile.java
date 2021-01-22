package my.application.mytestapplication;

/**
 * Created by Dheeraj_Kamath on 2/10/2018.
 */

public class UserProfile {

    private String Desc;
    private String userNum;
    private String userEmail;
    private String userName;

    public UserProfile(){
    }

    public UserProfile(String userNum, String userEmail, String userName,String userDesc) {
        this.userNum = userNum;
        this.userEmail = userEmail;
        this.userName = userName;
        this.Desc = userDesc;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
