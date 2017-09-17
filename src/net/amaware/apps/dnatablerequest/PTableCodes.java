/**
 * 
 */
package net.amaware.apps.dnatablerequest;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amaware.dna.proc.Enums.DbProcessStatus;
import com.amaware.dna.proc.Enums.FileProcessStatus;
import com.amaware.dna.query.TABLE_CODES;

//import com.vzw.cops.mainfomprocessing.SfoRecords.SfoRecord;


import net.amaware.app.DataStoreReport;
import net.amaware.aproc.SqlPsQueryProc;
import net.amaware.aproc.SqlPsApp.DbStatus;
import net.amaware.autil.AComm;
import net.amaware.autil.ACommDb;
import net.amaware.autil.ADataColResult;
import net.amaware.autil.AException;
import net.amaware.autil.AExceptionSql;
import net.amaware.autil.AFileI;
import net.amaware.autil.AFileO;
import net.amaware.serv.HtmlTargetServ;
import net.amaware.serv.SourceProperty;

/**
 * @author PSDAA88 - Angelo M Adduci - Sep 6, 2005 3:02:12 PM
 * 
 */

public class PTableCodes extends DataStoreReport {
/**
 	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String thisClassName = this.getClass().getName();
	//
	//*SqlApp AutoGen @2016-12-10 09:49:03.0
    //protected ADataColResult fId = mapDataCol("table_name");
    protected ADataColResult fTabName = mapDataCol("tab_name");
    protected ADataColResult fCodeName = mapDataCol("code_name");
    protected ADataColResult fCodeValue = mapDataCol("code_value");
    protected ADataColResult fUserModId = mapDataCol("user_mod_id");
    protected ADataColResult fUserModTs = mapDataCol("user_mod_ts");    
    //
	//------------------db area-----------------------------
    //DbtTABLE_CODES dbtTABLE_CODES= new DbtTABLE_CODES(this);
    UTABLE_CODES_ULOGS uTABLE_CODES_ULOGS= null;
    UTABLE_CODES uTABLE_CODES= null;
    ULOGS aDnaULOGS= null;
    ULOGS aAmawareLOGS= null;
    //
    //ULOGS uLOGS= new ULOGS();
    //
	private DbStatus dbProcessStatus = DbStatus.NotFound;    
	//
	//------------------file area-----------------------------
    AFileO extractFileO = new AFileO();
	String extractFileName    = "";	
	String extractFileNamePrefix="";
	//
	//String extractCntsFileName    = "";
	//
	String fileRecPrev = "";
	String fileRecCurr = "";
	//
	int fileRowNum = 0;
	int dataRowNum = 0;
	//
	private java.sql.Timestamp currTimestamp = new java.sql.Timestamp(
			(new java.util.Date()).getTime());

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	//Date date = new Date();
	//dateFormat.format(date)
	//
	ACommDb acomm=null;
	String[] args=null;
    //
	String table_codesSqlStmtWhere="";
    //	
	/**
	 * 
	 */
	public PTableCodes(ACommDb iacomm,String[] iargs) {
		super();
		
		acomm=iacomm;
		args=iargs;

	}

	public DataStoreReport processThis(ACommDb acomm, SourceProperty _aProperty,
			HtmlTargetServ _aHtmlServ) {
		
		super.processThis(acomm, _aProperty, _aHtmlServ); // always call this
		//
		
		//
		//String extractFileName = _aProperty.getValue(SourceProperty.getPropNameFull());
		//extractFileNamePrefix= getThisHtmlServ().getDirFileName().replaceAll(".txt.html", "");
		extractFileNamePrefix = _aProperty.getValue(SourceProperty.getPropNameFull());
		if (extractFileNamePrefix == "") {
			extractFileNamePrefix= getThisHtmlServ().getDirFileName().replaceAll(".xls.html", "");
		}
		
		//_aProperty.displayProperties(acomm);
		
		//_aProperty.setValue(SourceProperty.getPropDataRowEnd(), 15);
		//
		acomm.addPageMsgsLineOut(thisClassName
		  + ":processThis StatementId=" + getAStatementsID()
          + " |SourcePropertyFileName=" + _aProperty.getNameFull(acomm)
		 );

		acomm.addPageMsgsLineOut(
				    "        "
				  + " |dbMaxRowsToReturn=" + acomm.getDbRowsMaxReturn()
				  + " |PropertyNumberOfMaxDataRows=" + _aProperty.getValue(SourceProperty.getPropDataRowEnd())
				 );
		
		return this;
	}

