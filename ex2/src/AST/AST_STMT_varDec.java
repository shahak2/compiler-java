package AST;

public class AST_STMT_varDec extends AST_STMT
{
    public AST_varDec varDec;

	public AST_STMT_varDec(AST_varDec varDec)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== stmt -> varDec\n");

		this.varDec = varDec;
	}
	

	public void PrintMe()
	{
		
		System.out.print("AST NODE STMT varDec\n");


		if (varDec != null) varDec.PrintMe();
		
	
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"STMT\nvarDec");

		if (varDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,varDec.SerialNumber);
			
	}
}
