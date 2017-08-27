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
//
 
 @Override
public boolean doProcessRowFound(DataStoreReport dsr) {
	 
//	 if (getCodeValue().contentEquals("email")) {
//		 dsr.rptOutLine(acomm, this.getClass().getName() 
//				       + "...Report Row being bypassed for{" + getCodeValue() +  "}" ,dsr.htmlLineWarningStyle);
//	 } else {
		 
		 acomm.addPageMsgsLineOut(thisClassName+ " Row Found for"+this.getInWhereColValString(acomm));		 
		 
		 appReportGroupLevel=1;
		 

	     

	     
	     if (getTabName().contentEquals("logs")) {
	    	 
		     reportRowOutParent(dsr,"background-color:white;color:green;");
			 //   
			 aDnaULOGS.setEntryType(getCodeValue());
			 aDnaULOGS.doProcessResult(dsr, 100);
             //			    
			// aAmawareLOGS.setEntryType(getCodeValue());
 			//aAmawareLOGS.doProcessResult(dsr, 25);
			  aAmawareLOGS.doProcessResult("select * from logs "
				                          + " WHERE entry_type = " +"'"+getCodeValue()+"'" 
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
					   + getTabName() +  "}"
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
        //_arpt.addDataRowAppendLineRowCols("id", getId());
        _arpt.addDataRowAppendLineRowCols("tab_name", getTabName());
        _arpt.addDataRowAppendLineRowCols("code_name", getCodeName());
        _arpt.addDataRowAppendLineRowCols("code_value", getCodeValue());
        _arpt.addDataRowAppendLineRowCols("user_mod_id", getUserModId());
        _arpt.addDataRowAppendLineRowCols("user_mod_ts", getUserModTs());
        //
        _arpt.addDataRowAppendLineRowCols("Selection", getInWhereColValString(acomm));
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
