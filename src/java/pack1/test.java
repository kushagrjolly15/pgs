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
    ArrayList<String> returnVal;
    private String did;
    String courseid;
    String sname;
    


    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public ArrayList<String> hello(@WebParam(name = "user") String user,@WebParam(name = "pass") String pass)  {
        String type = null;
        returnVal= new ArrayList<>();
        System.out.println(user+pass);
        courseName= new ArrayList<>();

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
            if(type.contains("ft") || type.contains("gu"))
            {
            String name = "select userId from [pgs].[dbo].[Users] where userCode='"+user+"' and userPassword='"+pass+"';";
            System.out.println("Query"+name);
            ResultSet rs1 = stmt.executeQuery(name);
            while (rs1.next()) {
                id = rs1.getString("userId");
                
                System.out.println(id);
            }
            String query = "select distinct courseName from [pgs].[dbo].[qry_GeneralCourseOffered] where (facultyId1 = '"+id+"' or facultyId2 ='"+id+"' or facultyId3 = '"+id+"') and academicYear = '2014-15' and trimester='I'";
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
            returnVal.add(type);
           }
            else if(type.contentEquals("pr") || type.contentEquals("prhd")){
                
            String name = "select userId from [pgs].[dbo].[Users] where userCode='"+user+"' and userPassword='"+pass+"';";
            System.out.println("Query"+name);
            ResultSet rs1 = stmt.executeQuery(name);
            while (rs1.next()) {
                id = rs1.getString("userId");
                
                System.out.println(id);
            }
            
            String discipline = "select facultyDisciplineId FROM [pgs].[dbo].[qry_faculty] where facultyId ='"+id+"'";
            System.out.println("Query"+discipline);
            ResultSet rs2 = stmt.executeQuery(discipline);
            while (rs2.next()) {
                did = rs2.getString("facultyDisciplineId");
                
                System.out.println(did);
            }
            
            String query = "select distinct courseName from [pgs].[dbo].[qry_GeneralCourseOffered] where disciplineId='"+did+"' and academicYear='2014-15' and trimester='III';";
            System.out.println("Query"+query);
            ResultSet rs3 = stmt.executeQuery(query);
            int i=0;
            while (rs3.next()) {
                 courseName.add( rs3.getString("courseName"));
                 System.out.println(courseName.get(i));
                 
                 returnVal=courseName;
                 System.out.println(returnVal.get(i));
                 i++;
            }
            returnVal.add(type);
            }
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
      returnVal = new ArrayList<>();
      studentName=new ArrayList<>();

        try {
            Connection con;
            System.out.println("connecting");
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println("connected");
            con = DriverManager.getConnection("jdbc:odbc:pgs");
            System.out.println(" driver loaded in connection.jsp");
            Statement stmt = con.createStatement();
            String query1 = "SELECT userFname,userMname,userLname,courseOfferedId,rollNo FROM [pgs].[dbo].[qryStudentRegisteredCourse] where courseName='"+courseName+"' and academicYear='2014-15';";
            System.out.println("Query"+query1);
            ResultSet rs3 = stmt.executeQuery(query1);
            int j=0;
            while (rs3.next()) {
                sname=rs3.getString("userFname")+" "+rs3.getString("userMname")+" "+rs3.getString("userLname")+"#"+rs3.getString("rollNo");
                //courseid=rs3.getString("courseOfferedId");                
                studentName.add(sname);               
                returnVal=studentName;
                System.out.println(returnVal.get(j));
                j++;
            } 
            String query2 = "SELECT courseOfferedId FROM [pgs].[dbo].[qryStudentRegisteredCourse] where courseName='"+courseName+"' and academicYear='2014-15';";
            System.out.println("Query"+query2);
            ResultSet rs4 = stmt.executeQuery(query2);
            while(rs4.next()){
                courseid=rs4.getString("courseOfferedId");
                System.out.println(courseid);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return returnVal;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "insert")
    public String insert(@WebParam(name = "absent") String absent, @WebParam(name = "present")String present,@WebParam(name = "date") String date) throws SQLException {
            String ab[]=absent.split("/");
            String pr[]=present.split("/");
            
            
            for(int i=0;i<ab.length;i++){
            System.out.println(ab[i]);}
            for(int i=0;i<pr.length;i++){
            System.out.println(pr[i]);}
           try {
            Connection con;
            System.out.println("connecting");
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println("connected");
            con = DriverManager.getConnection("jdbc:odbc:pgs");
            System.out.println(" driver loaded in connection.jsp");
            Statement stmt   = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            for(int i=0;i<ab.length;i++){
            String Inserted = "insert into [pgs].[dbo].[Attendance] values('"+ab[i]+"','"+courseid+"','"+date+"','"+id+"','ABSENT')";
            System.out.println("InsertedQuery"+Inserted);
            stmt.executeUpdate(Inserted);
            }
            for(int i=0;i<pr.length;i++){
            String Inserted = "insert into [pgs].[dbo].[Attendance] values('"+pr[i]+"','"+courseid+"','"+date+"','"+id+"','PRESENT')";
            System.out.println("InsertedQuery"+Inserted);
            stmt.executeUpdate(Inserted);
            }
       } catch (ClassNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        

//TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "pending")
    public ArrayList<String> pending() {
        ArrayList<String> pending= new ArrayList<String>();
        try {
            int flag0=0,flag1=0,flag2=0,flag3=0,flag4=0,flag5=0,flag6=0,flag7=0,flag8=0,flag9=0;
            Connection con;
            System.out.println("connecting");
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            System.out.println("connected");
            con = DriverManager.getConnection("jdbc:odbc:pgs");
            System.out.println(" driver loaded in connection.jsp");
            Statement stmt   = con.createStatement();
            String query ="Select orwSubmitted from Student where rollno in (Select distinct(rollNo)From qryStudentAdvCommittee where membertype = 'Chairman' and facultyId= '"+id+"' and studentStatus='current' and (changeType<>'Removed' or changeType Is Null))";
            System.out.println("Query"+query);
            ResultSet rs = stmt.executeQuery(query);
            
            //first query
            String query1 ="Select * from [pgs].[dbo].qryStudentRegisteredCourse where  courseOfferedId in (Select distinct(courseOfferedId)From [pgs].[dbo].[GeneralCourseOffered] where facultyAllocated1 = '"+id+"' or facultyAllocated2 = '"+id+"' or facultyAllocated3 = '"+id+"'  or facultyAllocated4 = '"+id+"'  or facultyAllocated5 = '"+id+"'  or facultyAllocated6 = '"+id+"')";
            System.out.println("Query"+query1);
            ResultSet rsStudentRegCourse = stmt.executeQuery(query1);
            while (rsStudentRegCourse.next()) {
                String x=rsStudentRegCourse.getString("approveFaculty");
                System.out.println(x);
                
                //System.out.println(rs.getString("rollNo"));
                if(x=="NULL" || x==""){
                    flag0=1;
                    //System.out.println(rs.getString("rollNo"));
                }
                   
            }
            if(flag0==1){
                pending.add("Pending");
            }
            else{
                pending.add("Not Pending");
            }
//2nd command
            String query2 ="select * from [pgs].[dbo].[qryStudentRegisteredCourse] where rollNo in(select distinct(rollNo) from qryStudentAdvCommittee where memberType='Chairman' and item='ADVCOM' and facultyId= '"+id+"'  and facultyDisciplineId = '"+did+"' and studentStatus='current' and (changeType<>'Removed' or changeType Is Null))";
            System.out.println("Query"+query2);
            ResultSet rsqryStudRegCourse = stmt.executeQuery(query2);
            while (rsqryStudRegCourse.next()) {
                String x=rsqryStudRegCourse.getString("approvedGuide");
                System.out.println(x);
                
                //System.out.println(rs.getString("rollNo"));
                if(x=="NULL" || x==""){
                   flag1=1;
                }
            }
            if(flag1==1){
                pending.add("Pending");
            }
            else{
                pending.add("Not Pending");
            }
            //3rd command
            String query3 ="Select * from [pgs].[dbo].[qryStudentAdvCommittee] where facultyId = '"+id+"' and  item = 'AdvCom' and studentStatus='current'  and (changeType<>'Removed' or changeType Is Null) and (ppw<>'No' or ppw Is not Null) order by rollNo";
            System.out.println("Query"+query3);
            ResultSet rsAdvisoryCommitee = stmt.executeQuery(query3);
            while (rsAdvisoryCommitee.next()) {
                String x=rsAdvisoryCommitee.getString("status");
                System.out.println(x);
                
                //System.out.println(rs.getString("rollNo"));
                if(x=="NULL" || x==""){
                    flag2=1;
                    //System.out.println(rs.getString("rollNo"));
                }   
            }
            if(flag2==1){
                pending.add("Pending");
            }
            else{
                pending.add("Not Pending");
            }//4th
            String query4 ="Select * from [pgs].[dbo].[GeneralCourseOffered] where  courseOfferedId in (Select distinct(courseOfferedId)From [pgs].[dbo].[qryStudentRegisteredCourse] where facultyAllocated1 = '"+id+"' and trimester = 'I' and academicYear in ('2011-12','2012-13','2013-14','2014-2015') and approveFaculty<>'Disapproved')";
            System.out.println("Query"+query4);
            ResultSet rs4 = stmt.executeQuery(query4);
            while (rs4.next()) {
                String x=rs4.getString("facultyCourseResult");
                System.out.println(x);
                
                //System.out.println(rs.getString("rollNo"));
                if(x=="NULL" || x==""){
                flag3=1;
                }   
            }if(flag3==1){
                pending.add("Pending");
            }
            else{
                pending.add("Not Pending");
            }//5th
            String query5 ="Select * from [pgs].[dbo].[GeneralCourseOffered] where  courseOfferedId in (Select distinct(courseOfferedId)From [pgs].[dbo].[qryStudentRegisteredCourse] where facultyAllocated1 = '"+id+"' and trimester = 'II' and academicYear in ('2011-12','2012-13','2013-14','2014-2015') and approveFaculty<>'Disapproved')";
            System.out.println("Query"+query5);
            ResultSet rs5 = stmt.executeQuery(query5);
            while (rs5.next()) {
                String x=rs5.getString("facultyCourseResult");
                System.out.println(x);
                
                //System.out.println(rs.getString("rollNo"));
                if(x=="NULL" || x==""){
                flag4=1;    
                } 
            }if(flag4==1){
                pending.add("Pending");
            }
            else{
                pending.add("Not Pending");
            }//6th
             String query6 ="Select * from [pgs].[dbo].[GeneralCourseOffered] where  courseOfferedId in (Select distinct(courseOfferedId)From [pgs].[dbo].[qryStudentRegisteredCourse] where facultyAllocated1 = '"+id+"' and trimester = 'III' and academicYear in ('2011-12','2012-13','2013-14','2014-2015') and approveFaculty<>'Disapproved')";
            System.out.println("Query"+query6);
            ResultSet rs6 = stmt.executeQuery(query6);
            while (rs6.next()) {
                String x=rs6.getString("facultyCourseResult");
                System.out.println(x);
                
                //System.out.println(rs.getString("rollNo"));
                if(x=="NULL" || x==""){
                flag5=1;
                }
            }if(flag5==1){
                pending.add("Pending");
            }
            else{
                pending.add("Not Pending");
            }
            
            //7th
            String query7 ="Select * from [pgs].[dbo].[StudentApproval] where item='PPW' and rollNo in (Select distinct(rollNo)From [pgs].[dbo].[qryStudentAdvCommittee] where membertype = 'Chairman' and facultyId= '"+id+"' and studentStatus='current' and (changeType<>'Removed' or changeType Is Null))";
            System.out.println("Query"+query7);
            ResultSet rsStudentApproval = stmt.executeQuery(query7);
            while (rsStudentApproval.next()) {
                String x=rsStudentApproval.getString("approvedGuide");
                //String y=rs.getString("orwSubmitted");
                System.out.println(x);
                
                //System.out.println(rs.getString("rollNo"));
                if((x=="NULL" || x=="") /*&& y=="Yes"*/){
                        flag6=1;
                }
            }if(flag6==1){
                pending.add("Pending");
            }
            else{
                pending.add("Not Pending");
            }
            //8th
            String query8 ="Select * from [pgs].[dbo].[qryStudentAdvCommittee] where  facultyId ='"+id+"' and  item='ORW' and studentStatus='current' and (changeType<>'Removed' or changeType Is Null)";
            System.out.println("Query"+query8);
            ResultSet rsAdvisoryCommitteeORW = stmt.executeQuery(query8);
            while (rsAdvisoryCommitteeORW.next()) {
                String x=rsAdvisoryCommitteeORW.getString("status");
                System.out.println(x);
                
                //System.out.println(rs.getString("rollNo"));
                if(x=="NULL" || x==""){
                flag7=1; 
                }
                
            }if(flag7==1){
                pending.add("Pending");
            }
            else{
                pending.add("Not Pending");
            }
            //9th
            String query9 ="  Select * from [pgs].[dbo].[StudentApproval] where item='ORW' and rollNo in (Select distinct(rollNo)From [pgs].[dbo].[qryStudentAdvCommittee] where membertype = 'Chairman' and facultyId= '"+id+"' and studentStatus='current' and (changeType<>'Removed' or changeType Is Null))";
            System.out.println("Query"+query9);
            ResultSet rsStudentApp = stmt.executeQuery(query9);
            while (rsStudentApp.next()) {
                String x=rsStudentApp.getString("approvedGuide");
                //String y=rs.getString("orwSubmitted");
                System.out.println(x);
                
                //System.out.println(rs.getString("rollNo"));
                if((x=="NULL" || x=="") /*&& y=="Yes"*/){
                flag8=1;
                }
            }if(flag8==1){
                pending.add("Pending");
            }
            else{
                pending.add("Not Pending");
            }
           //last
            String query10 ="select * from StudentApproval where item='Thesis' and rollNo in(select distinct(rollNo) from qryStudentAdvCommittee where memberType='Chairman' and facultyId='"+id+"'  and (changeType<>'Removed' or changeType Is Null))";
            System.out.println("Query"+query10);
            ResultSet rsStudentThesis = stmt.executeQuery(query10);
            while (rsStudentThesis.next()) {
                String x=rsStudentThesis.getString("approvedGuide");
                System.out.println(x);
                
                //System.out.println(rs.getString("rollNo"));
                if(x=="NULL" || x==""){
                flag9=1;
                }
                   
            }if(flag9==1){
                pending.add("Pending");
            }
            else{
                pending.add("Not Pending");
            }
            System.out.println("exit");
        } catch (Exception ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pending;
    }
}

