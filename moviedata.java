import java.sql.DriverManager;  
import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;
import java.sql.DatabaseMetaData; 
import java.sql.PreparedStatement;   
import java.sql.Statement;  
   
public class moviedata
{  
  private Connection connect() {  
        Connection conn = null;  
        try {  
            
            String url = "jdbc:sqlite:C:/sqlite/mov.db";  
            
            conn = DriverManager.getConnection(url);  
              
            System.out.println("Connection to SQLite has been established.");  
              
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            try {  
                if (conn != null) {  
                    conn.close();  
                }  
            } catch (SQLException ex) {  
                System.out.println(ex.getMessage());  
            }  
        }  
		return conn;
    }  
  
  public static void createNewDatabase(String fileName) {  
   
        String url = "jdbc:sqlite:C:/sqlite/" + fileName;  
   
        try {  
            Connection conn = DriverManager.getConnection(url);  
            if (conn != null) {  
                DatabaseMetaData meta = conn.getMetaData();  
                System.out.println("The driver name is " + meta.getDriverName());  
                System.out.println("A new database has been created.");  
            }  
   
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
	
	 public static void createNewTable() {  

        String url = "jdbc:sqlite:C://sqlite/mov.db";  
          

        String sql = "CREATE TABLE IF NOT EXISTS movie (\n"  
                + " name text NOT NULL,\n"  
                + " lead_actress text NOT NULL,\n"  
				+ " actress text NOT NULL,\n"
                + " director text NOT NULL,\n"
				+ " year integer\n"  
                + ");";  
          
        try{  
            Connection conn = DriverManager.getConnection(url);  
            Statement stmt = conn.createStatement();  
            stmt.execute(sql);  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }  
	
   
  
    public void insert(String name, String lead_actress, String actress, String director, Integer year) {  
        String sql = "INSERT INTO movie(name,lead_actress,actress,dir,year) VALUES(?,?,?,?,?)";  
   
        try{  
            Connection conn = this.connect();  
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, name);  
            pstmt.setString(2, lead_actress);  
			pstmt.setString(3, actress);  
			pstmt.setString(4, director);  
			pstmt.setInt(5, year);  
            pstmt.executeUpdate();  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
    }
   
  
    public void selectAll( String name){ 
		String sql= "Select * from movie";
        String val = "SELECT name from movie";  
		if(val==name)
		{
          
        try {  
            Connection conn = this.connect();  
            Statement stmt  = conn.createStatement();  
            ResultSet rs    = stmt.executeQuery(sql);  
              
            // loop through the result set  
            while (rs.next()) {  
                System.out.println(rs.getString("name") +  "\t" +   
                                   rs.getString("lead_actress") + "\t" + rs.getString("actress") + "\t" +								   
                                    rs.getString("director")+ "\t" +rs.getInt("year") );  
            }  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } 
		}		
    }  
      
     

    public static void main(String[] args) {  
		moviedata app=new moviedata(); 
		 app.connect();
		 app.createNewDatabase("mov.db"); 
		 app.createNewTable(); 
		 app.insert("Kgf","yash","john","neel",2019);
		 app.insert("robert","darshan","prabhu","Jk",2020);  
	     app.selectAll("kgf");  
		 app.selectAll("robert"); 
    }  
   
}