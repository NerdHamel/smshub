package vas.util;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import vas.service.ussdpush.UssdPushServiceBean;

//update USSD_PROFILE_TBL set language = '" + lang + "' where msisdn = '" + msisdn + "'

//"merge into USSD_PROFILE_TBL t1 USING (select '" + msisdn + "' as msisdn, '" + pin + "' as pin, '" + lang + "' as language, '" + gender + "' as gender from dual) t2 on (t1.msisdn = t2.msisdn) when matched then update set t1.pin = t2.pin, t1.language = t2.language when not matched then insert (msisdn, pin, language, gender) values (t2.msisdn, t2.pin, t2.language, t2.gender)"

//select LANGUAGE, PIN from USSD_PROFILE_TBL where MSISDN = '" + msisdn + "'

public class DBUtil {
	public static Connection getDbConnectionEIMSPROD() throws Exception {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@SPPA001:1531:vasdevl", "norivr",
					"G00gle_2017");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return con;
	}

	public static Connection getDbConnection_vasprod() throws Exception {
		Connection con = null;
		Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
		con = DriverManager
				.getConnection(
						"jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = lotus_vip)(PORT = 1526)) (ADDRESS = (PROTOCOL = TCP)(HOST = lilac_vip)(PORT = 1526)) (LOAD_BALANCE = yes) (CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = vasprod) ) )",
						"VASAPP", "vas_2009_app");
		return con;
	}

	public static void insertUssdPushRequest(UssdPushServiceBean bean)
			throws Exception {
		Connection con = null;
		Statement stm = null;
		try {
			con = getDbConnectionEIMSPROD();
			stm = con.createStatement();
			if (bean.srId == null)
				bean.srId = "";
			String sql = "INSERT INTO USSD_PUSH_REQUESTS ( RECOVERYKEY, MSISDN, INSERT_TIME, LAST_UPDATE_TIME, STATUS, PUSHTYPE, SRID, SURVEYID, SURVEYREFID, REFERENCEID, AGENT_PF, REQUESTERNAME, INIT_SERVICE "
					+ " ) VALUES ( "
					+ "RECOVERYKEY_SID.NEXTVAL, '"
					+ bean.msisdn
					+ "', SYSDATE,  NULL, 'INSERTED BY MQ', '"
					+ bean.pushtype
					+ "', '"
					+ bean.srId
					+ "', '"
					+ bean.surveyId
					+ "', '"
					+ bean.surveyRefId
					+ "', '"
					+ bean.referenceId
					+ "','"
					+ bean.agent
					+ "','"
					+ bean.requestorChannelId + "', 'MQ')";
			stm.executeUpdate(sql);
		} catch (Exception exp) {
			throw exp;
		} finally {
			try {
				stm.close();
			} catch (Exception exp) {
			}
			try {
				con.close();
			} catch (Exception exp) {
			}
		}
	}

	public static UssdPushServiceBean getLatestSMSSurveyDate(
			UssdPushServiceBean bean) throws Exception {
		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			con = getDbConnectionEIMSPROD();
			stm = con.createStatement();

			// String sql = "SELECT * FROM( "
			// +
			// "SELECT TO_CHAR(LAST_UPDATE_TIME, 'MM/DD/YYYY HH24:MI:SS') AS LAST_UPDATED_TIME FROM USSD_PUSH_SMSCHATSRVY "
			// + "WHERE MSISDN = '" + bean.msisdn + "' "
			// + "ORDER BY LAST_UPDATE_TIME DESC) " + "WHERE ROWNUM = 1";

			String sql = "SELECT * FROM( "
					+ "SELECT TO_CHAR(UPS.LAST_UPDATE_TIME, 'MM/DD/YYYY HH24:MI:SS') AS LAST_UPDATED_TIME FROM USSD_PUSH_SMSCHATSRVY UPS,USSD_PUSH_REQUESTS UPR "
					+ "WHERE UPS.USSD_REQ_RKEY = UPR.RECOVERYKEY "
					+ "AND UPR.MSISDN = '" + bean.msisdn + "'  "
					+ "ORDER BY UPS.LAST_UPDATE_TIME DESC)  "
					+ "WHERE ROWNUM = 1";
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				bean.lastUpdatedDate = rs.getString("LAST_UPDATED_TIME");
				bean.errorCode = "0";
				bean.errorMsg = "";
			} else {
				bean.errorCode = "21";
				bean.errorMsg = "No Record Found";
			}
		} catch (Exception exp) {
			throw exp;
		} finally {
			try {
				rs.close();
			} catch (Exception exp) {
			}
			try {
				stm.close();
			} catch (Exception exp) {
			}
			try {
				con.close();
			} catch (Exception exp) {
			}
		}
		return bean;
	}
	public static void insertIvrAudit2(UssdPushServiceBean bean)
	        throws Exception
	    {
	        Connection con;
	        Statement stm;
	        con = null;
	        stm = null;
	        try
	        {
	            con = getDbConnectionEIMSPROD();
	            stm = con.createStatement();
	            if(bean.srId == null)
	            {
	                bean.srId = "";
	            }
	            String sql = "INSERT INTO ivr_report_enhancement ( MSISDN, INSERT_TIME, call_session_id, trans" +
	"action_id, backend_service_name, request, response, status, total_processing_tim" +
	"e  ) VALUES ( '"
	 + bean.msisdn + "', SYSDATE,  '" + bean.requestorTransId + "', '" + bean.reqDate + "','" + bean.surveyId 
	 + "', utl_raw.cast_to_raw('" + bean.requestorChannelId + "'), utl_raw.cast_to_raw('" + bean.referenceId + "'), '" 
	 + bean.srId + "', '" + bean.surveyRefId + "')";
	            System.out.println(sql);
	            stm.executeUpdate(sql);
	        }
	        catch(SQLException exp)
	        {
	            throw exp;
	        }
	        try
	        {
	            stm.close();
	        }
	        catch(Exception exception1) { }
	        try
	        {
	            con.close();
	        }
	        catch(Exception exception2) { }
	        try
	        {
	            stm.close();
	        }
	        catch(Exception exception3) { }
	        try
	        {
	            con.close();
	        }
	        catch(Exception exception4) { }
	    }
	
	public static void insertIvrAudit(UssdPushServiceBean bean)
	        throws Exception
	    {
	        Connection con;
	        PreparedStatement stm;
	        con = null;
	        stm = null;
	        try
	        {
	            con = getDbConnectionEIMSPROD();
	          
	            if(bean.srId == null)
	            {
	                bean.srId = "";
	            }
	            
	            byte[] byteData = bean.requestorChannelId.getBytes("UTF-8");//Better to specify encoding
	            Blob blobData = con.createBlob();
	            blobData.setBytes(1, byteData);
	            
	            byteData = bean.referenceId .getBytes("UTF-8");//Better to specify encoding
	            Blob resBlobData = con.createBlob();
	            resBlobData.setBytes(1, byteData);
	            
	            
	            String sql = "INSERT INTO norivr.ivr_report_enhancement ( MSISDN, INSERT_TIME, call_session_id, trans" +
	"action_id, backend_service_name, request, response, status, total_processing_tim" +
	"e  ) VALUES ( '"
	 + bean.msisdn + "', SYSDATE,  '" + bean.requestorTransId + "', '" + bean.reqDate + "','" + bean.surveyId 
	 + "', ?, ?, '"  + bean.srId + "', '" + bean.surveyRefId + "')";
	            System.out.println(sql);
	            
	            stm = con.prepareStatement(sql);
	            stm.setBlob(1, blobData);
	            stm.setBlob(2, resBlobData);
	            
	            stm.executeUpdate();
	        }
	        catch(SQLException exp)
	        {
	            throw exp;
	        }
	        try
	        {
	            stm.close();
	        }
	        catch(Exception exception1) { }
	        try
	        {
	            con.close();
	        }
	        catch(Exception exception2) { }
	        try
	        {
	            stm.close();
	        }
	        catch(Exception exception3) { }
	        try
	        {
	            con.close();
	        }
	        catch(Exception exception4) { }
	    }
}
