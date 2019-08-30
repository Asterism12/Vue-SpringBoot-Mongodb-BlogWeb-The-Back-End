package com.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.example.beans.Avatar;
import com.example.result.ImgResult;
import com.example.result.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.beans.User;
import com.example.result.UserResult;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {
	@Autowired
	private MongoTemplate mongotemplate;
	public static  final String ROOT = "./avatar/";
	@CrossOrigin
	@GetMapping("/api/user")
	@ResponseBody
	//个人主页
	public UserResult usermain(@RequestParam(value="username")String username){
		System.out.println("展示个人主页 "+username);
		Query query=new Query();
		User ret=mongotemplate.findOne(query.addCriteria(Criteria.where("username").is(username)),User.class);
		UserResult userresult=new UserResult();
		userresult.setage(ret.getage());
		userresult.setRegistertime(ret.getRegistertime());
		userresult.setsex(ret.getsex());
		userresult.setsign(ret.getsign());
		userresult.setUsername(ret.getUsername());
		userresult.setblogs(ret.getBlogs());
		userresult.setAvatarurl(ret.getAvatarurl());
		return userresult;
	}

  	@CrossOrigin
	@PostMapping("/api/modifyinfo")
	@ResponseBody
	//修改个人信息
	public MessageResult modifyinfo(@RequestBody User requestUser){
		System.out.println("修改个人信息 "+requestUser.getUsername());
		String username=requestUser.getUsername();
		Query query=new Query();
		User ret=mongotemplate.findOne(query.addCriteria(Criteria.where("username").is(username)),User.class);
		if(ret==null) {
			return new MessageResult(400,"用户不存在");
		}
		else {
			ret.setage(requestUser.getage());
			ret.setsex(requestUser.getsex());
			ret.setsign(requestUser.getsign());
			mongotemplate.save(ret);
			return new MessageResult(200,"修改成功");
		}
	}


	@CrossOrigin
	@PostMapping("/api/modifyavatar")
	@ResponseBody
	//修改头像
	public ImgResult singleFileUpload1(@RequestParam(value="username") String username,@RequestParam(value="file",required=false) MultipartFile file){
		System.out.println("修改头像 ");
		System.out.println(username);
		System.out.println(file.getOriginalFilename());
		if (file==null || file.isEmpty()) {
			System.out.println("null");
			return new ImgResult(400,null);
		}
		System.out.println(username+" "+file.getOriginalFilename());
		try {
			byte[] bytes = file.getBytes();
			String root = ROOT + username+"/";
			Path path = Paths.get(root+file.getOriginalFilename());
			//如果没有files文件夹，则创建
			if (!Files.isWritable(path)) {
				Files.createDirectories(Paths.get(root));
			}
			//文件写入指定路径
			Files.write(path, bytes);
			Query query=new Query();
			User ret=mongotemplate.findOne(query.addCriteria(Criteria.where("username").is(username)),User.class);
			ret.setAvatarurl(path.toString());
			mongotemplate.save(ret);
			return new ImgResult(200,path.toString());
		} catch (IOException e) {
			e.printStackTrace();
			return new ImgResult(200,null);
		}
	}

	@CrossOrigin
	@GetMapping(value="api/userlists")
	@ResponseBody
	//搜索用户
	public List<UserResult> finduser(@RequestParam(value="keyword") String keyword) {
		System.out.println("搜索用户 "+keyword);
		Pattern pattern = Pattern.compile("^.*" + keyword +".*$",Pattern.CASE_INSENSITIVE);
		Query query = new Query(Criteria.where("username").regex(pattern));
		List<User> ret = mongotemplate.find(query, User.class);
		List<UserResult> userlist=new ArrayList<UserResult>();
		for(int i=0;i<ret.size();i++) {
			UserResult userresult=new UserResult();
			userresult.setage(ret.get(i).getage());
			userresult.setRegistertime(ret.get(i).getRegistertime());
			userresult.setsex(ret.get(i).getsex());
			userresult.setsign(ret.get(i).getsign());
			userresult.setUsername(ret.get(i).getUsername());
			userresult.setblogs(ret.get(i).getBlogs());
          userresult.setAvatarurl(ret.get(i).getAvatarurl());
			userlist.add(userresult);
		}
		return userlist;
	}
}
