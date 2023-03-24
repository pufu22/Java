/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import static mail.Mail.sendEmail;

/**
 *
 * @author stageit
 */
public class Interfaccia implements ActionListener,HyperlinkListener,KeyListener{
   private final JFrame frame1;
   private final JPanel p1;
   private final JFrame frame2;
   private final JPanel p2;
   private final JFrame frame4;
   private final JPanel p4;
   private final JPanel p5;
   private static JTextField search;
   private static JTextField user;
   private final JLabel user_label;
   private static JPasswordField pass;
   private final JLabel pass_label;
   private final JButton login;
   private static JTextField ogg;
   private static JLabel ogg_label;
   private static JTextArea msg;
   private static JLabel msg_label;
   private static JTextField dest;
   private static JLabel dest_label;
   private static JButton invia;
   private static JButton mostra_password;
   private static JButton allega;
   static JTextArea emails;
   static JList lista_email;
   public static DefaultListModel listModel;
   private static JButton cambia;
   private static JButton cambia2;
   private static JLabel all_label;
   public static JEditorPane m;
   private static JButton back;
   public static JLabel from_label=new JLabel();
   public static JLabel ogg_label2=new JLabel("Oggetto: ");
   public static JLabel msg_label2=new JLabel("Messaggio: ");
   private static JButton reply;
    JFileChooser fileChooser = new JFileChooser();
   Interfaccia() {
       //login
       frame1=new JFrame();
       frame1.setBounds(0, 0, 300, 200);
       frame1.addKeyListener(this);
       frame1.setFocusable(true);
       frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//chiude tutto il programma quando chiudi il frame
       //panel
       p1=new JPanel();
       p1.setBounds(0, 0, 300, 300);
       p1.setLayout(new GridLayout(3,1));
       p1.addKeyListener(this);
       p1.setFocusable(true);
       frame1.add(p1);
       //username
       user=new JTextField();    
       user.setSize(500, 5);
       user.setVisible(true);
       user.addKeyListener(this);
       user_label=new JLabel();
       user_label.setText("User Name :");
       //password
       pass=new JPasswordField();
       pass.addKeyListener(this);
       pass.setSize(500, 5);
       pass.setVisible(true);
       pass_label = new JLabel();
       pass_label.setText("Password :");
       //bottone login
       login=new JButton("LOGIN");
       login.addActionListener(this); 
       login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
       p1.add(user_label);
       p1.add(user);
       p1.add(pass_label);
       p1.add(pass);
       //bottone mostra password
       mostra_password = new JButton(new ImageIcon("C:\\Users\\stageit\\Downloads\\icons8-eye-48.png"));
       mostra_password.addActionListener(this);
       mostra_password.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
       mostra_password.setToolTipText("Mostra/nascondi password");
       p1.add(mostra_password);
       p1.add(login);
       p1.setVisible(true);
       frame1.setVisible(true);
       frame1.setResizable(false);
       /////////
       
       
       //scrivi email
       frame2=new JFrame();
       frame2.setBounds(0, 0, 800, 900);
       frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       p2=new JPanel();
       p2.setBounds(0, 0, 800, 800);
       p2.setLayout(null);
       p2.setVisible(true);
      //destinatario
      dest=new JTextField();
      dest.setBounds(100, 0, 800, 50);
      p2.add(dest);
      dest_label=new JLabel("Destinatario:");
      dest_label.setBounds(0, 0, 100, 50);
      p2.add(dest_label);
      //oggetto della mail
      ogg=new JTextField();
      ogg.setBounds(100,50,800,50);
      p2.add(ogg);
      ogg_label=new JLabel("Oggetto:");
      ogg_label.setBounds(0, 50, 100, 50);
      p2.add(ogg_label);
      //messaggio
      msg=new JTextArea();
      msg.setBounds(0, 150, 800, 650);
      p2.add(msg);
      msg_label=new JLabel("Messaggio");
      msg_label.setBounds(0, 100, 100, 50);
      p2.add(msg_label);
      //tasto invia
      invia=new JButton("Invia");
      invia.setBounds(685, 800, 100, 50);
      invia.addActionListener(this);
      invia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      p2.add(invia);
      //tasto allega
      allega=new JButton("Allega file");
      allega.setBounds(585, 800, 100, 50);
      allega.addActionListener(this);
      allega.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      p2.add(allega);
      //tasto per passare alle email
      JButton check=new JButton("Emails");
      check.setBounds(485, 800, 100, 50);
      check.addActionListener(this);
      check.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      p2.add(check);
      //tasto per cambiare account
      cambia=new JButton("Cambia account");
      cambia.setBounds(355, 800, 130, 50);
      cambia.addActionListener(this);
      cambia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      p2.add(cambia);
      //label di quale file è allegato
      all_label=new JLabel();
      all_label.setBounds(0, 800, 300, 50);
      p2.add(all_label);
      frame2.add(p2);
      frame2.setVisible(false);
      frame2.setResizable(false);
     
      //controllo email
      frame4=new JFrame();
      frame4.setBounds(0, 0, 1920, 980);
      frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      p4=new JPanel();
      p4.setLayout(null);
      p5=new JPanel();
      p5.setLayout(null);
      p5.setBounds(0, 0, 1920, 980);
      
      //email
      m=new JEditorPane();
      m.setContentType("text/html");
      m.addHyperlinkListener(this);
      m.setEditable(false);
      JScrollPane scroll2=new JScrollPane(m);
      scroll2.setBounds(0, 100, 1920, 780);
      p5.add(scroll2);
      //tasto per tornare alle email
      back=new JButton("Indietro");
      back.setBounds(0, 880, 100, 50);
      back.addActionListener(this);
      back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      p5.add(back);
      //tasto per rispondere
      reply=new JButton("Rispondi");
      reply.setBounds(100, 880, 100, 50);
      reply.addActionListener(this);
      reply.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      p5.add(reply);
      from_label.setBounds(0, 0, 1920, 50);
      ogg_label2.setBounds(0, 50, 1920, 50);
      p5.add(ogg_label2);
      p5.add(from_label);
      p5.setVisible(false);
      frame4.add(p5);
      listModel = new DefaultListModel();
      lista_email=new JList(listModel);
      lista_email.addMouseMotionListener(new MouseMotionListener() {
        @Override
        public void mouseMoved(MouseEvent e) {
            final int x = e.getX();
            final int y = e.getY();
            //cambia il cursore quando sopra un'email
            final Rectangle cellBounds = lista_email.getCellBounds(0, lista_email.getModel().getSize() - 1);
            if (cellBounds != null && cellBounds.contains(x, y)) {
                lista_email.setCursor(new Cursor(Cursor.HAND_CURSOR));
                             
            } else {
            lista_email.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
          
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        }
      });
      lista_email.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent evt) {
//                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    try {
                    // doppio-click
                        p4.setVisible(false);
                        p5.setVisible(true);
                        try {
                            Mail.show(lista_email.getSelectedIndex(),null);
                        } catch (BadLocationException ex) {
                            Logger.getLogger(Interfaccia.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (MessagingException | IOException ex) {
                        Logger.getLogger(Interfaccia.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } 
        }
    });
      
      JScrollPane scroll=new JScrollPane(lista_email);
      scroll.setBounds(0, 25, 1920, 825);
      JLabel search_label=new JLabel("Cerca nella posta:");
      search_label.setOpaque(true);
      search_label.setBackground(Color.CYAN);
      search_label.setBounds(0, 0, 150, 25);
      p4.add(search_label);
      search=new JTextField();
      search.setBounds(150, 0, 400, 25);
      p4.add(search);
      JButton cerca=new JButton("Cerca");
      cerca.setBounds(550, 0, 75, 25);
      cerca.addActionListener(this);
      p4.add(cerca);
      p4.add(scroll);
      //bottone per passare alla scrittura di email
      JButton scrivi=new JButton("Scrivi");
      scrivi.setBounds(1815, 850, 100, 50);
      scrivi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      scrivi.addActionListener(this);
      p4.add(scrivi);
      //bottone per cambiare account
      cambia2=new JButton("Cambia account");
      cambia2.setBounds(1685, 850, 130, 50);
      cambia2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      cambia2.addActionListener(this);
      p4.add(cambia2);
      //
      JButton delete=new JButton("Elimina");
      delete.setBounds(1585, 850, 100, 50);
      delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      delete.addActionListener(this);
      p4.add(delete);
      frame4.add(p4);
      p4.setVisible(true);
      frame4.setVisible(false);
      frame4.setResizable(false);
   }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String str=arg0.getActionCommand();
        Object bott=arg0.getSource();
        //mostra o nasconde la pass
        if(bott.equals(mostra_password)){
            if(pass.getEchoChar()==(char)0)
                pass.setEchoChar('*');
            else
                pass.setEchoChar((char)0);
        }
        JFrame error=new JFrame();
        //tenta l'accesso
        if(str.equals("LOGIN")){
            Mail.username=user.getText();
            Mail.password=String.copyValueOf(pass.getPassword());
            try {
                if(!Mail.accesso())
                    JOptionPane.showMessageDialog(error, "username o password errati");
                else{
                   //se il login è riuscito chiude la finestra di login
                        frame1.dispose();
                        frame4.setVisible(true);
                        Mail.checkEmail();
                }
            }catch (MessagingException ex) {
                Logger.getLogger(Interfaccia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //invia l'email
        else if(str.equals("Invia")){
            if(dest.getText().equals("")||ogg.getText().equals("")||msg.getText().equals(""))
                JOptionPane.showMessageDialog(error, "Riempi tutti i campi");
            else
                 try {
                        sendEmail(dest.getText(),ogg.getText(),msg.getText(),false);
                        dest.setText("");
                        ogg.setText("");
                        msg.setText(""); 
                        all_label.setText("");
                        frame2.setVisible(false);
                        frame4.setVisible(true);
                        JOptionPane.showMessageDialog(error,"Mail inviata");
                        Mail.checkEmail();
                        
                 } catch (MessagingException ex) {
                        JOptionPane.showMessageDialog(error,"Mail non inviata");
                 }
        }
        //allega un file
        else if(str.equals("Allega file")){
            fileChooser.showOpenDialog(new JPanel());
            Mail.file=fileChooser.getSelectedFile().getPath();
            all_label.setText(fileChooser.getSelectedFile().getPath());
        }
        //passa alle email
        else if(str.equals("Emails")){
            frame2.setVisible(false);
            frame4.setVisible(true);
            Mail.checkEmail();
        }
        else if(str.equals("Scrivi")){
            frame4.setVisible(false);
            frame2.setVisible(true);
        }
        //passa al login
        else if(str.equals("Cambia account")){
            frame4.setVisible(false);
            frame2.setVisible(false);
            frame1.setVisible(true);
        }
        //torna alle email
        else if(str.equals("Indietro")){
            m.setText("");
            p4.setVisible(true);
            p5.setVisible(false);
        }
        else if(str.equals("Cerca")){
            try {
                Mail.search(search.getText());
            } catch (MessagingException ex) {
                Logger.getLogger(Interfaccia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(str.equals("Elimina"))
            if(!lista_email.isSelectionEmpty())
            try {
                System.out.println(lista_email.getSelectedIndex());
                Mail.delete(lista_email.getSelectedIndex());
        } catch (MessagingException ex) {
            Logger.getLogger(Interfaccia.class.getName()).log(Level.SEVERE, null, ex);
        }
            else System.out.println("sdad");
        else if(str.equals("Rispondi")){
            frame4.setVisible(false);
            frame2.setVisible(true);
        }
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent arg0) {
        if (arg0.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
        System.out.println(arg0.getURL());
        URI u = null;
        URL url=arg0.getURL();
            try {
                 u=url.toURI();
            } catch (URISyntaxException ex) {
                System.out.println("L'url è incorretto");
            }
       try {
            Desktop.getDesktop().browse(u);
       } catch (IOException ex) {
           Logger.getLogger(Interfaccia.class.getName()).log(Level.SEVERE, null, ex);
       }
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        
       if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
        login.doClick();
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        
        if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
        login.doClick();
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
