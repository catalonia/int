package com.tastesync.db.pool;

import org.apache.log4j.Logger;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
    public static final String TSDB_JNDI = "jdbc/TastesyncDB";
    private static final String PATH_TO_CONFIG_FILE = "./config/TastesyncDB.properties";

    //  "jdbc:mysql://localhost:3306/tastesyncdb");
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/Delta3_3May2013?generateSimpleParameterMetadata=true";
    private static final String DEFAULT_MAX_ACTIVE = "10";
    private static final String DEFAULT_MAX_IDLE = "8";
    private static final String DEFAULT_MIN_IDLE = "10";
    private static final String DEFAULT_MAX_WAIT = "10";
    private static final String DEFAULT_TEST_ON_BORROW = "true";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "";
    private static final String DEFAULT_VALIDATION_QUERY = "SELECT 1";
    private static final String DEFAULT_REMOVED_ABANDONED = "true";
    private static final String DEFAULT_REMOVED_ABANDONED_TIMEOUT = "1";
    private static final String DEFAULT_LOG_ABANDONED = "true";
    private static final String DEFAULT_DRIVERNAME = "com.mysql.jdbc.Driver";
    private Connection conn = null;
    private boolean autoCommit = true;

    private TSDataSource() {
        //Local init / Testing!
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
                throw new SQLException("No Database Connection available");
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

    private static void setDefaultProperties(Properties properties) {
        //properties.setProperty("url",
        properties.setProperty("url", DEFAULT_URL);
        properties.setProperty("maxActive", DEFAULT_MAX_ACTIVE);
        properties.setProperty("maxIdle", DEFAULT_MAX_IDLE);
        properties.setProperty("minIdle", DEFAULT_MIN_IDLE);
        properties.setProperty("maxWait", DEFAULT_MAX_WAIT);
        properties.setProperty("testOnBorrow", DEFAULT_TEST_ON_BORROW);
        properties.setProperty("username", DEFAULT_USERNAME);
        properties.setProperty("password", DEFAULT_PASSWORD);
        properties.setProperty("validationQuery", DEFAULT_VALIDATION_QUERY);
        properties.setProperty("removeAbandoned", DEFAULT_REMOVED_ABANDONED);
        properties.setProperty("removeAbandonedTimeout",
            DEFAULT_REMOVED_ABANDONED_TIMEOUT);
        properties.setProperty("logAbandoned", DEFAULT_LOG_ABANDONED);
        properties.setProperty("driverClassName", DEFAULT_DRIVERNAME);
        properties.setProperty("driverClassName",
            "com.p6spy.engine.spy.P6SpyDriver");
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

            //read from the property file.
            Properties dbProperties = new Properties();

            // Prepend a Root n case the environment variable is defined
            String configRoot = System.getProperty("CONFIG_ROOT");

            if (configRoot == null) {
                configRoot = "";
            } // end if
            else {
                if (!configRoot.endsWith(File.separator)) {
                    configRoot = configRoot + File.separator;
                } // end if
            } // end else

            String filepath = configRoot + PATH_TO_CONFIG_FILE;

            FileInputStream fis = null;

            try {
                fis = new FileInputStream(filepath);
                dbProperties.load(fis);

                String url = dbProperties.getProperty("url");

                if ((url != null) && !url.isEmpty()) {
                    properties.setProperty("url", url);
                } else {
                    properties.setProperty("url", DEFAULT_URL);
                }

                String maxActive = dbProperties.getProperty("maxActive");

                if ((maxActive != null) && !maxActive.isEmpty()) {
                    properties.setProperty("maxActive", maxActive);
                } else {
                    properties.setProperty("maxActive", DEFAULT_MAX_ACTIVE);
                }

                String maxIdle = dbProperties.getProperty("maxIdle");

                if ((maxIdle != null) && !maxIdle.isEmpty()) {
                    properties.setProperty("maxIdle", maxIdle);
                } else {
                    properties.setProperty("maxIdle", DEFAULT_MAX_IDLE);
                }

                String minIdle = dbProperties.getProperty("minIdle");

                if ((minIdle != null) && !minIdle.isEmpty()) {
                    properties.setProperty("minIdle", minIdle);
                } else {
                    properties.setProperty("minIdle", DEFAULT_MIN_IDLE);
                }

                String maxWait = dbProperties.getProperty("maxWait");

                if ((maxWait != null) && !maxWait.isEmpty()) {
                    properties.setProperty("maxWait", maxWait);
                } else {
                    properties.setProperty("maxWait", DEFAULT_MAX_WAIT);
                }

                String testOnBorrow = dbProperties.getProperty("testOnBorrow");

                if ((testOnBorrow != null) && !testOnBorrow.isEmpty()) {
                    properties.setProperty("testOnBorrow", testOnBorrow);
                } else {
                    properties.setProperty("testOnBorrow",
                        DEFAULT_TEST_ON_BORROW);
                }

                String username = dbProperties.getProperty("username");

                if ((username != null) && !username.isEmpty()) {
                    properties.setProperty("username", username);
                } else {
                    properties.setProperty("username", DEFAULT_USERNAME);
                }

                String password = dbProperties.getProperty("password");

                if ((password != null) && !password.isEmpty()) {
                    properties.setProperty("password", password);
                } else {
                    properties.setProperty("password", DEFAULT_PASSWORD);
                }

                String validationQuery = dbProperties.getProperty(
                        "validationQuery");

                if ((validationQuery != null) && !validationQuery.isEmpty()) {
                    properties.setProperty("validationQuery", validationQuery);
                } else {
                    properties.setProperty("validationQuery",
                        DEFAULT_VALIDATION_QUERY);
                }

                String removeAbandoned = dbProperties.getProperty(
                        "removeAbandoned");

                if ((removeAbandoned != null) && !removeAbandoned.isEmpty()) {
                    properties.setProperty("removeAbandoned", removeAbandoned);
                } else {
                    properties.setProperty("removeAbandoned",
                        DEFAULT_REMOVED_ABANDONED);
                }

                String removeAbandonedTimeout = dbProperties.getProperty(
                        "removeAbandonedTimeout");

                if ((removeAbandonedTimeout != null) &&
                        !removeAbandonedTimeout.isEmpty()) {
                    properties.setProperty("removeAbandonedTimeout",
                        removeAbandonedTimeout);
                } else {
                    properties.setProperty("removeAbandonedTimeout",
                        DEFAULT_REMOVED_ABANDONED_TIMEOUT);
                }

                String logAbandoned = dbProperties.getProperty("logAbandoned");

                if ((logAbandoned != null) && !logAbandoned.isEmpty()) {
                    properties.setProperty("logAbandoned", logAbandoned);
                } else {
                    properties.setProperty("logAbandoned", DEFAULT_LOG_ABANDONED);
                }

                String driverClassName = dbProperties.getProperty(
                        "driverClassName");

                if ((driverClassName != null) && !driverClassName.isEmpty()) {
                    properties.setProperty("driverClassName", driverClassName);
                } else {
                    properties.setProperty("driverClassName", DEFAULT_DRIVERNAME);
                }
            } // end try
            catch (FileNotFoundException e) {
                logger.error("File can not be read : " + filepath);
                logger.error("init():FileNotFoundException=", e);
                // ry to add add default values
                setDefaultProperties(properties);
            } // end catch
            catch (IOException e) {
                logger.error("No file to read at path " + filepath);

                logger.error("init():IOException=", e);
            } // end catch
            finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } // end try
                    catch (IOException e) {
                        logger.error("Error while closing the file: IOException=",
                            e);
                    } // end catch
                } // end if
            } // end finally

            try {
                System.out.println("Final database properties=" + properties);
                poolDSInstance = (BasicDataSource) BasicDataSourceFactory.createDataSource(properties);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                if (poolDSInstance != null) {
                    System.out.println("poolDSInstance=" + poolDSInstance +
                        " " + poolDSInstance.getMaxIdle());
                }
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