	public boolean doSourceStarted(ACommDb acomm) {
		int _currRowNum = getDataRowNum();		
		boolean retb = true;
		String extractFileExt=".summary.txt";
		String extractCntFileExt=".summary.fileCntsOnly.txt";

		acomm.addPageMsgsLineOut(" ");
		
		extractFileName = acomm.getOutFileDirectoryWithSep()+AComm.getArgFileName()+extractFileExt;
		
		try {
			extractFileO.openFile(extractFileName);
		} catch (IOException e1) {
			throw new AException(acomm, e1, "extractFileO Open=>");
		}
		
		//try {
		   acomm.addPageMsgsLineOut(thisClassName
			    //+":sqlSelectOpen=>RowColCount=" + sqlProc.queryRowColCount(acomm)
                +" |extractFileName=" + extractFileName
                +" |getThisHtmlServ().getDirFileName()=" + getThisHtmlServ().getDirFileName()
			    +"______________________"
			    // + sqlProc.getPrepStmt().getMaxRows() 
				);
		//} catch (SQLException e1) {
		//	throw new AExceptionSql(acomm, e1, thisClassName + "=>");
		//}
		  
		uTABLE_CODES= new UTABLE_CODES(acomm, "MainDbDnaTABLE_CODES.properties", args);
		uTABLE_CODES_ULOGS= new UTABLE_CODES_ULOGS(acomm, "MainDbDnaTABLE_CODES.properties", args);
		aDnaULOGS= new ULOGS(acomm, "MainDbDnaLOGS.properties", args);
		aAmawareLOGS= new ULOGS(acomm, "MainDbAmawareLOGS.properties", args);
		
		return retb;
	}
	
	public boolean doSourceEnded(ACommDb acomm) {
		super.doSourceEnded(acomm);

		//dbtTABLE_CODES.doProcessEndQueryLines(acomm, this);
		//
		uTABLE_CODES.doProcessEnd(acomm);
		uTABLE_CODES_ULOGS.doProcessEnd(acomm);
		aDnaULOGS.doProcessEnd(acomm);
		aAmawareLOGS.doProcessEnd(acomm);
		//
		extractFileO.writeLine("___________________________________________________________________________________________________ ");
		extractFileO.writeLine(" Report ENDED @"+dateFormat.format(new Date()) );
		extractFileO.writeLine("___________________________________________________________________________________________________ ");
		
		extractFileO.closeFile();
		
		return true;
	}

	public boolean doDataHead(ACommDb acomm, int _recNum
			//, Vector dataFields
			)
			throws AException {
		
        //setUserTitle(""); //overrides report header containing name of input file and header record in file
		//setUserTitle2(thisClassName); 

        if (getUserTitle() != "") {		  
		   extractFileO.writeLine(getUserTitle());
	    }
        if (getUserTitle2() != "") {
           extractFileO.writeLine(getUserTitle2());
        }
		extractFileO.writeLine(getSourceHeadVector().toString());
		extractFileO.writeLine("___________________________________________________________________________________________________ ");
		extractFileO.writeLine(" Report STARTED @"+dateFormat.format(new Date()));
		extractFileO.writeLine("___________________________________________________________________________________________________ ");

		super.doDataHead(acomm, _recNum);		
		//
	    return true;
	    //		return false to end processing    

	}
	
	public boolean doSourceCmdLine(ACommDb acomm, int recNum, ArrayList<String> _arrString) {
		//super will display _arrStringLines that exist before first data row
		//super.doSourceCmdLine(acomm, recNum, _arrString); 
			  return true;
	}	

	
	public boolean doDataRowsNotFound(ACommDb acomm) throws AException {

		 //if no sql statement on report for not found, comment out next line		
		setUserTitle2(getThisHtmlServ().formatForSqlout(acomm, getThisStatement()));
		super.doDataRowsNotFound(acomm);
		
		//throw new AException(acomm, "DataRowsNot Found");
		return true;

	}

	/*
	 * 
	 * 
	 */

