/**
* C:\projects\amawareData\AMA-Markets\database\generateSQL\DATA_TRACK.java @ 2016-05-29 07:54:33.0
*/
package net.amaware.apps.dnatablerequest;
import net.amaware.autil.ACommDb;
import net.amaware.autil.AExceptionSql;
import net.amaware.aproc.SqlPsQueryProc;
import net.amaware.aproc.SqlPsApp.ProcessRptMode;

import com.amaware.dna.query.TABLE_CODES;

import net.amaware.app.DataStoreReport;
/**
*  
*/
public class UTABLE_CODES extends TABLE_CODES {
//SqlApp
 private static final long serialVersionUID = 1L;
 final String thisClassName = this.getClass().getName();
/*
* 
*/
//
 public UTABLE_CODES() {
	  super();
	  
	  //setProcessRptMode(ProcessRptMode.End);// DEFAULT: end report block
	  //setProcessRptMode(ProcessRptMode.Continue); // Continue report output to same block

 } 
/*
* Override to change ORDER BY columns, generated using primary key columns...
*
*/
 public String getQueryStatementOrderBy(ACommDb acomm) {
//
   return " ORDER BY tab_name "
   + "        , code_name "
   + "        , code_value "
   ;
   
   //return " ORDER BY id ";
   
 } //End getQueryStatementOrderBy
//
 
 public void doProcessRowFound(ACommDb acomm, DataStoreReport dsr) {
	 
	 if (getCodeValue().contentEquals("email")) {
		 dsr.rptOutLine(acomm, this.getClass().getName() 
				       + "...Report Row being bypassed for{" + getCodeValue() +  "}" ,dsr.htmlLineOkStyle);
	 } else {
	     reportRowOut(acomm, dsr,"");
	 }

 }
 
 public void doProcessRowNotFound(ACommDb acomm, DataStoreReport dsr) {
	  dsr.rptOutLine(acomm, this.getClass().getName() 
			         + "...Rows NOT Found for" + this.getInWhereColValString(acomm),dsr.htmlLineErrorStyle);
 }
 
 /*
 * Overload to change order of output fields...or remove...or add new ones.
 */
  public void reportRowOutCols(ACommDb acomm, DataStoreReport _arpt) {
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
