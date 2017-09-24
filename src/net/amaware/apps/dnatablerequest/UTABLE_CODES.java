/**
* 
*/
package net.amaware.apps.dnatablerequest;
import net.amaware.autil.ACommDb;
import net.amaware.autil.ADataColResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.amaware.dna.query.TABLE_CODES;

import net.amaware.app.DataStoreReport;
/**
*  
*/
public class UTABLE_CODES extends TABLE_CODES {
//SqlApp
 private static final long serialVersionUID = 1L;
 final String thisClassName = this.getClass().getName();
 
 //
 ULOGS aDnaULOGS= null;
 ULOGS aAmawareLOGS= null;
 //
 
/*
* 
*/
 
//
 public UTABLE_CODES(ACommDb iacomm, String ipropFileName, String[] iargs) {
	  super(iacomm, ipropFileName, iargs);
	  
	  //setProcessRptMode(ProcessRptMode.End);// DEFAULT: end report block
	  //setProcessRptMode(ProcessRptMode.Continue); // Continue report output to same block

	  aDnaULOGS= new ULOGS(iacomm, "MainDbDnaLOGS.properties", iargs);
	  aAmawareLOGS= new ULOGS(iacomm, "MainDbAmawareLOGS.properties", iargs);
	  
 } 
 
 /*
 * Overload to change order of output fields...or remove...or add new ones.
 */
  //@Override
public void reportRowOutCols(DataStoreReport _arpt) {
     //
    //reportRowOutColumn( _arpt, ID);
    reportRowOutColumn( _arpt, TAB_NAME);
    reportRowOutColumn( _arpt, CODE_NAME);
    reportRowOutColumn( _arpt, CODE_VALUE);
    reportRowOutColumn( _arpt, USER_MOD_ID);
    reportRowOutColumn( _arpt, USER_MOD_TS);
    //
    reportRowOutColumn( _arpt, "Selection", getInWhereColValString(acomm));
    //
   // reportRowOutColumn( _arpt, "ColNames", getColNames('~'));
   // reportRowOutColumn( _arpt, "ColValues", getColValues('~'));
   // reportRowOutColumn( _arpt, "ColNameValues", getColNameValues('~'));
   reportRowOutColumn( _arpt, "MetaDataTableNames", getMetaDataTableNames());    
   //
  
	List<ADataColResult> outADataColResult = new ArrayList<ADataColResult>();
	 
	//using Resultset Columns
	/*
    for(Entry<String, ADataColResult> m:aADataColResultMap.entrySet()){  
    	if (m.getValue().isColSql() && !m.getValue().isAutoIncrement()) {
	    	outADataColResult.add(m.getValue());
    	}
    }
    */
	for(ADataColResult adcr: rsADataColResultList){	   
    	if (adcr.isColSql() && !adcr.isAutoIncrement()) {
	    	outADataColResult.add(adcr);
    	}
	}  	
    reportRowOutColumn( _arpt, "getInsert", getInsertStatement(acomm,rsTableName,outADataColResult));
    
    
    //Use autoincrement
    outADataColResult.clear();
    /*
    for(Entry<String, ADataColResult> m:aADataColResultMap.entrySet()){  
    	if (m.getValue().isColSql()) {
	    	outADataColResult.add(m.getValue());
    	}
    }
    */
	for(ADataColResult adcr: rsADataColResultList){	   
    	if (adcr.isColSql()) {
	    	outADataColResult.add(adcr);
    	}
	}  	
    reportRowOutColumn( _arpt, "getInsertWithAutoIncrement", getInsertStatement(acomm,rsTableName,outADataColResult));
    //
    //Use supplied columns for insert
    reportRowOutColumn( _arpt, "getInsertUserSupplied"
    		, getInsertStatement(acomm,rsTableName
    				,Arrays.asList(new ADataColResult(rsTableName,ID,getQueryRowColNameValue(acomm, ID),false)
    						      ,new ADataColResult(rsTableName,TAB_NAME,"userTAB_NAME",true)
    						      ,new ADataColResult(rsTableName,CODE_NAME,"userCODE_NAME",true)
    						      ,new ADataColResult(rsTableName,CODE_VALUE,"userCODE_VALUE",true)
    						      )
    				));    
    
    //
    //reportRowOutColumn( _arpt, "getDeleteStatementPK-this", getDeleteStatementPK(acomm));
    //reportRowOutColumn( _arpt, "getDeleteStatementPK", getDeleteStatementPK(acomm,"logs"));
   //
  } //End reportRowOutCols
 //

 //
//END
//
}
//
//DATA_TRACK Ended
//
