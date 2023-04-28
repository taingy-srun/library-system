package repository;

import controller.ConnectManager;
import model.Address;
import model.LibraryMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberRepository {
    private Connection connection;

    public LibraryMember getMember(String memberId) throws SQLException, Exception {

        connection = ConnectManager.getConnection();
        String query = String.format("select * from library_member where member_id = '%s'", memberId);
        PreparedStatement stat = connection.prepareStatement(query);
        ResultSet rs = stat.executeQuery();
        if (!rs.next())
            throw new Exception("Member does not exist");

        return new LibraryMember(rs.getInt("id"),
                rs.getString("member_id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("phone"),
                null);
    }

    public List<LibraryMember> getMemberList() throws SQLException {
        connection = ConnectManager.getConnection();
        String query = "select * from library_member";
        PreparedStatement stat = connection.prepareStatement(query);
        ResultSet rs = stat.executeQuery();
        return getMemberList(rs);
    }
    private List<LibraryMember> getMemberList(ResultSet rs) throws SQLException {
        List<LibraryMember> list = new ArrayList<>();
        int id;
        String memberId;
        String firstName;
        String lastName;
        String phone;
        Address address = null;

        while (rs.next()) {
            id = rs.getInt("id");
            memberId = rs.getString("member_id");
            firstName = rs.getString("firstname");
            lastName = rs.getString("lastname");
            phone = rs.getString("phone");

            list.add(new LibraryMember(id, memberId, firstName, lastName, phone, address));
        }

        return list;
    }
}
