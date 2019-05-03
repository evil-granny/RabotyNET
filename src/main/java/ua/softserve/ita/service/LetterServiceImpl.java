package ua.softserve.ita.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service("letterService")
public class LetterServiceImpl implements LetterService {

	@Autowired
	MailService mailService;

	@Override

	public void sendLetter(Object object) {
		mailService.sendEmail(object);
	}


}
