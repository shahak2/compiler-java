package AST;

public class AST_STMT_newExp extends AST_STMT
{

	public AST_VAR var;
	public AST_newExp newExp;


	public AST_STMT_newExp(AST_VAR var,AST_newExp newExp)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== stmt -> var ASSIGN newExp SEMICOLON\n");

		this.var = var;
		this.newExp = newExp;
	}

	public void PrintMe()
	{

		System.out.print("AST NODE ASSIGN STMT\n");

		if (var != null) var.PrintMe();
		if (newExp != null) newExp.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");
		
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (newExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,newExp.SerialNumber);
	}
}
