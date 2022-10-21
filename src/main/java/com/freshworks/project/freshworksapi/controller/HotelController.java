package com.freshworks.project.freshworksapi.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.freshworks.project.freshworksapi.Repository.GovNgoRepository;
import com.freshworks.project.freshworksapi.classes.GovNgoData;
@Controller
@RequestMapping("/Hotel")
public class HotelController {
	
	
	@Autowired
	public GovNgoRepository govNgoRepository;
	
	@ModelAttribute
	public void addCommonData(Principal p,Model m)//principal for points to current user
	{
		String email=p.getName();
		GovNgoData user=govNgoRepository.findByEmail(email);
		m.addAttribute("user",user);
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String afterHotel(Principal p)
	{
		String email=p.getName();
		GovNgoData user=govNgoRepository.findByEmail(email);//get current user by using email
		if(user.getStatus().compareTo("Decline")==0)  //if current user status is decline the not login and go to the not approve page
		{
			
			return "Hotel/notAprroved"; //go to the not approve page
		}
		else //if current user status is accept then it goes to add info page
		{
			return "Hotel/add_info"; //go to the add info page
		}
		
	}
	
	
	
	

}
