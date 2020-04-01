package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.EmpDao;
import com.example.demo.vo.EmpVo;

@Controller
public class MailController {

	@Autowired
	private MailSender JavaMailSender;	
	public void setJavaMailSender(MailSender javaMailSender) {
		JavaMailSender = javaMailSender;
	}

	public void setDao(EmpDao dao) {
		this.dao = dao;
	}

	@Autowired
	private EmpDao dao;
	public EmpDao getDao() {
		return dao;
	}
	
	
	//@RequestMapping("/mail.do")
	@Scheduled(cron = "0 14 13 1 * ?")
	public String sendMail() {
		String str = "메일발송 실패ㅠ";
		
		List<EmpVo> list = dao.listAll();
		for(EmpVo ev : list) {
			String email = ev.getEmail();
			String name = ev.getEname();
			int pay = ev.getComm()+ev.getSal();
			
			SimpleMailMessage mailMSG = new SimpleMailMessage();
			mailMSG.setSubject(name + "님 고생하셨어요!");
			mailMSG.setFrom("88.hmuse@gmail.com");
			mailMSG.setText(name + "님의 이번달 급여는" + pay + "입니다.");
			mailMSG.setTo(email);
			try {
				JavaMailSender.send(mailMSG);
				System.out.println("메일보내기완료");
				str = "메일발송 성공!";
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("메일보내기 실패"+ e.getMessage());
			}
		}		
		return str;
	}
}
