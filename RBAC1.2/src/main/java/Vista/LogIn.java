package Vista;
import javax.swing.*;
import java.awt.*;
public class LogIn extends JPanel{

    JTextField tf1;
    JPasswordField ps1;
    JLabel l1,l2;
    JButton btnExit,btnLog;

    public LogIn(){


        setSize(900,700);
        setLayout(null);

        ImageIcon iLog=new ImageIcon("C:\\Users\\eilda\\Desktop\\Proyecto desarrollo 2do sem\\RBAC1.2\\src\\main\\java\\logoIn.png");
        Font fuenteTxt=new Font("arial",Font.PLAIN,18);

        Dimension dText=new Dimension(300,40);
        Dimension dLabel=new Dimension(350,40);
        Dimension dLabelImag=new Dimension(350,200);
        Dimension dBtn=new Dimension(300,40);

        l1= new JLabel("LOGIN");
        l1.setFont(new Font("arial",Font.BOLD,42));
        l1.setLocation(385,50);
        l1.setSize(dLabel);


        add(l2=new JLabel(new ImageIcon(iLog.getImage().getScaledInstance(175, -1, Image.SCALE_DEFAULT))));
        l2.setLocation(275,95);
        l2.setSize(dLabelImag);

        tf1= new JTextField();
        tf1.setSize(dText);
        tf1.setLocation(300,300);
        new TextPrompt("Usuario", tf1);

        ps1= new JPasswordField();
        ps1.setSize(dText);
        ps1.setLocation(300,345);
        new TextPrompt("Contraseña", ps1);

        btnLog= new JButton("Ingresar");
        btnLog.setSize(dBtn);
        btnLog.setLocation(300,415);
        btnLog.setFocusable(false);
        btnLog.setFont(fuenteTxt);

        btnExit= new JButton("Salir");
        btnExit.setSize(100,30);
        btnExit.setLocation(50,560);
        btnExit.setFont(fuenteTxt);
        btnExit.setFocusable(false);




        add(tf1);
        add(ps1);
        add(btnLog);
        add(btnExit);
        add(l1);


        setVisible(true);

    }

}
