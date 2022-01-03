package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_newExp extends AST_STMT
{

	public AST_VAR var;
	public AST_newExp newExp;

	public int varLine;
	public int varColumn;

	public int newExpLine;
	public int newExpColumn;


	public AST_STMT_newExp(AST_VAR var, int varLine, int varColumn, AST_newExp newExp, int newExpLine, int newExpColumn)
	{

		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.print("====================== stmt -> var ASSIGN newExp SEMICOLON\n");

		this.var = var;
		this.newExp = newExp;

		this.varLine = varLine;
		this.varColumn = varColumn;
		this.newExpLine = newExpLine;
		this.newExpColumn = newExpColumn;
	}

	public void PrintMe()
	{

		System.out.print("AST NODE ASSIGN STMT\n");

		if (var != null) var.PrintMe();
		if (newExp != null) newExp.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");
		
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (newExp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,newExp.SerialNumber);
	}
	
	public boolean compareClasses(TYPE_CLASS t1, TYPE_CLASS t2)
	/* This function checks if t2 is an ancestor of t1 */
	{
		for ( TYPE_CLASS it = t1.father; it != null; it = it.father) 
		{
			if (it.name.equals(t2.name)) 
			{
				return true;
			}
		}
		return false;
	}
	
	public TYPE SemantMe()
	{
		TYPE t1 = null;
		TYPE t2 = null;
		
		if (var != null) t1 = var.SemantMe();
		if (newExp != null) t2 = newExp.SemantMe();
	
		if(t1 == null)
		{
			System.out.format(">> ERROR [%d:%d] %s is not decleared \n", varLine, varColumn, var.name);
			SYMBOL_TABLE.getInstance().printERROR(varLine);
			System.exit(0);
		}
		
		if(t1.isClass() == true)
		{
			if(t2 == TYPE_NIL.getInstance())
			{
				return null;
			}
			else
			{
				if (t2 != null && (t1.name.equals(t2.name) || 
						compareClasses((TYPE_CLASS) t2, (TYPE_CLASS) t1) == true))
				{
					return null;			
				}
				else
				{
					System.out.format(">> ERROR [%d:%d] type mismatch for %s := %s\n",varLine, varColumn, var.name, t2.name);
					SYMBOL_TABLE.getInstance().printERROR(varLine);
					System.exit(0);
				}
			}
		}
		
		if(t1.isArray() == true)
		{
			TYPE_ARRAY t_arr= (TYPE_ARRAY)t1;
			if(t2 != null && ( t_arr.arrayType.name.equals(t2.name) || t2 == TYPE_NIL.getInstance() ) )
			{
				return null;
			}
			else
			{
				System.out.format(">> ERROR [%d:%d] cannot assign %s to array %s\n",newExpLine, newExpColumn, t2.name, t_arr.arrayType.name);
				SYMBOL_TABLE.getInstance().printERROR(newExpLine);
				System.exit(0);
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
					TYPE t1_t = t1_cf.type;
					if (! t1_t.name.equals(t2.name) )
					{
						if(t1_t.isClass() && t2.isClass() && 
								(compareClasses((TYPE_CLASS)t1_t,(TYPE_CLASS)t2) != true && t2 != TYPE_NIL.getInstance()))
						{
							System.out.format(">> ERROR [%d:%d] faulty assign ",varLine,varColumn);
							SYMBOL_TABLE.getInstance().printERROR(varLine);
							System.exit(0);
						}
					}
					return null;
					
				}
			}
			
			System.out.format(">> ERROR [%d:%d] cannot use 'new' keyword for %s of type %s\n", newExpLine, newExpColumn, t1.name, t2.name);
			SYMBOL_TABLE.getInstance().printERROR(newExpLine);
			System.exit(0);
		}
		
		return null;
	}
}
