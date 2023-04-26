import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Movies {
    private JPanel panel1;
    private JTextField txtName;
    private JTextField txtDirector;
    private JTextField txtGenre;
    private JTextField txtYear;
    private JTextField txtRating;
    private JTabbedPane Panel;
    private JButton saveButton;
    private JButton clearButton;
    private JTable moviesTable;
    private JLabel Name;
    private JLabel Director;
    private JLabel Genre;
    private JLabel Year;
    private JLabel Rating;
    private JPanel Register;
    private JPanel About;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField txtId;
    private JButton editButton;
    private JPanel Movie;

    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame("Movies");
        frame.setContentPane(new Movies().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection connection;
    PreparedStatement pst;

    public void connect(){
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "102030");
            System.out.println("Sucess!");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    void tableLoad(){
        try {
            pst = connection.prepareStatement("select * from movies");
            ResultSet rs = pst.executeQuery();
            moviesTable.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Movies() {

        connect();
        tableLoad();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name, director, genre, year, rating;

                name = txtName.getText();
                director = txtDirector.getText();
                genre = txtGenre.getText();
                year = txtYear.getText();
                rating = txtRating.getText();

                try {
                    pst = connection.prepareStatement("insert into movies(name, director, genre, year, rating)values(?,?,?,?::integer,?::numeric)");
                    pst.setString(1,name);
                    pst.setString(2,director);
                    pst.setString(3,genre);
                    pst.setString(4,year);
                    pst.setString(5,rating);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Success! Movie added to your DB!");
                    txtName.setText("");
                    txtDirector.setText("");
                    txtGenre.setText("");
                    txtRating.setText("");
                    txtYear.setText("");
                    tableLoad();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Register NOT added! Invalid data.");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name, director, genre, year, rating;

                name = txtName.getText();
                director = txtDirector.getText();
                genre = txtGenre.getText();
                year = txtYear.getText();
                rating = txtRating.getText();

                txtName.setText("");
                txtDirector.setText("");
                txtGenre.setText("");
                txtRating.setText("");
                txtYear.setText("");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id;

                id = txtId.getText();

                try {

                    pst = connection.prepareStatement("delete from movies where id = ?::integer");
                    pst.setString(1, id);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Movie deleted!");
                    tableLoad();
                    txtName.setText("");
                    txtDirector.setText("");
                    txtGenre.setText("");
                    txtYear.setText("");
                    txtRating.setText("");
                    txtName.requestFocus();

                } catch (SQLException ex){

                    ex.printStackTrace();

                }

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id, name, director, genre, year, rating;

                id = txtId.getText();
                name = txtName.getText();
                director = txtDirector.getText();
                genre = txtGenre.getText();
                year = txtYear.getText();
                rating = txtRating.getText();

                try {
                    pst = connection.prepareStatement("update movies set name = ?, director = ?, genre = ?, year = ?::integer, rating = ?::numeric where id = ?::integer");
                    pst.setString(1, name);
                    pst.setString(2, director);
                    pst.setString(3, genre);
                    pst.setString(4, year);
                    pst.setString(5, rating);
                    pst.setString(6, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Register updated");
                    tableLoad();
                    txtName.setText("");
                    txtDirector.setText("");
                    txtGenre.setText("");
                    txtYear.setText("");
                    txtRating.setText("");
                    txtName.requestFocus();

                } catch (SQLException ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Register NOT added! Invalid data.");
                }

            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    String id = txtId.getText();

                    pst = connection.prepareStatement("select name, director, genre, year, rating from movies where id = ?::integer");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next()==true){

                        String name = rs.getString(1);
                        String director = rs.getString(2);
                        String genre = rs.getString(3);
                        String year = rs.getString(4);
                        String rating = rs.getString(5);

                        txtName.setText(name);
                        txtDirector.setText(director);
                        txtGenre.setText(genre);
                        txtYear.setText(year);
                        txtRating.setText(rating);

                    } else {

                        txtName.setText("");
                        txtDirector.setText("");
                        txtGenre.setText("");
                        txtYear.setText("");
                        txtRating.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid register!");

                    }

                } catch (SQLException ex){
                    ex.printStackTrace();
                }

            }
        });
    }
}
