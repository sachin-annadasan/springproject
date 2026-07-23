package com.example.springboot.service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.example.springboot.entity.Movie;
import com.example.springboot.exception.MovieNotfoundException;
import com.example.springboot.exception.RatingsNotfoundException;
import com.example.springboot.projection.MovieProjection;
import com.example.springboot.repository.HeroRepo;
import com.example.springboot.repository.MovieRepository;
import com.example.springboot.requestDto.MovieRequestDto;
import com.example.springboot.responseDto.MovieResponseDTO;
import com.example.springboot.spec.MovieSpecification;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MovieService {

	private final MovieRepository movierepository;
	private final ModelMapper modelMapper;
	private final EditService editService;
	private final HeroRepo heroRepo;

	public List<Movie> get(Sort sort) {
		 
		return movierepository.findAll(sort);
	}

	public List<MovieResponseDTO> gets() {
		
		List<Movie> movies = movierepository
				.findAll(Sort.by("ratings").ascending().and(Sort.by("releasedate").descending()));

		if (movies.isEmpty()) {
			throw new MovieNotfoundException("Movie table is empty");
		}

		return movies.stream().map(this::DtoConvert).toList();
	}

	public MovieResponseDTO addMovie(MovieRequestDto dto) {

		Movie movie = new Movie();

		movie.setName(dto.getName());
		movie.setOttavailable(dto.getOttavailable());
		movie.setRatings(dto.getRatings());
		movie.setReleasedate(dto.getReleasedate());

		movie = movierepository.save(movie);

		return DtoConvert(movie);
	}

	public MovieResponseDTO DtoConvert(Movie movie) {
		return modelMapper.map(movie, MovieResponseDTO.class);
	}

	public Object update(int id, MovieRequestDto dto, BindingResult result) {

		if (result.hasErrors()) {

			Map<String, Object> response = new HashMap<>();

			response.put("message", "Validation Failed");

			response.put("errors", result.getFieldErrors().stream().map(error -> error.getDefaultMessage()).toList());

			return response;
		}

		Optional<Movie> optionalMovie = movierepository.findById(id);

		if (optionalMovie.isEmpty()) {
			throw new MovieNotfoundException("Movie not found with id " + id);
		}

		Movie movie = optionalMovie.get();

		movie.setName(dto.getName());
		movie.setOttavailable(dto.getOttavailable());
		movie.setRatings(dto.getRatings());
		movie.setReleasedate(dto.getReleasedate());

		movie = movierepository.save(movie);

		return DtoConvert(movie);
	}
    @Cacheable(value="movies", key="#id" )
	public MovieResponseDTO getById(int id) {
       System.out.println("databasecaed");
		Movie movie = movierepository.findById(id)
				.orElseThrow(() -> new MovieNotfoundException("Movie not found with id " + id));
        
		MovieResponseDTO moviee= DtoConvert(movie);
		return moviee;
	}

	public List<MovieResponseDTO> getByName(String name) {

		System.out.println("databasecaed");
		List<Movie> movies = movierepository.findByName(name);

		if (movies.isEmpty()) {
			throw new MovieNotfoundException("Movie not found with name " + name);
		}

		return movies.stream().map(this::DtoConvert).toList();
	}

	public void delete(int id) {

		Movie movie = movierepository.findById(id)
				.orElseThrow(() -> new MovieNotfoundException("Movie not found with id " + id));

		movierepository.delete(movie);
	}

	public List<MovieResponseDTO> getMovieByRating(int ratings, Boolean ottavailable) {

		List<Movie> movies = movierepository.findByRatingsAndOttavailable(ratings, ottavailable);

		if (movies.isEmpty()) {
			throw new MovieNotfoundException("Movie not found");
		}

		return movies.stream().map(this::DtoConvert).toList();
	}

	public List<MovieResponseDTO> getMovieRating(int ratings) {

		List<Movie> movies = movierepository.findByRatings(ratings, Sort.by("name").ascending());

		if (movies.isEmpty()) {
			throw new RatingsNotfoundException("The ratings is not avaiable " + ratings);
		}

		return movies.stream()

				.map(this::DtoConvert).toList();
	}

	public List<MovieResponseDTO> findByNameAndRating(String name, int ratings) {

		List<Movie> movies = movierepository.findByNameAndRatings(name, ratings);

		if (movies.isEmpty()) {
			List<MovieResponseDTO> lisst = new ArrayList<>();
			return lisst;
		}

		return movies.stream().map(x -> DtoConvert(x)).toList();
	}

	public List<MovieResponseDTO> getPage(int page, int size) {

		PageRequest pages = PageRequest.of(page, size);
		List<MovieResponseDTO> list = movierepository.findAll(pages).stream().map(x -> DtoConvert(x)).toList();
		return list;

	}

	public List<MovieResponseDTO> getPageSort(int page, int size) {

		PageRequest pages = PageRequest.of(page, size,
				Sort.by("ratings").descending().and(Sort.by("name").ascending()));

//		  PageRequest pages=PageRequest.of(page, size,Sort.by("ratings").descending());
		List<MovieResponseDTO> list = movierepository.findAll(pages).stream().map(x -> DtoConvert(x)).toList();
		return list;

	}

	public Page<MovieResponseDTO> findAllPagable(Pageable pageable) {

		Page<MovieResponseDTO> pages = movierepository.findAll(pageable).map(this::DtoConvert);
		return pages;
	}

//	public Page<MovieResponseDTO> findAllPagableFilters(Integer id, Pageable pageable) {
//		Page<MovieResponseDTO> list=movierepository.findByRatings(id,pageable).stream()
//				.map(this::DtoConvert).toList();
//		return list;
//	}
	public Page<MovieResponseDTO> findAllPagableFilters(boolean availaible, Pageable pageabl) {
		return movierepository.findByOttavailable(availaible, pageabl).map(this::DtoConvert);

	}

	public List<MovieResponseDTO> greater(int ratings) {

		List<MovieResponseDTO> list = movierepository.findByRatingsGreaterThan(ratings).stream().map(x -> DtoConvert(x))
				.toList();

		return list;
	}

	public String uploadxlfile(MultipartFile file) throws Exception {

		InputStream stream = file.getInputStream();

		XSSFWorkbook workbook = new XSSFWorkbook(stream);

		XSSFSheet sheet = workbook.getSheetAt(0);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		for (Row row : sheet) {
			if (row.getRowNum() == 0) {
				continue;
			}
			Movie movie = new Movie();
			movie.setName(row.getCell(0).getStringCellValue());
			movie.setOttavailable(row.getCell(1).getBooleanCellValue());
			Cell dateCell = row.getCell(2);
			LocalDate releaseDate;
			if (dateCell.getCellType() == CellType.STRING) {
				releaseDate = LocalDate.parse(dateCell.getStringCellValue(), formatter);
			} else {

				releaseDate = dateCell.getLocalDateTimeCellValue().toLocalDate();

			}

			movie.setReleasedate(releaseDate);

			movie.setRatings((int) row.getCell(3).getNumericCellValue());

			movierepository.save(movie);
		}

		workbook.close();

		stream.close();

		System.out.println("File Size: " + file.getSize());

		return "Saved successfully";
	}

	public MovieResponseDTO addget(int id) {
		Optional<Movie> optionalMovie = movierepository.findById(id);
		Movie movie = null;
		if (optionalMovie.isPresent()) {
			movie = optionalMovie.get();
			movie.setName("TrueManShow");

		}
		movierepository.save(movie);
		throw new RuntimeException("updated in db");

	}

	@Transactional(propagation = Propagation.REQUIRED)
	public MovieResponseDTO addgets(int id) {
		Optional<Movie> optionalMovie = movierepository.findById(id);
		Movie movie = null;
		if (optionalMovie.isPresent()) {
			movie = optionalMovie.get();
			movie.setName("TrueManShow");

		}
		movierepository.save(movie);
		throw new RuntimeException("not updated in db");

	}

	@Transactional
	public MovieResponseDTO withoutreadonly(int id) {
		Optional<Movie> optionalMovie = movierepository.findById(id);
		Movie movie = null;
		if (optionalMovie.isPresent()) {
			movie = optionalMovie.get();
			movie.setName("TrueManShow");

		}
		movierepository.save(movie);
		return DtoConvert(movie);
	}

	@Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE) // locks one transaction then next
	public MovieResponseDTO withreadonly(int id) {
		Optional<Movie> optionalMovie = movierepository.findById(id);
		Movie movie = null;
		if (optionalMovie.isPresent()) {
			movie = optionalMovie.get();
			movie.setName("TrueManShow");

		}
		movierepository.save(movie);
		return DtoConvert(movie);

	}

	@Transactional
	public MovieResponseDTO addgetrollback(int id) throws Exception {
		Optional<Movie> optionalMovie = movierepository.findById(id);
		Movie movie = null;
		if (optionalMovie.isPresent()) {
			movie = optionalMovie.get();
			movie.setName("TrueManShow");

		}
		movierepository.save(movie);
		throw new Exception("not updated in db");
	}

