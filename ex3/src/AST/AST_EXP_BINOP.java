package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_BINOP extends AST_exp
{
	int OP;
	public AST_exp left;
	public AST_exp right;

	public int leftLine;
	public int leftColumn;
	public int rightLine;
	public int rightColumn;
	
	public AST_EXP_BINOP(AST_exp left, int leftLine, int leftColumn, AST_exp right, int rightLine, int rightColumn, int OP)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== exp -> exp BINOP exp\n");

		this.left = left;
		this.right = right;
		this.OP = OP;

		this.leftLine = leftLine;
		this.leftColumn = leftColumn;
		this.rightLine = rightLine;
		this.rightColumn = rightColumn;
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
	
	public boolean compareClasses(TYPE_CLASS t1, TYPE_CLASS t2)
	/* This function checks if t2 is an instance of t1 or its ancestors */
	{
		for (TYPE_CLASS it = t1; it != null; it = it.father) 
		{
			if (it.name.equals(t2.name)) 
			{
				return true;
			}
		}
		return false;
	}
	
	public TYPE CFIELD_handler(TYPE_CFIELD tcfl)
	{
		if(tcfl.type.isFunction() == true)
		{
			TYPE_FUNCTION tcfl_tf = (TYPE_FUNCTION) tcfl.type;
			return tcfl_tf.returnType;
		}
		
		TYPE t = tcfl.type;
		TYPE_VAR  tv = (TYPE_VAR) t;
		
		return tv.varType;

	}

	public String getBinopSymbol(int op) {

		String sOP="";
		
		if (OP == 0) {sOP = "+";}
		if (OP == 1) {sOP = "-";}
		if (OP == 2) {sOP = "*";}
		if (OP == 3) {sOP = "/";}
		if (OP == 4) {sOP = "<";}
		if (OP == 5) {sOP = ">";}
		if (OP == 6) {sOP = "=";}

		return sOP;

	}
	
	public TYPE SemantMe()
	{
		TYPE t1 = null;
		TYPE t2 = null;

		if (left  != null) t1 = left.SemantMe();
		if (right != null) t2 = right.SemantMe();

		if (t1 == null) {
			System.out.format(">> ERROR [%d:%d] SemantMe of left side binop (%s) returned null\n",leftLine, leftColumn, getBinopSymbol(OP));
			SYMBOL_TABLE.getInstance().printERROR(leftLine);
			System.exit(0);

		}
		
		if (t2 == null) {
			System.out.format(">> ERROR [%d:%d] SemantMe of right side binop (%s) returned null\n",leftLine, leftColumn, getBinopSymbol(OP));
			SYMBOL_TABLE.getInstance().printERROR(rightLine);
			System.exit(0);

		} 
		if(t1.isCFIELD() == true) {
			t1 = CFIELD_handler((TYPE_CFIELD) t1);
		}
		if(t2.isCFIELD() == true)
			t2 = CFIELD_handler((TYPE_CFIELD) t2);
		
		if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()))
		{
			System.out.print(t2.name);
			if (OP==3 && right.value==0)
			{
				System.out.format(">> ERROR [%d:%d] divison by zero\n",rightLine, rightColumn);
				SYMBOL_TABLE.getInstance().printERROR(rightLine);
				System.exit(0);
			}
			return TYPE_INT.getInstance();
		}

		if ((t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance()))
		{
			if(OP==0)
				return TYPE_STRING.getInstance();
			if(OP==6)
				return TYPE_INT.getInstance();
		}

		if (OP==6)
		{
			if (t1.name.equals(t2.name))
			{
				return TYPE_INT.getInstance();
			}
			/* SAME TYPE but DIFFERENT NAME */
			else
			{	
				if ((t1 == TYPE_NIL.getInstance()) && (t2 == TYPE_NIL.getInstance())) {
					return TYPE_INT.getInstance();
				}

				if ((t1 ==  TYPE_NIL.getInstance()) && (t2.isClass())) {
					return TYPE_INT.getInstance();
				}
				if ((t1.isClass()) && (t2 == TYPE_NIL.getInstance())) {
					return TYPE_INT.getInstance();
				}
				if( ( t1.isClass() ) && (t2.isClass() ))
				{	
					TYPE_CLASS t1_CLASS = (TYPE_CLASS) t1;
					TYPE_CLASS t2_CLASS = (TYPE_CLASS) t2;
					
					if (compareClasses(t1_CLASS,t2_CLASS))
					{
						return TYPE_INT.getInstance();
					}
				}

				if ((t1 ==  TYPE_NIL.getInstance()) && (t2.isArray())) {
					return TYPE_INT.getInstance();
				}
				if ((t1.isArray()) && (t2 == TYPE_NIL.getInstance())) {
					return TYPE_INT.getInstance();
				}
			}
		}

		System.out.format(">> ERROR [%d:%d] cannot compare %s and %s\n",leftLine, leftColumn, t1.name, t2.name);
		SYMBOL_TABLE.getInstance().printERROR(leftLine);
		
		System.exit(0);
		
		return null;
	}
}

