package com.nortal.ping.simple;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;

/**
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @author Hatem A. <hatem@tuta.io>
 */
public class PingMail {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final String hostName;
    private final int smtpPort;
    private final Boolean ssl;
    private final String username;
    private final String password;
    private final String fromAadress;
    private final String[] toAddresses;
    private final String subjectPrefix;
 
    
    public PingMail(String hostName, int smtpPort, Boolean ssl, String username, String password, String fromAadress, String[] toAddresses, String subjectPrefix) {
        super();
        this.hostName = hostName;
        this.smtpPort = smtpPort;
        this.ssl = ssl;
        this.username = username;
        this.password = password;
        this.fromAadress = fromAadress;
        this.toAddresses = toAddresses;
        this.subjectPrefix = subjectPrefix == null ? "" : subjectPrefix.trim();
    }

    public void send(String subject, String body) {
        try {
            Email email = new HtmlEmail();
            email.setHostName(hostName);
            email.setSmtpPort(smtpPort);

            if(!username.isEmpty() && !password.isEmpty()){
            	email.setAuthenticator(new DefaultAuthenticator(username, password));
            }
   
            if(ssl){
            	email.setSSLOnConnect(ssl);	
            }

            email.setFrom(fromAadress);
            
            if (subjectPrefix.length() > 0) {
                email.setSubject(subjectPrefix + " " + subject);
            } else {
                email.setSubject(subject);
            }

            if (body.length() > 0) {
                email.setMsg(body);
            }

            for (String to : toAddresses) {
                email.addTo(to.trim());
            }

            email.send();
            LOGGER.log(Level.INFO, "Mail sent to: " + toAddresses);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error on sending email.", ex);
        }
    }



}