//    @Transactional(noRollbackFor = IllegalArgumentException.class)//even exception occur no rollback happen
	@Transactional(rollbackFor = Exception.class)
	public MovieResponseDTO ddgetrollbacks(int id) throws Exception {
		Optional<Movie> optionalMovie = movierepository.findById(id);
		Movie movie = null;
		if (optionalMovie.isPresent()) {
			movie = optionalMovie.get();
			movie.setName("True");

		}
		movierepository.save(movie);
		throw new Exception("not updated in db");
	}

	@Transactional(timeout = 30, rollbackFor = Exception.class)
	public MovieResponseDTO addgetrollbackreqnew(int id) throws Exception {

		Movie movie = movierepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));

		movie.setName("ABCD");

		try {
			editService.editName(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		movierepository.save(movie);

		return null;
	}

	public List<MovieResponseDTO> getmoviebySpec(String name) {
		Specification<Movie> spec = MovieSpecification.hasName(name);
		return movierepository.findAll(spec).stream().map(x -> DtoConvert(x)).toList();
	}
    
	public List<MovieResponseDTO> getMoviebyFilterSpec(String name,Integer ratings,
			Boolean avai,Pageable page
	)
	{
		Specification<Movie> spec=Specification.allOf();
		if(name!=null && !name.isEmpty())
		{
			spec=spec.and(MovieSpecification.hasName(name));
		}
		if(ratings!=null)
		{
			spec=spec.and(MovieSpecification.hasRatings(ratings));
		}
		if(avai!=null)
		{
			spec=spec.and(MovieSpecification.hasOttAvailable(avai));
		}
		
		return movierepository.findAll(spec,page).stream().map(x->DtoConvert(x)).toList();
	}
	public List<MovieResponseDTO> getMoviebyFilterSpeclike(String name) {
		// TODO Auto-generated method stub
		Specification<Movie> spec=Specification.allOf();
		if(name!=null)
		{
			spec=spec.and(MovieSpecification.hasNameLike(name));
		}
		return movierepository.findAll(spec).stream().map(x->DtoConvert(x)).toList();

	}
	public List<MovieResponseDTO> getMoviebyFilterSpecOrder() {
		// TODO Auto-generated method stub
		Specification<Movie> spec=Specification.allOf();
			spec=spec.and(MovieSpecification.orderByRatingDesc());
		return movierepository.findAll(spec).stream().map(x->DtoConvert(x)).toList();

	}

	public List<MovieResponseDTO> getNative(int id) {
		
		
		return movierepository.getMovies(id).stream().map(this::DtoConvert).toList();
	}

	public List<MovieProjection> findAll() {
		
		return movierepository.findAllProjectedBy();
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public List<MovieResponseDTO> getMovieByHero(String heroname, String moviename) {
//	    List<Movie> movies;
//	    if (heroname != null && moviename != null) {
//	        Hero hero = heroRepo.findByName(heroname)
//	                .orElseThrow(() -> new RuntimeException("Hero not found"));
//	        movies = movierepository.findByHeroAndName(hero, moviename);
//	    } else if (heroname != null) {
//	        Hero hero = heroRepo.findByName(heroname)
//	                .orElseThrow(() -> new RuntimeException("Hero not found"));
//	        movies = movierepository.findByHero(hero);
//	    } else if (moviename != null) {
//	        movies = movierepository.findByName(moviename);
//	    } else {
//	        movies = movierepository.findAll();
//	    }
//	    return movies.stream()
//	            .map(this::DtoConvert)
//	            .toList();
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
//not working
