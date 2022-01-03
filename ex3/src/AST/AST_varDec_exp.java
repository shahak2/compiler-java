package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_varDec_exp extends AST_varDec
{
    public String name;
    public AST_type type;
    public AST_exp exp;

    public int nameLine;
    public int nameColumn;
    public int typeLine;
    public int typeColumn;
    public int expLine;
    public int expColumn;

    /******************/
    /* CONSTRUCTOR(S) */

    /******************/
    public AST_varDec_exp(AST_type type, int typeLine, int typeColumn, String name, int nameLine, int nameColumn, AST_exp exp, int expLine, int expColumn)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        System.out.format("====================== varDec -> type ID( %s ) ASSIGN exp; \n", name);

        /*******************************/
        /* COPY INPUT DATA MEMBERS ... */
        /*******************************/
        this.name = name;
        this.type = type;
        this.exp = exp;

        this.typeLine = typeLine;
        this.typeColumn = typeColumn;
        this.nameLine = nameLine;
        this.nameColumn = nameColumn;
        this.expLine = expLine;
        this.expColumn = expColumn;
    }

    /**************************************************/
    /* The printing message for a simple var AST node */

    /**************************************************/
    public void PrintMe() {
        /**********************************/
        /* AST NODE TYPE = AST SIMPLE VAR */
        /**********************************/
        System.out.format("AST NODE varDec exp( %s )\n", name);

        if (type != null) type.PrintMe();
        if (exp != null) exp.PrintMe();

        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("ASSIGN\n(%s) := right\n", name));

        if (type != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
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
    
    public TYPE CFIELD_handler(TYPE_CFIELD tcfl)
	{
		if(tcfl.type.isFunction() == true)
		{
			TYPE_FUNCTION tcfl_tf = (TYPE_FUNCTION) tcfl.type;
			return tcfl_tf.returnType;
		}
		
		TYPE t = tcfl.type;
		TYPE_VAR  tv = (TYPE_VAR) t;
		
		return tv.varType;

	}
    
    public TYPE SemantMe()
    {
        TYPE t;

        t = SYMBOL_TABLE.getInstance().find(type.name);
        if(t == null)
        {
            System.out.format(">> ERROR [%d:%d] non existing type %s\n", typeLine, typeColumn,type.name);
            SYMBOL_TABLE.getInstance().printERROR(typeLine);
            System.exit(0);
        }
        
        if(t.name.equals("void"))
        {
            System.out.format(">> ERROR [%d:%d] illegal to declare a void type\n", typeLine, typeColumn);
            SYMBOL_TABLE.getInstance().printERROR(typeLine);
            System.exit(0);
        }

        if(SYMBOL_TABLE.getInstance().find_in_curr_scope(name) != null)
        {
            System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n", nameLine, nameColumn,name);
            SYMBOL_TABLE.getInstance().printERROR(nameLine);
            System.exit(0);
        }
        
        TYPE exp_t = exp.SemantMe();
        
        if(exp_t != t)
        {
        	if(t.isClass() == true)
        	{
        		if(! (exp_t == TYPE_NIL.getInstance() ))
    			{
        			if( ! (t.name.equals(exp_t.name) == true || 
    						compareClasses((TYPE_CLASS) exp_t, (TYPE_CLASS) t) == true ))
    				{
        				System.out.format(">> ERROR [%d:%d] type mismatch for %s := %s\n", typeLine, typeColumn, t.name, exp_t.name);
                        SYMBOL_TABLE.getInstance().printERROR(typeLine);
    					System.exit(0);		
    				}	
    			}
        	}
        	else
        	{
        		if(t.isArray() == true)
    			{
    				if(!( t.name.equals(exp_t.name) || exp_t == TYPE_NIL.getInstance()  ))
    				{
    					System.out.format(">> ERROR [%d:%d] type mismatch for %s := %s\n", typeLine, typeColumn, t.name, exp_t.name);
                        SYMBOL_TABLE.getInstance().printERROR(typeLine);
    					System.exit(0);
    				}
    			}
        		else
        		{
        			System.out.format(">> ERROR [%d:%d] type mismatch for %s := %s\n", typeLine, typeColumn, t.name, exp_t.name);
                    SYMBOL_TABLE.getInstance().printERROR(typeLine);
                    System.exit(0);
        		}
        	}
        }

        SYMBOL_TABLE.getInstance().enter(name, t);
        TYPE_VAR var= new TYPE_VAR(name,t);
        return var;
    }

}
