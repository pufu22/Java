/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

/**
 *
 * @author stageit
 */
import com.sun.mail.imap.IMAPFolder;
import java.io.IOException;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.search.SearchTerm;
import javax.swing.text.BadLocationException;

public class Mail {
    public static String username;
    public static String password;
    public static String file=null;
    static Message[] messages;
    public static void main(String[] args) {
      Interfaccia inter=new Interfaccia();  
    }
    public static void sendEmail(String toAddress,String oggetto, String messaggio,boolean re) throws MessagingException 
             {

        //proprietà server smtp
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", username);

        // crea una nuova sessione
        Session session = Session.getInstance(properties);

        // crea un nuovo messaggio
        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(username));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        //componenti dell'email
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(oggetto);
        msg.setSentDate(new Date());
        msg.setText(messaggio,"html");
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(messaggio,"text/html");
        String filename = null ;
        //controllo se c'è un file allegato
        if(file!=null)
            filename=file;
        if(filename!=null){
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);
        }
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        // spedisce e-mail
        Transport t = session.getTransport("smtp");
        t.connect(username, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
        
    }
    //mostra come lista tutte le email in inbox
    public static void checkEmail(){
            try {
                    Properties properties = new Properties();
                    properties.put("mail.pop3.host", "pop.gmail.com");
                    properties.put("mail.pop3.port", "995");
                    properties.put("mail.pop3.starttls.enable", "true");
                    Session emailSession = Session.getDefaultInstance(properties);
                    //emailSession.setDebug(true);
                    //crea il POP3 store e si connette con il pop server
                    Store store = emailSession.getStore("pop3s");

                    store.connect("pop.gmail.com", username, password);

                    //crea il folder object e lo apre
                    Folder emailFolder = store.getFolder("INBOX");
                    emailFolder.open(Folder.READ_ONLY);

                    // prende i messaggi dal folder,li mette in un array e stampa a video
                     messages = emailFolder.getMessages();
                    
                      Interfaccia.listModel.clear();
                    for (int i = 0, n = messages.length; i < n; i++) {
                       Interfaccia.listModel.addElement("Email Numero " + (i + 1)+" "+"Oggetto: "+messages[i].getSubject()+" Da: " + messages[i].getFrom()[0]);                       
                    }
                    //chiusura store e folder
                    emailFolder.close(true);
                    store.close();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    //mostra l'email selezionata
    public static void show(int l,Part p) throws MessagingException, IOException, BadLocationException{
         Properties properties = new Properties();
                    properties.put("mail.pop3.host", "pop.gmail.com");
                    properties.put("mail.pop3.port", "995");
                    properties.put("mail.pop3.starttls.enable", "true");
                    Session emailSession = Session.getDefaultInstance(properties);
                    //emailSession.setDebug(true);
                    
                    //crea il POP3 store e si connette con il pop server
                    Store store = emailSession.getStore("pop3s");
                    store.connect("pop.gmail.com", username, password);
                     Folder emailFolde = store.getFolder("INBOX");
                    emailFolde.open(Folder.READ_ONLY);
                    Message[] gia=emailFolde.getMessages();
                    if(p==null){
                        p=gia[l];
                        Interfaccia.from_label.setText("Da:"+gia[l].getFrom()[0]);
                        Interfaccia.ogg_label2.setText("Oggetto:"+gia[l].getSubject());
                    }
        System.out.println("CONTENT-TYPE: " + p.getContentType());
         if (p.isMimeType("text/plain")) {
             System.out.println("This is plain text");
             System.out.println("---------------------------");
             System.out.println("<html>"+(String)p.getContent()+"</html>");
             Interfaccia.m.setText("<html>"+(String)p.getContent()+"</html>");
             
        } 
        else if (p.isMimeType("multipart/*")){
            System.out.println("This is a Multipart");
            System.out.println("---------------------------");
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++){
                MimeBodyPart part =(MimeBodyPart) mp.getBodyPart(i);
                //scarica il file allegato
               if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                part.saveFile(part.getFileName());
               }
                show(l,mp.getBodyPart(i)); 
            }
        }
        else if (p.isMimeType("message/rfc822")) {
            System.out.println("This is a Nested Message");
            System.out.println("---------------------------");
            show(l,(Part) p.getContent());
      }
        else if(p.isMimeType("text/html")){
             System.out.println((String)p.getContent());
             String lo=Interfaccia.m.getDocument().getText(0, Interfaccia.m.getDocument().getLength());
             System.out.println("////////////////"+lo+(String)p.getContent());
             Interfaccia.m.setText((String)p.getContent());
             System.out.println("////////////////"+Interfaccia.m.getDocument().getText(0, Interfaccia.m.getDocument().getLength()));
        }
        
         emailFolde.close(true);
         store.close();
         checkEmail();
    
    }
    public static void search(String parola) throws MessagingException{
        Properties properties = new Properties();
                    properties.put("mail.pop3.host", "pop.gmail.com");
                    properties.put("mail.pop3.port", "995");
                    properties.put("mail.pop3.starttls.enable", "true");
                    Session emailSession = Session.getDefaultInstance(properties);
                    //emailSession.setDebug(true);
                    SearchTerm term = new SearchTerm() {
                            public boolean match(Message message) {
                                try {
                                    if (message.getSubject().contains(parola)) {
                                        return true;
                                    }
                                }catch (MessagingException ex) {
                                    ex.printStackTrace();
                                }
                                return false;
                            }
                    };
                    //crea il POP3 store e si connette con il pop server
                    Store store = emailSession.getStore("pop3s");
                    store.connect("pop.gmail.com", username, password);
                     Folder emailFolde = store.getFolder("INBOX");
                    emailFolde.open(Folder.READ_ONLY);
                    
                    messages = emailFolde.search(term);
                    
                      Interfaccia.listModel.clear();
                    for (int i = 0, n = messages.length; i < n; i++) {
                       Interfaccia.listModel.addElement("Email Numero " + (i + 1)+" "+"Oggetto: "+messages[i].getSubject()+" Da: " + messages[i].getFrom()[0]);                       
                    }
                   
                     emailFolde.close(true);
                     store.close();
    }
    public static void delete(int i) throws MessagingException{
        Properties properties = new Properties();
                    properties.put("mail.pop3.host", "pop.gmail.com");
                    properties.put("mail.pop3.port", "995");
                    properties.put("mail.pop3.starttls.enable", "true");
                    Session emailSession = Session.getDefaultInstance(properties);
                    Store store = emailSession.getStore("pop3s");
                    store.connect("pop.gmail.com", username, password);
                    Folder emailFolde = store.getFolder("INBOX");
                    emailFolde.open(Folder.READ_WRITE);
                    messages = emailFolde.getMessages();
                    //emailFolde.getMessages()[i].setFlag(Flags.Flag.DELETED, true);
        messages[i].setFlag(Flags.Flag.DELETED, true);
         emailFolde.close(true);
         store.close();
         checkEmail();
        
         
    }
        //autentificazione username password
    public static boolean accesso() throws MessagingException{
        Transport transport;
        Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", username);
        Session session = Session.getInstance(properties);
        try {
            transport = session.getTransport("smtp");
            transport.connect(username, password);
            transport.close();
        }catch (AuthenticationFailedException  e) {
            //accesso rifiutato
            return false;
        }//accesso riuscito
            return true;
    }
}
