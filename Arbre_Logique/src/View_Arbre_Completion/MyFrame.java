package View_Arbre_Completion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

import Arbre_Completion.*;
import Controller_Arbre_Completion.ControllerTree;
public class MyFrame extends JFrame{
	
	private ControllerTree controller;
	private Tree[][] treesToProof=new Tree[4][3];
	private JMenu[] menuHorizontal = new JMenu[4];
	private MyJMenuItem[][] menuVertical = new MyJMenuItem[4][3];
	private JMenuBar barMenu;
	private JPanel treePanel=new JPanel();;
	
	public MyFrame(Tree[][] matriceTree){
		//this.matriceTree = matriceTree;
		setSize(800, 800); 
		setTitle("Methode des arbres"); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		barMenu = new JMenuBar();
		int i =0;
		for (Tree[] trees : matriceTree) {
			menuHorizontal[i] =  new JMenu("Niveau : " + (i+1));
			for (int j = 0; j < trees.length; j++) {
				treesToProof[i][j] = matriceTree [i][j];
				menuVertical[i][j] = new MyJMenuItem(trees[j].toStringExpressionOfBranch(0, 0),i,j);
				menuVertical[i][j].addActionListener(new JMenuListener());
				menuHorizontal[i].add(menuVertical[i][j]);
			}
			barMenu.add(menuHorizontal[i]);
			i++;
		}
		setJMenuBar(barMenu);
		add(treePanel);
		
	}
	
	public void printTree(Tree tree){
		System.out.println("tree: "+tree.toString());
		controller = new ControllerTree(tree,this);
		treePanel.removeAll();
		int width  = tree.getNbLevel();
		int height = (tree.getNbLevel()-1)*2;
		if(width<=20) width=800;
		else width = width *40;
		
		//Pour le moment 
		height = 800;
			
		treePanel.setSize(width,height);
		for (int i = 0; i < tree.getNbLevel(); i++) {
			for (int j = 0; j < tree.getNbBranchOfLevel(i); j++) {
				String[] exprs = tree.toStringExpression(i,j);
				if (exprs.length>0){
					System.out.println(exprs.length);
					for(int index = 0; index<exprs.length;index++){
						MyJButton jb = new MyJButton(exprs[index], i, j, index);
						jb.addActionListener(new JButtonListener());
						treePanel.add(jb);
					}
				}
			}
		}
		this.validate();
	}
	
	
	class JMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			MyJMenuItem menuItem= (MyJMenuItem)e.getSource();
			printTree(treesToProof[menuItem.getI()][menuItem.getJ()]);
		}
	}
	
	class JButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			MyJButton button= (MyJButton)e.getSource();
			controller.developExpression(button.getIndex(), button.getLevel(), button.getBranch());
		}
	}
	public static void main(String[] args) {
			
		Expression E1 = new Literal(false,"p");
		Expression E2 = new Literal(false,"q");
		
		Expression E4 = new Literal(true,"q");
		
		Expression Complexe1 = new Complexe (true,EnumOperator.IMPLICATION,E1,E2);
		Expression Complexe2 = new Complexe (false,EnumOperator.IMPLICATION,E4,Complexe1);
		
		Expression Final = new Complexe(false,EnumOperator.IMPLICATION,E1,Complexe2);
		
		Expression Final2 = new Complexe(false,EnumOperator.OR,E1,E2);
		Tree[][] matriceTree = new Tree[1][2];
		matriceTree[0][0] = new Tree(Final);
		matriceTree[0][1] = new Tree(Final2);
		
		MyFrame f = new MyFrame(matriceTree);
		f.printTree(matriceTree[0][1]);
		f.setVisible(true);
	}
}
