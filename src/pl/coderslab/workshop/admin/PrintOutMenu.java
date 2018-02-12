package pl.coderslab.workshop.admin;

public class PrintOutMenu {

	/**
	 * @param menuName
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
		sb.append("=== START MENU ===\n");
		sb.append("Wybierz jedną z opcji : \n");
		sb.append("add - dodanie "+this.menuName+"\n");
		sb.append("edit - edycja "+this.menuName+"\n");
		sb.append("delete - edycja "+this.menuName+"\n");
		sb.append("quit - zakończenie programu\n");
		sb.append("=== KONIEC MENU ===");
		return sb.toString();
	}
}
