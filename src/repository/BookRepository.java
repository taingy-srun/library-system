package repository;

import controller.ConnectManager;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private Connection connection;

    public Book getBook(String isbn) throws SQLException, Exception {

        connection = ConnectManager.getConnection();
        String query = String.format("select * from book where isbn = '%s'", isbn);
        PreparedStatement stat = connection.prepareStatement(query);
        ResultSet rs = stat.executeQuery();

        if (!rs.next())
            throw new Exception("Book does not exist");

        return new Book(rs.getInt("id"),
                rs.getString("isbn"),
                rs.getString("title"),
                rs.getInt("max_checkout_length"));
    }
    public List<Book> getBookList() throws SQLException {
        connection = ConnectManager.getConnection();
        String query = "select * from book";
        PreparedStatement stat = connection.prepareStatement(query);
        ResultSet rs = stat.executeQuery();
        return getBookList(rs);
    }
    private List<Book> getBookList(ResultSet rs) throws SQLException {
        List<Book> list = new ArrayList<>();
        int id;
        String isbn;
        String title;
        int maxCheckoutLength;

        while (rs.next()) {
            id = rs.getInt("id");
            isbn = rs.getString("isbn");
            title = rs.getString("title");
            maxCheckoutLength = rs.getInt("max_checkout_length");

            list.add(new Book(id, isbn, title, maxCheckoutLength));
        }

        return list;
    }
}
