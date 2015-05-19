package com.sds.icto.guestbook.controller;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sds.icto.guestbook.dao.GuestBookDao;
import com.sds.icto.guestbook.vo.GuestBookVo;


@Controller
public class GuestBookController {
	
	@Autowired
	GuestBookDao guestBookDao;
	
	@RequestMapping("/index")
	public String index(Model model){
		List<GuestBookVo> list = guestBookDao.fetch();
		model.addAttribute("list",list);
		return "/views/show_guestBookList.jsp";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String insert(
			@RequestParam("name") String name,
			@RequestParam("pass") String pass,
			@RequestParam("content") String content
			){
		GuestBookVo vo = new GuestBookVo();
		vo.setName(name);
		vo.setPassword(pass);
		vo.setMessage(content);
		
		guestBookDao.guestBookInsert(vo);
		return "redirect:/index";
	}
	
	@RequestMapping("/deleteForm")
	public String deleteForm(
			@RequestParam("no") Long no
			){
		return "/views/deleteform.jsp?"+no;
	}

	
	@RequestMapping("/delete")
	public String delete(@ModelAttribute GuestBookVo vo,@RequestParam String no){
		
		try {
			guestBookDao.guestBookDelete(no, vo.getPassword());
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return "redirect:/index";
	}
//	
//	@RequestMapping(value= "/delete",method=RequestMethod.POST)
//	public String delete(
//			@RequestParam("name") String name,
//			@RequestParam("pass") String pass,
//			@RequestParam("content") String content
//			){
//		GuestBookVo vo = new GuestBookVo();
//		vo.setName(name);
//		vo.setPassword(pass);
//		vo.setMessage(content);
//		
//		guestBookDao.guestBookInsert(vo);
//		return "redirect:/index";
//	}
}
