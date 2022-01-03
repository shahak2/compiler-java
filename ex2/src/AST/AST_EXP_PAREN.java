package AST;

public class AST_EXP_PAREN extends AST_exp
{
	public AST_exp exp;
	
	public AST_EXP_PAREN(AST_exp exp) 
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		System.out.format("====================== exp -> ( exp ) \n");
		this.exp = exp;
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE EXP PAREN\n");

		if (exp != null) exp.PrintMe();
		

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("AST EXP PAREN"));
		

		if (exp  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

}
