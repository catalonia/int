package com.tastesync.db.pool;

//import com.tastesync.util.TSConstants;

import org.apache.log4j.Logger;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TSDataSource {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TSDataSource.class);
    private static BasicDataSource poolDSInstance; // Database connection pool
    private Connection conn = null;
    private boolean autoCommit = true;
    public static final String TSDB_JNDI = "jdbc/TastesyncDB";
    private TSDataSource() {
        //TODO Local Testing!
        initialize();
    }

    public static BasicDataSource getPoolDSInstance() {
        return poolDSInstance;
    }

    public static void setPoolDSInstance(BasicDataSource poolDSInstance) {
        TSDataSource.poolDSInstance = poolDSInstance;
    }

    public static TSDataSource getInstance() {
        return TSDataSourceHolder.tsDataSource;
    }

    public Connection getConnection() throws SQLException {
        if (conn == null) {
            try {
                //Double check
                if (TSDataSource.getPoolDSInstance() == null) {
                    InitialContext initContext;
                    Context envContext;

                    try {
                        initContext = new InitialContext();

                        envContext = (Context) initContext.lookup(
                                "java:comp/env");
                        TSDataSource.setPoolDSInstance((BasicDataSource) envContext.lookup(
                                TSDB_JNDI));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                conn = poolDSInstance.getConnection();

                if (conn == null) {
                    throw new SQLException("No Database Connection available");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Connection unavailable: " + e);
                logger.warn("Connection unavailable, close the connection " +
                    "to be able to get another connection ");
            }
        }

        return conn;
    }

    public Connection getConnection(String username, String password)
        throws SQLException {
        if (conn == null) {
            try {
                //Double check
                if (TSDataSource.getPoolDSInstance() == null) {
                    InitialContext initContext;
                    Context envContext;

                    try {
                        initContext = new InitialContext();

                        envContext = (Context) initContext.lookup(
                                "java:comp/env");
                        TSDataSource.setPoolDSInstance((BasicDataSource) envContext.lookup(
                                TSDB_JNDI));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                conn = poolDSInstance.getConnection(username, password);

                if (conn == null) {
                    throw new SQLException("No Database Connection available");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Connection unavailable: " + e);
                logger.warn("Connection unavailable, close the connection " +
                    "to be able to get another connection ");
            }
        }

        return conn;
    }

    private static void initialize() {
        try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

            InitialContext ic = new InitialContext();

            ic.createSubcontext("java:");
            ic.createSubcontext("java:comp");
            ic.createSubcontext("java:comp/env");
            ic.createSubcontext("java:comp/env/jdbc");

            // Construct DataSource
            Properties properties = new Properties();

            //properties.setProperty("url",
            //  "jdbc:mysql://localhost:3306/tastesync");
            properties.setProperty("url",
                "jdbc:mysql://localhost:3306/Delta3_3May2013");
            properties.setProperty("maxActive", "10");
            properties.setProperty("maxIdle", "8");
            properties.setProperty("minIdle", "10");
            properties.setProperty("maxWait", "10");
            properties.setProperty("testOnBorrow", "true");
            //properties.setProperty("username", "username");
            //properties.setProperty("password", "password");
            properties.setProperty("username", "root");
            properties.setProperty("password", "");

            properties.setProperty("validationQuery", "SELECT 1");
            properties.setProperty("removeAbandoned", "true");
            properties.setProperty("removeAbandonedTimeout", "1");
            properties.setProperty("logAbandoned", "true");

            try {
                poolDSInstance = (BasicDataSource) BasicDataSourceFactory.createDataSource(properties);
                System.out.println("poolDSInstance=" + poolDSInstance + " " +
                    poolDSInstance.getMaxIdle());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (poolDSInstance == null) {
                throw new IllegalArgumentException(
                    "Datasource can not be created.");
            }

            ic.bind("java:comp/env/jdbc/TastesyncDB", poolDSInstance);
        } catch (NamingException ex) {
            // Logger.getLogger(MyDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeConnection(Connection conection, Statement statement,
        ResultSet resultset) {
        try {
            if (null != statement) {
                statement.close();
            }

            if (null != resultset) {
                resultset.close();
            }

            if (null != conection) {
                conection.close();
            }
        } catch (SQLException sqle1) {
            sqle1.printStackTrace();
        }
    }

    public boolean getAutoCommit() {
        return autoCommit;
    }

    public void rollback() throws SQLException {
        if (conn != null) {
            if (getAutoCommit()) {
                System.out.println("Cannot rollback a " +
                    "transaction without a begin");
            }

            conn.rollback();
        }

        setAutoCommit(true);
    }

    public void commit() throws SQLException {
        if (conn != null) {
            if (getAutoCommit()) {
                System.out.println(
                    "Cannot commit a transaction without a begin");
            }

            conn.commit();
        }

        setAutoCommit(true);
    }

    public void setAutoCommit(final boolean commit) {
        autoCommit = commit;

        if (conn != null) {
            try {
                conn.setAutoCommit(autoCommit);
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }

    public synchronized boolean testConnection() {
        Connection connection = null;
        boolean isConnected = false;

        try {
            connection = getConnection();

            if (connection != null) {
                isConnected = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            close();
        }

        return isConnected;
    }

    public void begin() {
        setAutoCommit(false);
    }

    public void close() {
        if (conn != null) {
            try {
                if (!conn.getAutoCommit()) {
                    try {
                        conn.commit();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException sqlex) {
                sqlex.printStackTrace();
            } finally {
                conn = null;
                autoCommit = true;
            }
        }
    }

    public static class TSDataSourceHolder {
        public static TSDataSource tsDataSource = new TSDataSource();
    }
}
