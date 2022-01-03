package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_STMT_EXP_LIST extends AST_STMT
{
	public String name;
	public AST_EXP_LIST el;

	public int nameLine;
	public int nameColumn;
	
	public AST_STMT_EXP_LIST(String name, int nameLine, int nameColumn, AST_EXP_LIST el)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		if(el == null) System.out.format("====================== stmt -> ID(%s) ( ); \n", name);
		if(el != null) System.out.format("====================== stmt -> ID(%s) (expList); \n", name);
		
		this.name = name;
		this.el = el;

		this.nameLine = nameLine;
		this.nameColumn = nameColumn;
	}
	
	public void PrintMe()
	{
		if(el == null) System.out.format("AST STMT ID EMPTY ( %s )\n",name);
		if(el != null) 
		{
			System.out.format("AST STMT ID ( %s ) (exps) \n",name);
			el.PrintMe();
		}

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ID(%s)(exps)",name));
		
		if (el != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,el.SerialNumber);
	}
	
	public TYPE SemantMe()
	{
		TYPE func_t = SYMBOL_TABLE.getInstance().find(name);
		TYPE_FUNCTION func_tf = null;
		TYPE_LIST func_tf_iter = null;
		TYPE_LIST calling_param = null;
		TYPE_LIST t_iter = null;

		
		/*STEP 1: check the function exists.*/
		
		if(func_t == null )
		{
			System.out.format(">> ERROR [%d:%d] function(%s) doesn't exist.\n", nameLine, nameColumn, name);
			SYMBOL_TABLE.getInstance().printERROR(nameLine);
            System.exit(0);
		}
		
		/*STEP 2: check the name is for a function.*/
		
		if(func_t.isFunction() == false)
		{
			System.out.format(">> ERROR [%d:%d] function(%s) is not a function.\n", nameLine, nameColumn, name);
			SYMBOL_TABLE.getInstance().printERROR(nameLine);
            System.exit(0);		
		}
		
		func_tf = (TYPE_FUNCTION) func_t;
		
		/*STEP 3: verify the given parameters match the functions parameters.*/
		
		if(el == null)
		{
			if (func_tf.params != null)
			{
				System.out.format(">> ERROR [%d:%d] ID(%s) function(%s) is missing parameters.\n", nameLine, nameColumn, name);
				SYMBOL_TABLE.getInstance().printERROR(nameLine);
	            System.exit(0);
			}
		}
		else
		{
			if (func_tf.params == null)
			{
				System.out.format(">> ERROR [%d:%d] ID(%s) function(%s) should have no parameters.\n", nameLine, nameColumn, name);
				SYMBOL_TABLE.getInstance().printERROR(nameLine);
	            System.exit(0);
			}
			else
			{
				func_tf_iter = func_tf.params;

				for(AST_EXP_LIST el_iter = el; el_iter != null; el_iter = el_iter.tail){
					TYPE current_el_type = el_iter.head.SemantMe();
					calling_param= new TYPE_LIST(current_el_type,calling_param);
				}

				for(t_iter = calling_param; (t_iter != null) && (func_tf_iter != null) ; t_iter = t_iter.tail, func_tf_iter = func_tf_iter.tail)
				{
					TYPE current_t_type = t_iter.head;
					TYPE current_param = func_tf_iter.head;


					if(current_t_type != current_param)
					{
						System.out.format(">> ERROR [%d:%d] function has unmatching types .\n",
								nameLine, nameColumn);
								SYMBOL_TABLE.getInstance().printERROR(nameLine);
			            System.exit(0);
					}


				}
				if(!(t_iter == null && func_tf_iter == null))
				{
					System.out.format(">> ERROR [%d:%d] function has unmatching types \n", nameLine, nameLine);
					SYMBOL_TABLE.getInstance().printERROR(nameLine);
					System.exit(0);
				}
			}
			
		}
		return null;		
	}
}
