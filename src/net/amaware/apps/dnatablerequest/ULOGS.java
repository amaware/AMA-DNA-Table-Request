/**
* C:\projects\amawareData\AMA-Markets\database\generateSQL\DATA_TRACK.java @ 2016-05-29 07:54:33.0
*/
package net.amaware.apps.dnatablerequest;
import net.amaware.autil.ACommDb;
import net.amaware.autil.AException;
import net.amaware.autil.AExceptionSql;

import java.sql.SQLException;

import com.amaware.dna.query.LOGS;

import net.amaware.app.DataStoreReport;
/**
*  
*/
public class ULOGS extends LOGS {
//SqlApp
 private static final long serialVersionUID = 1L;
 final String thisClassName = this.getClass().getName();

 String entryType=""; 
 
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
 * Required IF SQL not supplied 
 *       in doProcessResult, doProcessStartResult
 *  
 */
 public String doQueryStatement(ACommDb acomm) {
	 
	 
	 return doQueryStatement(acomm
	   , "SELECT " 
	   + "    id "
	   + "    ,create_ts "
	   + "    ,entry_type "
	   + "    ,entry_subject "
	   + "    ,entry_topic "
	   + "    ,entry_msg "
	   + "    ,user_name "
	   + "    ,user_email "
	   //+ "    ,user_ip "
	   + " FROM logs "
	//
	 
	   //+ " WHERE  entry_type = 'access'"
       + " WHERE  entry_type = " +"'"+getEntryType()+"'" 
	   
	   + "   AND  create_ts > '2015-03-29 22:26:02.0'"
	 
	 //, getQueryStatementOrderBy()
	 , "ORDER BY create_ts DESC "
	 
	 
	  //Not valid for MySQL => + "  WITH UR  "
	   + " ; "
	    );
	//
	 } //End doQueryStatement
 
/*
* Override to change ORDER BY columns, generated using primary key columns...
*
*/
/// @Override
 public String getQueryStatementOrderByxxx() {
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
      reportRowOutColumn( _arpt, id);
      reportRowOutColumn( _arpt, create_ts);
      reportRowOutColumn( _arpt, entry_type);
      reportRowOutColumn( _arpt, entry_subject);
      reportRowOutColumn( _arpt, entry_topic);
      reportRowOutColumn( _arpt, entry_msg);
      reportRowOutColumn( _arpt, user_name);
      reportRowOutColumn( _arpt, user_email);
      reportRowOutColumn( _arpt, user_ip);	          

      reportRowOutColumn( _arpt, "Selection", getInWhereColValString(acomm));
      
	  try {
	          //reportRowOutColumn( _arpt, "MetaDataTableNames", getMetaDataTableNames());
	          //reportRowOutColumn( _arpt, "getInsertStatement", getInsertStatement(acomm));
	          //
                 reportRowOutColumn( _arpt, "getDeleteStatementPK", getDeleteStatementPK(acomm));
	          
		} catch (AException e) {
				reportRowOutColumn( _arpt, "getDeleteStatementPK", "AException{"+e.getMessage()+"}");
					// TODO Auto-generated catch block
					//throw new AExceptionSql(parentAcomm, e); 
		}
      
      
      //
	 } //End reportRowOutCols
 //


public String getEntryType() {
	return entryType;
}


public void setEntryType(String entryType) {
	this.entryType = entryType;
}

 //
//END
//
}
//
//DATA_TRACK Ended
//
