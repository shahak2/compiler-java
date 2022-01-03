package AST;

public class AST_STMT_EXP_LIST extends AST_STMT
{
	public String name;
	public AST_EXP_LIST el;
	
	public AST_STMT_EXP_LIST(String name, AST_EXP_LIST el)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		if(el == null) System.out.format("====================== stmt -> ID(%s) ( ); \n", name);
		if(el != null) System.out.format("====================== stmt -> ID(%s) (expList); \n", name);
		
		this.name = name;
		this.el = el;
	}
	
	public void PrintMe()
	{
		if(el == null) System.out.format("AST STMT ID EMPTY ( %s )\n",name);
		if(el != null) 
		{
			System.out.format("AST STMT ID ( %s ) (exps) \n",name);
			el.PrintMe();
		}

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ID(%s)(exps)",name));
		
		if (el != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,el.SerialNumber);
	}
}
