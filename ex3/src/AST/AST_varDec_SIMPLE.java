package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_varDec_SIMPLE extends AST_varDec 
{

    public String name;
    public AST_type type;

    public int nameLine;
    public int nameColumn;
    public int typeLine;
    public int typeColumn;


    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_varDec_SIMPLE(AST_type type, int typeLine, int typeColumn, String name, int nameLine, int nameColumn) {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== varDec -> type ID( %s ); \n", name);

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.name = name;
        this.type = type;

        this.typeLine = typeLine;
        this.typeColumn = typeColumn;
        this.nameLine = nameLine;
        this.nameColumn = nameColumn;
    }

    /**************************************************/
    /* The printing message for a simple var AST node */

    /**************************************************/
    public void PrintMe() {
        /**********************************/
        /* AST NODE TYPE = AST SIMPLE VAR */
        /**********************************/
        System.out.format("AST NODE VAR DEC SIMPLE\n");

        if (type != null) type.PrintMe();

        System.out.format("ID NAME( %s )\n",name);


        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("type ID(%s)", name));

        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);

    }

    public TYPE_VAR SemantMe()
    {
        TYPE t;
        t = type.SemantMe();
        //System.out.println(t.name);
        if (t == null)
        {
            System.out.format(">> ERROR [%d:%d] non existing type\n", typeLine, typeColumn);
            SYMBOL_TABLE.getInstance().printERROR(typeLine);
            System.exit(0);
        }
        if (SYMBOL_TABLE.getInstance().find_in_curr_scope(name) != null)
        {
            System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n",nameLine, nameColumn,name);
            SYMBOL_TABLE.getInstance().printERROR(nameLine);
            System.exit(0);
        }

        SYMBOL_TABLE.getInstance().enter(name,t);
        TYPE_VAR var= new TYPE_VAR(name,t);


        return var;
    }
}
