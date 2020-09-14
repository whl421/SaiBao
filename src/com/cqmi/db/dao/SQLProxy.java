package com.cqmi.db.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.apache.ibatis.session.SqlSession;




public class SQLProxy {
	//-------------------------------
		public Connection conn;
		public ResultSet rs;
		public ResultSetMetaData rsmd;
		public Statement stmt;
		//private String thepage;
		//private String totalpage;
		public CallableStatement cStatement;
		public PreparedStatement pstmt = null;
		public SqlSession sqlSession;
		
	/** 
	 * 获取数据库连接 
	 * @return 
	 */  
	public Connection getConnection() {  
		    try {
		    	sqlSession=new MybatisSessionDataSource().getSession();
		        conn =sqlSession.getConnection();	//ContextDataSource.getConnection(); 
//		        conn =sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
		    } catch (Exception e) {    
		       e.printStackTrace();  
		    }    
		    return conn;
	} 
	
	

// -------------------------------------- Constructor --------------------------------------
	public SQLProxy() {
		conn = null;
		rs = null;
		rsmd = null;
		stmt = null;
	}

// -----------------------------------------------------------------------------------------
  public void clearResult()  {
    if (rs != null) {
	    try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    rs = null;
    if (stmt != null) {
    	try {
    		stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    if(pstmt!=null){
    	try {
    		pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    pstmt=null;
    stmt = null;
    rsmd = null;
  }

  public void closeConn(){
    clearResult();
    if (sqlSession != null) {
    		sqlSession.close();
//      System.out.println("SQLProxy 关闭数据库链接");
    		//System.identityHashCode(con4)
      Loggerlog.log("sqlproxy").warn(System.identityHashCode(this)+"-----------关闭数据库链接---------");
    }
//    if (conn != null) {
//    	try {
//    		conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//      System.out.println("SQLProxy 关闭数据库链接");
//    }
    conn = null;
  }

  /**
   * 设置提交为非自动提交
   * @throws SQLException
   */
  public void setAutoCommitFalse() throws SQLException {
    if (conn != null) {
//      System.out.println("SQLProxy set commit false");
//      Loggerlog.log("sqlproxy").warn("-----------set commit false---------");
      conn.setAutoCommit(false);
      Loggerlog.log("sqlproxy").warn(System.identityHashCode(this)+"-----------事物开启---------");
    }
  }

  /**
   * 设置提交为自动提交
   * @throws SQLException
   */
  public void setAutoCommitTrue() throws SQLException {
    if (conn != null) {
//      System.out.println("SQLProxy set commit false");
//      Loggerlog.log("sqlproxy").warn("-----------set commit false---------");
      conn.setAutoCommit(true);
      Loggerlog.log("sqlproxy").warn(System.identityHashCode(this)+"-----------事物开启---------");
    }
  }

  /**
   * 手动显示提交确认要执行的SQL
   * @throws SQLException
   */
  public void commitExe() throws SQLException {
    if (conn != null) {
//      System.out.println("SQLProxy commit execute");
//      Loggerlog.log("sqlproxy").warn("-----------commit execute---------");
      conn.commit();
      Loggerlog.log("sqlproxy").warn(System.identityHashCode(this)+"-----------事物提交成功---------");
    }
  }

  /**
   * 回滚未完成的事务
   */
  public void rollbackExe() throws SQLException {
    if (conn != null) {
//      System.out.println("SQLProxy rollback execute");
      Loggerlog.log("sqlproxy").warn(System.identityHashCode(this)+"-----------事物回滚---------");	
//      Loggerlog.log("sqlproxy").warn("-----------rollback execute---------");
      conn.rollback();
    }
  }

  public synchronized int execSQL(String sqlStmt) throws SQLException {
    
    if (conn == null || conn.isClosed()) {
      throw new SQLException(
          "platform.dao.SQLProxy: execSQL()|This connection has not been established yet.");
    }
    if (sqlStmt == null) {
      throw new SQLException(
          "platform.dao.SQLProxy: execSQL()|SQL-statement is null.");
    }
    clearResult();
    stmt = conn.createStatement();
    if (sqlStmt.trim().toUpperCase().startsWith("SELECT")) {
      rs = stmt.executeQuery(sqlStmt);
      rsmd = rs.getMetaData();
      return -1;
    }
    else {
      int numRow = stmt.executeUpdate(sqlStmt);
      clearResult();
      return numRow;
    }
  }
  
  public synchronized int execSQL(String sqlStmt,List<String> ParaMeter) throws SQLException {
	    
	    if (conn == null || conn.isClosed()) {
	      throw new SQLException(
	          "platform.dao.SQLProxy: execSQL()|This connection has not been established yet.");
	    }
	    if (sqlStmt == null) {
	      throw new SQLException(
	          "platform.dao.SQLProxy: execSQL()|SQL-statement is null.");
	    }
	    clearResult();
	    stmt = conn.createStatement();
	    if (sqlStmt.trim().toUpperCase().startsWith("SELECT")) {
	      pstmt = conn.prepareStatement(sqlStmt);
	      for (int i = 0; i < ParaMeter.size(); i++) {
	    	  pstmt.setString(i+1, ParaMeter.get(i));
	      }
	      rs = pstmt.executeQuery();
	      rsmd = rs.getMetaData();
	      return -1;
	    }
	    else {
	      pstmt = conn.prepareStatement(sqlStmt);
	      for (int i = 0; i < ParaMeter.size(); i++) {
	    	  pstmt.setString(i+1, ParaMeter.get(i));
	      }
	      int numRow = pstmt.executeUpdate();;
	      clearResult();
	      return numRow;
	    }
 }
  
  
  public int execCountSQL( String sqlStmt )throws SQLException {
	if( conn==null || conn.isClosed() )
		throw new SQLException( "platform.dao.SQLProxy: execSQL()|" +
				"This connection has not been established yet." ) ;
	if( sqlStmt==null )
		throw new SQLException( "platform.dao.SQLProxy: execSQL()|" +
				"SQL-statement is null." ) ;
	clearResult();
	conn.setAutoCommit( true ) ;
	stmt=conn.createStatement() ;
	rs=stmt.executeQuery( sqlStmt ) ;
	int numRow = 0;
	rs.next();
	numRow = rs.getInt(1);
	clearResult();
	return numRow ;

}
  
  public synchronized void execUpdate(String[] sqlStmts) throws SQLException {
    if (conn == null || conn.isClosed()) {
      throw new SQLException("platform.dao.SQLProxy: execUpdate()|The connection has not been established yet.");
    }
    if (sqlStmts == null || sqlStmts.length == 0) {
      throw new SQLException(
          "platform.dao.SQLProxy: execUpdate()|SQL-statement is null.");
    }
    clearResult();
    conn.setAutoCommit(false);
    try {
      for (int i = 0; i < sqlStmts.length; i++) {
        
        stmt = conn.createStatement();
        stmt.executeUpdate(sqlStmts[i]);
        stmt.close();
      }
      conn.commit();
    }
    catch (SQLException ex) {
      conn.rollback();
      throw ex;
    }
  }

  public String callFuction(String proceName) throws SQLException {
    String reulseValue = null;
    if (conn == null || conn.isClosed()) {
      throw new SQLException("platform.dao.SQLProxy: execUpdate()|The connection has not been established yet.");
    }
    try {
      String sql = "{? = call "+proceName+" }";
      cStatement = conn.prepareCall(sql);
      cStatement.registerOutParameter(1,Types.VARCHAR);
      cStatement.execute();
      reulseValue = cStatement.getString(1);
      
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return reulseValue;
  }

  public String callProce(String proceName) throws SQLException {
   String reulseValue = null;
   if (conn == null || conn.isClosed()) {
     throw new SQLException("platform.dao.SQLProxy: execUpdate()|The connection has not been established yet.");
   }
   try {
     String sql = "{call "+proceName+" }";

     cStatement = conn.prepareCall(sql);
     //cStatement.registerOutParameter(1,Types.VARCHAR);
     cStatement.execute();
     //reulseValue = cStatement.getString(1);
     //System.out.println("//-->reulseValue="+reulseValue);
   }
   catch (SQLException e) {
     e.printStackTrace();
   }
   return reulseValue;
 }
 public void closeProce() throws SQLException {
    try {
      if (cStatement != null) {
        cStatement.close();
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public void closeFunction() throws SQLException {
    try {
      if (cStatement != null) {
        cStatement.close();
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void proceSetParameter(int index, int x) throws SQLException {
    cStatement.setInt(index, x);
  }

  public void proceSetParameter(int index, boolean x) throws SQLException {
    cStatement.setBoolean(index, x);
  }

  public void proceSetParameter(int index, String x) throws SQLException {
    cStatement.setString(index, x);
  }

  public void proceSetParameter(int index, Date x) throws SQLException {
    cStatement.setDate(index, x);
  }

  public void proceSetParameter(int index, double x) throws SQLException {
    cStatement.setDouble(index, x);
  }

  public void proceSetParameter(int index, float x) throws SQLException {
    cStatement.setFloat(index, x);
  }

  public void proceSetParameter(int index, Time x) throws SQLException {
    cStatement.setTime(index, x);
  }

  public void proceSetParameter(int index, Timestamp x) throws SQLException {
    cStatement.setTimestamp(index, x);
  }

  public synchronized void execProceUpdate() throws SQLException {
    if (cStatement == null) {
      throw new SQLException(
          "platform.dao.SQLProxy: execProceUpdate() | cStatement is null");
    }
    cStatement.executeUpdate();
  }

  public synchronized void execMultUpdate(String[] sqlStmts) throws
      SQLException {
    if (conn == null || conn.isClosed()) {
      throw new SQLException("platform.dao.SQLProxy: execUpdate()|The connection has not been established yet.");
    }
    if (sqlStmts == null || sqlStmts.length == 0) {
      throw new SQLException(
          "platform.dao.SQLProxy: execUpdate()|SQL-statement is null.");
    }
    clearResult();
    //conn.setAutoCommit(false);
    stmt = conn.createStatement();
    try {
      stmt.clearBatch();
      for (int i = 0; i < sqlStmts.length; i++) {
        stmt.addBatch(sqlStmts[i]);
        //System.out.println("sqlStmts[" + i + "] ok!");
      }
      stmt.executeBatch();
      //conn.commit();
      System.out.println("提交成功！");
    }

    catch (SQLException ex) {
      //conn.rollback();
      System.out.println("// -------> rollback<---------\\");
      throw ex;
    }
  }

  public int getColumnCount() throws SQLException {
    if (rsmd == null) {
      throw new SQLException("platform.dao.SQLProxy: getColumnCount()");
    }
    return rsmd.getColumnCount();
  }

  public String[] getColumnNames() throws SQLException {
    if (rsmd == null) {
      throw new SQLException("platform.dao.SQLProxy: getColumnNames()");
    }
    String[] columnNames = new String[getColumnCount()];
    for (int i = 1; i <= columnNames.length; i++) {
      columnNames[i - 1] = rsmd.getColumnName(i);
      
    }
    return columnNames;
  }

  @SuppressWarnings("deprecation")
protected Object getField(int column, boolean convertToString) throws
      SQLException {
    if (rs == null || rsmd == null) {
      throw new SQLException("platform.dao.SQLProxy: getField()");
    }
    switch (rsmd.getColumnType(column)) {
      case Types.BIGINT:
        if (convertToString) {
          return String.valueOf(rs.getLong(column));
        }
        else {
          return new Long(rs.getLong(column));
        }

      case Types.BINARY:
        if (convertToString) {
          return Byte.toString(rs.getByte(column));
        }
        else {
          return new Byte(rs.getByte(column));
        }

      case Types.BIT:
        if (convertToString) {
          return String.valueOf(rs.getBoolean(column));
        }
        else {
          return new Boolean(rs.getBoolean(column));
        }

      case Types.CHAR:
        return rs.getString(column);

      case Types.DATE:
        if (convertToString) {
          return (rs.getDate(column)).toString();
        }
        else {
          return rs.getDate(column);
        }

      case Types.DECIMAL:
        if (convertToString) {
          return (rs.getBigDecimal(column, rsmd.getScale(column))).toString();
        }
        else {
          return rs.getBigDecimal(column, rsmd.getScale(column));
        }

      case Types.DOUBLE:
        if (convertToString) {
          return String.valueOf(rs.getDouble(column));
        }
        else {
          return new Double(rs.getDouble(column));
        }

      case Types.FLOAT:
        if (convertToString) {
          return String.valueOf(rs.getDouble(column));
        }
        else {
          return new Float(rs.getDouble(column));
        }

      case Types.INTEGER:
        if (convertToString) {
          return String.valueOf(rs.getInt(column));
        }
        else {
          return new Integer(rs.getInt(column));
        }

      case Types.LONGVARBINARY:
        if (convertToString) {
          return (rs.getBinaryStream(column)).toString();
        }
        else {
          return rs.getBinaryStream(column);
        }

      case Types.LONGVARCHAR:
        return rs.getString(column);

      case Types.NULL:
        if (convertToString) {
          return "NULL";
        }
        else {
          return null;
        }

      case Types.NUMERIC:
        BigDecimal temp_bd = rs.getBigDecimal(column, rsmd.getScale(column));
        if (convertToString) {
          if (temp_bd == null) {
            //System.out.println("//-->得到的数为null:Types.NUMERIC");
            return null;
          }
          else {
            return temp_bd.toString();
          }
        }
        else {
          return rs.getBigDecimal(column, rsmd.getScale(column));
        }

      case Types.REAL:
        if (convertToString) {
          return String.valueOf(rs.getFloat(column));
        }
        else {
          return new Float(rs.getFloat(column));
        }

      case Types.SMALLINT:
        if (convertToString) {
          return String.valueOf(rs.getShort(column));
        }
        else {
          return new Short(rs.getShort(column));
        }

      case Types.TIME:
        if (convertToString) {
          return (rs.getTime(column)).toString();
        }
        else {
          return rs.getTime(column);
        }

      case Types.TIMESTAMP:
        if (convertToString) {
          //return (rs.getTimestamp(column)).toString();
          Object strTemp = rs.getTimestamp(column);
          if (strTemp == null) {
            return " --- ";
          }
          else {
            //return (rs.getTimestamp(column)).toString();
            return strTemp.toString();
          }
        }
        else {
          return rs.getTimestamp(column);
        }

      case Types.TINYINT:
        if (convertToString) {
          return String.valueOf(rs.getByte(column));
        }
        else {
          return new Byte(rs.getByte(column));
        }

      case Types.VARBINARY:
        if (convertToString) {
          return (rs.getBytes(column) == null ? " --- " :
                  rs.getBytes(column).toString());
        }
        else {
          return rs.getBytes(column);
        }

      case Types.VARCHAR:
        return rs.getString(column);
     
      default:
        if (convertToString) {
          return (rs.getObject(column) == null ? " --- " :
                  rs.getObject(column).toString());
        }
        //return rs.getString(column);
        else {
          return rs.getObject(column);
        }
    }
  }

  public Object getField(int column) throws SQLException {
    return getField(column, false);
  }

  public Object getField(String fieldName) throws SQLException {
    return getField(rs.findColumn(fieldName), false);
  }

  public String getFieldString(int column) throws SQLException {
    return (String) getField(column, true);
    //return (String) getField(column);
  }

  public String getFieldString(String fieldName) throws SQLException {
	  String returnStr = (String) getField(rs.findColumn(fieldName), true);
	  return returnStr;
  }

  public boolean nextRow() throws SQLException {
    if (rs == null) {
      throw new SQLException("platform.dao.SQLProxy: nextRow()");
    }
    return rs.next();
  }

  public void openConn(Connection connection) throws SQLException {
    try {
      if (this.conn != null && !this.conn.isClosed()) {
        return;
      }
      //System.out.println("//--->openConn<---\\");
      this.conn = connection;
      //System.out.println("//--->openConn ok<---\\");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void openConn() throws SQLException {
    try {
      if (conn != null && !conn.isClosed()) {
        return;
      }
      clearResult();
      this.conn = getConnection();
      //System.identityHashCode(con4) "sqlproxy"
      Loggerlog.log("sqlproxy").warn(System.identityHashCode(this)+"-----------链接数据库---------");
       System.out.println("SQLProxy:链接数据库");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setConnectionSwitch(String on_off) throws SQLException {
    if (conn == null) {
      throw new SQLException("conn is null");
    }
    try {
      if (on_off.equalsIgnoreCase("ON")) {
        openConn();
      }
      else if (on_off.equalsIgnoreCase("OFF")) {
        closeConn();
      }
    }
    catch (SQLException ex) {
      throw new SQLException("platform.dao.SQLProxy: setConnectionSwitch()");
    }
  }

//  public Connection getConnection() throws SQLException {
//    try {
//      
//  	 Context initCtx = new InitialContext();
//	 Object obj = (Object) initCtx.lookup("java:/SqlServer2000");
//
//      
//      javax.sql.DataSource ds = (javax.sql.DataSource) obj;
//      
//
//      return ds.getConnection();
//
//    }
//    catch (Exception ex) {
//      ex.printStackTrace();
//      throw new SQLException(
//          "platform.dao.SQLProxy: cannot get Connection pool.");
//    }
//  }
	
}
