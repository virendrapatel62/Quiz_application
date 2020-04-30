package models;

import constants.DatabaseConstants;
import exceptions.LoginException;

import java.sql.*;
import java.util.ArrayList;

public class Student {
    private Integer id;
    private String firstName;
    private String lastName;
    private String mobile;
    private Character gender;
    private String email;
    private String password;

    public static class MetaData {
        public static final String TABLE_NAME = "students";
        public static final String ID = "ID";
        public static final String MOBILE = "mobile";
        public static final String LAST_NAME = "lastname";
        public static final String FIRST_NAME = "firstName";
        public static final String GENDER = "gender";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
    }

    public Student(String firstName, String lastName, String mobile, Character gender, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public Student(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Student(){

    }

    public Student(Integer id, String firstName, String lastName, String mobile, Character gender, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public Character getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    //    create table
    public static void createTable() {
        String raw = "CREATE TABLE IF NOT EXISTS %s ( \n" +
                "  %s INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "  %s VARCHAR(20),\n" +
                "  %s VARCHAR(20),\n" +
                "  %s VARCHAR(20),\n" +
                "  %s VARCHAR(20),\n" +
                "  %s VARCHAR(20),\n" +
                "  %s char )";
        String query = String.format(raw,
                MetaData.TABLE_NAME,
                MetaData.ID,
                MetaData.FIRST_NAME,
                MetaData.LAST_NAME,
                MetaData.MOBILE,
                MetaData.EMAIL,
                MetaData.PASSWORD,
                MetaData.GENDER
        );

        System.out.println(query);
        try {
            String connectionUrl = DatabaseConstants.CONNECTION_URL;
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query);
            boolean b = ps.execute();
            System.out.println("models.Student.createTable()");
            System.out.println(b);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // save student

    public Student save(){
        String raw = "INSERT into students (%s , %s , %s , %s , %s ,%s )\n" +
                "values ( ? , ? , ? , ? , ? , ?);";

        String query = String.format(raw ,
                MetaData.FIRST_NAME ,
                MetaData.LAST_NAME ,
                MetaData.MOBILE ,
                MetaData.EMAIL ,
                MetaData.PASSWORD ,
                MetaData.GENDER
                );
        String connectionUrl = "jdbc:sqlite:quiz.db";
        try
        {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query , Statement.RETURN_GENERATED_KEYS);
                ps.setString(1 , this.firstName );
                ps.setString(2 , this.lastName );
                ps.setString(3 , this.mobile );
                ps.setString(4 , this.email );
                ps.setString(5 , this.password );
                ps.setString(6 , String.valueOf(this.gender));
                int i = ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                if(keys.next()){
                    this.id  = keys.getInt(1);
                }
                System.out.println("Updated Rows : " + i);
                return this;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Student> getAll(){
        ArrayList<Student> students =  new ArrayList<>();

        String query = String.format("select %s ," +
                        " %s , " +
                        "%s ," +
                        " %s , " +
                        "%s , " +
                        "%s  , %s from %s ;" ,
                MetaData.ID ,
                MetaData.FIRST_NAME ,
                MetaData.LAST_NAME ,
                MetaData.MOBILE ,
                MetaData.EMAIL,
                MetaData.PASSWORD,
                MetaData.GENDER ,
                MetaData.TABLE_NAME
        );
        String connectionUrl = DatabaseConstants.CONNECTION_URL;
        try
        {
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query);

                ResultSet result = ps.executeQuery();
                while(result.next()){
                    Student s = new Student();
                    s.setId(result.getInt(1));
                    s.setFirstName(result.getString(2));
                    s.setLastName(result.getString(3));
                    s.setMobile(result.getString(4));
                    s.setEmail(result.getString(5));
                    s.setPassword(result.getString(6));
                    s.setGender(result.getString(7).charAt(0));
                    students.add(s);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return students;
    }


    public boolean isExists(){

        String query = String.format("select * from %s where %s = ?;" ,
                MetaData.TABLE_NAME ,
                MetaData.EMAIL
        );
        String connectionUrl = DatabaseConstants.CONNECTION_URL;
        try
        {
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1 , this.email );

                ResultSet result = ps.executeQuery();
                if(result.next()){
                    return true;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }


    public void login() throws SQLException , ClassNotFoundException , LoginException{

        String query = String.format("select %s , %s , %s, %s , %s " +
                        " from %s where %s = ? and %s = ?;" ,
                MetaData.ID ,
                MetaData.FIRST_NAME ,
                MetaData.LAST_NAME ,
                MetaData.MOBILE ,
                MetaData.GENDER ,
                MetaData.TABLE_NAME ,
                MetaData.EMAIL,
                MetaData.PASSWORD
        );

        System.out.println(query);
        String connectionUrl = DatabaseConstants.CONNECTION_URL;
            Class.forName(DatabaseConstants.DRIVER_CLASS);
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1 , this.email );
                ps.setString(2 , this.password );

                ResultSet result = ps.executeQuery();
                if(result.next()){
                    this.setId(result.getInt(1));
                    this.setFirstName(result.getString(2));
                    this.setLastName(result.getString(3));
                    this.setMobile(result.getString(4));
                    this.setGender(result.getString(5).charAt(0));
                }else{
                    throw new LoginException("Login Failed..");
                }
            }
        }
    }

