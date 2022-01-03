package AST;

import TYPES.*;

public class AST_STMT_RETURN_EXP extends AST_STMT
{
	public AST_exp e;

	public int eLine;
	public int eColumn;

	public AST_STMT_RETURN_EXP(AST_exp e, int eLine, int eColumn)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		if (e != null) System.out.print("====================== stmts -> RETURN [exp] ;\n");
		if (e == null) System.out.print("====================== stmts -> RETURN ;      \n");

		this.e = e;

		this.eLine = eLine;
		this.eColumn = eColumn;
	}


	public void PrintMe()
	{
		System.out.print("AST NODE STMT RETURN [exp] ;\n");

		if (e != null) e.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"STMT\nRETURN [exp] \n");
		
		if (e != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,e.SerialNumber);

	}
	
	public TYPE SemantMe()
	{
		if (e == null) /* in case of "return;"*/
		{
			return new TYPE_RETURN("void",TYPE_VOID.getInstance());
		}
		TYPE t = e.SemantMe();	
		if (t.isCFIELD()) {
			TYPE_CFIELD temp1 = (TYPE_CFIELD) t;
			if (temp1.type.isVar()) {
				TYPE_VAR temp2 = (TYPE_VAR) temp1.type;
				return new TYPE_RETURN(temp2.varType.name, temp2.varType);
			}
			t = temp1.type;	
		}

		return new TYPE_RETURN(t.name, t);		
	}
	
}
