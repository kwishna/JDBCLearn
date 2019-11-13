package DataBase;

import java.sql.*;

public class DataBase4 {
    /**
     * For Queryinig The Squlite DB In Resources Folder!
     * @param query SELECT Query
     */
    private static void doFetch(String query){

        ConnectionProvider cp = new ConnectionProvider();
//      String query = "SELECT * FROM albums";
        try (Connection conn = cp.getSqliteConnection();
             Statement state = conn.createStatement()) {

            ResultSet resultSet = state.executeQuery(query);

            while (resultSet.next()) {
                System.out.println(resultSet.getObject(1) + "\t" +
                        resultSet.getObject(2) + "\t" +
                        resultSet.getObject(3) + "\t"
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  INSERT Data In Table
     * @param query INSERT Query For inserting Data Into Table
     */
    private static void doInsert(String query, String... args) {

        ConnectionProvider cp = new ConnectionProvider();
//      String query = "INSERT INTO albums (AlbumId, Title, ArtistId) VALUES (?, ?, ?)";
        try (Connection conn = cp.getSqliteConnection();
             PreparedStatement state = conn.prepareStatement(query)) {

            for(int i=1; i <= args.length; i++) {

                state.setObject(i, args[i]);
            }

            int i = state.executeUpdate();
            System.out.println(i + " Rows Affected!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  UPDATE Data In Table
     * @param query UPDATE Query For inserting Data Into Table
     */
    private static void doUpdate(String query) {

        ConnectionProvider cp = new ConnectionProvider();
//      String query = "UPDATE albums SET Title='Anthony', ArtistId='349' WHERE AlbumId='349'"
        try (Connection conn = cp.getSqliteConnection();
             PreparedStatement state = conn.prepareStatement(query)) {

            int i = state.executeUpdate();
            System.out.println(i + " Rows Affected!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        /**
         *  Main Method Starts
         */
    public static void main(String[] args) {

        doFetch("SELECT * FROM albums");
        doInsert("INSERT INTO albums (AlbumId, Title, ArtistId) VALUES (?, ?, ?)");
        // UPDATE table_name SET column1=value, column2=value2,... WHERE column_name=some_value
        doUpdate("UPDATE albums SET Title='Anthony', ArtistId='349' WHERE AlbumId='349'");
    }
}
