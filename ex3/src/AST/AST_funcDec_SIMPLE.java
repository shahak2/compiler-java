package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_funcDec_SIMPLE extends AST_funcDec
{
    public AST_type type;
    public String name;
    public AST_STMT_LIST stmtList;

    public int typeLine;
    public int typeColumn;
    public int nameLine;
    public int nameColumn;

	public int lineResult;
    public int columnResult;

    public AST_funcDec_SIMPLE(AST_type type, int typeLine, int typeColumn, String name, int nameLine, int nameColumn, AST_STMT_LIST stmtList) 
    {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== funcDec -> type ID( %s ) {stmtList}; \n", name);

        this.type = type;
        this.name = name;
        this.stmtList = stmtList;

        this.typeLine = typeLine;
        this.typeColumn = typeColumn;
        this.nameLine = nameLine;
        this.nameColumn = nameColumn;

		this.lineResult = -1;
		this.columnResult = -1;
    }

    public void PrintMe() 
    {
        System.out.print("AST funcDec SIMPLE\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (type != null) type.PrintMe();
		if (stmtList != null) stmtList.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
                String.format("FUNC\nDEC\nSIMPLE\n(%s)",name));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		if (stmtList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,stmtList.SerialNumber);
	}
	public boolean isLegalOverride(TYPE_FUNCTION f1, TYPE_FUNCTION f2)
		/*
		 * This function returns true if function f1 may override function f2.
		 */
	{
		TYPE_LIST  classParams = f1.params;
		TYPE_LIST  parentParams = f2.params;

		while(classParams!= null && parentParams!= null)
		{
			if(!(classParams.head.name.equals( parentParams.head.name)))
				return false;
			parentParams=parentParams.tail;
			classParams=classParams.tail;
		}
		if (!(parentParams == null && classParams==null))
			return false;
		if(!(f1.returnType.name.equals(f2.returnType.name)))
			return false;

		return true;
	}
    
    public boolean compareClasses(TYPE_CLASS t1, TYPE_CLASS t2)
	/* This function checks if t2 is an instance of t1 or its ancestors */
	{
		for ( TYPE_CLASS it = t1.father; it != null; it = it.father) 
		{
			System.out.println(t1.name);
			System.out.println(t2.name);
			if (it.name.equals(t2.name))
			{
				return true;
			}
		}
		return false;
	}
    
    public boolean Semant_body(AST_STMT_LIST body, TYPE returnType)
    /*
     * INPUT: the stmtList(body) of a function and its return type.
     * OUTPUT: The function semants the body, returns true of the returnType matches
     * and false otherwise.
     */
    {
    	for(AST_STMT_LIST stmt_iter = stmtList; stmt_iter != null; stmt_iter = stmt_iter.tail)
        {
			lineResult = stmt_iter.headLine;
			columnResult = stmt_iter.headColumn;
    		TYPE stmt_return_type = stmt_iter.head.SemantMe();
    		if(stmt_return_type == null || stmt_return_type.isReturn() == false)
    			continue;
    		
    		if(stmt_return_type.name.equals(returnType.name) == false)
    		{	
				TYPE_RETURN tr_checked = (TYPE_RETURN) stmt_return_type;

				
    			if(returnType.isArray() == true)
    			{
    				if(tr_checked.returnType == TYPE_NIL.getInstance())
    					return true;
					else{
						if(tr_checked.returnType.isArray()==true){
							TYPE_ARRAY r_t_array=(TYPE_ARRAY) returnType;
							TYPE_ARRAY stmt_array=(TYPE_ARRAY) tr_checked.returnType;
							if(r_t_array.arrayType.isClass() == true) {
								if (stmt_array.arrayType.isClass() == true) {
									if (compareClasses((TYPE_CLASS) stmt_array.arrayType, (TYPE_CLASS) r_t_array.arrayType) == false) {
										return false;
									}
								}
							}
						}
						else{
							return false;
						}



					}
    			}
    			else
    			{	
    				if(returnType.isClass() == true)
    				{
    					if(stmt_return_type != TYPE_NIL.getInstance())
    					{

							if(compareClasses((TYPE_CLASS)stmt_return_type, (TYPE_CLASS)returnType) == false)
    						{
    							return false;
    						}
    					}
    					
    				}
    				else
    				{
    					return false;
    				}
    			}
    		}
        }
    	return true;
    }
    
    public TYPE SemantMe()
    {
        TYPE returnType = null;
        TYPE_LIST type_list = null;

		TYPE_CFIELD funcCfield=null;
		TYPE_FUNCTION funcFather=null;

		returnType = SYMBOL_TABLE.getInstance().find(type.name);

		if (returnType == null)
		{
			System.out.format(">> ERROR [%d:%d] non existing return type %s\n", typeLine, typeColumn, type.name);
			SYMBOL_TABLE.getInstance().printERROR(typeLine);
			System.exit(0);
		}

		TYPE funcType = SYMBOL_TABLE.getInstance().find(name);

		if (funcType != null)
		{
			if(funcType.isCFIELD()){
				funcCfield=(TYPE_CFIELD)funcType;
				if(funcCfield.type.isFunction()){
					funcFather=(TYPE_FUNCTION)funcCfield.type;
					if(!(funcFather.returnType.name.equals(returnType.name))){
						System.out.format(">> ERROR [%d:%d] illegal method overriding \n", typeLine , typeColumn);
						SYMBOL_TABLE.getInstance().printERROR(typeLine);
						System.exit(0);
					}

				}
				else{
					System.out.format(">> ERROR [%d:%d] not a Function  %s\n", nameLine, nameColumn, name);
					SYMBOL_TABLE.getInstance().printERROR(nameLine);
					System.exit(0);
				}

			}
			else {
				System.out.format(">> ERROR [%d:%d] Function name exists %s\n", nameLine, nameColumn, name);
				SYMBOL_TABLE.getInstance().printERROR(nameLine);
				System.exit(0);
			}
		}


        
        SYMBOL_TABLE.getInstance().beginScope();
        
        SYMBOL_TABLE.getInstance().enter(name,new TYPE_FUNCTION(returnType,name, null));
        
        if(Semant_body(stmtList, returnType) == false)
        {
        	System.out.format(">> ERROR [%d:%d] function(%s) should return(%s)\n",lineResult, columnResult ,name, returnType.name);
			SYMBOL_TABLE.getInstance().printERROR(lineResult);
            System.exit(0);
        }

        SYMBOL_TABLE.getInstance().endScope();

		TYPE_FUNCTION resultingFunc = new TYPE_FUNCTION(returnType,name,type_list);
        SYMBOL_TABLE.getInstance().enter(name, resultingFunc);

        return resultingFunc;
    }
}



