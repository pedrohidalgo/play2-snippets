package util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.imgscalr.Scalr;
import play.Logger;
import play.Play;

/**
 *
 * @author pedro
 */
public abstract class MiscUtil {

    public static final int IMG_WIDTH = Integer.parseInt(getConfValue("images_width"));
    public static final int IMG_HEIGHT = Integer.parseInt(getConfValue("images_height"));
    public static final String IMAGES_DIR = getConfValue("images_dir");
    public static final String EMAIL_FROM = getConfValue("email_from");
    public static final String EMAIL_PASSWORD = getConfValue("email_password");
    public static final String SEPARATOR = getConfValue("separator");

    public static String getConfValue(String key) {
        return Play.application().configuration().getString(key);
    }

    public static Optional<String> optConfValue(String key) {
        return Optional.ofNullable(Play.application().configuration().getString(key));
    }

    public static void writeImageToFileSystem(String fileName, byte[] imageInByteArray) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageInByteArray));

        BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Method.SPEED,
            Scalr.Mode.AUTOMATIC, IMG_WIDTH, IMG_HEIGHT, Scalr.OP_ANTIALIAS);
        ImageIO.write(resizedImage, "jpg", new File(IMAGES_DIR + "/" + fileName));
    }

    public static void sendEmail(String email, String subject, String body) {
        Runnable sendEmailTask = () -> {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
                    }
                });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(EMAIL_FROM));
                message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
                message.setSubject(subject);
                message.setText(body);

                Transport.send(message);

                Logger.info("Email sent...");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        };

        new Thread(sendEmailTask).start();
    }

}
