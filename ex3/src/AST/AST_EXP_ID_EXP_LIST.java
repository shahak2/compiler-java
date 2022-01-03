package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_ID_EXP_LIST extends AST_exp
{
	public String name;
	public AST_EXP_LIST el;

	public int nameLine;
	public int nameColumn;

	public AST_EXP_ID_EXP_LIST(String name, int nameLine, int nameColumn, AST_EXP_LIST el)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();


		if(el == null) System.out.format("====================== exp -> ID(%s) ( )\n", name);
		if(el != null) System.out.format("====================== exp -> ID(%s) (expList)\n", name);

		this.name = name;
		this.el = el;

		this.nameLine = nameLine;
		this.nameColumn = nameColumn;
	}

	public void PrintMe()
	{
		if(el == null) System.out.format("AST EXP ID EMPTY ( %s )\n",name);
		if(el != null)
		{
			String.format("CALL(%s)\nWITH",name);
			el.PrintMe();
		}

		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("CALL(%s)\nWITH",name));

		if (el != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,el.SerialNumber);
	}

	public boolean compareClasses(TYPE_CLASS t1, TYPE_CLASS t2)
	/* This function checks if t2 is an instance of t1 or its ancestors */
	{
		for ( TYPE_CLASS it = t1.father; it != null; it = it.father) 
		{
			if (it.name == t2.name) 
			{
				return true;
			}
		}
		return false;
	}
	
	
	public TYPE SemantMe()
	{
		TYPE t_beforeCheck = SYMBOL_TABLE.getInstance().find(name);

		if (t_beforeCheck == null) {
			System.out.format(">> ERROR [%d:%d] %s does not exist\n", nameLine, nameColumn, name);	
			SYMBOL_TABLE.getInstance().printERROR(nameLine);	
			System.exit(0);
		}
		if (!(t_beforeCheck.isFunction()))
		{
			System.out.format(">> ERROR [%d:%d] %s is  not a function\n", nameLine, nameColumn, name);
			SYMBOL_TABLE.getInstance().printERROR(nameLine);		
			System.exit(0);
		}
		
		TYPE_FUNCTION typeFunc = (TYPE_FUNCTION) t_beforeCheck;
		TYPE_LIST func_iter = typeFunc.params;
		TYPE_LIST calling_params = el.SemantMe();
		TYPE_LIST rev_calling_params = null;
		
		for(TYPE_LIST t = calling_params; t != null; t = t.tail)
		{
			rev_calling_params= new TYPE_LIST(t.head,rev_calling_params);
		}
		
		TYPE_LIST calling_params_iter = rev_calling_params;
				
		while(calling_params_iter != null && func_iter != null)
		{	
			TYPE t = calling_params_iter.head;

			if(t.name != func_iter.head.name)
			{
				System.out.format(">> ERROR [%d:%d] method %s gets %s instead of %s.\n",nameLine, nameColumn,
						name, t.name, func_iter.head.name);
						SYMBOL_TABLE.getInstance().printERROR(nameLine);
				System.exit(0);
			}

			
			func_iter = func_iter.tail;
			calling_params_iter = calling_params_iter.tail;
		}
		if(calling_params_iter == null && func_iter == null)
		{
			return typeFunc.returnType;
		}
		
		System.out.format(">> ERROR [%d:%d] function %s gets wrong parameters\n",nameLine, nameColumn, name);
		SYMBOL_TABLE.getInstance().printERROR(nameLine);		
		System.exit(0);	
		
		return null;
	}
}
