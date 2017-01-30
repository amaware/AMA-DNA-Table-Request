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
    //protected ADataColResult fId = mapDataCol("id");
    protected ADataColResult fTabName = mapDataCol("tab_name");
    protected ADataColResult fCodeName = mapDataCol("code_name");
    protected ADataColResult fCodeValue = mapDataCol("code_value");
    protected ADataColResult fUserModId = mapDataCol("user_mod_id");
    protected ADataColResult fUserModTs = mapDataCol("user_mod_ts");    
    //
	//------------------db area-----------------------------
    //DbtTABLE_CODES dbtTABLE_CODES= new DbtTABLE_CODES(this);
    UTABLE_CODES uTABLE_CODES= new UTABLE_CODES();
	private DbProcessStatus uTABLE_CODES_DbProcessStatus = DbProcessStatus.NotFound;    
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
	//Inline Classes
    //
    //	
	/**
	 * 
	 */
	public PTableCodes() {
		super();

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
		
		extractFileName = getThisHtmlServ().getDirFileName().replace(".html", extractFileExt);
		
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
		
		return retb;
	}
	
	public boolean doSourceEnded(ACommDb acomm) {
		super.doSourceEnded(acomm);

		//dbtTABLE_CODES.doProcessEndQueryLines(acomm, this);
		
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
		//report on input file row		
		uTABLE_CODES.doDataRowOut(acomm, this, _exceptionSql,  _isRowBreak);
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
    	uTABLE_CODES.doProcessResult(acomm
			    , "" //String id
			    , fTabName.getColumnValueTrim() //String tab_name
			    , fCodeName.getColumnValueTrim() //String code_name
			    , fCodeValue.getColumnValueTrim() //String code_value
			    , fUserModId.getColumnValueTrim() //String user_mod_id
			    , "" //String user_mod_ts
			    
			    , this
			    , 99999
			    
			    );
		
    	switch (uTABLE_CODES.dbStatus) {
    	
		case OK: 
		    acomm.addPageMsgsLineOut(thisClassName+ "=>Processed Row#{" + fileRowNum + "}" 
	                + " Found Where{" + uTABLE_CODES.getInWhereColValString(acomm) + "}"
	                + " dbStatus{" + uTABLE_CODES.dbStatus.toString() + "}"
	                );
			
			break;
    	
    	
		case NotFound: 
		    acomm.addPageMsgsLineOut(thisClassName+ "=>Processed Row#{" + fileRowNum + "}" 
	                + " NOT Found Where{" + uTABLE_CODES.getInWhereColValString(acomm) + "}"
	                + " dbStatus{" + uTABLE_CODES.dbStatus.toString() + "}"
	                );
			
			break;

		default:
			
		    acomm.addPageMsgsLineOut(thisClassName+ "=>Processed Row#{" + fileRowNum + "}" 
	                + " Where{" + uTABLE_CODES.getInWhereColValString(acomm) + "}"
	                + " UNHANDLED dbStatus{" + uTABLE_CODES.dbStatus.toString() + "}"
	                );
			
			
			break;
		}
    	
    	
        //		

		//
		return true; // or false to stop processing of file

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
 	    	throw new AException(acomm, "Requested MAX ROWS EXCEEDED...MORE ROWS EXIST" 
 	    			+ " @DataRow Start #" + getSourceDataRowStartNum()
    				+ " @DataRow End# " + getSourceDataRowEndNum() 	    			
 	    			+ " @SourceRows #" + getSourceRowNum()
               		+ " @DataRow# " + getDataRowNum()
 	    					);
         }
        
        
        
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
			   //_qClass.setId(fId.getColumnValue());
			   _qClass.setTabName(fTabName.getColumnValue());
			   _qClass.setCodeName(fCodeName.getColumnValue());
			   _qClass.setCodeValue(fCodeValue.getColumnValue());
			   _qClass.setUserModId(fUserModId.getColumnValue());
			   _qClass.setUserModTs(fUserModTs.getColumnValue());
			//
    } //End doDSRFieldsToTable TABLE_CODES _qClass
			//
		 
	/**
 	 * Copy from table Class fields to input file fields - this is generated and copied here 
	 */		
	//
	 public void doDSRFieldsFromTableTABLE_CODES(ACommDb acomm, TABLE_CODES _qClass) {
			   // fId.setColumnValue(_qClass.getId());
			    fTabName.setColumnValue(_qClass.getTabName());
			    fCodeName.setColumnValue(_qClass.getCodeName());
			    fCodeValue.setColumnValue(_qClass.getCodeValue());
			    fUserModId.setColumnValue(_qClass.getUserModId());
			    fUserModTs.setColumnValue(_qClass.getUserModTs());
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
