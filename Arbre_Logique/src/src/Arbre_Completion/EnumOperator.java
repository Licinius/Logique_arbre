package Arbre_Completion;

public enum EnumOperator {
	AND("∧"),
	OR("∨"),
	IMPLICATION("→");
	private final String fieldDescription;

    private EnumOperator(String value) {
        fieldDescription = value;
    }

    public String toString() {
        return fieldDescription;
    }
}
