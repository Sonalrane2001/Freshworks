package com.freshworks.project.freshworksapi.controller;

import java.security.Principal;
import java.util.List;
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
import com.freshworks.project.freshworksapi.Repository.HotelFoodRepository;
import com.freshworks.project.freshworksapi.classes.GovNgoData;
import com.freshworks.project.freshworksapi.classes.HotelFoodData;

@Controller
public class HotelFoodController {
	
	@Autowired
	public HotelFoodRepository hotelFoodRepository;
	@Autowired
	public GovNgoRepository govNgoRepository;
	
	@RequestMapping(value="/saveFoodInfo",method=RequestMethod.POST)
	public String saveHotelFoodInfo(@ModelAttribute HotelFoodData user,Principal p,HttpSession session)
	{
		String email=p.getName(); 
		GovNgoData j=govNgoRepository.findByEmail(email); //get the current hotel user
		user.setGovNgoData(j);//and for entity add the current hotel user
		hotelFoodRepository.save(user);//save the data into mysql table
		//System.out.println(user);
		
		return "redirect:/viewFoodList";//go to the view hotel food list
	}
	
	@RequestMapping(value="/edit_food_info",method=RequestMethod.POST)
	public String editHotelFoodInfo(@ModelAttribute HotelFoodData user,Principal p)
	{
		String email=p.getName();
		GovNgoData j=govNgoRepository.findByEmail(email);//get the current hotel user
		user.setGovNgoData(j);//set same into entity
		hotelFoodRepository.save(user);//edit info save into mysql database
		System.out.println(user);
		return "redirect:/viewFoodList";
	}		
										
	@RequestMapping(value="/viewFoodList",method=RequestMethod.GET)
	public String viewHotelFoodInfoList(Model m,Principal p)
	{
		String email=p.getName();
		GovNgoData j=govNgoRepository.findByEmail(email);//get current user
		
		int id=j.getId();//get current user ID
		List<HotelFoodData> list=hotelFoodRepository.findDataByUser(id);//get the all list which is created by current user Id
		m.addAttribute("list",list);//pass list to view_data.html page
		System.out.println(list);
		return "Hotel/view_data";//pass to the view_data.html page
	}
	
	
	
	@RequestMapping(value="/Hotel_edit_info/{id}",method=RequestMethod.GET)
	public String editHotelFoodInfo(@PathVariable int id,Model m) {
		
		Optional<HotelFoodData> n=hotelFoodRepository.findById(id);//get the hotel user from hotel user id
		if(n!=null)//if n not null 
		{
			HotelFoodData notes=n.get();//get the hotel user object
			m.addAttribute("notes",notes);//pass object to edit info and show data into edit_info page
		}
		return "Hotel/edit_info";
	}
	@RequestMapping(value="/Hotel_delete_info/{id}",method=RequestMethod.GET)
	public String deleteHotelFoodInfo(@PathVariable int id,HttpSession session)
	{
		Optional<HotelFoodData> notes=hotelFoodRepository.findById(id);//get the hotel user using id
		if(notes!=null)
		{
			hotelFoodRepository.delete(notes.get());//delete the hotel user
			
		}
		return "redirect:/viewFoodList";
	}
	
	
	
	@ModelAttribute
	public void addCommonData(Principal p,Model m)//principal for points to current user
	{
		String email=p.getName();
		GovNgoData user=govNgoRepository.findByEmail(email);
		m.addAttribute("user",user);
	}

	@RequestMapping(value="/viewHotelProfile",method=RequestMethod.GET)
	public String viewHotelProfile() {
		
		return "Hotel/view_profile";  //go to the view_profile
	}
	
	@RequestMapping(value="/updateHotelUser",method=RequestMethod.POST)
	public String updateHotelProfile(@ModelAttribute GovNgoData user,Model m,HttpSession session)
	{
		Optional<GovNgoData> Olduser=govNgoRepository.findById(user.getId());
		System.out.println(Olduser);
		if(Olduser!=null)
		{
			user.setPassword(Olduser.get().getPassword());
			//user.setRole(Olduser.get().getRole());
			//user.setEmail(Olduser.get().getEmail());
			user.setEmail(Olduser.get().getEmail());
			user.setRole(Olduser.get().getRole());
			user.setStatus(Olduser.get().getStatus());
			GovNgoData updateUser=govNgoRepository.save(user);
			if(updateUser!=null)
			{
				session.setAttribute("msg","User Update SuccessFully!!");
				m.addAttribute("user",updateUser);
				
			}
			else
			{
				session.setAttribute("msg","User Not Updated Successfully!!");
			}
			
		}
		
		
		return "redirect:/viewHotelProfile";
	}
	

}
