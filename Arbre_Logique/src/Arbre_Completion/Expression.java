package Arbre_Completion;

public abstract class Expression {
	private boolean complement;
	private String nom;
	
	public Expression(boolean complement, String nom){
		this.setComplement(complement);
		this.setNom(nom);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean getComplement() {
		return complement;
	}

	public void setComplement(boolean complement) {
		this.complement = complement;
	}
	
	public abstract boolean isLiteral();
	
	public String getNotationComplement(){
		String res= "";
		if(getComplement()){
			res = "¬";
		}
		return res;
	}
	
	public boolean equals(Expression expr){
		return this.complement == expr.complement && this.nom == expr.nom;
	}

	public void reverseComplement() {
		this.complement = complement ? false : true;
	}
	
	public abstract String toString();

}
