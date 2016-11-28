package Controller_Arbre_Completion;

import Arbre_Completion.*;
import View_Arbre_Completion.*;

public class ControllerTree {
	private Tree model;
	private MyFrame view;
	
	public ControllerTree(Tree treeProof, MyFrame frame){
		this.model = treeProof;
		this.view = frame;
	}
	
	public void developExpression(int index,int level,int indexBranch){
		model.developExpression(index, level, indexBranch);
	}
	
	public void updateView(){				
		view.printTree(model);
	}	
}
