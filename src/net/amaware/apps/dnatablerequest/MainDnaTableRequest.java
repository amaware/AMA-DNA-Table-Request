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
			
		    String inFileName = inputArgsSql(acomm,args);
		    
		    
		    //if (inFileName.toUpperCase().startsWith(tableNameToProcess)) {
		    //if (inFileName.toLowerCase().contains("data_track_feed")) {
		    if (inFileName.toLowerCase().contains("table_codes")) {
		    	
		    	tableNameToProcess="table_codes";
		    	
		    	mainApp = new MainAppDataStore(acomm, new PTableCodes()
                        , args, acomm.getFileTextDelimTab()
	                      //, int rowDataStartNum, int rowDataEndNum, int rowDataHeadNum)
	                      , 2, 999999, 1
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
			
			mainApp.getHtmlServ().outPageLine(acomm, thisClassName+" completed ");
			
			
			acomm.end();
			
		} catch (AException e1) {
			throw e1;
		}
		
	}
	
	   public static String inputArgsSql(ACommDb acomm, String[] args) {
			String filename="";
			/**/
		    System.out.println("====Args Input Process==========");
		    
		    if (args.length == 0) {        
		    	System.out.println("========None==========");
		    	
			    	   throw new AException(acomm
				    		, "No input arguments...FileName to process is required. Must beging with tablename");
		    	
		    } else {
		    	System.out.println("--Args Input---len=" + args.length);	    	
		    	for (int i = 0; i < args.length; i++) {
			    	System.out.println(i + ">" + args[i]);    			
		    	}
				if (args[0].length() == 0) {
				    	throw new AException(acomm
					    		, "===Arg FileName needed======");	    	
				}
				
				filename=args[0];

			}
			acomm.addPageMsgsLineOut("_______________Input Args_________________");
			for (int i=0; i < args.length;i++){
		    	acomm.addPageMsgsLineOut("Arg#"+i+"=" + args[i]);
			}
			//
			File f = new File(filename);
			filename=f.getName();			
			//
			return filename;
			
		}

//
// END CLASS
//	
}
