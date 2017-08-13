/**
* C:\projects\amawareData\AMA-Markets\database\generateSQL\DATA_TRACK.java @ 2016-05-29 07:54:33.0
*/
package net.amaware.apps.dnatablerequest;
import net.amaware.autil.ACommDb;

import com.amaware.dna.query.LOGS;

import net.amaware.app.DataStoreReport;
/**
*  
*/
public class ULOGS extends LOGS {
//SqlApp
 private static final long serialVersionUID = 1L;
 final String thisClassName = this.getClass().getName();
 
 int outnum=0;
 int outnumlimit=999999;
/*
* 
*/
//
 public ULOGS(ACommDb iacomm, String ipropFileName, String[] iargs) {
	  super(iacomm, ipropFileName, iargs); //always
	  
	  //setProcessRptMode(ProcessRptMode.End);// DEFAULT: end report block
	  //setProcessRptMode(ProcessRptMode.Continue); // Continue report output to same block

 } 
/*
* Override to change ORDER BY columns, generated using primary key columns...
*
*/
 @Override
 public String getQueryStatementOrderBy() {
//
   return " ORDER BY create_ts desc "
   ;
 } //End getQueryStatementOrderBy
//
 
 @Override
public boolean doProcessRowFound(DataStoreReport dsr) {

     ++outnum;
     if (outnum<=outnumlimit){	 
	     reportRowOut(dsr,"");
	     
         if (outnum==outnumlimit) {
        	 //dsr.rptOutLine(acomm, "Requested Max rows reached for{"+getInWhereColValString(acomm)+"}",dsr.htmlLineWarningStyle);
         }
     }
     
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
	          _arpt.addDataRowAppendLineRowCols("create_ts", getCreateTs());
	          _arpt.addDataRowAppendLineRowCols("entry_type", getEntryType());
	          _arpt.addDataRowAppendLineRowCols("entry_subject", getEntrySubject());
	          _arpt.addDataRowAppendLineRowCols("entry_topic", getEntryTopic());
	          _arpt.addDataRowAppendLineRowCols("entry_msg", getEntryMsg());
	          _arpt.addDataRowAppendLineRowCols("user_name", getUserName());
	          _arpt.addDataRowAppendLineRowCols("user_email", getUserEmail());
	          _arpt.addDataRowAppendLineRowCols("user_ip", getUserIp());
	
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
