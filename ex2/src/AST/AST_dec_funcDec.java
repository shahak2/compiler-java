package AST;

public class AST_dec_funcDec extends AST_dec 
{

    public AST_funcDec funcDec;

	public AST_dec_funcDec(AST_funcDec funcDec)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== dec -> funcDec\n");

		this.funcDec= funcDec;
	}
	

	public void PrintMe()
	{
		
		System.out.print("AST NODE funcDec\n");

		if (funcDec != null) funcDec.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"dec\nvfuncDec");

			if (funcDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,funcDec.SerialNumber);
			
	}
}
