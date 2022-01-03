package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_ASSIGN extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_exp exp;

	public int varLine;
	public int varColumn;
	public int expLine;
	public int expColumn;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_VAR var, int varLine, int varColumn, AST_exp exp, int expLine, int expColumn)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;

		this.varLine = varLine;
		this.varColumn = varColumn;
		this.expLine = expLine;
		this.expColumn = expColumn;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}
	
	/*   EXAMPLE: A => B => C , where A => B means A is a subclass of B                        */
    /*   C x = new A is VALID ,   A y = new C is INVALID                                       */
    /*   compareClasses(t1, t2) returns true iff t1 and t2 are instances of same class OR      */
    /*                                              OR t2 is a subclass of t1                  */

    public boolean compareClasses(TYPE_CLASS t1, TYPE_CLASS t2)
	{
        /* start from t1 not t1.father */
		for ( TYPE_CLASS it = t2; it != null; it = it.father) 
		{
			if (it.name.equals(t1.name)) 
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
	
	public TYPE SemantMe()
	{
		TYPE t1 = null;
		TYPE t2 = null;
		
		if (var != null) t1 = var.SemantMe();
		if (exp != null) t2 = exp.SemantMe();
	
		if(t1 == null || t2 == null)
		{
			System.out.format(">> ERROR [%d:%d] %s is not decleared \n", varLine, varColumn, var.name);
			SYMBOL_TABLE.getInstance().printERROR(varLine);
			System.exit(0);
		}
				
		if(t1.isClass())
		{
			if(t2 == TYPE_NIL.getInstance())
			{
				return null;
			}
			else
			{	
				if (t2.isClass()) {
					/*  t1 ASSIGN t2 allow iff  t1 and t2 same class or t2 subclass of t1 */
					if (compareClasses((TYPE_CLASS) t1, (TYPE_CLASS) t2)) 
					{
						return null;
					}
				}
				else
				{
					System.out.format(">> ERROR [%d:%d] type mismatch for %s := %s\n",varLine, varColumn, t1.name, t2.name);
					SYMBOL_TABLE.getInstance().printERROR(varLine);
					System.exit(0);
				}
			}
		}
		else
		{
			if(t1.isCFIELD() == true)
			{
				TYPE_CFIELD t1_cf = (TYPE_CFIELD) t1; 
				if(t1_cf.type.isFunction() == true)
				{
					System.out.format(">> ERROR [%d:%d] cannot assign into a method %s\n", varLine, varColumn, t1.name);
					SYMBOL_TABLE.getInstance().printERROR(varLine);
					System.exit(0);		
				}
				else
				{
					TYPE_VAR temp = (TYPE_VAR) t1_cf.type; 
					TYPE t1_t = temp.varType;
					if (! t1_t.name.equals(t2.name) )
					{
						if(t1_t.isClass() && t2.isClass() && 
								(compareClasses((TYPE_CLASS)t1_t,(TYPE_CLASS)t2) != true && t2 != TYPE_NIL.getInstance()))
						{
							System.out.format(">> ERROR [%d:%d] faulty assign",varLine, varColumn);
							SYMBOL_TABLE.getInstance().printERROR(varLine);
							System.exit(0);
						}
					}
					return null;
				}
			}
			if(t1.isArray() == true)
			{
				if( t1.name.equals(t2.name) || t2 == TYPE_NIL.getInstance()  )
				{
					return null;
				}
				else
				{
					System.out.format(">> ERROR [%d:%d] cannot assign %s to array %s\n",expLine, expColumn, t2.name, t1.name);
					SYMBOL_TABLE.getInstance().printERROR(expLine);
					System.exit(0);
				}
			}
			else
			{
				if(t1 != TYPE_INT.getInstance() && t1 != TYPE_STRING.getInstance())
				{
					System.out.format(">> ERROR [%d:%d] cannot assign to %s of type %s\n",varLine, varColumn, var.name, t2.name);
					SYMBOL_TABLE.getInstance().printERROR(varLine);
					System.exit(0);
				}
				if (! t1.name.equals(t2.name) ) 
				{
					System.out.format(">> ERROR [%d:%d] cannot assign to %s of type %s\n", varLine, varColumn, var.name, t2.name);
					SYMBOL_TABLE.getInstance().printERROR(varLine);
					System.exit(0);
				}
			}
		}
		
		
		return null;
	}
}
