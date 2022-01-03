package AST;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_exp cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/********************/
	public AST_STMT_WHILE(AST_exp cond,AST_STMT_LIST body)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== stmt -> WHILE (exp) {stmts} \n");
		this.cond = cond;
		this.body = body;
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
}