	public boolean doDataRow(ACommDb acomm, AException _exceptionSql, boolean _isRowBreak)
			throws AException {
		if (!_exceptionSql.isExceptionNone()) {
			throw _exceptionSql;
		}
		
		super.doDataRow(acomm, _exceptionSql, _isRowBreak);
		
		//report on input file row		
		//uTABLE_CODES.doDataRowOut(acomm, this, _exceptionSql,  _isRowBreak);
		//
		String outMsg="";
		//
		++fileRowNum;		
		if (fileRowNum > getSourceDataRowEndNum()) {
			throw new AException(acomm, thisClassName 
					+ "=>More rows retuned than expected. CurrentRow#=" + fileRowNum
               		+ " |@DataRow#" + getDataRowNum()					
               		+ " |@SourceRow#" + getSourceRowNum()
    				+ " |#MaxRows=" + getSourceDataRowEndNum()
					);
		}
        //
		table_codesSqlStmtWhere="";
		List<String> outWhereItemsList = new ArrayList<String>();
		if(fTabName.isColValPopulated()) {
			outWhereItemsList.add("tab_name = " +"'"+ fTabName.getColumnValue()+"'");
		}
		if(fCodeName.isColValPopulated()) {
			outWhereItemsList.add("code_name = " +"'"+ fCodeName.getColumnValue()+"'");
		}
		if(fCodeValue.isColValPopulated()) {
			outWhereItemsList.add("code_value = " +"'"+ fCodeValue.getColumnValue()+"'");
		}
		int instrCtr=0;
		for (String instr: outWhereItemsList) {
			++instrCtr;
			if (instrCtr==1) {
				table_codesSqlStmtWhere = " WHERE " + instr;
			} else {
				table_codesSqlStmtWhere += " AND " + instr;
			}
		}
        //		
		doDataRowAsInline(acomm);
		
		doDataRowAsResult(acomm);
		
		//uTABLE_CODES.doDataRowOut(this, _exceptionSql,  _isRowBreak);
		
		//
		return true; // or false to stop processing of file

		
		
	}
	
	public boolean doDataRowAsInline(ACommDb acomm) {
		//
		this.reportLineOut(acomm, this.getClass().getName()
			         + "=> doDataRowAsInline" 
			         ,this.htmlLineOkStyle+"font-size:2em;");   	 		
        //		
		DbProcessStatus outprocessStatus = DbProcessStatus.NotFound;
		int rowCnt=0, rowLogCnt=0;
		int rowMaxCnt=10;
		//
		//		
	    //uTABLE_CODES.setEntryType(uTABLE_CODES.getCodeValue());
        //uTABLE_CODES.doProcessStartResult(uTABLE_CODES.doQueryStatement(uTABLE_CODES.getAcomm()));
		
		
		uTABLE_CODES.doProcessStartResult("select"
                + " id "
                + ",tab_name "
                + ",code_name "
                + ",code_value "
//                + ",user_mod_id "
                + ",user_mod_ts "				
				
                + " FROM table_codes "
				+ table_codesSqlStmtWhere

                + " ORDER BY tab_name ASC ");   	          
           
    	while (uTABLE_CODES.doQueryRowData()) {
    		++rowCnt;
    		//dbtTABLE_CODES.reportRowOut(acomm, uTABLE_CODES, this, "col 1 style" , "col 1 header text");
    		
    		//uTABLE_CODES.doProcessRowFound(this);
    	
    		uTABLE_CODES.setAppReportGroupLevel(1);

    		//uTABLE_CODES.setAppReportGroupLevel(uTABLE_CODES.getAppReportGroupLevel()+1);    		
   	        //if (uTABLE_CODES.getTabName().contentEquals("logs")) {
    		 if (uTABLE_CODES.isDataColResultValueFound(TABLE_CODES.TAB_NAME,"logs")) {
   	           uTABLE_CODES.reportRowOutParent(this,"background-color:white;color:green;");	
   	        	
   	           uTABLE_CODES.setAppReportGroupLevel(uTABLE_CODES.getAppReportGroupLevel()+1);   	        	
               /*
                * 
                */
   	           //
   	           //aDnaULOGS.setEntryType(uTABLE_CODES.getCodeValue());
   	           //aDnaULOGS.doProcessStartResult(aDnaULOGS.doQueryStatement(aDnaULOGS.getAcomm()));
   	           aDnaULOGS.doProcessStartResult("select * from logs "
                       + " WHERE entry_type = " +"'"+ uTABLE_CODES.getDataColResultValue(TABLE_CODES.CODE_VALUE)+"'" 
                       + "   AND create_ts >= '2015-09-09 10:00:43.0'"
                       + " ORDER BY create_ts DESC ");   	           
   	           
   	           
   	           int utcRowCnt=0;
   	    	   while (aDnaULOGS.doQueryRowData()) {
   	    		   ++utcRowCnt;
   	    		//dbtTABLE_CODES.reportRowOut(acomm, uTABLE_CODES, this, "col 1 style" , "col 1 header text");
   	    		
   	    		   if (utcRowCnt<21) {
   	    		       //aDnaULOGS.doProcessRowFound(this);
   	    		       
   	    		       aDnaULOGS.reportRowOut(this,"");
   	    		       
   	    		   } else {
   	    			   this.rptOutLine(aDnaULOGS.getAcomm(), this.getClass().getName()
				          + " for{" + aDnaULOGS.getAcomm().getDbUrlDbAndSchemaName()+  "}"
						  +"...Request to not continue. More Rows may exist"
						  +" maxrows{"+20+"}"
						  +" #rowsout{"+utcRowCnt+"}"
						//+" #rsRows{"+ acomm.getDbResultSet().getFetchSize()
						,this.htmlLineWarningStyle);   
   	    			   
   	    			   break;
   	    		   }
   	    		
   	    	   }   	           
   	    	   if (utcRowCnt==0) {
   	    		  this.rptOutLine(aDnaULOGS.getAcomm(), this.getClass().getName()
   				         + " for{" + aDnaULOGS.getAcomm().getDbUrlDbAndSchemaName() +  "}"
   				         + "...Rows NOT Found for" 
   				         + aDnaULOGS.getInWhereColValString(aDnaULOGS.getAcomm())
   				         ,this.htmlLineErrorStyle);   	    		   
   	    	   }
    		   //
   	    	   
   	    	   
 			  aAmawareLOGS.doProcessResult("select * from logs "
                      + " WHERE entry_type = " +"'"+ uTABLE_CODES.getDataColResultValue(TABLE_CODES.CODE_VALUE)+"'" 
                      + "   AND (create_ts >= '2015-09-09 10:00:43.0'"
                      + "        OR entry_subject LIKE " + "'%dnalady%')"
                      + " ORDER BY create_ts DESC "
                      ,this, 25);
    		   
    		   
    		   
    		   
    		   
    		
   	        } else {
   			   this.rptOutLine(uTABLE_CODES.getAcomm(), this.getClass().getName()
   					   + " for{" + uTABLE_CODES.getAcomm().getDbUrlDbAndSchemaName() +  "}"
   				       
   					   
   					   + uTABLE_CODES.getInWhereColValString(uTABLE_CODES.getAcomm())
   					   
   					   + "...Unknown table name{" 
   					   + uTABLE_CODES.getDataColResultValue(TABLE_CODES.TAB_NAME) +  "}"
   					   ,this.htmlLineErrorStyle);
   	      }
   	        
    	} //end while
		
		

		
	    return true;
	}
	
