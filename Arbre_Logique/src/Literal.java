
public class Literal extends Expression {
	private String value;
	
	public Literal(boolean complement, String val){
		super(complement);
		this.setValue(val);

	}

	public Literal(Literal lit) {
		super(lit.getComplement());
		this.setValue(lit.getValue());
	}

	public String getValue() {
		return value;
	}

	public void setValue(String name) {
		this.value = name;
	}
	
	public boolean isLeaf(){
		return true;
	}
	
	public boolean isComplementary(Literal Literal) {
		return getValue().equals(Literal.getValue()) && super.getComplement() != Literal.getComplement();
	}
	
	public String toString(){
		return  super.getNotationComplement() + getValue();
	}
	
	public boolean equals(Literal lit){
		return super.equals(lit) && value.equals(lit.getValue());
	}

}
