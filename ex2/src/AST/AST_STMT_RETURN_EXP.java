package AST;

public class AST_STMT_RETURN_EXP extends AST_STMT
{
	public AST_exp e;

	public AST_STMT_RETURN_EXP(AST_exp e)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		if (e != null) System.out.print("====================== stmts -> RETURN [exp] ;\n");
		if (e == null) System.out.print("====================== stmts -> RETURN ;      \n");

		this.e = e;
	}


	public void PrintMe()
	{
		System.out.print("AST NODE STMT RETURN [exp] ;\n");

		if (e != null) e.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"STMT\nRETURN [exp] \n");
		
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);

	}
	
}