	public boolean doDataRowAsResult(ACommDb acomm) {
		
		
		  this.reportLineOut(acomm, this.getClass().getName()
			         + "=> doDataRowAsResult" 
			         ,this.htmlLineOkStyle+"font-size:2em;");	 
		
		uTABLE_CODES_ULOGS.SqlStmtWhere=table_codesSqlStmtWhere;
		uTABLE_CODES_ULOGS.doProcessResult(this, 99999);

    	
    	dbProcessStatus=uTABLE_CODES_ULOGS.dbStatus;
    	switch (dbProcessStatus) {
		case OK: 
		    uTABLE_CODES_ULOGS.outLogDbStatus(uTABLE_CODES_ULOGS.thisClassName, getSourceDataRowsRead());
			break;
		case NotFound: 
		    uTABLE_CODES_ULOGS.outLogDbStatus(uTABLE_CODES_ULOGS.thisClassName, getSourceDataRowsRead());
			break;
		default:
		    uTABLE_CODES_ULOGS.outLogDbStatus(uTABLE_CODES_ULOGS.thisClassName, getSourceDataRowsRead());
			break;
		}		
	    
	       return true;
	}
	
	/*
	 * 
	 */
	
	public boolean doDataRowsEnded(ACommDb acomm) throws AException {
        //
		super.doDataRowsEnded(acomm);
		//
		String outLine = "";
		//
		extractFileO.writeLine("___________________________________________________________________________________________________ ");
		outLine=" END TOTALS...#Records{"+ fileRowNum +"}" 
					+ " #DbRowsSelected{"+ acomm.getDbConnRowSelectCnts() +"}"
			        + " #Inserted{"+ acomm.getDbConnRowInsertCnts() +"}"
			        + " #Deleted{"+ acomm.getDbConnRowDeleteCnts() +"}"
			        + " #Updated{"+ acomm.getDbConnRowUpdateCnts() +"}"
			        + " #Tot Rows Changed{"+ acomm.getDbConnRowModCnts() +"}"
	                ;

			//getThisHtmlServ().outPageLine(acomm, outLine, htmlTitleStyle);
			
		this.rptOutLine(acomm, this.getClass().getName() + outLine, this.htmlTrailerStyle);
		extractFileO.writeLine(outLine);
		//
		acomm.addPageMsgsLineOut(thisClassName
				    +":doDataRowsEnded=>StatementId=" + getAStatementsID() 
               		+ " @SourceRows#" + getSourceRowNum()
               		+ " @DataRow#" + getDataRowNum()
    				+ " |#MaxRows=" + getSourceDataRowEndNum()
    				);

		
		
        if (getSourceRowNum() >  getSourceDataRowEndNum()) {
 	    	getThisHtmlServ().outPageLineWarning(acomm,  
	    			 "More data from Source may exist....ended due to requested max rows"
 	    			+ " |DataRow Start# " + getSourceDataRowStartNum()
                    + " |DataRow End# " + getSourceDataRowEndNum() 	    			
 	    			+ " |SourceRows# " + getSourceRowNum()
               		+ " |DataRow#" + getDataRowNum()
					);
 	    	
 	    	//super.doDataRowsEnded(acomm);
 	    	/*
 	    	throw new AException(acomm, "Requested MAX ROWS EXCEEDED...MORE ROWS EXIST" 
 	    			+ " @DataRow Start #" + getSourceDataRowStartNum()
    				+ " @DataRow End# " + getSourceDataRowEndNum() 	    			
 	    			+ " @SourceRows #" + getSourceRowNum()
               		+ " @DataRow# " + getDataRowNum()
 	    					);
 	    	*/
        }
        //
        //doLogs(acomm, uTABLE_CODES);
        //
 	   
 	    //
		return true;
		
	}
 
