package ro.emanuel.oop.tema1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.time.LocalDateTime;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		// prepare the connection obj auth props
		Properties connectionProps = new Properties();
		connectionProps.put("user", "root");
		connectionProps.put("password", "");
		
		// open connection (create Connection obj)
		Connection dbConn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/teme_jdbc", 
				connectionProps);
		
		// open statement (create Statement obj to run sql commands)
		Statement stmt = dbConn.createStatement();
		
		System.out.println("\nCRUD on users");
//		INSERT - creates
		final String userName = "Nae";
		final String userStatus = "active";
		final LocalDateTime userDateJoined =  LocalDateTime.now();
		final LocalDateTime userSubscriptionActivated =  LocalDateTime.now();
		final String userPaymentMethod = "Revolut";
		final String userSubscriptionType = "pro";
		
		String sqlInsertUser = "INSERT INTO `users`(`name`, `status`, `date_joined`, `date_subscription_activated`, `payment_method`, `subscription_type`) values (?, ?, ?, ?, ?, ?)";
		PreparedStatement psUser = dbConn.prepareStatement(sqlInsertUser);
		
		psUser.setString(1, userName);
		psUser.setString(2, userStatus);
		psUser.setString(3, userDateJoined.toString());
		psUser.setString(4, userSubscriptionActivated.toString());
		psUser.setString(5, userPaymentMethod);
		psUser.setString(6, userSubscriptionType);
		
		System.out.println("INSERT: " + psUser.executeUpdate());
		
//		UPDATE - update
		String commnandUpdate = "UPDATE `users` SET `status` = 'inactive' WHERE `name` LIKE 'Nae%'";
		System.out.println("UPDATE: " + stmt.executeUpdate(commnandUpdate) + " rows affected");
		
//		SELECT - read
		final ResultSet userResults = stmt.executeQuery("SELECT * FROM users");
		while (userResults.next()) {
			final int id = userResults.getInt("id");
			final String name = userResults.getString("name");
			final String status = userResults.getString("status");
			final String dateJoined = userResults.getString("date_joined");
			final String subscriptionActivated = userResults.getString("date_subscription_activated");
			final String paymentMethod = userResults.getString("payment_method");
			final String subscriptionType = userResults.getString("subscription_type");
			System.out.println(id + " " + name + " (" + status + " - " + dateJoined + "): " + subscriptionType + " (" + subscriptionActivated +", " + paymentMethod + ")");
		}
		
//		DELETE - delete
		String sqlDeleteAll = "DELETE FROM `users`";
		PreparedStatement deleteAllUsers = dbConn.prepareStatement(sqlDeleteAll);
		System.out.println(deleteAllUsers.executeUpdate() + " rows affected");
		
		
		System.out.println("\nCRUD on payment_methods");
//		INSERT - create
		final String paymentName = userPaymentMethod;
		final int userID = 4;
		final String paymentCode = "rn490unfu303";
		
		String sqlInsertPayment = "INSERT INTO `payment_methods`(`name`, `user_id`, `code`) values (?, ?, ?)";
		PreparedStatement psPayment = dbConn.prepareStatement(sqlInsertPayment);
		
		psPayment.setString(1, paymentName);
		psPayment.setInt(2, userID);
		psPayment.setString(3, paymentCode);
		
		System.out.println("INSERT: " + psPayment.executeUpdate());
		
//		UPDATE - update
		commnandUpdate = "UPDATE `payment_methods` SET `code` = 'd20hr20br2d20' WHERE `name` LIKE 'Revolut%'";
		System.out.println("UPDATE: " + stmt.executeUpdate(commnandUpdate) + " rows affected");
		
//		SELECT - read
		final ResultSet paymentMethodsResults = stmt.executeQuery("SELECT * FROM payment_methods");
		while (paymentMethodsResults.next()) {
			final String name = paymentMethodsResults.getString("name");
			final int userId = paymentMethodsResults.getInt("user_id");
			final String code = paymentMethodsResults.getString("code");
			System.out.println(name + " is used by " + userId + " (" + code + ")");
		}
		
//		DELETE - delete
		String sqlDeleteAllPayments = "DELETE FROM `payment_methods`";
		System.out.println(stmt.executeUpdate(sqlDeleteAllPayments) + " rows affected");
		
//		close connection
		dbConn.close();

	}

}
