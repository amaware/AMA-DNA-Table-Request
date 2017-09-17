package net.amaware.apps.dnatablerequest;

import java.io.File;

import net.amaware.autil.*;
import net.amaware.app.MainAppDataStore;
import net.amaware.app.MainApp.InputType;
//
//
/**
 * @author AMAWARE - Angelo M Adduci
 * 
 */
public class MainDnaTableRequest {
	//Properties file 
    final static String propFileName   = "MainDnaTableRequest.properties";
	//Architecture Common communication Class 
	static ACommDb acomm;
	//Architecture Framework Class
	static MainAppDataStore mainApp;
	//Application Classes
	//static ProcPRODUCT_BUNDLE_VALUESInsert _fileProcess = new ProcPRODUCT_BUNDLE_VALUESInsert();
    //
	public static void main(String[] args) {
		final String thisClassName = "MainDnaTableRequest";
		//
		String tableNameToProcess="unknown";
		//
		try {  
			acomm = new ACommDb(propFileName, args);
			//acomm.dbConGet(); //this is done in MainAppDataStore
			
			String inFileName=AComm.getArgFileFullName();
		    
		    //if (inFileName.toUpperCase().startsWith(tableNameToProcess)) {
		    //if (inFileName.toLowerCase().contains("data_track_feed")) {
			
		    if (inFileName.toLowerCase().contains("table_codes")) {
		    	
		    	tableNameToProcess="table_codes";
		    	
		    	mainApp = new MainAppDataStore(acomm, new PTableCodes(acomm,args)
                        , args, acomm.getFileTextDelimTab()
	                      //, int rowDataStartNum, int rowDataEndNum, int rowDataHeadNum)
	                      , 2, 999999, 1
                          // , 4, 999999, 3
	                      );		    	
		    

		    } else if (inFileName.toLowerCase().contains("data_track")) {
			    	
			    	tableNameToProcess="data_track";
			    	
			    	System.out.println("==== "+tableNameToProcess+" process==========");
			    	
			    	throw new AException(acomm
				    		, "===FileName: "+inFileName
				    		+"=>tablename "+tableNameToProcess+" NOT YET IMPLIMENTED======"
			    			);	    	

			    	
		    } else {
		    	throw new AException(acomm
			    		, "===FileName: "+inFileName
			    		+"=>tablename "+tableNameToProcess+" needs to be part of filename for rows processed======"
			    		+"...or tablename in file needs to be added for processing"
		    			);	    	
		    }
			
			mainApp.doProcess(acomm, thisClassName, ".9em");
			
			//mainApp.getHtmlServ().outPageLine(acomm, thisClassName+" completed ");
			
		    //acomm.dbConClose(); //if NOT using MainAppDataStore

			acomm.end();

		} catch (AExceptionSql e1) {
			acomm.addPageMsgsLineOut("***AExceptionSql=>"
					                +" Msg{"+e1.getExceptionMsg()+"}" 
					                +" Code{"+e1.getExceptionCode()+"}"
					                +" State{"+e1.getExceptionState()+"}"
					                );
			throw e1;
					
			
		} catch (AException e1) {
			throw e1;
		}
		
	}
	


//
// END CLASS
//	
}
