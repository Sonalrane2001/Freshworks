package com.freshworks.project.freshworksapi.controller;

import java.security.Principal;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.freshworks.project.freshworksapi.Repository.GovNgoRepository;
import com.freshworks.project.freshworksapi.classes.GovNgoData;


@Controller
public class GovUserController {
	
	@Autowired
	public GovNgoRepository govNgoRepository;
	@ModelAttribute
	public void addCommonData(Principal p,Model m)//principal for points to current user
	{
		String email=p.getName();
		GovNgoData user=govNgoRepository.findByEmail(email);
		m.addAttribute("user",user);
	}

	@RequestMapping(value="/viewGovProfile",method=RequestMethod.GET)
	public String viewGoveProfile() {
		
		return "Govern/view_profile";  //view profile page
	}
	
	@RequestMapping(value="/updateGovUser",method=RequestMethod.POST)
	public String updateGovProfile(@ModelAttribute GovNgoData user,Model m,HttpSession session)
	{
		Optional<GovNgoData> Olduser=govNgoRepository.findById(user.getId());  //get old user by using current user id
		System.out.println(Olduser);
		if(Olduser!=null)
		{
			user.setPassword(Olduser.get().getPassword());  //set password same as oldUser
			//user.setRole(Olduser.get().getRole());
			//user.setEmail(Olduser.get().getEmail());
			user.setEmail(Olduser.get().getEmail());  //set email same as oldUser
			user.setRole(Olduser.get().getRole());  //set role same as oldUser
			user.setStatus(Olduser.get().getStatus());  //set status same as oldUser
			GovNgoData updateUser=govNgoRepository.save(user);  //and finally update the user  with new user info
			if(updateUser!=null)
			{
				
				session.setAttribute("msg","User Profile Updated SuccessFully!!");
				
				m.addAttribute("user",updateUser);//pass to view updated info to view profile
				
			}
			else
			{
				session.setAttribute("msg","User Profile Not Updated SuccessFully!!");
			}
			
		}
		
		
		return "redirect:/viewGovProfile";
	}
	
	
	
	
}
