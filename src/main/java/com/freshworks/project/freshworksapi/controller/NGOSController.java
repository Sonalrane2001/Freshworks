package com.freshworks.project.freshworksapi.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freshworks.project.freshworksapi.Repository.GovNgoRepository;
import com.freshworks.project.freshworksapi.Repository.HotelFoodRepository;
import com.freshworks.project.freshworksapi.classes.GovNgoData;
import com.freshworks.project.freshworksapi.classes.HotelFoodData;
@Controller
@RequestMapping("/NGOS")
public class NGOSController {
	
	@Autowired
	public HotelFoodRepository hotelFoodRepository;
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
	public String viewAllHotelInfoIntoList(Model m)
	{
		
		List<HotelFoodData> list=new ArrayList<>();
		hotelFoodRepository.findAll().forEach(list::add);//get all hotel food info into the list
		m.addAttribute("list",list);//pass the list into the view_data.html page
		//System.out.println(list);
		return "NGOS/view_data";//go to the view_data.html page
	}
	
	
}
