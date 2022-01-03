package AST;

import TYPES.*;

public class AST_EXP_STRING extends AST_exp
{
	public String val;

	public int valLine;
	public int valColumn;
	
	public AST_EXP_STRING(String v, int valLine, int valColumn)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		String val = v.substring(1, v.length() - 1);
		this.val = val;

		this.valLine = valLine;
		this.valColumn = valColumn;


		System.out.format("====================== exp -> STRING( %s )\n", val);
		
	}

	public void PrintMe()
	{
		System.out.format("AST NODE STRING( %s )\n",val);

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STRING(%s)",val));
	}
	
	public TYPE SemantMe()
	{
		return TYPE_STRING.getInstance();
	}
}
