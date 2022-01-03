package AST;

import TYPES.*;

public class AST_dec_classDec extends AST_dec 
{
    public AST_classDec classDec;

	public AST_dec_classDec(AST_classDec classDec)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== dec -> classDec\n");

		this.classDec = classDec;
	}
	

	public void PrintMe()
	{
		
		System.out.print("AST NODE classDec\n");

		if (classDec != null) classDec.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"dec\nclassDec");

		if (classDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,classDec.SerialNumber);
			
	}
	
	public TYPE SemantMe()
	{
		if(classDec != null)
		{
			return classDec.SemantMe();
		}
		return null;
	}
}
