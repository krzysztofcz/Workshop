package pl.coderslab.workshop.admin;

public class PrintOutMenu {

	/**
	 * @param menuName
	 * 
	 */
	public PrintOutMenu(String menuName) {
		this.menuName = menuName;
	}

	private String menuName;	
	
	/**
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * 
	 */
	public String toString() {
		StringBuffer sb= new StringBuffer();
		sb.append("=== start MENU ===\n");
		sb.append("1. add - dodanie "+this.menuName+"\n");
		sb.append("2. edit- edycja "+this.menuName+"\n");
		sb.append("3. delete- edycja "+this.menuName+"\n");
		sb.append("4. QUIT- zakończenie programu\n");
		sb.append("=== end MENU ===");
		return sb.toString();
	}
}
