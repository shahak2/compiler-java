package AST;

import TYPES.*;

public class AST_dec_arrayTypedef extends AST_dec 
{

    public AST_arrayTypedef arrayTypedef;

	public AST_dec_arrayTypedef(AST_arrayTypedef arrayTypedef)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== dec -> arrayTypedef\n");

		this.arrayTypedef = arrayTypedef;
	}
	

	public void PrintMe()
	{
		
		System.out.print("AST NODE arrayTypedef\n");

		if (arrayTypedef != null) arrayTypedef.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"dec\nvarrayTypedef");

		if (arrayTypedef != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,arrayTypedef.SerialNumber);
			
	}
	
	public TYPE SemantMe()
	{
		if(arrayTypedef != null)
			return arrayTypedef.SemantMe();
		return null;
	}
}
