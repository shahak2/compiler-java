package AST;

public class AST_funcDec_NESTED extends AST_funcDec 
{

    public AST_type type;
    public String name;
    public AST_typeID_LIST typeIDList;
    public AST_STMT_LIST stmtList;

    public AST_funcDec_NESTED(AST_type type, String name, AST_typeID_LIST typeIDList, AST_STMT_LIST stmtList) {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== funcDec -> type ID( %s ) (typeIDLlist) {stmtList}; \n", name);

        this.type = type;
        this.name = name;
        this.stmtList = stmtList;
        this.typeIDList = typeIDList;
    }


    public void PrintMe() {
        
        System.out.print("AST funcDec NESTED\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (type != null) type.PrintMe();
        System.out.format(" ID FIELD( %s )\n",name);
		if (typeIDList != null) typeIDList.PrintMe();
		if (stmtList != null) stmtList.PrintMe();
        

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
                String.format("FUNC\nDEC\nNESTED\n(%s)",name));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
		if (typeIDList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,typeIDList.SerialNumber);
		if (stmtList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,stmtList.SerialNumber);
	}
    
    
}
