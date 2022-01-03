package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_arrayTypedef extends AST_Node
{

    /************************/
    /* simple variable name */
    /************************/
    public String name;
    public AST_type type;

    public int nameLine;
    public int nameColumn;
    public int typeLine;
    public int typeColumn;

    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_arrayTypedef(String name, int nameLine, int nameColumn, AST_type type, int typeLine, int typeColumn)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== arrayTypeDef -> ARRAY ID( %s ) = type[]; \n",name);

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.name = name;
        this.type = type;

        this.nameLine = nameLine;
        this.nameColumn = nameColumn;
        this.typeLine = typeLine;
        this.typeColumn = typeColumn;
    }

    /**************************************************/
    /* The printing message for a simple var AST node */
    /**************************************************/
    public void PrintMe()
    {
        /**********************************/
        /* AST NODE TYPE = AST SIMPLE VAR */
        /**********************************/
        System.out.format("AST NODE ARRAY TYPE DEF\n");

        System.out.format("ID NAME( %s )\n",name);

        if (type != null) type.PrintMe();


        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("ARRAY ID(%s) := type [];",name));

        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,type.SerialNumber);
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
        TYPE t = null;
        TYPE arrayType = null;

        t = SYMBOL_TABLE.getInstance().find(name);
        
        if(t != null)
        {
            System.out.format(">> ERROR [%d:%d] ID (%s) exists in scope.\n", nameLine, nameColumn, name);
            SYMBOL_TABLE.getInstance().printERROR(nameLine);
            System.exit(0);
        }

        arrayType = SYMBOL_TABLE.getInstance().find(type.name);
        if (arrayType == null) {
            System.out.format(">> ERROR [%d:%d] type (%s) does not exist.\n", typeLine, typeColumn, type.name);
            SYMBOL_TABLE.getInstance().printERROR(typeLine);
            System.exit(0);
        }
      
        if(!(isAllowed(arrayType, type.name))) {
            System.out.format(">> ERROR [%d:%d] array cannot be assigned to %s[].\n", typeLine, typeColumn, type.name);
            SYMBOL_TABLE.getInstance().printERROR(typeLine);
            System.exit(0);
        }
    
        TYPE_ARRAY typeArr = new TYPE_ARRAY(name, arrayType);

        SYMBOL_TABLE.getInstance().enter(name, typeArr);

        return null;
    }
}
