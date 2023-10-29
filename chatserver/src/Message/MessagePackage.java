package Message;

import java.io.Serializable;
import java.util.ArrayList;

public class MessagePackage implements Serializable {
    
    private String nickname, ip, message;
    private ArrayList<String> ipArray;

    public ArrayList<String> getIpArray() {
        return ipArray;
    }
    
    public void setIpArray(ArrayList<String> ipArray) {
        this.ipArray = ipArray;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
