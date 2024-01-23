package com.example.mvccrudoperationer.repository;

import com.example.mvccrudoperationer.model.Category;
import com.example.mvccrudoperationer.model.InvoiceEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tobias Heidlund
 */
public class InvoiceRepository extends BaseRepository{

    public InvoiceRepository(){
        super();
        String creationQuery = "CREATE TABLE `invoices` (\n" +
                "  `title` varchar(32) NOT NULL,\n" +
                "  `date` date NOT NULL,\n" +
                "  `description` varchar(32) NOT NULL,\n" +
                "  `category` varchar(32) NOT NULL,\n" +
                "  `price` double NOT NULL,\n" +
                "  `owner` int(11) NOT NULL,\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "   PRIMARY KEY (id),\n" +
                "   FOREIGN KEY (owner) REFERENCES users(id)\n" +
                ");";
        try {
            connection.prepareCall(creationQuery).execute();
        } catch (SQLException e) {
            if(!e.getLocalizedMessage().contains("already exists")) throw new RuntimeException(e);
        }
    }


    public void create(InvoiceEntry invoiceEntry) {
        try {
            PreparedStatement ps = connection.prepareCall("INSERT INTO invoices(title, date, description, category, price, owner) VALUES (?,?,?,?,?,?)");
            ps.setString(1,invoiceEntry.getTitle());
            ps.setDate(2,invoiceEntry.getDate());
            ps.setString(3,invoiceEntry.getDescription());
            ps.setString(4,invoiceEntry.getCategory().label);
            ps.setDouble(5,invoiceEntry.getPrice());
            ps.setInt(6,invoiceEntry.getOwner());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<InvoiceEntry> getAll(int owner) {
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareCall("SELECT * FROM Invoices WHERE owner=?");
            ps.setInt(1,owner);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                InvoiceEntry in = new InvoiceEntry.InvoiceEntryBuilder(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getDate("date"),
                        rs.getString("description"),
                        Category.valueOfLabel(rs.getString("category")),
                        rs.getDouble("price"),
                        owner
                ).build();
                invoiceEntries.add(in);
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return invoiceEntries;
    }
    public InvoiceEntry get(int owner,int id) {
        try {
            PreparedStatement ps = connection.prepareCall("SELECT * FROM Invoices WHERE owner=? AND id=?");
            ps.setInt(1,owner);
            ps.setInt(2,id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            return new InvoiceEntry.InvoiceEntryBuilder(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getDate("date"),
                    rs.getString("description"),
                    Category.valueOfLabel(rs.getString("category")),
                    rs.getDouble("price"),
                    owner
            ).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean update(InvoiceEntry invoiceEntry) {
        try {
            PreparedStatement ps = connection.prepareCall("UPDATE Invoices SET title=?, date=?, description=?, category=?, price=? where id=? and owner=?");
            ps.setString(1,invoiceEntry.getTitle());
            ps.setDate(2,invoiceEntry.getDate());
            ps.setString(3,invoiceEntry.getDescription());
            ps.setString(4,invoiceEntry.getCategory().label);
            ps.setDouble(5,invoiceEntry.getPrice());
            ps.setInt(6,invoiceEntry.getId());
            ps.setInt(7,invoiceEntry.getOwner());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public boolean delete(int id, int user) {
        try {
            PreparedStatement ps = connection.prepareCall("DELETE FROM Invoices WHERE owner=? AND id=?");
            ps.setInt(1,user);
            ps.setInt(2,id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
