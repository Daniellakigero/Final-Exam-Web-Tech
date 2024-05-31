//package com.FinalExam.pharmacy;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//
//@SpringBootApplication
//public class PharmacyApplication {
//
//	@Autowired
//	private EmailSenderService senderService;
//
//	public static void main(String[] args) {
//		SpringApplication.run(PharmacyApplication.class, args);
//	}
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void sendMail((ApplicationReadyEvent event)){
//				// the email that will receive
////		senderService.sendEmail("daniellakigero@gmail.com", "Online Medecine Store", "Thanks for reaching out");
//		String email = "kevinayikson14@gmail.com";
//
//		senderService.sendEmail(email, "Online Medecine Store", "Thanks for reaching out");
//	}
//}
//package com.FinalExam.pharmacy;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//
//@SpringBootApplication
//public class PharmacyApplication {
//
//	@Autowired
//	private EmailSenderService senderService;
//
//	public static void main(String[] args) {
//		SpringApplication.run(PharmacyApplication.class, args);
//	}
//
//	@EventListener(ApplicationReadyEvent.class)
//	public void sendMail(ApplicationReadyEvent event) {
//		String email = "kevinayikson14@gmail.com"; // Default email or retrieve it from some source
//		senderService.sendEmail(email, "Online Medicine Store", "Thanks for reaching out");
//	}
//}
package com.FinalExam.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class PharmacyApplication {

	@Autowired
	private EmailSenderService senderService;

	public static void main(String[] args) {
		SpringApplication.run(PharmacyApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onApplicationReady(ApplicationReadyEvent event) {
		String email = "kevinayikson14@gmail.com"; // Default email or retrieve it from some source
		sendMail(email);
	}

	public void sendMail(String email) {
		senderService.sendEmail(email, "Welcome to Pharmacy Conseil!", "Dear Customer,\n" + //
						"Thank you for joining Pharmacy Conseil! We're excited to have you as a part of our community.\n" + //
						"Your account is now active, and you can log in using the email and password you provided during registration. Start exploring our wide range of medicines, healthcare products, and wellness essentials today.\n" + //
						"We prioritize your privacy and security, and all your data is protected with industry-standard encryption.\n" + //
						"Stay tuned for exclusive offers and promotions tailored to your preferences. Our friendly support team is always here to assist you.\n" + //
						"Welcome aboard! Here's to a healthier and happier life.\n" + //
						"Best regards,\n" + //
						"The Pharmacy Conseil Team");
	}
}
