package com.kalata.spring.login.SendEmail;


import com.kalata.spring.login.models.Utilisateurs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


    @Component
    public class EmailConstructor {

        @Autowired
        private Environment env;

        @Autowired
        private TemplateEngine templateEngine;

        public MimeMessagePreparator constructNewUserEmail(Utilisateurs user) {
            Context context = new Context();
            context.setVariable("user", user);
            String text = templateEngine.process("newUserEmailTemplate", context);
            MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                    email.setPriority(1);
                    email.setTo(user.getEmail());
                    email.setSubject("Bienvenue sur KALATA ");
                    email.setText(text, true);
                    email.setFrom(new InternetAddress(env.getProperty("support.email")));
                }
            };
            return messagePreparator;
        }

        public MimeMessagePreparator constructResetPasswordEmail(Utilisateurs user, String password) {
            Context context = new Context();
            context.setVariable("user", user);
            context.setVariable("password", password);
            String text = templateEngine.process("resetPasswordEmailTemplate", context);
            MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                    email.setPriority(1);
                    email.setTo(user.getEmail());
                    email.setSubject("New Password - Orchard");
                    email.setText(text, true);
                    email.setFrom(new InternetAddress(env.getProperty("support.email")));
                }
            };
            return messagePreparator;
        }

        public MimeMessagePreparator constructUpdateUserProfileEmail(Utilisateurs user) {
            Context context = new Context();
            context.setVariable("user", user);
            String text = templateEngine.process("updateUserProfileEmailTemplate", context);
            MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                    email.setPriority(1);
                    email.setTo(user.getEmail());
                    email.setSubject("Profile Update - Orchard");
                    email.setText(text, true);
                    email.setFrom(new InternetAddress(env.getProperty("support.email")));
                }
            };
            return messagePreparator;
        }
}
