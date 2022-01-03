package AST;

import TYPES.*;

public class AST_EXP_VAR extends AST_exp
{
	public AST_VAR var;

	public int varLine;
	public int varColumn;

	public AST_EXP_VAR(AST_VAR var, int varLine, int varColumn)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== exp -> var\n");

		this.var = var;
		this.varLine = varLine;
		this.varColumn = varColumn;
	}
	

	public void PrintMe()
	{
		System.out.print("AST NODE EXP VAR\n");

		if (var != null) var.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"EXP\nVAR");

		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}
	
	public TYPE SemantMe()
	{
		return var.SemantMe();
	}
}

