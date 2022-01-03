package AST;

public class AST_STMT_VAR_FIELD_EXP_LIST extends AST_STMT
{
	public AST_VAR var;
	public String fieldName;
	public AST_EXP_LIST el;

	public AST_STMT_VAR_FIELD_EXP_LIST(AST_VAR var,String fieldName, AST_EXP_LIST el)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();


		if(el == null) System.out.format("====================== stmt -> var. ID(%s) ( )\n", fieldName);
		if(el != null) System.out.format("====================== stmt -> var . ID(%s) (exps)\n", fieldName);
		
		this.var = var;
		this.fieldName = fieldName;
		this.el = el;
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE STMT VAR FIELD EXP LIST\n");
		
		if (var != null) var.PrintMe();
		System.out.format("FIELD NAME( %s )\n",fieldName);
		if(el != null) el.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("STMT\nVAR\nFIELD\n...->%s.(exps)",fieldName));
			
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (el != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,el.SerialNumber);
			
	}
}
