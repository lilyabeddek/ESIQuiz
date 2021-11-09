package noyau;



import java.io.Serializable;




public class Utilisateur implements Serializable {
	
	
	protected static final long serialVersionUID = 6979691458343068713L;
	protected String image= "file:photos/yacineuser.png";
    protected String username;
    protected String passwd;

    public Utilisateur() {}

    public Utilisateur(String login, String passwd) {
        this.username = login;
        this.passwd = passwd;
    }
    
    public int compareTo(String username, String passwd) {
    	if (username.compareTo(this.username)!=0 ) {return -1;}
    	else {
    		if (passwd.compareTo(this.passwd)==0) {return 1;}
    		else {return 0;}
    	}
      
    }
    
    public String getimage() {
		return image;
	}
	public void setimage(String image) {
		this.image = image;
	}
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
    //autentifier utilisateur
    public int compareTo (Utilisateur user) {
		if (user.username.compareTo(this.username)!=0 ) {return -1;}
    	else {
    		if (user.passwd.compareTo(this.passwd)==0) {return 1;}
    		else {return 0;}
    	}
    }

	
}