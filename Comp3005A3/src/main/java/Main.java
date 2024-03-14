import java.sql.*;
import java.util.Scanner;
public class Main
{
    public static void getAllStudents(String url, String user, String password)
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();
            statement.executeQuery("Select * from students");
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next())
            {

                new String(new char[15 - resultSet.getString("student_id").length()]).replace("\0", " ");
                System.out.print(resultSet.getString("student_id") + new String(new char[16 - resultSet.getString("student_id").length()]).replace("\0", " ") + "| ");
                System.out.print(resultSet.getString("first_name") + new String(new char[16 - resultSet.getString("first_name").length()]).replace("\0", " ") + "| ");
                System.out.print(resultSet.getString("last_name") + new String(new char[16 - resultSet.getString("last_name").length()]).replace("\0", " ") + "| ");
                System.out.print(resultSet.getString("email") + new String(new char[48 - resultSet.getString("email").length()]).replace("\0", " ") + "| ");
                System.out.print(resultSet.getString("enrollment_date") + "\n");
            }

        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.toString());
        }
    }

    public static void addStudent(String url, String user, String password, String first_name, String last_name, String email, String enrollment_date)
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();
            statement.executeQuery("Insert into students (first_name, last_name, email, enrollment_date) values ('" + first_name + "','" + last_name +  "','" + email + "','" + enrollment_date + "')");
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.toString());
        }
    }

    public static void updateStudent(String url, String user, String password, int id, String email)
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();
            statement.executeQuery("Update students set email = '" + email + "' where student_id = " + id + ";");
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.toString());
        }
    }

    public static void deleteStudent(String url, String user, String password, int id)
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();
            statement.executeQuery("delete from students where student_id = '" + id + "';");
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.toString());
        }
    }

    public static void main(String[] args)
    {
        // store the url, users name and users password to connect to our db
        String url = "jdbc:postgresql://localhost:5432/A3";

        Scanner in = new Scanner(System.in);

        System.out.println("Enter username");
        String user = in.nextLine();

        System.out.println("Enter password");
        String password = in.nextLine();

        String first_name;
        String last_name;
        String email;
        String enrollment_date;
        String id;
        String choice;

        while (true)
        {
            System.out.println("Enter function:\n'1' - getAllStudents\n'2' - addStudent\n'3' - updateStudent\n'4' - deleteStudent\n'5' - Exit");
            choice = in.nextLine();

            if(choice.equals("1"))
            {
                getAllStudents(url, user, password);
            }
            else if (choice.equals("2"))
            {
                System.out.println("Enter first name");
                first_name = in.nextLine();
                System.out.println("Enter last name");
                last_name = in.nextLine();
                System.out.println("Enter email");
                email = in.nextLine();
                System.out.println("Enter date enrolled (separated by '-', eg. '2023-09-02')");
                enrollment_date = in.nextLine();

                addStudent(url, user, password, first_name, last_name, email, enrollment_date);
            }
            else if (choice.equals("3"))
            {
                System.out.println("Enter id");
                id = in.nextLine();
                System.out.println("Enter email");
                email = in.nextLine();

                updateStudent(url, user, password, Integer.parseInt(id), email);
            }
            else if (choice.equals("4"))
            {
                System.out.println("Enter id");
                id = in.nextLine();

                deleteStudent(url, user, password, Integer.parseInt(id));
            }
            else if (choice.equals("5"))
            {
                break;
            }
            else
            {
                System.out.println("invalid choice");
            }
        }

        in.close();
    }
}