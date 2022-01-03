package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_VAR var;
	public AST_exp subscript;

	public int varLine;
	public int varColumn;

	public int subscriptLine;
	public int subscriptColumn;

	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SUBSCRIPT(AST_VAR var, int varLine, int varColumn, AST_exp subscript, int subscriptLine, int subscriptColumn)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== var -> var [ exp ]\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.subscript = subscript;

		this.varLine = varLine;
		this.varColumn = varColumn;
		this.subscriptLine = subscriptLine;
		this.subscriptColumn = subscriptColumn;
	}

	/*****************************************************/
	/* The printing message for a subscript var AST node */
	/*****************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE SUBSCRIPT VAR\n");

		/****************************************/
		/* RECURSIVELY PRINT VAR + SUBSRIPT ... */
		/****************************************/
		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"SUBSCRIPT\nVAR\n...[...]");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var       != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,subscript.SerialNumber);
	}
	
	public TYPE SemantMe()
	{
		TYPE t = null;
		TYPE_ARRAY ta = null;
		
		if (var != null) t = var.SemantMe();
		if (t.isArray() == false)
		{
			System.out.format(">> ERROR [%d:%d] %s is not an array\n", varLine, varColumn, var.name);
			SYMBOL_TABLE.getInstance().printERROR(varLine);
			System.exit(0);
			
		}
		else
		{
			ta = (TYPE_ARRAY) t;
		}
		
		if(subscript.SemantMe() != TYPE_INT.getInstance())
		{
			System.out.format(">> ERROR [%d:%d] array index must be an integer\n", subscriptLine, subscriptColumn);
			SYMBOL_TABLE.getInstance().printERROR(subscriptLine);
			System.exit(0);
		}
		
		return ta.arrayType;
	}
}
