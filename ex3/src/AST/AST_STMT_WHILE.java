package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_exp cond;
	public AST_STMT_LIST body;

	public int condLine;
	public int condColumn;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/********************/
	public AST_STMT_WHILE(AST_exp cond, int condLine, int condColumn, AST_STMT_LIST body)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== stmt -> WHILE (exp) {stmts} \n");
		this.cond = cond;
		this.body = body;

		this.condLine = condLine;
		this.condColumn = condColumn;
	}
	public void PrintMe() {
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.print("AST NODE STMT WHILE\n");

		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("STMT\nWHILE (cond) {body}\n"));

		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
	}
	
	public TYPE SemantMe()
	{

		if (cond.SemantMe() != TYPE_INT.getInstance())
		{
			System.out.format(">> ERROR [%d:%d] condition inside WHILE is not integral\n", condLine, condColumn);
			SYMBOL_TABLE.getInstance().printERROR(condLine);
			System.exit(0);
		}
		
		SYMBOL_TABLE.getInstance().beginScope();

		body.SemantMe();

		SYMBOL_TABLE.getInstance().endScope();

		return null;		
	}
}