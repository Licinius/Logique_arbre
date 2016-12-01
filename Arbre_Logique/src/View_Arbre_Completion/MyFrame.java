package View_Arbre_Completion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import Arbre_Completion.Complexe;
import Arbre_Completion.EnumOperator;
import Arbre_Completion.Expression;
import Arbre_Completion.Literal;
import Arbre_Completion.Tree;
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
				menuVertical[i][j] = new MyJMenuItem(trees[j].getRootExpression(),i,j);
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
		controller = new ControllerTree(tree,this);
		treePanel.removeAll();
		
		//Parcours en largeur
		ArrayList<Tree> AT = new ArrayList<Tree>();
		ArrayList<Tree> dejaVu = new ArrayList<Tree>();
		AT.add(tree); 
		dejaVu.add(tree);
		Tree t =null;	
		while(!AT.isEmpty()){
			t = AT.remove(0);
			Expression[] expr = t.getExpressionsInArray();
			for (int i = 0; i < expr.length; i++) {
				MyJButton jb = new MyJButton(expr[i].toString(), t.getIdentifiant(), i);
				if(!t.getBlocked())	{
					if(expr[i].isLiteral()){}
					else jb.addActionListener(new JButtonComplexeListener());
				}
				treePanel.add(jb);
			}
			if(t.getLeftSon() != null){
				if(!dejaVu.contains(t.getLeftSon())){
					AT.add(t.getLeftSon());
					dejaVu.add(t.getLeftSon());
				}
			}
			if(t.getRightSon() != null){
				if(!dejaVu.contains(t.getRightSon())){
					AT.add(t.getRightSon());
					dejaVu.add(t.getRightSon());
				}
			}
		}
		this.validate();
		this.repaint();
	}

	class JMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			MyJMenuItem menuItem= (MyJMenuItem)e.getSource();
			printTree(treesToProof[menuItem.getI()][menuItem.getJ()]);
		}
	}
	
	class JButtonComplexeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			MyJButton button= (MyJButton)e.getSource();
			controller.developExpression(button.getBranch(),button.getIndex());
		}
	}
	
	public static void main(String[] args) {
		
		Expression E1 = new Literal(false,"p"); // p
		Expression E2 = new Literal(false,"q"); // q
		Expression E3 = new Literal(true,"p"); // ¬p
		Expression E4 = new Literal(true,"q"); // ¬q
		
		//(p → (¬q → ¬(p → q)))
		Expression Complexe1 = new Complexe (true,EnumOperator.IMPLICATION,E1,E2); // ¬(p → q)
		Expression Complexe2 = new Complexe (false,EnumOperator.IMPLICATION,E4,Complexe1); // (¬q → ¬(p → q))
		Expression Final1 = new Complexe(false,EnumOperator.IMPLICATION,E1,Complexe2);

		//(p → ((p → q) → q)) 
		Expression Complexe3 = new Complexe (false, EnumOperator.IMPLICATION, E1, E2); // (p → q)
		Expression Complexe4 = new Complexe (false, EnumOperator.IMPLICATION, Complexe3, E2); // ((p → q) → q)
		Expression Final2 = new Complexe(false, EnumOperator.IMPLICATION, E1, Complexe4);

		//((¬p → ¬q) → (q → p)) 
		Expression Complexe5 = new Complexe (false, EnumOperator.IMPLICATION, E3, E4); // (¬p → ¬q)
		Expression Complexe6 = new Complexe (false, EnumOperator.IMPLICATION, E2, E1); // (q → p)
		Expression Final3 = new Complexe(false, EnumOperator.IMPLICATION, Complexe5, Complexe6);		

		Expression shortFinal = new Complexe(false, EnumOperator.AND, E1, E3);
		
		Tree[][] matriceTree = new Tree[2][2];
		matriceTree[0][0] = new Tree(shortFinal);
		matriceTree[0][1] = new Tree(Final1);
		matriceTree[1][0] = new Tree(Final2);
		matriceTree[1][1] = new Tree(Final3);
		
		
		MyFrame f = new MyFrame(matriceTree);
		f.setVisible(true);
	}
}
