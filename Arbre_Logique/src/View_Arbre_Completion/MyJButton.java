package View_Arbre_Completion;

import javax.swing.JButton;

public class MyJButton extends JButton {
	private int branch;
	private int index;
	
	public MyJButton (String text,int branch,int index){
		super(text);
		this.branch = branch;
		this.index = index;
	}
	public int getBranch() {
		return branch;
	}

	public int getIndex() {
		return index;
	}
}
