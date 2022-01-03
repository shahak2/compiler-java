package AST;

public class AST_EXP_BINOP extends AST_exp
{
	int OP;
	public AST_exp left;
	public AST_exp right;
	
	public AST_EXP_BINOP(AST_exp left,AST_exp right,int OP)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== exp -> exp BINOP exp\n");

		this.left = left;
		this.right = right;
		this.OP = OP;
	}
	
	public void PrintMe()
	{
		String sOP="";
		
		if (OP == 0) {sOP = "+";}
		if (OP == 1) {sOP = "-";}
		if (OP == 2) {sOP = "*";}
		if (OP == 3) {sOP = "/";}
		if (OP == 4) {sOP = "<";}
		if (OP == 5) {sOP = ">";}
		if (OP == 6) {sOP = "=";}
		
		System.out.print("AST NODE BINOP EXP\n");

		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("BINOP(%s)",sOP));
		
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,left.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,right.SerialNumber);
	}
}
