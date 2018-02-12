package pl.coderslab.workshop.admin;

public class PrintOutMenu {


	
//	/**
//	 * @param menuName
//	 * @return 
//	 */
//	public PrintOutMenu(String menuName) {
//		this.menuName = menuName;
//		System.out.print("=== START MENU ===\n");
//		System.out.print("Wybierz jedną z opcji ( wielkosc liter przy komednach nie ma znaczenia ): \n");
//		System.out.print("1. add - dodanie "+this.menuName+"\n");
//		System.out.print("2. edit- edycja "+this.menuName+"\n");
//		System.out.print("3. delete- edycja "+this.menuName+"\n");
//		System.out.print("4. QUIT- zakończenie programu\n");
//		System.out.print("=== KONIEC MENU ===\n");
//		return;
//	}
	
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
		sb.append("=== START MENU ===\n");
		sb.append("Wybierz jedną z opcji ( wielkosc liter przy komednach nie ma znaczenia ): \n");
		sb.append("1. add - dodanie "+this.menuName+"\n");
		sb.append("2. edit- edycja "+this.menuName+"\n");
		sb.append("3. delete- edycja "+this.menuName+"\n");
		sb.append("4. QUIT- zakończenie programu\n");
		sb.append("=== KONIEC MENU ===");
		return sb.toString();
	}
}
