package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_newExp_EXP extends AST_newExp
{
    public AST_type type;
    public AST_exp exp;

    public int typeLine;
    public int typeColumn;
    public int expLine;
    public int expColumn;

    public AST_newExp_EXP(AST_type t, int typeLine, int typeColumn, AST_exp e, int expLine, int expColumn){
        SerialNumber = AST_Node_Serial_Number.getFresh();

        System.out.print("====================== newExp -> NEW type [exp] \n");

        this.type = t;
        this.exp = e;

        this.typeLine = typeLine;
        this.typeColumn = typeColumn;
        this.expLine = expLine;
        this.expColumn = expColumn;

    }
    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = AST FIELD VAR */
        /*********************************/
        System.out.print("AST NODE newExp EXP\n");

        /**********************************************/
        /* RECURSIVELY PRINT VAR, then FIELD NAME ... */
        /**********************************************/
        if (type != null) type.PrintMe();
        if (exp != null) exp.PrintMe();


        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "newExp\nEXP\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
    }

    public boolean isAllowed(TYPE t, String name) {
        
        /* name must be directly "int", "string" */
        if(!(name == TYPE_INT.getInstance().name || name == TYPE_STRING.getInstance().name)) {
            if (!(t.isArray() || t.isClass()  || t.isFunction())){
                return false;
            }
            else {
                return true;
            }
        }
        return true;
    }
    
    public TYPE SemantMe()
    {    	
    	TYPE t = type.SemantMe();
        if (t == null) {
            System.out.format(">> ERROR [%d:%d] type (%s) does not exist.\n", typeLine, typeColumn, type.name);
            SYMBOL_TABLE.getInstance().printERROR(typeLine);
            System.exit(0);
        }
        if(!(isAllowed(t, type.name))) {
            System.out.format(">> ERROR [%d:%d] array cannot intialized to %s[?].\n", typeLine, typeColumn, type.name);
            SYMBOL_TABLE.getInstance().printERROR(typeLine);
            System.exit(0);
        }
        if (exp == null) {
            System.out.format(">> ERROR [%d:%d] array must be initialized with an integer value.\n", expLine, expColumn);
            SYMBOL_TABLE.getInstance().printERROR(expLine);
			System.exit(0);
        }
        if(exp.SemantMe() != TYPE_INT.getInstance())
    	{
    		System.out.format(">> ERROR [%d:%d] array must be initialized with an integer value.\n", expLine, expColumn);
            SYMBOL_TABLE.getInstance().printERROR(expLine);
			System.exit(0);
    	}
        if (exp.value <= 0) {
            System.out.format(">> ERROR [%d:%d] array must be initialized with a POSITIVE integer value.\n", expLine, expColumn);
            SYMBOL_TABLE.getInstance().printERROR(expLine);
			System.exit(0);
        }
        
    	return t;
    }

}
