package View_Arbre_Completion;

import javax.swing.JButton;

public class MyJButton extends JButton {
	private int level;
	private int branch;
	private int index;
	
	public MyJButton (String text,int level,int branch,int index){
		super(text);
		this.level = level;
		this.branch = branch;
		this.index = index;
	}

	public int getLevel() {
		return level;
	}

	public int getBranch() {
		return branch;
	}

	public int getIndex() {
		return index;
	}
}
