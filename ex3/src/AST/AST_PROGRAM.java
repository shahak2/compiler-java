package AST;

import TYPES.*;


public class AST_PROGRAM extends AST_Node
{
	public AST_dec head;
	public AST_PROGRAM tail;


	public AST_PROGRAM(AST_dec head, AST_PROGRAM tail){

		SerialNumber = AST_Node_Serial_Number.getFresh();


		if (tail != null) System.out.print("====================== Program -> dec Program\n");
		if (tail == null) System.out.print("====================== Program -> dec      \n");

		this.head = head;
		this.tail = tail;

	}

	public void PrintMe()
	{


		System.out.print("AST NODE PROGRAM\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"PROGRAM\nLIST\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	public TYPE SemantMe()
	{
		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null) head.SemantMe();
		if (tail != null) tail.SemantMe();

		return null;
	}

}
