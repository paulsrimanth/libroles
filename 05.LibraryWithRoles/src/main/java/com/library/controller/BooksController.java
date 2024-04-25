package com.library.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.library.model.AllUsers;
import com.library.model.Books;
import com.library.repository.AllUsersRepository;
import com.library.repository.BooksRepository;
import com.library.service.AuthenticationService;


@RestController
@RequestMapping("/book")
@CrossOrigin
public class BooksController {
	
	private AllUsersRepository alluserrepo;
	private AuthenticationService authService;
	private BooksRepository bookrepo;
	
	public BooksController(AllUsersRepository alluserrepo, AuthenticationService authService,
			BooksRepository bookrepo) {
		super();
		this.alluserrepo = alluserrepo;
		this.authService = authService;
		this.bookrepo = bookrepo;
	}
	
	@PostMapping("/addbook/{adminid}")
    public ResponseEntity<Books> register(@PathVariable Integer adminid,@RequestBody Books request) {
		AllUsers user = alluserrepo.findById(adminid).orElseThrow();
		System.out.println(user);
		Books book = new Books();
		book.setName(request.getName());
		book.setCategory(request.getCategory());
		book.setPublishyear(request.getPublishyear());
		book.setUsers(user);
		
//		AllUserser = new AllUsers();
//		user.setEmail(request.getEmail());
//		user.setPassword(passwordEncoder.encode(request.getPassword()));
//		user.setRoles(request.getRoles());
        return ResponseEntity.ok(bookrepo.save(book));
    }
	
	@PostMapping("/upload")
	public Books uploadImage(@RequestParam("imageofbook")MultipartFile imageofbook,
			@RequestParam("name")String name,
			@RequestParam("author")String author,
			@RequestParam("category") String category,
			@RequestParam("publishyear")String publishyear,
			@RequestParam("createdid") Integer user)throws  Exception
	{
//		@RequestHeader("Authorization")String jwt,
		Books book = new Books();
		book.setImageofbook((imageofbook.getBytes()));
		book.setName(name);
		book.setAuthor(author);
		book.setCategory(category);
		book.setPublishyear(publishyear);
		AllUsers createduser = alluserrepo.findById(user).orElseThrow();	
		book.setUsers(createduser);
//		Book i=new Book();
//		i.setImageofbook((imageofbook.getBytes()));
//		i.setTitleofbook(titleofbook);
//		i.setAuthor(author);
//		i.setPublishyear(publishyear);
//		Category category=categoryRepo.findByName(cname);
//		i.setCategory(category);
//
//		return bookrepo.save(i);
		return bookrepo.save(book);
	
		
	}
}
