package AST;

public class AST_funcDec_SIMPLE extends AST_funcDec
{
    public AST_type type;
    public String name;
    public AST_STMT_LIST stmtList;

    public AST_funcDec_SIMPLE(AST_type type, String name, AST_STMT_LIST stmtList) {

        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.format("====================== funcDec -> type ID( %s ) {stmtList}; \n", name);

        this.type = type;
        this.name = name;
        this.stmtList = stmtList;
    }


    public void PrintMe() {
        
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
    

}



