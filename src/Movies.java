import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Movies {
    private JPanel panel1;
    private JTextField txtName;
    private JTextField txtDirector;
    private JTextField txtGender;
    private JTextField txtYear;
    private JTextField txtScore;
    private JTabbedPane Panel;
    private JButton saveButton;
    private JButton clearButton;
    private JTable moviesTable;
    private JLabel Name;
    private JLabel Director;
    private JLabel Gender;
    private JLabel Year;
    private JLabel Score;
    private JPanel Register;
    private JPanel About;
    private JPanel List;
    private JButton editButton;
    private JButton deleteButton;
    private JButton listButton;

    public static void main(String[] args) {
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

            String name, director, gender, year, score;

            name = txtName.getText();
            director = txtDirector.getText();
            gender = txtGender.getText();
            year = txtYear.getText();
            score = txtScore.getText();

            try {
                pst = connection.prepareStatement("insert into movies(name, director, gender, year, score)values(?,?,?,?::integer,?::integer)");
                pst.setString(1,name);
                pst.setString(2,director);
                pst.setString(3,gender);
                pst.setString(4,year);
                pst.setString(5,score);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Success! Movie added to your DB!");
                txtName.setText("");
                txtDirector.setText("");
                txtGender.setText("");
                txtScore.setText("");
                txtYear.setText("");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name, director, gender, year, score;

                name = txtName.getText();
                director = txtDirector.getText();
                gender = txtGender.getText();
                year = txtYear.getText();
                score = txtScore.getText();

                txtName.setText("");
                txtDirector.setText("");
                txtGender.setText("");
                txtScore.setText("");
                txtYear.setText("");
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            tableLoad();
            }
        });
    }
}
