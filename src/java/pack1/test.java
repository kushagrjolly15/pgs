/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Kushagr Jolly
 */
@WebService(serviceName = "test")
public class test {
    
    public String id;
    ArrayList<String> courseName;
    ArrayList<String> studentName;

      
    


    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public ArrayList<String> hello(@WebParam(name = "user") String user,@WebParam(name = "pass") String pass)  {
        String type = null;
        ArrayList<String> returnVal = new ArrayList<>();
        System.out.println(user+pass);
        courseName= new ArrayList<>();
        studentName=new ArrayList<>();

        try {
            Connection con;
            System.out.println("connecting");
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println("connected");
            con = DriverManager.getConnection("jdbc:odbc:pgs");
            System.out.println(" driver loaded in connection.jsp");
            Statement stmt = con.createStatement();

            String ID = "select * from [pgs].[dbo].[Users] where userCode='"+user+"' and userPassword='"+pass+"';";
            System.out.println("Query"+ID);
            ResultSet rs = stmt.executeQuery(ID);
            while (rs.next()) {
                type = rs.getString("userTypeId");
                //returnVal="Succesful/"+type;
            }
            String name = "select userId from [pgs].[dbo].[Users] where userCode='"+user+"' and userPassword='"+pass+"';";
            System.out.println("Query"+name);
            ResultSet rs1 = stmt.executeQuery(name);
            while (rs1.next()) {
                id = rs1.getString("userId");
                
                System.out.println(id);
            }
            String query = "select distinct courseName from [pgs].[dbo].[qry_GeneralCourseOffered] where (facultyId1 = '"+id+"' or facultyId2 ='"+id+"' or facultyId3 = '"+id+"') and academicYear = '2014-15'";
            System.out.println("Query"+query);
            ResultSet rs2 = stmt.executeQuery(query);
            int i=0;
            while (rs2.next()) {
                 courseName.add( rs2.getString("courseName"));
                 System.out.println(courseName.get(i));
                 returnVal=courseName;
                 System.out.println(returnVal.get(i));
                 i++;
                 
            }
            /*String query1 = "SELECT distinct [userFname],[userMname],[userLname] FROM [pgs].[dbo].[qryStudentRegisteredCourse] where courseOfferedId in (select distinct(courseOfferedId) from [pgs].[dbo].[qry_GeneralCourseOffered] where facultyId1 = '"+id+"' or facultyId2= '"+id+"' or facultyId3='"+id+"') and academicYear='2014-15';";
            System.out.println("Query"+query1);
            
            ResultSet rs3 = stmt.executeQuery(query1);
            int j=0;
            while (rs3.next()) {
                //System.out.println(rs3.getString("userFname")+rs3.getString("userMname")+" "+rs3.getString("userLname"));
                String sname;
                sname=rs3.getString("userFname")+" "+rs3.getString("userMname")+" "+rs3.getString("userLname");
                studentName.add(sname);
                //System.out.println( studentName.get(j));
                
                j++;
            } */
            
               
             
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnVal;   
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "names")
    public ArrayList<String> names(@WebParam(name = "courseName") String courseName) throws SQLException {
      ArrayList<String> returnVal = new ArrayList<>();

        try {
            Connection con;
            System.out.println("connecting");
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println("connected");
            con = DriverManager.getConnection("jdbc:odbc:pgs");
            System.out.println(" driver loaded in connection.jsp");
            Statement stmt = con.createStatement();
            String query1 = "SELECT userFname,userMname,userLname FROM [pgs].[dbo].[qryStudentRegisteredCourse] where courseName='"+courseName+"' and academicYear='2014-15';";
            System.out.println("Query"+query1);
            
            ResultSet rs3 = stmt.executeQuery(query1);
            int j=0;
            while (rs3.next()) {
                //returnVal.clear();
                //System.out.println(rs3.getString("userFname")+rs3.getString("userMname")+" "+rs3.getString("userLname"));
                String sname;
                sname=rs3.getString("userFname")+" "+rs3.getString("userMname")+" "+rs3.getString("userLname");
                studentName.add(sname);
                //System.out.println( studentName.get(j));
                returnVal=studentName;
                System.out.println(returnVal.get(j));
                j++;
            } 
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return returnVal;
    }
}

