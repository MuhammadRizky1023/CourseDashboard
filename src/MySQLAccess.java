/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Acer
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLAccess {
    private Connection connect = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    

    // Method untuk membuat koneksi ke database
    public void connectToDatabase() throws Exception {
        try {
            // Menghubungkan ke database menggunakan JDBC
            connect = DriverManager.getConnection("jdbc:mysql://localhost/course?" +
                    "user=root&password=");
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    public boolean checkCredentials(String username, String password) throws SQLException {
        // Membuat kueri SQL untuk memeriksa kredensial pengguna
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connect.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Jika ada baris yang cocok, kembalikan true
                return resultSet.next();
            }
        }
    }
    



    // Method untuk menambahkan data siswa baru ke dalam tabel
    public void insertSiswa(String name, String gender, String phone, String course) throws SQLException {
        preparedStatement = connect.prepareStatement("INSERT INTO record (name, gender, phone, course) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, gender);
        preparedStatement.setString(3, phone);
        preparedStatement.setString(4, course);
        preparedStatement.executeUpdate();
    }

    // Method untuk membaca data siswa dari tabel
    public ResultSet readSiswa() throws SQLException {
        preparedStatement = connect.prepareStatement("SELECT * FROM record");
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
    
    public ResultSet getResultSet(String query) throws SQLException {
    preparedStatement = connect.prepareStatement(query);
    return preparedStatement.executeQuery();
}


    // Method untuk memperbarui data siswa dalam tabel
    public void updateSiswa(String id, String name, String gender, String phone, String course) throws SQLException {
        preparedStatement = connect.prepareStatement("UPDATE record SET name = ?, gender = ?, phone = ?, course = ? WHERE id = ?");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, gender);
        preparedStatement.setString(3, phone);
        preparedStatement.setString(4, course);
        preparedStatement.setString(5, id);
        preparedStatement.executeUpdate();
    }

    // Method untuk menghapus data siswa dari tabel
    public void deleteSiswa(String id) throws SQLException {
        preparedStatement = connect.prepareStatement("DELETE FROM record WHERE id = ?");
        preparedStatement.setString(1, id);
        preparedStatement.executeUpdate();
    }

    // Method untuk menutup koneksi ke database
    public void close() {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}