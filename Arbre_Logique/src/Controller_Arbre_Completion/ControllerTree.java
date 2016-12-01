package Controller_Arbre_Completion;

import Arbre_Completion.Tree;
import View_Arbre_Completion.MyFrame;

public class ControllerTree {
	private Tree model;
	private MyFrame view;
	
	public ControllerTree(Tree treeProof, MyFrame frame){
		this.model = treeProof;
		this.view = frame;
	}
	
	public void developExpression(int indexBranch,int indexExpression){
		model.developExpression(indexBranch,indexExpression);
		updateView();
	}
	
	public void setBlocked(int indexBranch,int indexExpression1, int indexExpression2){
		boolean isBlocked = model.setBlocked(indexBranch,indexExpression1,indexExpression2);
		printBranchBlocked(isBlocked);
		if(isBlocked) updateView();
	}
	public void printBranchBlocked(boolean isBlocked){
		String titleBar = " Information sur la branche";
		String infoMessage = "";
		if(isBlocked)	infoMessage+="Vous avez selectionné des literaux complémentaire la branche est donc verrouillé";
		else	infoMessage+="Vous n'avez pas selectionner de literaux complementaire";
		
		view.showMessage(infoMessage, titleBar);
	}
	
	public void updateView(){				
		view.printTree(model);
	}	
}
