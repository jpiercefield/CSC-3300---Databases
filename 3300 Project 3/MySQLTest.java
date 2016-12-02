
/**
 * 
 * @James Logan Piercefield 
 * See Word Doc. For Program Documentation
 */

import java.sql.*;
import java.util.*;
class MySQLTest
{
    public void main()
    {
        String input = new String("");
        System.out.println("Welcome to University Database!\n");
        System.out.println("Would you like to login as brown(staff) or grey(student)?");
        boolean correct = false;
        while(correct != true)
        {
            input = Keyboard.getKeyboard().readString("Enter b for brown or g for grey: ");
            if(input.equals("b") || input.equals("g"))
            {
                correct = true;
            } else {
                System.out.println("You've entered an invalid option, please try again.");
            }
        }
        if(input.equals("b"))
        {
            brown();            
        } else if(input.equals("g")) {
            grey(); 
        }
    } 

    private void brown()
    {
        try
        {
            String input = new String("");
            boolean correct = false;
            System.out.println("Hello user: Brown");
            while(correct == false)
            {               
                input = Keyboard.getKeyboard().readString("Please enter password: ");
                if(input.equals("brown123"))
                {
                    correct = true;
                } else {
                    System.out.println("You've entered an incorrect password for user: Brown");
                    System.out.println("Please try again.");
                }
            }
            System.out.println("---------------Logging In-----------------\n");
            Class.forName( "com.mysql.jdbc.Driver" );
            Connection conn = DriverManager.getConnection( "jdbc:mysql://127.0.0.1/university?user=jpiercefield&password=classwork");
            for( SQLWarning warn = conn.getWarnings(); warn != null; warn = warn.getNextWarning() )
            {
                System.out.println( "SQL Warning:" );
                System.out.println( "State  : " + warn.getSQLState() );
                System.out.println( "Message: " + warn.getMessage() );
                System.out.println( "Error  : " + warn.getErrorCode() );
            }

            boolean end = false;
            while(end != true)
            {
                brownMenu();
                List<String> deptNameList = new ArrayList<String>();
                Statement stmtList = conn.createStatement();
                ResultSet rsList = stmtList.executeQuery( "SELECT dept_name, building FROM department;" );
                while( rsList.next() )
                {
                    deptNameList.add(rsList.getString(1));
                }
                rsList.close();
                stmtList.close();

                List<String> courseIdList = new ArrayList<String>();
                Statement stmtList2 = conn.createStatement();
                ResultSet rsList2 = stmtList2.executeQuery( "SELECT DISTINCT course_id FROM course;" );
                while( rsList2.next() )
                {
                    courseIdList.add(rsList2.getString(1));
                }
                rsList2.close();
                stmtList2.close();

                input = Keyboard.getKeyboard().readString("Enter number of option: " );
                if(input.equals("1"))
                {
                    System.out.println("Thanks for coming! --- See ya!");
                    System.out.println("Program By: James 'Logan' Piercefield");
                    end = true;
                } else if(input.equals("2")) {
                    System.out.println("Retrieve info about all departments without info about budgets: ");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery( "SELECT dept_name, building FROM department;" );
                    while( rs.next() )
                    {
                        System.out.println("Dept_name: " + rs.getString(1) + " --Building: " + rs.getString(2));
                    }
                    rs.close();
                    stmt.close();
                } else if(input.equals("3")) {
                    String courseId = "";
                    String title = "";
                    String deptName = "";
                    int credits = 0;
                    System.out.println("Create Course:");
                    boolean test = false;
                    while(test == false)
                    {
                        courseId = Keyboard.getKeyboard().readString("Enter course_id (VARCHAR(8)) : ");
                        if(courseId.length() > 8)
                        {
                            System.out.println("Sorry: course_id must be 8 characters or less, please try again.");
                        } else {
                            test = true;
                        }                    
                    }
                    test = false;
                    while(test == false)
                    {
                        title = Keyboard.getKeyboard().readString("Enter title (VARCHAR(50)) : ");
                        if(title.length() > 50)
                        {
                            System.out.println("Sorry: title must be 50 characters or less, please try again.");
                        } else {
                            test = true;
                        }                    
                    }
                    test = false;
                    while(test == false)
                    {
                        deptName = Keyboard.getKeyboard().readString("Enter dept_name (VARCHAR(20)) : ");
                        if(deptName.length() > 20)
                        {
                            System.out.println("ERROR: Sorry: title must be 20 characters or less, please try again.");
                        } else if(!deptNameList.contains(deptName)) {
                            System.out.println("ERROR: Sorry: " + deptName + " is not a valid Department Name.");
                            System.out.println("Here is the list you have to choose from: " + deptNameList);
                        } else {
                            test = true;
                        }                    
                    }
                    test = false;
                    String strCredits = "";
                    while(test == false)
                    {
                        credits = Keyboard.getKeyboard().readInt("Enter amount of credits (0-99) : ");
                        if(credits < 0 || credits > 99)
                        {
                            System.out.println("ERROR: Sorry: You've entered a invalid amount of credits. ");
                        } else {
                            strCredits = Integer.toString(credits);
                            test = true;
                        }
                    }
                    String query = "INSERT INTO course VALUES ('";
                    query += courseId;
                    query += "', '";
                    query += title;
                    query += "', '";
                    query += deptName;
                    query += "', ";
                    query += strCredits;
                    query += ");";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(query);
                    System.out.println("Insert has completed, Thanks!");
                    
                    stmt.close();
                } else if(input.equals("4")) {
                    System.out.println("Retrieve all courses");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery( "SELECT * FROM course;" );
                    while( rs.next() )
                    {
                        System.out.println( rs.getString(1) );
                    }
                    rs.close();
                    stmt.close();
                } else if(input.equals("5")) {
                    System.out.println("Update Course: ");
                    String value = "";
                    boolean pick = false;
                    while(pick == false)
                    {
                        System.out.println("What would you like to update?\nOPTIONS:");
                        System.out.println("(1) course_id");
                        System.out.println("(2) title");
                        System.out.println("(3) dept_name");
                        System.out.println("(4) credits");
                        value = Keyboard.getKeyboard().readString("Enter Value of Option: ");
                        if(value.equals("1") || value.equals("2") || value.equals("3") || value.equals("4"))
                        {
                            pick = true;
                        } else {
                            System.out.println("ERROR: Sorry: You've entered an invalid option.");
                        }
                    }
                    String courseId = "";
                    String newCourseId = "";
                    String title = "";
                    String deptName = "";
                    int credits = 0;
                    if(value.equals("1"))
                    {
                        boolean inList = false;
                        while(inList == false)
                        {
                            courseId = Keyboard.getKeyboard().readString("Enter the ORIGINAL course_id, to which you'd like to change: ");
                            if(courseIdList.contains(courseId)) {
                                inList = true;
                            } else {
                                System.out.println("ERROR: Sorry: The course_id you entered, is not in the database.");
                                System.out.println("Here is the list of course_id's: " + courseIdList);
                            }
                        }
                        inList = false;
                        while(inList == false)
                        {
                            newCourseId = Keyboard.getKeyboard().readString("Enter the NEW course_id: ");
                            if(newCourseId.length() > 8 || newCourseId.length() <= 0)
                            {
                                System.out.println("ERROR: Sorry: course_id must be greater than 0 chracters, and less than 9.");
                            } else {
                                inList = true;
                            }
                        }
                        String query = "UPDATE course SET course_id = '";
                        query += courseId;
                        query += "' WHERE course_id = '";
                        query += newCourseId;
                        query += "';";

                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate( query );
                       
                        System.out.println("Thanks for Updating!! ");

                        stmt.close();
                    } else if(value.equals("2")) {
                        boolean inList = false;
                        while(inList == false)
                        {
                            courseId = Keyboard.getKeyboard().readString("Enter the course_id, to which you'd like to change the title to: ");
                            if(courseIdList.contains(courseId))
                            {
                                inList = true;
                            } else {
                                System.out.println("ERROR: Sorry: The course_id you entered, is not in the database.");
                                System.out.println("Here is the list of course_id's: " + courseIdList);
                            }
                        }
                        inList = false; //(not checking a list here)
                        while(inList == false)
                        {
                            title = Keyboard.getKeyboard().readString("Enter the new title: ");
                            if(title.length() <= 0 || title.length() > 50)
                            {
                                System.out.println("title length must be between 1 and 50. VARCHAR(50)");
                            } else {
                                inList = true;
                            }   
                        }
                        String query = "UPDATE course SET title = '";
                        query += title;
                        query += "' WHERE course_id = '";
                        query += courseId;
                        query += "';";
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate( query );
                        
                        System.out.println("Update complete!! Thanks!\n");

                        stmt.close();

                    } else if(value.equals("3")) {
                        System.out.println("Update dept_name:");
                        boolean inList = false;
                        while(inList == false)
                        {
                            courseId = Keyboard.getKeyboard().readString("Enter the course_id, to which you'd like to change the dept_name: ");
                            if(courseIdList.contains(courseId))
                            {
                                inList = true;
                            } else {
                                System.out.println("ERROR: Sorry: The course_id you entered, is not in the database.");
                                System.out.println("Here is the list of course_id's: " + courseIdList);
                            }
                        }
                        inList = false;
                        while(inList == false)
                        {
                            deptName = Keyboard.getKeyboard().readString("Enter name of NEW dept_name for course_id " + courseId + " :");
                            if(deptNameList.contains(deptName))
                            {
                                inList = true;
                            } else {
                                System.out.println("You've entered an invalid dept_name, which is not in the database");
                                System.out.println("Here is the list of dept_name's: " + deptNameList);
                            }
                        }

                        String query = "UPDATE course SET dept_name = '";
                        query += deptName;
                        query += "' WHERE course_id = '";
                        query += courseId;
                        query += "';";
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate( query );
                        System.out.println("Update complete!! Thanks\n");
                        stmt.close();
                    } else if(value.equals("4")) {
                        boolean inList = false;
                        while(inList == false)
                        {
                            courseId = Keyboard.getKeyboard().readString("Enter the course_id, to which you'd like to change the credits: ");
                            if(courseIdList.contains(courseId))
                            {
                                inList = true;
                            } else {
                                System.out.println("ERROR: Sorry: The course_id you entered, is not in the database.");
                                System.out.println("Here is the list of course_id's: " + courseIdList);
                            }
                        }
                        inList = false;
                        while(inList == false) //not checking list here
                        {
                            credits = Keyboard.getKeyboard().readInt("Enter the new credits for course_id - " + courseId + " : ");
                            if(credits < 0 || credits > 99)
                            {
                                System.out.println("ERROR: Sorry: credits must be between 0 and 99");
                            } else {
                                inList = true;
                            }
                        }
                        String creditStr = Integer.toString(credits);
                        String query = "UPDATE course SET credits = ";
                        query += creditStr;
                        query += " WHERE course_id = '";
                        query += courseId;
                        query += "';";
                        Statement stmt = conn.createStatement();
                        stmt.executeUpdate( query );
                        System.out.println("Update complete! - Thank you!\n");

                        stmt.close();

                    }
                } else if(input.equals("6")) {
                    System.out.println("Delete course:");
                    boolean inList = false;
                    String courseId = "";
                    while(inList == false)
                    {
                        courseId = Keyboard.getKeyboard().readString("Enter the course_id, for the course to which you'd like to DELETE: ");
                        if(courseIdList.contains(courseId))
                        {
                            inList = true;
                        } else {
                            System.out.println("ERROR: Sorry: The course_id you entered, is not in the database.");
                            System.out.println("Here is the list of course_id's: " + courseIdList);
                        }
                    }
                    String query = "DELETE FROM course WHERE course_id = '";
                    query += courseId;
                    query += "';";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate( query );
                    System.out.println("You've successfully delete this course, Thanks!\n");

                    stmt.close();
                } else if(input.equals("7")) {
                    System.out.println("Create new course section:");
                    String sCourseId = Keyboard.getKeyboard().readString("Enter course_id (VARCHAR(8)) :");
                    String sSecId = Keyboard.getKeyboard().readString("Enter sec_id (VARCHAR(8)) :");
                    String sSemester = Keyboard.getKeyboard().readString("Enter semester (VARCHAR(6)) :");
                    boolean yearIn = false;
                    int sYear = 0;
                    while(yearIn == false)
                    {
                        sYear = Keyboard.getKeyboard().readInt("Enter year (DECIMAL(4,0) EX: 2016)  : ");
                        if(sYear < 0 || sYear > 9999)
                        {
                            System.out.println("ERROR: Sorry: Year must be between 0 and 9999, ex: 2016");
                        } else {
                            yearIn = true;
                        }
                    }
                    String sYearStr = Integer.toString(sYear);
                    String sBuilding = Keyboard.getKeyboard().readString("Enter building (VARCHAR(15)) :");
                    String sRoomNum = Keyboard.getKeyboard().readString("Enter room_number (VARCHAR(7)) :");
                    String sTimeSlotId = Keyboard.getKeyboard().readString("Enter time_slot_id (VARCHAR(4)) :");
                    String query = "INSERT INTO section VALUES ('";
                    query += sCourseId;
                    query += "', '";
                    query += sSecId;
                    query += "', '";
                    query += sSemester;
                    query += "', ";
                    query += sYearStr;
                    query += ", '";
                    query += sBuilding;
                    query += "', '";
                    query += sRoomNum;
                    query += "', '";
                    query += sTimeSlotId;
                    query += "');";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate( query );

                    System.out.println("The values have been inserted, Thanks!!\n");
                    stmt.close();
                } else if(input.equals("8")) {
                    System.out.println("Retrieve all course sections:");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery( "SELECT * FROM section;" );
                    while( rs.next() )
                    {
                        System.out.println( rs.getString(1) );
                    }
                    rs.close();
                    stmt.close();
                } else if(input.equals("9")) {
                    String query = Keyboard.getKeyboard().readString("Type out update statement for course section: ");
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery( query );
                    while( rs.next() )
                    {
                        System.out.println( rs.getString(1) );
                    }
                    rs.close();
                    stmt.close();
                } else if(input.equals("10")) {
                    System.out.println("Delete Course Section:");
                    String sCourseId = Keyboard.getKeyboard().readString("Type in course_id of section you'd like to delete: ");
                    String sSecId = Keyboard.getKeyboard().readString("Type in sec_id of section you'd like to delete: ");
                    String sSemester = Keyboard.getKeyboard().readString("Type in semester of section you'd like to delete: ");
                    boolean yearIn = false;
                    int sYear = 0;
                    while(yearIn == false)
                    {
                        sYear = Keyboard.getKeyboard().readInt("Enter year of section you'd like to delete : ");
                        if(sYear < 0 || sYear > 9999)
                        {
                            System.out.println("ERROR: Sorry: Year must be between 0 and 9999, ex: 2016");
                        } else {
                            yearIn = true;
                        }
                    }
                    String sYearString = Integer.toString(sYear);
                    String query = "DELETE FROM section WHERE course_id = '";
                    query += sCourseId;
                    query += "' AND sec_id = '";
                    query += sSecId;
                    query += "' AND semester = '";
                    query += sSemester;
                    query += "' AND year = ";
                    query += sYear;
                    query += ";";
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(query);
                    stmt.close();
                    System.out.println("Successfully deleted this course section.\n");
                } else {
                    System.out.println("You've entered an invalid option");
                }
            }
            conn.close();
        } catch( SQLException se) {
            System.out.println( "SQL Exception: " );

            while( se != null )
            {
                System.out.println( "State  : " + se.getSQLState() );
                System.out.println( "Message: " + se.getMessage() );
                System.out.println( "Error  : " + se.getErrorCode() );

                se = se.getNextException() ;
            }
        } catch( Exception e) {
            System.out.println( e );
        }
    }

