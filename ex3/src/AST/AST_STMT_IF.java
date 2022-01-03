package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_STMT_IF extends AST_STMT
{
	public AST_exp cond;
	public AST_STMT_LIST body;

	public int condLine;
	public int condColumn;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_IF(AST_exp cond, int condLine, int condColumn, AST_STMT_LIST body)
	{
		this.cond = cond;
		this.body = body;

		this.condLine = condLine;
		this.condColumn = condColumn;

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== stmt -> IF (exp) {stmts} \n");

	}
	public void PrintMe() 
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.print("AST NODE STMT IF\n");

		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("STMT\nIF (cond) {body}\n"));

		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
	}
	
	public TYPE SemantMe()
	{

		if (cond.SemantMe() != TYPE_INT.getInstance())
		{
			System.out.format(">> ERROR [%d:%d] condition inside IF is not integral\n",condLine, condColumn);
			SYMBOL_TABLE.getInstance().printERROR(condLine);
			System.exit(0);
		}
		
		SYMBOL_TABLE.getInstance().beginScope();

		body.SemantMe();

		SYMBOL_TABLE.getInstance().endScope();

		return null;		
	}
}
