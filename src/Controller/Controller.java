package Controller;

public abstract class Controller {
	public abstract void listen(String value);
	public abstract void updateUserInfo(String usrInfo);
	public abstract void switchView(String value);
	public String ID = "undef";
}
