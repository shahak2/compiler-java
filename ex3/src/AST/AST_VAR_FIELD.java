package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_VAR var;
	public String fieldName;

	public int varLine;
	public int varColumn;
	public int fieldNameLine;
	public int fieldNameColumn;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(AST_VAR var, int varLine, int varColumn, String fieldName, int fieldNameLine, int fieldNameColumn)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> var DOT ID( %s )\n",fieldName);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.fieldName = fieldName;

		this.varLine = varLine;
		this.varColumn = varColumn;
		this.fieldNameLine = fieldNameLine;
		this.fieldNameColumn = fieldNameColumn;
	}

	/*************************************************/
	/* The printing message for a field var AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.print("AST NODE FIELD VAR\n");

		/**********************************************/
		/* RECURSIVELY PRINT VAR, then FIELD NAME ... */
		/**********************************************/
		if (var != null) var.PrintMe();
		System.out.format("FIELD NAME( %s )\n",fieldName);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FIELD\nVAR\n...->%s",fieldName));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}
	
	public TYPE SemantMe()
	{
		TYPE t = null;
		
		TYPE_CLASS tc = null;
		
		if (var != null) t = var.SemantMe();



		if (t.isClass() == false)
		{
			System.out.format(">> ERROR [%d:%d] access %s field of a non-class variable %s\n", fieldNameLine , fieldNameColumn,fieldName, t.name);
			SYMBOL_TABLE.getInstance().printERROR(fieldNameLine);
			System.exit(0);
		}
		else
		{
			tc = (TYPE_CLASS) t;
		}
		
		for (TYPE_CFIELD_LIST it = tc.data_members;it != null;it=it.tail)
		{

			if (it.head.name.equals(fieldName))
			{
				TYPE_VAR t_v= (TYPE_VAR)it.head.type;

				return t_v.varType;
			}
		}
		
		System.out.format(">> ERROR [%d:%d] field %s does not exist in class\n", fieldNameLine, fieldNameColumn, fieldName);
		SYMBOL_TABLE.getInstance().printERROR(fieldNameLine);							
		System.exit(0);
		return null;
	}
}
