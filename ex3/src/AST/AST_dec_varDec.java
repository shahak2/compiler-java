package AST;

import TYPES.*;

public class AST_dec_varDec extends AST_dec
{
    public AST_varDec varDec;

	public AST_dec_varDec(AST_varDec varDec)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== dec -> varDec\n");

		this.varDec = varDec;
	}
	

	public void PrintMe()
	{
		
		System.out.print("AST NODE varDec\n");


		if (varDec != null) varDec.PrintMe();
		
	
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"dec\nvarDec");

		if (varDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,varDec.SerialNumber);
			
	}
	
	public TYPE SemantMe()
	{
		if(varDec != null)
			return varDec.SemantMe();
		return null;
	}
}
