package View_Arbre_Completion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

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
	private JPanel treePanel=new JPanel();
	private long time;
	private int[] lastLiteral= new int[2]; // 0 : Branch  1: Index
	
	public MyFrame(Tree[][] matriceTree){
		//this.matriceTree = matriceTree;
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setTitle("Methode des arbres"); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		barMenu = new JMenuBar();
		int i =0;
		for (Tree[] trees : matriceTree) {
			menuHorizontal[i] =  new JMenu("Niveau : " + (i+1));
			for (int j = 0; j < trees.length; j++) {
				if (matriceTree[i][j] != null) {
					treesToProof[i][j] = matriceTree [i][j];
					menuVertical[i][j] = new MyJMenuItem(trees[j].getRootExpression(),i,j);
					menuVertical[i][j].addActionListener(new JMenuListener());
					menuHorizontal[i].add(menuVertical[i][j]);
				}
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
		ArrayList<Integer> largeur_AT = new ArrayList<Integer>();
		ArrayList<Tree> dejaVu = new ArrayList<Tree>();
		AT.add(tree);
		largeur_AT.add(1600);
		dejaVu.add(tree);
		Tree t =null;
		int taille_pere;
		int hauteur_panel;
		
	
		while(!AT.isEmpty()){
			t = AT.remove(0);
			taille_pere = largeur_AT.remove(0);
			Expression[] expr = t.getExpressionsInArray();
			hauteur_panel = expr.length*50;
			Dimension dim = new Dimension();
			dim.height = hauteur_panel;
			dim.width = taille_pere;
//			JPanel jp =new JPanel(new BoxLayout(treePanel, BoxLayout.PAGE_AXIS));
			JPanel jp =new JPanel();
			jp.setBorder(new LineBorder(Color.BLACK));
			jp.setMinimumSize(dim);
			jp.setMaximumSize(dim);
			jp.setPreferredSize(dim);
			jp.setLayout(new GridLayout(expr.length,0));
			for (int i = 0; i < expr.length; i++) {
				MyJButton jb = new MyJButton(expr[i].toString(), t.getIdentifiant(), i);
				if(!t.getBlocked())	{
					if(expr[i].isLiteral())jb.addActionListener(new JButtonLiteralListener());
					else jb.addActionListener(new JButtonComplexeListener());
				}else{
					jb.setEnabled(false);
				}
				jp.add(jb);
				treePanel.add(jp);
			}
			
			if(t.getLeftSon() != null){
				if(!dejaVu.contains(t.getLeftSon())){
					AT.add(t.getLeftSon());
					if(t.getRightSon() == null){
						largeur_AT.add(taille_pere);
					}else{
						largeur_AT.add(taille_pere/2);
					}
					dejaVu.add(t.getLeftSon());
				}
			}
			if(t.getRightSon() != null){
				if(!dejaVu.contains(t.getRightSon())){
					AT.add(t.getRightSon());
					largeur_AT.add(taille_pere/2);
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
			time = System.currentTimeMillis();
			printTree(treesToProof[menuItem.getI()][menuItem.getJ()]);
		}
	}
	
	class JButtonComplexeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			lastLiteral[0]=-1;
			lastLiteral[1]=-1;
			MyJButton button= (MyJButton)e.getSource();
			controller.developExpression(button.getBranch(),button.getIndex());
		}
	}
	
	class JButtonLiteralListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			MyJButton button= (MyJButton)e.getSource();	
			if(lastLiteral[0]!=-1 && lastLiteral[1]!=-1){
				if(lastLiteral[1]!=button.getIndex() && lastLiteral[0]==button.getBranch()){
					controller.setBlocked(lastLiteral[0],lastLiteral[1],button.getIndex());
					lastLiteral[0]=-1; lastLiteral[1]=-1;
				}
			}else{
				lastLiteral[0]=button.getBranch();
				lastLiteral[1]=button.getIndex();
			}
			
		}
	}
	
	public void showMessage(String infoMessage,String titleBar){
		 JOptionPane.showMessageDialog(this, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
	}
	public void RAZ(){
		treePanel.removeAll();
		this.validate();
		this.repaint();
	}
	public void printFinished() {
		long timeFinished = (System.currentTimeMillis() - time)/1000;
		showMessage("Bravo vous avez gagné en " + timeFinished + " seconde(s)", "C'est Gagné ! Yes we did it");
		controller.RAZ();
	}
	public static void main(String[] args) {
		
		Expression E1 = new Literal(false,"p"); // p
		Expression E2 = new Literal(false,"q"); // q
		Expression E3 = new Literal(true,"p"); // ¬p
		Expression E4 = new Literal(true,"q"); // ¬q
		
		//(p → (¬q → ¬(p → q)))
		Expression Complexe1 = new Complexe (true,EnumOperator.IMPLICATION,E1,E2); // ¬(p → q)
		Expression Complexe2 = new Complexe (false,EnumOperator.IMPLICATION,E4,Complexe1); // (¬q → ¬(p → q))
		Expression Final1 = new Complexe(true,EnumOperator.IMPLICATION,E1,Complexe2);

		//(p → ((p → q) → q)) 
		Expression Complexe3 = new Complexe (false, EnumOperator.IMPLICATION, E1, E2); // (p → q)
		Expression Complexe4 = new Complexe (false, EnumOperator.IMPLICATION, Complexe3, E2); // ((p → q) → q)
		Expression Final2 = new Complexe(true, EnumOperator.IMPLICATION, E1, Complexe4);

		//((¬p → ¬q) → (q → p)) 
		Expression Complexe5 = new Complexe (false, EnumOperator.IMPLICATION, E3, E4); // (¬p → ¬q)
		Expression Complexe6 = new Complexe (false, EnumOperator.IMPLICATION, E2, E1); // (q → p)
		Expression Final3 = new Complexe(true, EnumOperator.IMPLICATION, Complexe5, Complexe6);		
		
		Tree[][] matriceTree = new Tree[2][3];
		
		matriceTree[1][0] = new Tree(Final1);
		matriceTree[1][1] = new Tree(Final2);
		matriceTree[1][2] = new Tree(Final3);
		
		
		MyFrame f = new MyFrame(matriceTree);
		f.setVisible(true);
	}


}
