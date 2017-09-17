/**
* 
*/
package net.amaware.apps.dnatablerequest;
import net.amaware.autil.ACommDb;
import com.amaware.dna.query.TABLE_CODES;

import net.amaware.app.DataStoreReport;
/**
*  
*/
public class UTABLE_CODES_ULOGS extends TABLE_CODES {
//SqlApp
 private static final long serialVersionUID = 1L;
 final String thisClassName = this.getClass().getName();
 //
 public String SqlStmtWhere=""; 
 // //
 ULOGS aDnaULOGS= null;
 ULOGS aAmawareLOGS= null;
 //
 
/*
* 
*/
 
//
 public UTABLE_CODES_ULOGS(ACommDb iacomm, String ipropFileName, String[] iargs) {
	  super(iacomm, ipropFileName, iargs);
	  
	  //setProcessRptMode(ProcessRptMode.End);// DEFAULT: end report block
	  //setProcessRptMode(ProcessRptMode.Continue); // Continue report output to same block

	  aDnaULOGS= new ULOGS(iacomm, "MainDbDnaLOGS.properties", iargs);
	  aAmawareLOGS= new ULOGS(iacomm, "MainDbAmawareLOGS.properties", iargs);
	  
 } 
/*
* Override to change ORDER BY columns, generated using primary key columns...
*
*/
 /*
 * Required IF SQL not supplied 
 *       in doProcessResult, doProcessStartResult
 *  
 */
 public String doQueryStatement(ACommDb acomm) {
	 
	 
	 return "Select "
             + "tab_name "
             + ",code_name "
             + ",code_value "
//             + ",user_mod_id "
           //  + ",user_mod_ts "				
				
             + " FROM table_codes "
			 +  SqlStmtWhere
				//+ " WHERE tab_name = 'logs' "
	   
	   + "   AND  user_mod_ts > '2015-03-29 22:26:02.0'"
	 
	 //, getQueryStatementOrderBy()
	   + " ORDER BY tab_name ASC "
	 
	 
	  //Not valid for MySQL => + "  WITH UR  "
	  + " ; "
	  
	   ;
	//
	 } //End doQueryStatement
  
//
 
 @Override
public boolean doProcessRowFound(DataStoreReport dsr) {
	 
//	 if (getCodeValue().contentEquals("email")) {
//		 dsr.rptOutLine(acomm, this.getClass().getName() 
//				       + "...Report Row being bypassed for{" + getCodeValue() +  "}" ,dsr.htmlLineWarningStyle);
//	 } else {
		 
		 acomm.addPageMsgsLineOut(thisClassName+ " Row Found for"+this.getInWhereColValString(acomm));		 
		 
		 appReportGroupLevel=1;
		 

	     

	     
	     if (isDataColResultValueFound(TAB_NAME,"logs")) {
	    	 
		     reportRowOutParent(dsr,"background-color:white;color:green;");
			 //   
			 aDnaULOGS.setEntryType(getDataColResultValue(CODE_VALUE));
			 aDnaULOGS.doProcessResult(dsr, 100);
             //			    
			// aAmawareLOGS.setEntryType(getCodeValue());
 			//aAmawareLOGS.doProcessResult(dsr, 25);
			  aAmawareLOGS.doProcessResult("select * from logs "
				                          + " WHERE entry_type = " +"'"+getDataColResultValue(CODE_VALUE)+"'" 
					                      + "   AND (create_ts >= '2015-09-09 10:00:43.0'"
					                      + "        OR entry_subject LIKE " + "'%dnalady%')"
					                      + " ORDER BY create_ts DESC "
					                      ,dsr, 25);
		 	    
	     } else {
			 dsr.rptOutLine(acomm, this.getClass().getName()
					   + " for{" + acomm.getDbUrlDbAndSchemaName() +  "}"
					   + "...Where{" 
					   + this.getInWhereColValString(acomm) +  "}"
				       + "...Unknown table name{" 
					   + getDataColResultValue(TAB_NAME) +  "}"
					   ,dsr.htmlLineErrorStyle);
	     }
         //*/
	     
//	 }

	 return true;
	     
 }
 
 @Override
public void doProcessRowNotFound(DataStoreReport dsr) {
	  dsr.rptOutLine(acomm, this.getClass().getName()
			         + " for{" + acomm.getDbUrlDbAndSchemaName() +  "}"
			         + "...Rows NOT Found for" 
			         + this.getInWhereColValString(acomm)
			         ,dsr.htmlLineErrorStyle);
 }
 
 /*
 * Overload to change order of output fields...or remove...or add new ones.
 */
  @Override
public void reportRowOutCols(DataStoreReport _arpt) {
     //
        reportRowOutColumn( _arpt, TAB_NAME);
        reportRowOutColumn( _arpt, CODE_NAME);
        reportRowOutColumn( _arpt, CODE_VALUE);
        reportRowOutColumn( _arpt, USER_MOD_ID);
        reportRowOutColumn( _arpt, USER_MOD_TS);
        //
        reportRowOutColumn( _arpt, "Selection", getInWhereColValString(acomm));
        //        
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
