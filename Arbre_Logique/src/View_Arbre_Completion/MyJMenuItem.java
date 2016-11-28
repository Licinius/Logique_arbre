package View_Arbre_Completion;

import javax.swing.JMenuItem;

public class MyJMenuItem extends JMenuItem{
	private int i;
	private int j;
	
	public MyJMenuItem(String text,int i, int j){
		super(text);
		this.i=i;
		this.j=j;
	}
	
	public int getI(){
		return i;
	}
	public int getJ(){
		return j;
	}
}
