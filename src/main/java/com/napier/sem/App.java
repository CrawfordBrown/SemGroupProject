package com.napier.sem;

import java.sql.*;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }


    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /*
    All the countries in the world organised by largest population to smallest
     */

    private void report1() {

        System.out.println("All the countries in the world organised by largest population to smallest");
        StringBuilder sb  = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String sql = "select * from country order by Population desc;";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(sql);
            // Return new country if valid.
            // Check one is returned
            while (rset.next()) {
                String code = rset.getString("code");
                String name = rset.getString("name");
                String continent = rset.getString("continent");
                String region = rset.getString("region");
                Integer surfaceArea = rset.getInt("surfaceArea");
                Integer indepYear = rset.getInt("indepYear");
                Integer population = rset.getInt("population");
                Integer lifeExpectancy = rset.getInt("lifeExpectancy");
                Integer gnp = rset.getInt("gnp");
                Integer gnpOld = rset.getInt("gnpOld");
                String localName = rset.getString("localName");
                String governmentForm = rset.getString("governmentForm");
                String headOfState = rset.getString("headOfState");
                Integer capital = rset.getInt("capital");
                String code2 = rset.getString("code2");
                Country country = new Country(code, name, continent, region, surfaceArea, indepYear, population,
                        lifeExpectancy, gnp, gnpOld, localName, governmentForm, headOfState, capital, code2);
                sb.append(country.toString() + "\r\n");
            }
            // Displays the records
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return;
        }

    }

    /*
    All the cities in the world organised by largest population to smallest.
     */

    private void report5() {

        System.out.println("All the cities in the world organised by largest population to smallest.");
        StringBuilder sb = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String sql5 = "select * from city order by Population desc;";
            // Execute SQL statement
            ResultSet rset5 = stmt.executeQuery(sql5);
            // Return new country if valid.
            // Check one is returned
            while (rset5.next()) {

                Integer id = rset5.getInt("id");
                String name = rset5.getString("name");
                String countryCode = rset5.getString("countryCode");
                String district = rset5.getString("district");
                Integer population = rset5.getInt("population");
                City city = new City(id, name, countryCode, district, population);
                sb.append(city.toString() + "\r\n");
            }
            // Displays the records
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return;
        }

    }
    /*
    All the cities in a continent organised by largest population to smallest.
     */

    private void report6(String cont) {

        System.out.println("All the cities in a continent organised by largest population to smallest.\n");
        StringBuilder sb = new StringBuilder();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String sql6 = "SELECT * \n" +
                    "FROM city\n" +
                    "JOIN country ON city.CountryCode=country.Code\n" +
                    "WHERE country.Continent = " + cont +
                    "ORDER BY city.Population DESC;";
            // Execute SQL statement
            ResultSet rset6 = stmt.executeQuery(sql6);
            // Return new country if valid.
            // Check one is returned
            while (rset6.next()) {
                Integer id = rset6.getInt("id");
                String name = rset6.getString("name");
                String countryCode = rset6.getString("countryCode");
                String district = rset6.getString("district");
                Integer population = rset6.getInt("population");
                City city = new City(id, name, countryCode, district, population);
                sb.append(city.toString() + "\r\n");
            }
            // Displays the records
            System.out.println(sb.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return;
        }

    }

    public static void main(String[] args) {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Display the Records
        //a.report1();


        // Display all the cities in the world organised by largest population to smallest.
        //a.report5();

        // Display All the cities in a continent organised by largest population to smallest.
        a.report6("'Europe'");

        // Disconnect from database
        a.disconnect();
    }

}
