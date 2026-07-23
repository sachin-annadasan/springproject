package com.example.springboot.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.entity.Movie;
import com.example.springboot.projection.MovieProjection;
import com.example.springboot.requestDto.MovieRequestDto;
import com.example.springboot.responseDto.MovieResponseDTO;
import com.example.springboot.service.MovieService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/movies")
public class MovieController {

	private MovieService movieservice;

	@GetMapping
	public ResponseEntity<List<MovieResponseDTO>> getAll() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(movieservice.gets());
	}

	@GetMapping("/sorted")
	public List<Movie> sorted(Sort sort) {
		return movieservice.get(sort);
	}

	@PostMapping("/add/movie")
	public Object addMovie(@Valid @RequestBody MovieRequestDto dto, BindingResult result) {

		if (result.hasErrors()) {

			Map<String, Object> response = new HashMap<>();

			response.put("message", "Validation Failed");

			response.put("errors", result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList());

			return ResponseEntity.badRequest().body(response);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(movieservice.addMovie(dto));
	}

	@PutMapping("/update/{id}")
	public Object update(@PathVariable int id, @Valid @RequestBody MovieRequestDto dto, BindingResult result) {

		return movieservice.update(id, dto, result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MovieResponseDTO> getById(@PathVariable int id) {

		return ResponseEntity.ok(movieservice.getById(id));
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<MovieResponseDTO>> getByName(@PathVariable String name) {

		return ResponseEntity.ok(movieservice.getByName(name));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable int id) {

		movieservice.delete(id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/rating")
	public ResponseEntity<List<MovieResponseDTO>> getMovieByRating(

			@RequestParam Integer ratings, @RequestParam Boolean ottavailable) {

		return ResponseEntity.ok(movieservice.getMovieByRating(ratings, ottavailable));
	}

	@GetMapping("/getratingsof/{ratings}")
	public ResponseEntity<List<MovieResponseDTO>> getMovieRating(@PathVariable Integer ratings) {

		return ResponseEntity.ok(movieservice.getMovieRating(ratings));
	}

	@GetMapping("/list")
	public ResponseEntity<List<MovieResponseDTO>> getFilter(

			@RequestParam(required = false) String name,

			@RequestParam(required = false) Integer ratings) {

		if (name != null && ratings != null) {
			return ResponseEntity.ok(movieservice.findByNameAndRating(name, ratings));
		}

		if (name != null) {
			return ResponseEntity.ok(movieservice.getByName(name));
		}

		if (ratings != null) {
			return ResponseEntity.ok(movieservice.getMovieRating(ratings));
		}

		return ResponseEntity.ok(movieservice.gets());
	}

	@GetMapping("/getPage")
	public List<MovieResponseDTO> getPage(@RequestParam int page, @RequestParam int size) {
		return movieservice.getPage(page, size);
	}

	@GetMapping("/getPage/sort")
	public List<MovieResponseDTO> getPageSort(@RequestParam int page, @RequestParam int size) {
		return movieservice.getPageSort(page, +size);
	}

	@GetMapping("/getPage/Pagable")
	public Page<MovieResponseDTO> getPagePagable(
			@PageableDefault(page = 0, size = 5, sort = "releasedate", direction = Sort.Direction.DESC) Pageable pageable) {
		return movieservice.findAllPagable(pageable);
	}

	@GetMapping("/page/filtering")
	public Page<MovieResponseDTO> getpagefilter(@RequestParam Boolean available, Pageable pageable) {
		return movieservice.findAllPagableFilters(available, pageable);
	}
	@GetMapping("/greater/{id}")
	public List<MovieResponseDTO> greater(@PathVariable("id") int ratings)
	{
		return movieservice.greater(ratings);
	}
	@PostMapping("/file")
	public String uploadfile(@RequestParam("file") MultipartFile file) throws IOException
	{
		String str=new String(file.getBytes());
	    return file.getOriginalFilename()+" "+str;
	}
	@PostMapping("/files")
	public String uploadxlfile(@RequestParam("file") MultipartFile file) throws Exception
	{
		 movieservice.uploadxlfile(file);
		 return null;
	}
	@PutMapping("/without/transactional/{id}")
	public MovieResponseDTO addget(@PathVariable int id)
	{
		return movieservice.addget(id);
	}
	@PutMapping("/with/transactional/{id}")
	public MovieResponseDTO addgets(@PathVariable int id)
	{
		return movieservice.addgets(id);
	}
	@GetMapping("/withoutreadonly/{id}")
	public MovieResponseDTO withoutreadonly (@PathVariable int id)
	{
		return movieservice. withoutreadonly(id);
	}
	@GetMapping("/withreadonly/{id}")
	public MovieResponseDTO withreadonly (@PathVariable int id)
	{
		return movieservice. withreadonly(id);
	}
	@PutMapping("/withrollback/{id}")
	public MovieResponseDTO addgetrollbacks(@PathVariable int id) throws Exception
	{
		return movieservice.ddgetrollbacks(id);
	}
	@PutMapping("/withoutrollback/{id}")
	public MovieResponseDTO addgetrollback(@PathVariable int id) throws Exception
	{
		return movieservice.addgetrollback(id);
	}
	@PutMapping("/withreqnew/{id}")
	public MovieResponseDTO addgetrollbackreqnew(@PathVariable int id) throws Exception
	{
		return movieservice.addgetrollbackreqnew(id);
	}
	@GetMapping("/spec/{name}")
	public List<MovieResponseDTO> getmoviebySpec(@PathVariable String name)
	{
		return movieservice.getmoviebySpec(name);
	}
	@GetMapping("/specfilter")
	public List<MovieResponseDTO> getmoviebySpec(@RequestParam(required = false) String name,
			@RequestParam(required = false) Boolean avai,@RequestParam(required = false) Integer ratings,
			Pageable page)
	{
		return movieservice. getMoviebyFilterSpec(name,ratings,avai,page);
	}
	@GetMapping("/specfilter/like")
	public List<MovieResponseDTO> getmoviebySpeclike(@RequestParam(required = false) String name)
	{
		return movieservice. getMoviebyFilterSpeclike(name);
	}
	@GetMapping("/specfilter/order")
	public List<MovieResponseDTO> getmoviebySpeclike()
	{
		return movieservice. getMoviebyFilterSpecOrder();
	}
	@GetMapping("/native/{id}")
	public List<MovieResponseDTO> getNative(@PathVariable int id)
	{
		return movieservice.getNative(id);
	}
	
	@GetMapping("/projection")
	public List<MovieProjection> getProjection()
	{
		return movieservice.findAll();
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//     @GetMapping("/hero")
//     public List<MovieResponseDTO> getMoviebyHero(@RequestParam(required = false) String heroname,
//    		 @RequestParam(required=false) String moviename )
//     {
//    	 return movieservice.getMovieByHero(heroname,moviename);
//     } 
//	


//	@GetMapping("/cache/{id}")
//    public Movie getMovie(@PathVariable Integer id) {
//        return movieservice.getMovieById(id);
//	}
//	    public void parent() {child();}
//	    @Transactional(propagation = Propagation.MANDATORY) //throw error because t1 doesnt exist
//	    public void child() {movieRepository.save(movie);}}
//	@Transactional(propagation = Propagation.SUPPORTS)If a transaction exists → Join it.
	//If no transaction exists → Run normally without a transaction.
//	@Transactional     
//	public void updateMovie() {
//	    movieRepository.save(movie);
//   printMessage();
//     movieRepository.save(movie2);}
//	@Transactional(propagation = Propagation.NOT_SUPPORTED)//susoend the transaction print no need to involve in the transaction so it wil not take part in the currebnt transaction
//	public void printMessage() { System.out.println("Movie updated successfully");}

//asyn 
	
}
//listing la constatnt