	//
	/**
 	 * Following is generated for table in table generated file....copyied here for use in application
	 */
		
	/**
 	 * Copy from input file fields to table Class fields - this is generated and copied here 
	 */		
	//
	 public void doDSRFieldsToTableTABLE_CODES(ACommDb acomm, TABLE_CODES _qClass) {
		       //_qClass.setDataColResultValue(TABLE_CODES.ID,fId.getColumnValue());
		       _qClass.setDataColResultValue(TABLE_CODES.TAB_NAME,fTabName.getColumnValue());
		       _qClass.setDataColResultValue(TABLE_CODES.CODE_NAME,fCodeName.getColumnValue());
		       _qClass.setDataColResultValue(TABLE_CODES.CODE_VALUE,fCodeValue.getColumnValue());
		       _qClass.setDataColResultValue(TABLE_CODES.USER_MOD_ID,fUserModId.getColumnValue());
		       _qClass.setDataColResultValue(TABLE_CODES.USER_MOD_TS,fUserModTs.getColumnValue());
			//
    } //End doDSRFieldsToTable TABLE_CODES _qClass
	//
	 
	/**
 	 * Copy from table Class fields to input file fields - this is generated and copied here 
	 */		
	//
	 public void doDSRFieldsFromTableTABLE_CODES(ACommDb acomm, TABLE_CODES _qClass) {
		        //fId.setColumnValue(_qClass.getDataColResultValue(TABLE_CODES.ID));
			    fTabName.setColumnValue(_qClass.getDataColResultValue(TABLE_CODES.TAB_NAME));
			    fCodeName.setColumnValue(_qClass.getDataColResultValue(TABLE_CODES.CODE_NAME));
			    fCodeValue.setColumnValue(_qClass.getDataColResultValue(TABLE_CODES.CODE_VALUE));
			    fUserModId.setColumnValue(_qClass.getDataColResultValue(TABLE_CODES.USER_MOD_ID));
			    fUserModTs.setColumnValue(_qClass.getDataColResultValue(TABLE_CODES.USER_MOD_TS));

			//
	 } //End doDSRFieldsFromTable qTABLE_CODES
	//
	/**
	 * Validate input file fields for correct data type - this is generated and copied here 
	 */		
	//*SqlApp AutoGen @2016-12-10 09:49:03.0
	 public void doDSRFieldsValidate(ACommDb acomm) throws Exception {
				 
			   // fId.setColumnValue(String.valueOf(doFieldValidateInt(acomm, fId.getColumnValue(),0)));
			    
			    fTabName.setColumnValue(doFieldValidateString(acomm, fTabName.getColumnValue()));
			    fCodeName.setColumnValue(doFieldValidateString(acomm, fCodeName.getColumnValue()));
			    fCodeValue.setColumnValue(doFieldValidateString(acomm, fCodeValue.getColumnValue()));
			    fUserModId.setColumnValue(doFieldValidateString(acomm, fUserModId.getColumnValue()));
			    fUserModTs.setColumnValue(doFieldValidateString(acomm, fUserModTs.getColumnValue()));
			//
	 } //End doDSRFieldsValidate
			//
		
		
//
// END
//	
 }
