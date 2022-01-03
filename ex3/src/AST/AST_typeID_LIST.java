package AST;

public class AST_typeID_LIST extends AST_Node {

  
	public AST_typeID typeID;
	public AST_typeID_LIST tail;

	public int typeIDLine;
	public int typeIDColumn;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_typeID_LIST(AST_typeID typeID, int typeIDLine, int typeIDColumn, AST_typeID_LIST tail)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) System.out.print("====================== typeIDList -> typeID COMMA typeIDList\n");
		if (tail == null) System.out.print("====================== typeIDList -> typeID      \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.typeID = typeID;
		this.tail = tail;

		this.typeIDLine = typeIDLine;
		this.typeIDColumn = typeIDColumn;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE typeID LIST\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (typeID != null) typeID.PrintMe();
		if (tail != null) tail.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"typeID\nLIST\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (typeID != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,typeID.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
    
}
