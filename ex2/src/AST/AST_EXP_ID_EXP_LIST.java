package AST;

public class AST_EXP_ID_EXP_LIST extends AST_exp
{
	public String name;
	public AST_EXP_LIST el;
	
	public AST_EXP_ID_EXP_LIST(String name, AST_EXP_LIST el)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();


		if(el == null) System.out.format("====================== exp -> ID(%s) ( )\n", name);
		if(el != null) System.out.format("====================== exp -> ID(%s) (expList)\n", name);
		this.name = name;
		this.el = el;
	}
	
	public void PrintMe()
	{
		if(el == null) System.out.format("AST EXP ID EMPTY ( %s )\n",name);
		if(el != null) 
		{
			System.out.format("AST EXP ID ( %s ) (exps) \n",name);
			el.PrintMe();
		}

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ID(%s)(exps)",name));
		
		if (el != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,el.SerialNumber);
	}
}
