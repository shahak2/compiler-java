package AST;

public class AST_EXP_STRING extends AST_exp
{
	public String value;
	
	public AST_EXP_STRING(String v)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		String value = v.substring(1, v.length() - 1);
		this.value = value;


		System.out.format("====================== exp -> STRING( %s )\n", value);
		
	}

	public void PrintMe()
	{
		System.out.format("AST NODE STRING( %s )\n",value);

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STRING(%s)",value));
	}
}
