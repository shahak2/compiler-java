package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_funcDec_NESTED extends AST_funcDec 
{

    public AST_type type;
    public String name;
    public AST_typeID_LIST typeIDList;
    public AST_STMT_LIST stmtList;

    public int typeLine;
    public int typeColumn;
    public int nameLine;
    public int nameColumn;

    public int lineResult;
    public int columnResult;
    

    public AST_funcDec_NESTED(AST_type type, int typeLine, int typeColumn, String name, int nameLine, int nameColumn, AST_typeID_LIST typeIDList, AST_STMT_LIST stmtList) {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== funcDec -> type ID( %s ) (typeIDLlist) {stmtList}; \n", name);

        this.type = type;
        this.name = name;
        this.stmtList = stmtList;
        this.typeIDList = typeIDList;
        
        this.typeLine = typeLine;
        this.typeColumn = typeColumn;

        this.lineResult = -1;
        this.columnResult = -1;

    }


    public void PrintMe() 
    {
        
        System.out.print("AST funcDec NESTED\n");

		if (type != null) type.PrintMe();
        System.out.format(" ID FIELD( %s )\n",name);
		if (typeIDList != null) typeIDList.PrintMe();
		if (stmtList != null) stmtList.PrintMe();
        
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
                String.format("FUNC\nDEC\nNESTED\n(%s)",name));
		

		if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		if (typeIDList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,typeIDList.SerialNumber);
		if (stmtList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,stmtList.SerialNumber);
	}
    
    public boolean compareClasses(TYPE_CLASS t1, TYPE_CLASS t2)
	/* This function checks if t2 is an instance of t1 or its ancestors */
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
    public void printList(TYPE_LIST t_l)
        /*
         * for debug purposes
         */
    {
        for (TYPE_LIST tl_iter = t_l; tl_iter != null ; tl_iter = tl_iter.tail)
        {
            System.out.println(tl_iter.head.name);
        }
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
        TYPE funcType = null;
        TYPE_LIST type_list = null;
        TYPE_CFIELD funcCfield=null;
        TYPE_FUNCTION funcFather=null;
        TYPE parType;
        TYPE parName;

        returnType = SYMBOL_TABLE.getInstance().find(type.name);
        if (returnType == null /*&& returnType.name != "void"*/)
        {
            System.out.format(">> ERROR [%d:%d] non existing return type %s\n", typeLine, typeColumn,type.name);
            SYMBOL_TABLE.getInstance().printERROR(typeLine);
            System.exit(0);
        }

        /* Check if function name already exists in scope*/
        funcType = SYMBOL_TABLE.getInstance().find(name);

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
                    SYMBOL_TABLE.getInstance().beginScope();
                    for (AST_typeID_LIST it = typeIDList; it  != null; it = it.tail)
                    {
                        parType = SYMBOL_TABLE.getInstance().find(it.typeID.type.name);
                        parName = SYMBOL_TABLE.getInstance().find_in_curr_scope(it.typeID.name);

                        if (parType != null)
                        {
                            if (parName == null)
                            {
                                type_list = new TYPE_LIST(parType, type_list);
                                SYMBOL_TABLE.getInstance().enter(it.typeID.name ,parType);

                            }
                            else
                            {
                                System.out.format(">> ERROR [%d:%d] parameter name already exists %s\n", it.typeID.nameLine , it.typeID.nameColumn , parName);
                                SYMBOL_TABLE.getInstance().printERROR(it.typeID.nameLine);
                                System.exit(0);
                            }
                        }
                        else
                        {
                            System.out.format(">> ERROR [%d:%d] parameter type does not exist %s\n", it.typeID.type.nameLine, it.typeID.type.nameColumn, parType);
                            SYMBOL_TABLE.getInstance().printERROR(it.typeID.type.nameLine);
                            System.exit(0);
                        }
                    }
                    TYPE_LIST father_list= funcFather.params;
                    TYPE_LIST son_list= type_list;
                    while(father_list!= null && son_list!= null)
                    {
                        if(!(son_list.head.name.equals( father_list.head.name)))
                        {
                            System.out.format(">> ERROR [%d:%d] parameter type does not match, override %s\n", typeLine,typeColumn, son_list.head.name);
                            SYMBOL_TABLE.getInstance().printERROR(typeLine);
                            System.exit(0);
                        }
                        father_list=father_list.tail;
                        son_list=son_list.tail;
                    }
                    if (!(father_list == null && son_list==null))
                    {
                        System.out.format(">> ERROR [%d:%d] parameters length does not match, override \n", typeLine,typeColumn);
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
        else{
            SYMBOL_TABLE.getInstance().beginScope();
            for (AST_typeID_LIST it = typeIDList; it  != null; it = it.tail)
            {
                parType = SYMBOL_TABLE.getInstance().find(it.typeID.type.name);
                parName = SYMBOL_TABLE.getInstance().find_in_curr_scope(it.typeID.name);

                if (parType != null)
                {
                    if (parName == null)
                    {
                        type_list = new TYPE_LIST(parType, type_list);
                        SYMBOL_TABLE.getInstance().enter(it.typeID.name ,parType);
                    }
                    else
                    {
                        System.out.format(">> ERROR [%d:%d] parameter name already exists %s\n", it.typeID.nameLine , it.typeID.nameColumn , parName);
                        SYMBOL_TABLE.getInstance().printERROR(it.typeID.nameLine);
                        System.exit(0);
                    }
                }
                else
                {
                    System.out.format(">> ERROR [%d:%d] parameter type does not exist %s\n", it.typeID.type.nameLine, it.typeID.type.nameColumn, parType);
                    SYMBOL_TABLE.getInstance().printERROR(it.typeID.type.nameLine);
                    System.exit(0);
                }
            }

        }



        TYPE_FUNCTION res =new TYPE_FUNCTION(returnType,name,type_list);
        
        SYMBOL_TABLE.getInstance().enter(name,res);



        if(Semant_body(stmtList, returnType) == false)
        {
        	System.out.format(">> ERROR [%d:%d] function should return %s\n",lineResult, columnResult ,returnType.name);
            SYMBOL_TABLE.getInstance().printERROR(lineResult);
            System.exit(0);
        }

    
        SYMBOL_TABLE.getInstance().endScope();

        SYMBOL_TABLE.getInstance().enter(name,res);

        return res;

    }
    
}
