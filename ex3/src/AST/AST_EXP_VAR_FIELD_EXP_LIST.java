package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_VAR_FIELD_EXP_LIST extends AST_exp
{
	public AST_VAR var;
	public String fieldName;
	public AST_EXP_LIST el;

	public int varLine;
	public int varColumn;
	public int fieldNameLine;
	public int fieldNameColumn;



	public AST_EXP_VAR_FIELD_EXP_LIST(AST_VAR var, int varLine, int varColumn, String fieldName, int fieldNameLine, int fieldNameColumn, AST_EXP_LIST el)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		
		if(el == null) System.out.format("====================== exp -> var. ID(%s) ( )\n", fieldName);
		if(el != null) System.out.format("====================== exp -> var . ID(%s) (exps)\n", fieldName);
		
		this.var = var;
		this.fieldName = fieldName;
		this.el = el;

		this.varLine = varLine;
		this.varColumn = varColumn;
		this.fieldNameLine = fieldNameLine;
		this.fieldNameColumn = fieldNameColumn;
	}
	
	public void PrintMe()
	{
		System.out.print("AST NODE EXP VAR FIELD EXP LIST\n");
		
		if (var != null) var.PrintMe();
		System.out.format("FIELD NAME( %s )\n",fieldName);
		if(el != null) el.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				String.format("EXP\nVAR\nFIELD\n...->%s.(exps)",fieldName));
			
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (el != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,el.SerialNumber);
			
	}
	
	public TYPE SemantMe()
	{
		TYPE typeVar = null;
		TYPE_CLASS typeVar_tc = null;
		TYPE_FUNCTION typeFunc = null;
		TYPE_LIST func_iter = null;
		TYPE_LIST calling_params = null;
		TYPE_LIST calling_params_iter = null;
		
		if (var != null) typeVar = var.SemantMe();
	
		/* typeVar could be a cField */
		if (typeVar.isCFIELD()) {
			TYPE_CFIELD typeVarChecked = (TYPE_CFIELD) typeVar;
			/* cField type may be TYPE_VAR */
			/* if so remember we need the name TYPE_VAR.varType*/
			if(typeVarChecked.type.isVar()) {
				TYPE_VAR temp = (TYPE_VAR) typeVarChecked.type;
				typeVar = temp.varType;
			}
		}

		if (typeVar.isVar()) {
			TYPE_VAR typeVarChecked = (TYPE_VAR) typeVar;
			typeVar = typeVarChecked.varType;
		}
	
		if(typeVar.isClass() == false)
		{
			typeVar_tc = null;
			System.out.format(">> ERROR [%d:%d] %s is not a class\n", varLine, varColumn, var.name);
			SYMBOL_TABLE.getInstance().printERROR(varLine);
			System.exit(0);
		}
		else
		{
			typeVar_tc = (TYPE_CLASS) typeVar;
		}
		
		/* STEP 2: finding the function in class var */ 
		
		for (TYPE_CFIELD_LIST it=typeVar_tc.data_members; it != null; it=it.tail)
		{	
			if (it.head.name.equals(fieldName)) 
			{
				if(it.head.type.isFunction() == false)
				{
					System.out.format(">> ERROR [%d:%d] class %s had no %s function\n",varLine, varColumn, var.name, it.head.name);
					SYMBOL_TABLE.getInstance().printERROR(varLine);
					System.exit(0);
				}
				else /* STEP 3: making sure the function has the correct parameters. */
				{
					typeFunc = (TYPE_FUNCTION) it.head.type;
					if(typeFunc.params == null)
					{
						if(el == null)
						{
							return typeFunc.returnType;
						}
						else
						{
							System.out.format(">> ERROR [%d:%d] %s.%s method should get no parameters. \n", varLine, varColumn, var.name, it.head.name);
							SYMBOL_TABLE.getInstance().printERROR(varLine);
							System.exit(0);
						}
					}
					else
					{
						if(el == null)
						{
							System.out.format(">> ERROR [%d:%d] %s.%s method is missing parameters. \n", varLine, varColumn, var.name, it.head.name);
							SYMBOL_TABLE.getInstance().printERROR(varLine);
							System.exit(0);
						}
						else
						{
							func_iter = typeFunc.params;
							calling_params = el.SemantMe();
							TYPE_LIST rev_calling_params=null;
							for(TYPE_LIST t = calling_params; t != null; t = t.tail){
								rev_calling_params= new TYPE_LIST(t.head,rev_calling_params);
							}
							calling_params_iter = rev_calling_params;
							while(calling_params_iter != null && func_iter != null)
							{
								TYPE t = calling_params_iter.head;
								if( ! (t.name.equals(func_iter.head.name) ) )
								{
									System.out.format(">> ERROR [%d:%d] method %s.%s gets %s instead of %s.\n",varLine, varColumn, var.name,
											it.head.name, t.name, func_iter.head.name);
											SYMBOL_TABLE.getInstance().printERROR(varLine);
									System.exit(0);
								}
								func_iter = func_iter.tail;
								calling_params_iter = calling_params_iter.tail;
							}
							if(calling_params_iter == null && func_iter == null)
							{
								return typeFunc.returnType;
							}							
						}
					}
				}
			}

		}
		System.out.format(">> ERROR [%d:%d] class %s has no %s method\n", varLine,varColumn, var.name, fieldName);
		SYMBOL_TABLE.getInstance().printERROR(varLine);
		System.exit(0);
		return null; 	
	}
}