    private void grey()
    {
        try
        {
            String input = "";
            boolean correct = false;
            System.out.println("Hello user: Grey");
            while(correct == false)
            {               
                input = Keyboard.getKeyboard().readString("Please enter password: ");
                if(input.equals("grey123"))
                {
                    correct = true;
                } else {
                    System.out.println("You've entered an incorrect password for user: Grey");
                    System.out.println("Please try again.");
                }
            }
            System.out.println("---------------Logging In-----------------\n");
            Class.forName( "com.mysql.jdbc.Driver" );
            Connection conn = DriverManager.getConnection( "jdbc:mysql://127.0.0.1/university?user=jpiercefield&password=classwork");
            for( SQLWarning warn = conn.getWarnings(); warn != null; warn = warn.getNextWarning() )
            {
                System.out.println( "SQL Warning:" );
                System.out.println( "State  : " + warn.getSQLState() );
                System.out.println( "Message: " + warn.getMessage() );
                System.out.println( "Error  : " + warn.getErrorCode() );
            }

            List<String> studentIdList = new ArrayList<String>();
            Statement stmtList3 = conn.createStatement();
            ResultSet rsList3 = stmtList3.executeQuery( "SELECT DISTINCT ID FROM student;" );

            while( rsList3.next() )
            {
                studentIdList.add(rsList3.getString(1));
            }
            rsList3.close();
            stmtList3.close();

            List<String> sectionsList = new ArrayList<String>();
            Statement stmtList4 = conn.createStatement();
            ResultSet rsList4 = stmtList4.executeQuery( "SELECT course_id, sec_id, semester, year FROM section;" );

            while( rsList4.next() )
            {
                String cID = rsList4.getString(1);
                String sID = rsList4.getString(2);
                String tempSemester = rsList4.getString(3);
                String tempYear = rsList4.getString(4);
                String fStr = cID;
                fStr += ", ";
                fStr += sID;
                fStr += ", ";
                fStr += tempSemester;
                fStr += ", ";
                fStr += tempYear;

                sectionsList.add(fStr);
            }
            rsList4.close();
            stmtList4.close();

            String studentID = "";
            boolean registeredStudent = false;
            while(registeredStudent == false){
                studentID = Keyboard.getKeyboard().readString("Please enter your student 'ID' (VARCHAR(5)): "); //used till program end
                if(!studentIdList.contains(studentID))
                {
                    System.out.println("You've entered an invalid student, ID. This ID is not contained within the database");
                    System.out.println("If you're a prospecting student please see the admissions office.");
                    System.out.println("TRY AGAIN");
                } else {
                    registeredStudent = true;
                }
            }

            boolean end = false;
            while(end != true)
            {
                greyMenu();
                input = Keyboard.getKeyboard().readString("Enter 1, 2, 3, 4, or 5 (based on list above): ");
                if(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5"))
                {
                    if(input.equals("1"))
                    {
                        System.out.println("EXIT PROGRAM: See ya!");
                        System.out.println("Program By: James 'Logan' Piercefield");
                        end = true;
                    } else if(input.equals("2")) {
                        boolean doneReg = false;
                        while(doneReg == false)
                        {
                            boolean properSection = false;
                            String fStr2 = "";
                            String courseID = "";
                            String secID = "";
                            String semester = "";
                            String year = "";
                            while(properSection == false)
                            {
                                System.out.println("Register for a course section");
                                courseID = Keyboard.getKeyboard().readString("Enter course_id (VARCHAR(8)) : ");
                                secID = Keyboard.getKeyboard().readString("Enter sec_id (VARCHAR(8)) : ");
                                semester = Keyboard.getKeyboard().readString("Enter semester (VARCHAR(6)) : ");
                                year = Keyboard.getKeyboard().readString("Enter year (DECIMAL(4,0)) Ex: 2016 : ");
                                fStr2 = courseID;
                                fStr2 += ", ";
                                fStr2 += secID;
                                fStr2 += ", ";
                                fStr2 += semester;
                                fStr2 += ", ";
                                fStr2 += year;
                                if(!sectionsList.contains(fStr2))
                                {
                                    System.out.println("ERROR: Sorry: You've entered an invalid course_id, sec_id, semester, year combination.");
                                    System.out.println("Please try again.");
                                } else {
                                    properSection = true;
                                }
                            }

                            List<String> takesCIDList = new ArrayList<String>();
                            Statement stmtList5 = conn.createStatement();
                            String tempQuery = "SELECT DISTINCT course_id FROM takes WHERE ID = '";
                            tempQuery += studentID;
                            tempQuery += "';";
                            ResultSet rsList5 = stmtList5.executeQuery( tempQuery );

                            while( rsList5.next() )
                            {
                                takesCIDList.add(fStr2); //had "fStr" --- placer ..
                            }
                            rsList5.close();
                            stmtList5.close();

                            boolean preReqCheck = false;

                            String tempStr = "SELECT prereq_id FROM prereq WHERE course_id = '";
                            tempStr += courseID;
                            tempStr += "');";
                            Statement prSt = conn.createStatement();
                            ResultSet preRs = prSt.executeQuery( tempStr );
                            boolean issue = false;
                            while( preRs.next() )
                            {
                                //List of all takes, compare prereq id w. course_id from take
                                if(!takesCIDList.contains(preRs.getString(1)))
                                {
                                    issue = true;
                                } 
                            } 
                            preRs.close();
                            prSt.close();

                            if(issue == true)
                            {
                                System.out.println("ERROR Sorry: You do not have the proper pre-req's to register for this class.");
                                System.out.println("Please choose a new course, Thanks! ");
                            } else {
                                //insert into takes here studentID, courseID, secId, semester, and year, and (grade to null)
                                String tempQuery2 = "INSERT INTO takes (ID, course_id, sec_id, semester, year, grade) VALUES('";
                                tempQuery2 += studentID;
                                tempQuery2 += "', '";
                                tempQuery2 += courseID;
                                tempQuery2 += "', '";
                                tempQuery2 += secID;
                                tempQuery2 += "', '";
                                tempQuery2 += semester;
                                tempQuery2 += "', '";
                                tempQuery2 += year;
                                tempQuery2 += "', NULL);";
                                Statement stmtList6 = conn.createStatement();
                                stmtList6.executeUpdate( tempQuery2 );
                                System.out.println("Thanks, you've successfully inserted!!\n");
                                stmtList6.close();
                                System.out.println("Thanks, you're registered for this class!\n\n");
                                doneReg = true;
                            }
                        }
                    } else if(input.equals("3")) {
                        boolean doneReg = false;
                        while(doneReg == false)
                        {
                            boolean properSection = false;
                            String fStr2 = "";
                            String courseID = "";
                            String secID = "";
                            String semester = "";
                            String year = "";
                            while(properSection == false)
                            {
                                System.out.println("\nDrop a course section:");
                                courseID = Keyboard.getKeyboard().readString("Enter course_id (VARCHAR(8)) : ");
                                secID = Keyboard.getKeyboard().readString("Enter sec_id (VARCHAR(8)) : ");
                                semester = Keyboard.getKeyboard().readString("Enter semester (VARCHAR(6)) : ");
                                year = Keyboard.getKeyboard().readString("Enter year (DECIMAL(4,0)) Ex: 2016 : ");
                                fStr2 = courseID;
                                fStr2 += ", ";
                                fStr2 += secID;
                                fStr2 += ", ";
                                fStr2 += semester;
                                fStr2 += ", ";
                                fStr2 += year;
                                if(!sectionsList.contains(fStr2))
                                {
                                    System.out.println("ERROR: Sorry: You've entered an invalid course_id, sec_id, semester, year combination.");
                                    System.out.println("Please try again.");
                                } else {
                                    properSection = true;
                                }
                            }

                            String query = "DELETE FROM takes WHERE ID = '";
                            query += studentID;
                            query += "' AND course_id = '";
                            query += courseID;
                            query += "' AND sec_id = '";
                            query += secID;
                            query += "' AND semester = '";
                            query += semester;
                            query += "' AND year = '";
                            query += year;
                            query += "';";

                            Statement stmt7 = conn.createStatement();
                            stmt7.executeUpdate( query );
                            stmt7.close();
                            System.out.println("\n\nYou've successfully dropped this course.\n");
                            doneReg = true;
                        }
                    } else if(input.equals("4")) {
                        System.out.println("Retrieve all classes currently taking:");
                        String query = "SELECT * FROM takes WHERE ID = '";
                        query += studentID;
                        query += "' AND grade IS NULL;";
                        Statement stmt7 = conn.createStatement();
                        ResultSet rs7 = stmt7.executeQuery( query );
                        while( rs7.next() )
                        {
                            System.out.println( "Course_ID: " + rs7.getString(2) );
                            System.out.println( "Sec_ID: " + rs7.getString(3) );
                            System.out.println( "Semester: " + rs7.getString(4) );
                            System.out.println( "Year: " + rs7.getString(5) );
                        }
                        rs7.close();
                        stmt7.close();

                    } else if(input.equals("5")) {
                        System.out.println("View Transcript:\n");
                        String query = "SELECT * FROM takes WHERE ID = '";
                        query += studentID;
                        query += "' ORDER BY year ASC;";
                        Statement stmt7 = conn.createStatement();
                        ResultSet rs7 = stmt7.executeQuery( query );

                        while( rs7.next() )
                        {
                            System.out.println( "Course_ID: " + rs7.getString(2) + " ");
                            System.out.println( "Sec_ID: " + rs7.getString(3) + " ");
                            System.out.println( "Semester: " + rs7.getString(4));
                            System.out.println( "Year: " + rs7.getString(5));
                            System.out.println( "Grade: " + rs7.getString(6));
                        }
                        rs7.close();
                        stmt7.close();
                        
                        query = "SELECT grade FROM takes WHERE ID = '";
                        query += studentID;
                        query += "' AND grade IS NOT NULL";
                        Statement stmt8 = conn.createStatement();
                        ResultSet rs8 = stmt8.executeQuery( query );
                        double GPA = 0.0;
                        double counter = 0.0;
                        while( rs8.next() )
                        {
                            String temp = rs8.getString(1);
                            if(temp.equals("A+"))
                            {
                                GPA += 4.0;
                            } else if(temp.equals("A")) {
                                GPA += 4.0;
                            } else if(temp.equals("A-")) {
                                GPA += 3.7;
                            } else if(temp.equals("B+")) {
                                GPA += 3.3;
                            } else if(temp.equals("B")) {
                                GPA += 3.0;
                            } else if(temp.equals("B-")) {
                                GPA += 2.7;
                            } else if(temp.equals("C+")) {
                                GPA += 2.3;
                            } else if(temp.equals("C")) {
                                GPA += 2.0;
                            } else if(temp.equals("C-")) {
                                GPA += 1.7;
                            } else if(temp.equals("D+")) {
                                GPA += 1.3;
                            } else if(temp.equals("D")) {
                                GPA += 1.0;
                            } else if(temp.equals("D-")) {
                                GPA += 0.7;
                            } else if(temp.equals("F")) {
                                GPA += 0.0;
                            }
                            counter++;
                        }
                        double GPACount = 0;
                        GPACount = GPA / counter;
                        String strGPA = String.valueOf(GPACount);
                        System.out.println("GPA: " + strGPA);
                        rs8.close();
                        stmt8.close();
                    }
                } else {
                    System.out.println("\n\nERROR: Sorry: You've entered an invalid selection. Enter (1,2,3,4, or 5");
                }
            }
            conn.close();
        } catch( SQLException se) {
            System.out.println( "SQL Exception: " );

            while( se != null )
            {
                System.out.println( "State  : " + se.getSQLState() );
                System.out.println( "Message: " + se.getMessage() );
                System.out.println( "Error  : " + se.getErrorCode() );

                se = se.getNextException() ;
            }
        } catch( Exception e) {
            System.out.println( e );
        } 
        
    }

    private void brownMenu()
    {
        System.out.println("\n\nBrown, Here are your options:");
        System.out.println("(1)  Exit Program");
        System.out.println("(2)  Retrieve info about all departments (without budgets)");
        System.out.println("(3)  Create course");
        System.out.println("(4)  Retrieve all courses");
        System.out.println("(5)  Update course");
        System.out.println("(6)  Delete course");
        System.out.println("(7)  Create course section");
        System.out.println("(8)  Retrieve all course sections");
        System.out.println("(9)  Update course section");
        System.out.println("(10) Delete course section");
    }

    private void greyMenu()
    {
        System.out.println("\n\nGrey, Here are your options:");
        System.out.println("(1) Exit Program");
        System.out.println("(2) Register for a course section");
        System.out.println("(3) Drop a course section");
        System.out.println("(4) Retrieve all course sections currently taking (grade is null)");
        System.out.println("(5) View transcript");
    }
}
