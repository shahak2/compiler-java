package AST;

import TYPES.*;

public class AST_type_TYPE_INT extends AST_type
{

	public AST_type_TYPE_INT()
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== type -> TYPE_INT\n");
		this.name="int";

	}
	

	public void PrintMe()
	{
		
		System.out.print("AST NODE TYPE_INT\n");
		
	
		AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("TYPE\nINT"));
	}
	
	public TYPE SemantMe()
	{
		return TYPE_INT.getInstance();
	}
}
