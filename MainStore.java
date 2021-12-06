import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MainStore {

	private static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		String url = "jdbc:mysql://localhost:3306/";
		String user = "TestUser";
		String password = "test";

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, user, password);

		Statement stt = con.createStatement();
		stt.execute("USE pizza_store");
		
		//comment out the function below to just view the result set of the information
		//otherwise it will create a new pizza to add to the database.
		insertPizza(con);
		
		
		displayPizzaInformation(stt);
		
	}

	private static void displayPizzaInformation(Statement stt) throws SQLException {
		ResultSet resultSet = stt.executeQuery("select NameOfPizza, CostOfPizza, DescriptionOfPizza, TimeToMake from pizzas");
		System.out.println("Here are your pizzas so far: ");
		
		while(resultSet.next()) {
			String name = resultSet.getString(1);
			int cost = resultSet.getInt(2);
			String desc = resultSet.getString(3);
			int time = resultSet.getInt(4);
			
			System.out.println(name + ": " + desc + " $" + cost + " Expected time: " + time);
		}
	}

	private static void insertPizza(Connection con) throws SQLException {
		
		String a = getNamePizza("Enter the name of your pizza: ");
		int b = getPizzaCost("Enter the cost of your pizza: ");
		String c = getPizzaDescription("Now lets build our pizza!");
		int d = getPizzaTime("Enter the time in minutes to make your pizza (must be greater than 0 and less than 120): ");
		
		PreparedStatement prep = con.prepareStatement(
				"INSERT INTO pizzas (NameOfPizza, CostOfPizza, DescriptionOfPizza, TimeToMake) VALUES (?,?,?,?)");
		prep.setString(1, a);
		prep.setInt(2, b);
		prep.setString(3, c);
		prep.setInt(4, d);
		int row = prep.executeUpdate();
		
		System.out.println("The pizza was added successfully!");
	}
	private static String getNamePizza(String prompt) {
		String r;
		do {
			System.out.print(prompt);
			r = input.nextLine();
			if (r == null || r.length() < 1 || r.length() > 20) {
				System.out.print("The name of the pizza can not be null, less than 1 character or greater than 20 characters. Re-Enter value. \n");
			}

		} while (r == null || r.length() < 1 || r.length() > 20);
		return r;
	}
	private static int getPizzaCost(String prompt) {
		int r = -1;
		do {
			try {
				System.out.print(prompt);
				r = Integer.parseInt(input.nextLine());

				if (r < 0)
					System.out.print("Enter a value greater than 0! Re-enter value. \n");
			} catch (Exception E) {
				System.out.print(
						"Enter a valid value. It must be a number greater than 0 and not contain any letters or special characters. \n");
			}

		} while (r < 0);
		return r;
		
	}
	private static String getPizzaDescription(String prompt) {
		int sauce = 0;
		int meat = 0;
		int veggie = 0;
		
		String Sauce = "";
		String Meat = "";
		String Veggie = "";
		
		System.out.println(prompt);
		do {
			System.out.print("Choose a sauce by entering a number \n(1)Marinara\n(2)White\n(3)Pesto");
			sauce = input.nextInt();
			input.nextLine();
		}while(sauce < 1 || sauce > 3);
		
		if (sauce == 1)
			Sauce = "Marinara";
		else if(sauce == 2)
			Sauce = "White";
		else if(sauce == 3)
			Sauce = "Pesto";
		
		do {
			System.out.println("Choose one meat: \n(1)Pepperoni\n(2)Sausage\n(3)Ham");
			meat = input.nextInt();
			input.nextLine();
			
		}while(meat < 1 || meat > 3);
		
		if (meat == 1)
			Meat = "Pepperoni";
		else if(meat == 2)
			Meat = "Sausage";
		else if(meat == 3)
			Meat = "Ham";
		
		do {
			System.out.println("Choose one veggie: \n(1)Green Peppers\n(2)Onions\n(3)Pineapple\n(4)Olives\n(5)Mushrooms");
			veggie = input.nextInt();
			input.nextLine();
		}while(veggie < 1 || veggie > 5);
		if (veggie == 1)
			Veggie = "Green Peppers";
		else if (veggie == 2)
			Veggie = "Onions";
		else if (veggie == 3)
			Veggie = "Pineapple";
		else if (veggie == 4)
			Veggie = "Olives";
		else if (veggie == 5)
			Veggie = "Mushrooms";
		
		return Sauce + " sauce, " + Meat + ", " +  "and " + Veggie;
	}
	private static int getPizzaTime(String prompt) {
		int r = 0;
		do {
			System.out.print(prompt);
			r = input.nextInt();
			input.nextLine();
		} while (r > 120 || r < 0);
		return r;
	}

}
