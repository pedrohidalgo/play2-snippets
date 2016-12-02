Usefull play 2 utility codes
============================================

Currently contains utility codes for:

1. Write image to file system
2. Send Email
3. Get play conf value
4. JSON Entity validation
5. Log incoming HTTP Requests
6. DateUtil class
7. PlayUtil class

Those code snippets has been tested with Play 2.3.x and Java 8

<h3>Write image to fileSystem</h3>

in application.conf file:

IMAGES_DIR="/home/pedro/development/images"

IMG_WIDTH=640

IMG_HEIGHT=480

    public static void writeImageToFileSystem(String fileName, byte[] imageInByteArray) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageInByteArray));

        BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Method.SPEED,
            Scalr.Mode.AUTOMATIC, IMG_WIDTH, IMG_HEIGHT, Scalr.OP_ANTIALIAS);
        ImageIO.write(resizedImage, "jpg", new File(IMAGES_DIR + "/" + fileName));
    }

Usage:

add the following dependency: libraryDependencies += "org.imgscalr" % "imgscalr-lib" % "4.2"

in play controller:

    Http.MultipartFormData body = request().body().asMultipartFormData();
    Http.MultipartFormData.FilePart part = body.getFile("offer_picture");

    if (part != null) {
        File file = part.getFile();
        MiscUtil.writeImageToFileSystem(newImageFileName, Files.toByteArray(file));
    }

------------------------------------------------
<h3>Send Email</h3>

add the following dependency: libraryDependencies += "com.sun.mail" % "javax.mail" % "1.5.2"

in application.conf file add: 

EMAIL_FROM=myemail@gmail.com

EMAIL_PASSWORD=mypassword

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

Usage:

    MiscUtil.sendEmail(to, subject, body);

---------------------------------------------------------------
<h3>Get configuration Value</h3>

    public static Optional<String> optConfValue(String key) {
        return Optional.ofNullable(Play.application().configuration().getString(key));
    }

Usage:
    
    Optional<String> myValueOptional = MiscUtil.optConfValue("my_key");

    if(myValueOptional.isPresent()){
        String myKey = myValueOptional.get();
    }

