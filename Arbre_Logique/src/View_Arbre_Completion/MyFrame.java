package View_Arbre_Completion;

import java.util.ArrayList;
import javax.swing.*;

import Arbre_Completion.*;
public class MyFrame extends JFrame{
	private Tree treeProof;
	private JMenu[] menuHorizontal = new JMenu[4];
	private JMenuItem[][] menuVertical = new JMenuItem[4][3];
	private JMenuBar barMenu;
	private Tree[][] matriceTree;
	
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
				System.out.println("i "+ i + "j " + j);
				menuVertical[i][j] = new JMenuItem(trees[j].toStringExpressionOfBranch(0, 0));
				menuHorizontal[i].add(menuVertical[i][j]);
			}
			barMenu.add(menuHorizontal[i]);
			i++;
		}
		setJMenuBar(barMenu);
		
	}
	
	public static void main(String[] args) {
			
		Expression E1 = new Literal(false,"p");
		Expression E2 = new Literal(false,"q");
		
		Expression E4 = new Literal(true,"q");
		
		Expression Complexe1 = new Complexe (true,EnumOperator.IMPLICATION,E1,E2);
		Expression Complexe2 = new Complexe (false,EnumOperator.IMPLICATION,E4,Complexe1);
		
		Expression Final = new Complexe(false,EnumOperator.IMPLICATION,E1,Complexe2);
		Tree[][] matriceTree = new Tree[1][1];
		matriceTree[0][0] = new Tree(Final);
		
		MyFrame f = new MyFrame(matriceTree);
		f.setVisible(true);
	}
}
